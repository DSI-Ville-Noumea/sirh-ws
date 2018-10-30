package nc.noumea.mairie.service.sirh;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.web.dto.CarriereDto;
import nc.noumea.mairie.web.dto.ContratDto;
import nc.noumea.mairie.web.dto.HistoContratDto;

@Service
public class ContratService implements IContratService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private ISpcarrRepository spcarrRepository;

	private Logger logger = LoggerFactory.getLogger(ContratService.class);

	@Override
	public Contrat getContratBetweenDate(Integer idAgent, Date dateRecherche) {

		Contrat res = null;
		TypedQuery<Contrat> query = sirhEntityManager
				.createQuery(
						"select c from Contrat c where c.agent.idAgent=:idAgent "
								+ " and (c.dateDebutContrat <=:dateDeb and (c.dateFinContrat >:dateDeb or c.dateFinContrat is null))",
						Contrat.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateDeb", dateRecherche);

		List<Contrat> listeContrat = query.getResultList();
		if (listeContrat != null && listeContrat.size() > 0) {
			res = listeContrat.get(0);
		}

		return res;
	}
	
	@Override 
	public boolean isPeriodeEssai(Integer idAgent, Date dateRecherche) {
		
		Contrat contrat = getContratBetweenDate(idAgent, dateRecherche);
		
		if(null == contrat)
			return false;
		
		if(contrat.getDateFinPeriodeEssai().after(dateRecherche)) {
			return true;
		}
		
		return false;
	}

	@Override
	public Integer getNbJoursPeriodeEssai(Date dateDebutContrat, Date dateFinPeriodeEssai) {

		if (dateDebutContrat == null || dateFinPeriodeEssai == null)
			return null;

		long aTimeUneDate = dateDebutContrat.getTime();
		long aTimeAutreDate = dateFinPeriodeEssai.getTime();

		return (int) ((aTimeAutreDate - aTimeUneDate) / 86400000);
	}

	@Override
	public Contrat getContratById(Integer idContrat) {
		logger.debug("Entrée fonction getContratById with id = {}", idContrat);

		Contrat res = null;
		TypedQuery<Contrat> query = sirhEntityManager.createQuery(
				"select c from Contrat c where c.idContrat=:idContrat", Contrat.class);
		query.setParameter("idContrat", idContrat);

		List<Contrat> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}
	
	@Override
	public byte[] getHistoContratForTiarhe(boolean isFonctionnaire) throws IOException, ParseException {
		List<HistoContratDto> dataList = getHistoContratsList(isFonctionnaire);

		XSSFWorkbook wb = new XSSFWorkbook();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Sheet sheet1 = wb.createSheet("Histo contrats");
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    
	    // En-têtes
	    Row row = sheet1.createRow(0);
	    row.createCell(0).setCellValue("ID VDN");
	    row.createCell(1).setCellValue("ID TIARHE");
		row.createCell(2).setCellValue("Type contrat");
	    row.createCell(3).setCellValue("Date de début");
		row.createCell(4).setCellValue("Date de fin");
		row.createCell(5).setCellValue("Statut");
		row.createCell(6).setCellValue("Motif");
		row.createCell(7).setCellValue("Justification");
		row.createCell(8).setCellValue("IBAN");
		row.createCell(9).setCellValue("INM");
		row.createCell(10).setCellValue("Grade");
	    // Fin des en-têtes
	    
	    Integer i = 1;
	    
	    for (HistoContratDto entry : dataList) {
		    row = sheet1.createRow(i);
		    row.createCell(0).setCellValue(entry.getIdAgent().toString());
		    row.createCell(1).setCellValue(entry.getIdTiarhe());
			row.createCell(2).setCellValue(entry.getTypeContrat());
			row.createCell(3).setCellValue(sdf.format(entry.getDateDebut()));
			if (entry.getDateFin() != null) {
				if (entry.isDateFinMoinsUnJour())
					row.createCell(4).setCellValue(sdf.format(new DateTime(entry.getDateFin()).plusDays(-1).toDate()));
				else
					row.createCell(4).setCellValue(sdf.format(entry.getDateFin()));
			}
			row.createCell(5).setCellValue(entry.getStatut());
			row.createCell(6).setCellValue(entry.getMotif());
			row.createCell(7).setCellValue(entry.getJustification());
			row.createCell(8).setCellValue(entry.getIBAN());
			row.createCell(9).setCellValue(entry.getINM());
			row.createCell(10).setCellValue(entry.getCodeGrade());
		    ++i;
		}
	    
	    try {
	        wb.write(bos);
	    } finally {
	        bos.close();
	    }

	    // Write the output to a file
	    return bos.toByteArray();
	}
	
	private List<HistoContratDto> getHistoContratsList(boolean isFonctionnaire) throws ParseException {
		List<HistoContratDto> returnList = Lists.newArrayList();
		Date curseur;
		
	    HistoContratDto histo;
		
		// Pour chaque agent actif, on fait le traitement
		for (Integer idAgent : agentSrv.listIdAgentsActifsForTiahre(isFonctionnaire)) {
			logger.debug("Traitement de l'agent ID " + idAgent);
			
			// Récupération des contrats et des carrières
			ContratDto contratUnique;
			List<CarriereDto> carrieres = spcarrRepository.getAllCarrieresByAgentForTiarhe(idAgent, isFonctionnaire);
			
			// Pour chaque contrat différent
			for (CarriereDto spcarr : carrieres) {
				histo = new HistoContratDto();

				histo.setIdAgent(idAgent);
				histo.setIdTiarhe(spcarr.getIdTiarhe());
				histo.setDateDebut(spcarr.getDateDebut());
				histo.setDateFin(spcarr.getDateFin());
				histo.setDateFinMoinsUnJour(true);
				histo.setStatut(spcarr.getLibelleCategorie());
				histo.setIBAN(spcarr.getIBAN());
				histo.setINM(spcarr.getINM());
				histo.setCodeGrade(spcarr.getGrade().getCodeGrade());
				
				List<ContratDto> listContratsSurCettePeriode = getContratsOnPeriodByAgentForTiarhe(spcarr.getDateDebut(), spcarr.getDateFin(), idAgent);
				
				if (listContratsSurCettePeriode.size() == 0) {
					returnList.add(histo);
				}
				
				// ===========  1 seul contrat sur la période ========== //
				else if (listContratsSurCettePeriode.size() == 1) {
					contratUnique = listContratsSurCettePeriode.get(0);
					
					// Si la date de début du contrat est après la date de début de la carrière, alors il faut créer le petit interval entre les deux
					if (contratUnique.getDateDebutContrat().after(spcarr.getDateDebut())) {
						histo.setDateFin(contratUnique.getDateDebutContrat());
						histo.setDateFinMoinsUnJour(true);
						returnList.add(histo);
						histo = new HistoContratDto(histo);
						// Puis si la date de fin du contrat est inférieure à celle de la carrière, alors il faut créer 2 nouveaux intervals.
						if (contratUnique.getDateFinContrat() != null && contratUnique.getDateFinContrat().before(spcarr.getDateFin())) {
							histo.setJustification(contratUnique.getJustification());
							histo.setMotif(contratUnique.getMotif());
							histo.setTypeContrat(contratUnique.getTypeContrat());
							histo.setDateDebut(contratUnique.getDateDebutContrat());
							histo.setDateFin(contratUnique.getDateFinContrat());
							histo.setDateFinMoinsUnJour(false);
							returnList.add(histo);
							histo = new HistoContratDto(histo);
							
							histo.setJustification(null);
							histo.setMotif(null);
							histo.setTypeContrat(null);
							histo.setDateDebut(new DateTime(contratUnique.getDateFinContrat()).plusDays(1).toDate());
							histo.setDateFin(spcarr.getDateFin());
							histo.setDateFinMoinsUnJour(true);
							returnList.add(histo);
							histo = new HistoContratDto(histo);
							
						// Sinon, on créé le dernier.
						} else {
							histo.setJustification(contratUnique.getJustification());
							histo.setMotif(contratUnique.getMotif());
							histo.setTypeContrat(contratUnique.getTypeContrat());
							histo.setDateDebut(contratUnique.getDateDebutContrat());
							histo.setDateFin(spcarr.getDateFin());
							histo.setDateFinMoinsUnJour(true);

							returnList.add(histo);
							histo = new HistoContratDto(histo);
						}
					// Si la date de début du contrat est antérieure ou égale à la date de début de la carrière
					} else {
						// Si la date de fin du contrat est inférieure à la date de fin de la carrière, alors il faut créer 2 intervals
						if (contratUnique.getDateFinContrat() != null && (spcarr.getDateFin() == null || contratUnique.getDateFinContrat().before(spcarr.getDateFin()))) {
							histo.setJustification(contratUnique.getJustification());
							histo.setMotif(contratUnique.getMotif());
							histo.setTypeContrat(contratUnique.getTypeContrat());
							histo.setDateDebut(spcarr.getDateDebut());
							histo.setDateFin(contratUnique.getDateFinContrat());
							histo.setDateFinMoinsUnJour(false);
							returnList.add(histo);
							histo = new HistoContratDto(histo);

							// Il ne faut pas renseigner l'historique si on est sur le même jour.
							if (!new DateTime(contratUnique.getDateFinContrat()).plusDays(1).toDate().equals(spcarr.getDateFin())) {
								histo.setJustification(null);
								histo.setMotif(null);
								histo.setTypeContrat(null);
								if (contratUnique.getDateFinContrat().equals(spcarr.getDateDebut()))
									histo.setDateDebut(contratUnique.getDateFinContrat());
								else
									histo.setDateDebut(new DateTime(contratUnique.getDateFinContrat()).plusDays(1).toDate());
								histo.setDateFin(spcarr.getDateFin());
								histo.setDateFinMoinsUnJour(true);
								returnList.add(histo);
								histo = new HistoContratDto(histo);
							}
						// Sinon, alors toute la carrière peut prendre les attributs du contrat
						} else {
							histo.setJustification(contratUnique.getJustification());
							histo.setMotif(contratUnique.getMotif());
							histo.setTypeContrat(contratUnique.getTypeContrat());
							histo.setDateFin(spcarr.getDateFin());
							histo.setDateFinMoinsUnJour(true);
							returnList.add(histo);
							histo = new HistoContratDto(histo);
						}
					}
					
				// ===========  plusieurs contrats sur la période ========== //
				} else {
					curseur = spcarr.getDateDebut();
					for (int i = 0 ; i < listContratsSurCettePeriode.size() ; ++i) {
						ContratDto contrat = listContratsSurCettePeriode.get(i);
						
						// Il faut traiter les carrières sans contrats entre les deux (matr. 4877, 31/12/11 -> 06/02/12)
						if (curseur != null && curseur != spcarr.getDateDebut() && curseur.before(contrat.getDateDebutContrat())) {
							histo.setDateDebut(curseur);
							histo.setJustification(null);
							histo.setMotif(null);
							histo.setTypeContrat(null);
							histo.setDateFin(contrat.getDateDebutContrat());
							histo.setDateFinMoinsUnJour(true);
							returnList.add(histo);
							curseur = contrat.getDateDebutContrat();
							histo = new HistoContratDto(histo);
						}
						
						// Si le contrat commence avant ou à la date de la carrière
						if (!contrat.getDateDebutContrat().after(spcarr.getDateDebut())) {
							histo.setDateDebut(curseur);
							histo.setJustification(contrat.getJustification());
							histo.setMotif(contrat.getMotif());
							histo.setTypeContrat(contrat.getTypeContrat());
							histo.setDateFin(contrat.getDateFinContrat());
							histo.setDateFinMoinsUnJour(false);
							returnList.add(histo);
							histo = new HistoContratDto(histo);
							curseur = new DateTime(contrat.getDateFinContrat()).plusDays(1).toDate();
						} else {
							// Si la date de fin du contrat est inférieure à la date de fin de la carrière, alors il faut créer 2 intervals
							if (contrat.getDateFinContrat() != null && (spcarr.getDateFin() == null || contrat.getDateFinContrat().before(spcarr.getDateFin()))) {
								histo.setJustification(contrat.getJustification());
								histo.setMotif(contrat.getMotif());
								histo.setTypeContrat(contrat.getTypeContrat());
								histo.setDateDebut(new DateTime(curseur).toDate());
								histo.setDateFin(contrat.getDateFinContrat());
								histo.setDateFinMoinsUnJour(false);
								returnList.add(histo);
								histo = new HistoContratDto(histo);
								curseur = new DateTime(contrat.getDateFinContrat()).plusDays(1).toDate();

								// Il ne faut pas renseigner l'historique si on est sur le même jour, ni si ce n'est pas le dernier contrat sur la période
								if (!new DateTime(contrat.getDateFinContrat()).plusDays(1).toDate().equals(spcarr.getDateFin()) && i == listContratsSurCettePeriode.size()-1) {
									histo.setJustification(null);
									histo.setMotif(null);
									histo.setTypeContrat(null);
									if (contrat.getDateFinContrat().equals(spcarr.getDateDebut()))
										histo.setDateDebut(contrat.getDateFinContrat());
									else
										histo.setDateDebut(new DateTime(contrat.getDateFinContrat()).plusDays(1).toDate());
									histo.setDateFin(spcarr.getDateFin());
									histo.setDateFinMoinsUnJour(true);
									returnList.add(histo);
									histo = new HistoContratDto(histo);
									curseur = spcarr.getDateFin();
								}
							// Sinon, alors toute la carrière peut prendre les attributs du contrat
							} else {
								histo.setJustification(contrat.getJustification());
								histo.setMotif(contrat.getMotif());
								histo.setTypeContrat(contrat.getTypeContrat());
								histo.setDateDebut(contrat.getDateDebutContrat());
								histo.setDateFin(spcarr.getDateFin());
								histo.setDateFinMoinsUnJour(true);
								returnList.add(histo);
								histo = new HistoContratDto(histo);
								curseur = spcarr.getDateFin();
							}
						}
					}
					logger.debug("Plusieurs contrats pour l'agent {}, avec la carriere du {} au {}", idAgent, spcarr.getDateDebut(), spcarr.getDateFin());
				}
			}
		}
		
		for (int i = 0; i < returnList.size() ; ++i) {
			if (returnList.get(i).getDateFin() != null) {
				// Si un contrat possède une date de fin incrémentée de 1 jour, alors on se retrouve dans cette situation.
				if (!returnList.get(i).getDateFin().after(returnList.get(i).getDateDebut())) {
					logger.debug("ATTENTION !! La date de fin est inférieure à la date de début pour l'agent {}", returnList.get(i).getIdAgent());
					returnList.remove(i);
					--i;
					continue;
				}
			}
			if (i != returnList.size()-1 && returnList.get(i).equals(returnList.get(i+1))) {
				returnList.get(i).setDateFin(returnList.get(i+1).getDateFin());
				returnList.remove(i+1);
				--i;
			}
		}
		
		return returnList;
	}

	@Override
	public List<ContratDto> getAllContratsByAgentForTiarhe(Integer idAgent) {
		StringBuilder sb = new StringBuilder();

		sb.append("select c.id_agent, max(a.id_tiarhe), max(t.lib_type_contrat), c.justification, max(m.lib_motif), min(c.datdeb), max(c.date_fin) ");
		sb.append("from Contrat c, r_type_contrat t, r_motif m, agent a ");
		sb.append("where c.id_agent = :idAgent AND t.id_type_contrat = c.id_type_contrat AND m.id_motif = c.id_motif AND c.id_agent = a.id_agent ");
		sb.append("GROUP BY c.id_agent, c.justification, c.id_motif, c.id_type_contrat ");
		sb.append("order by min(c.datdeb)");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		query.setParameter("idAgent", idAgent);

		List<ContratDto> returnList = Lists.newArrayList();
		ContratDto newContrat;
		
		for (Object[] o : (List<Object[]>)query.getResultList()) {
			newContrat = new ContratDto();
			newContrat.setIdAgent((Integer)o[0]);
			newContrat.setIdTiarhe((String)o[1]);
			newContrat.setTypeContrat((String)o[2]);
			newContrat.setJustification((String)o[3]);
			newContrat.setMotif((String)o[4]);
			newContrat.setDateDebutContrat((Date)o[5]);
			newContrat.setDateFinContrat((Date)o[6]);
			
			returnList.add(newContrat);
		}
		
		return returnList;
	}

	private List<ContratDto> getContratsOnPeriodByAgentForTiarhe(Date dateDebut, Date dateFin, Integer idAgent) {
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT c.id_agent, t.lib_type_contrat, c.justification, m.lib_motif, c.datdeb, c.date_fin ");
		sb.append("FROM contrat c, r_type_contrat t, r_motif m ");
		sb.append("WHERE c.id_agent = :idAgent AND t.id_type_contrat = c.id_type_contrat AND m.id_motif = c.id_motif ");
		if (dateFin != null)
			sb.append("AND c.datdeb <= :dateFin ");
		sb.append("AND (c.date_fin is null OR c.date_fin >= :dateDebut) ");
		sb.append("ORDER BY c.datdeb");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		query.setParameter("idAgent", idAgent);
		query.setParameter("dateDebut", dateDebut);
		// Il faut enlever un jour à la date de fin de la carrière pour avoir la même date de fin que les contrats.
		if (dateFin != null) {
			query.setParameter("dateFin", new DateTime(dateFin).plusDays(-1).toDate());
		}
			
		List<ContratDto> returnList = Lists.newArrayList();
		ContratDto newContrat;
		
		for (Object[] o : (List<Object[]>)query.getResultList()) {
			newContrat = new ContratDto();
			newContrat.setIdAgent((Integer)o[0]);
			newContrat.setTypeContrat((String)o[1]);
			newContrat.setJustification((String)o[2]);
			newContrat.setMotif((String)o[3]);
			newContrat.setDateDebutContrat((Date)o[4]);
			newContrat.setDateFinContrat((Date)o[5]);
			
			returnList.add(newContrat);
		}
		
		return returnList;
	}

}
