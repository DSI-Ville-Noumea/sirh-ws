package nc.noumea.mairie.service.sirh;

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
	public byte[] getHistoContratForTiarhe() throws IOException, ParseException {
		List<HistoContratDto> dataList = getHistoContratsList();

		XSSFWorkbook wb = new XSSFWorkbook();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	    Sheet sheet1 = wb.createSheet("Histo contrats");
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    
	    // En-têtes
	    Row row = sheet1.createRow(0);
	    row.createCell(0).setCellValue("ID VDN");
	    row.createCell(1).setCellValue("ID TIARHE");
		row.createCell(2).setCellValue("Type contrat (contrat)");
		row.createCell(3).setCellValue("Type contrat (carrière)");
	    row.createCell(4).setCellValue("Date de début");
		row.createCell(5).setCellValue("Date de fin");
		row.createCell(6).setCellValue("Statut");
		row.createCell(7).setCellValue("Motif");
		row.createCell(8).setCellValue("Justification");
		row.createCell(9).setCellValue("IBAN");
		row.createCell(10).setCellValue("INM");
	    // Fin des en-têtes
	    
	    Integer i = 1;
	    
	    for (HistoContratDto entry : dataList) {
		    row = sheet1.createRow(i);
		    row.createCell(0).setCellValue(entry.getIdAgent().toString());
		    row.createCell(1).setCellValue(entry.getIdTiarhe());
			row.createCell(2).setCellValue(entry.getTypeContratContrat());
			row.createCell(3).setCellValue(entry.getTypeContratCarriere());
			row.createCell(4).setCellValue(sdf.format(entry.getDateDebut()));
			if (entry.getDateFin() != null) {
				if (entry.isDateFinMoinsUnJour())
					row.createCell(5).setCellValue(sdf.format(new DateTime(entry.getDateFin()).plusDays(-1).toDate()));
				else
					row.createCell(5).setCellValue(sdf.format(entry.getDateFin()));
			}
			row.createCell(6).setCellValue(entry.getStatut());
			row.createCell(7).setCellValue(entry.getMotif());
			row.createCell(8).setCellValue(entry.getJustification());
			row.createCell(9).setCellValue(entry.getIBAN());
			row.createCell(10).setCellValue(entry.getINM());
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
	
	private List<HistoContratDto> getHistoContratsList() throws ParseException {
		List<HistoContratDto> returnList = Lists.newArrayList();
		Date curseur;
		
	    HistoContratDto histo;
		
		// Pour chaque agent actif, on fait le traitement
		for (Integer idAgent : agentSrv.listIdAgentsActifsForTiahre()) {
			curseur = null;
			logger.debug("Traitement de l'agent ID " + idAgent);
			
			// Récupération des contrats et des carrières
			List<ContratDto> contrats = getAllContratsByAgentForTiarhe(idAgent);
			List<CarriereDto> carrieres = spcarrRepository.getAllCarrieresByAgentForTiarhe(idAgent);
			
			// Pour chaque contrat différent
			for (ContratDto contrat : contrats) {
				curseur = contrat.getDateDebutContrat();
				// Pour chaque carrière différente
				for (CarriereDto spcarr : carrieres) {
					
					if (!canWriteHistoContrat(contrat, spcarr, curseur))
						continue;

					histo = new HistoContratDto();
					histo.setJustification(contrat.getJustification());
					histo.setMotif(contrat.getMotif());
					histo.setTypeContratContrat(contrat.getTypeContrat());
					histo.setTypeContratCarriere(spcarr.getTypeContrat());
					histo.setIdAgent(idAgent);
					histo.setIdTiarhe(contrat.getIdTiarhe());
					histo.setIBAN(spcarr.getIBAN());
					histo.setINM(spcarr.getINM());
					histo.setStatut(spcarr.getLibelleCategorie());
					
					// Si la date de début du contrat est antérieure à celle de la carrière
					if (contrat.getDateDebutContrat().before(spcarr.getDateDebut())) {
						histo.setDateDebut(contrat.getDateDebutContrat());
					} else {
						histo.setDateDebut(spcarr.getDateDebut());
					}
					if (contrat.getDateFinContrat() != null && spcarr.getDateFin() != null && (contrat.getDateFinContrat().before(spcarr.getDateFin()) || 
							(contrat.getDateFinContrat() != null && contrat.getDateFinContrat().equals(spcarr.getDateFin())))) {
						histo.setDateFin(contrat.getDateFinContrat());
					} else {
						// Un jour doit être oté si la date de fin est celle de la carrière.
						histo.setDateFin(spcarr.getDateFin());
						histo.setDateFinMoinsUnJour(true);
					}
					
					if (histo.getDateDebut().before(curseur))
						histo.setDateDebut(curseur);
					
					// On replace le curseur sur la dernière période traitée
					curseur = histo.getDateFin();

					// On ajoute pas la ligne si la date de début est égal à la date de fin.
					if (histo.getDateFin() != null && !histo.getDateDebut().equals(histo.getDateFin()) || histo.getDateFin() == null)
						returnList.add(histo);
				}
			}
		}
		
		return returnList;
	}
	
	private boolean canWriteHistoContrat(ContratDto contrat, CarriereDto spcarr, Date curseur) {
		
		if (curseur.before(contrat.getDateDebutContrat()) || curseur.before(spcarr.getDateDebut()))
			return false;
		if (contrat.getDateFinContrat() != null && curseur.after(contrat.getDateFinContrat()))
			return false;
		if (spcarr.getDateFin() != null && curseur.after(spcarr.getDateFin()))
			return false;
		
		return true;
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

}
