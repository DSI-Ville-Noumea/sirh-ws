package nc.noumea.mairie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.web.dto.avancements.ArreteDto;
import nc.noumea.mairie.web.dto.avancements.ArreteListDto;
import nc.noumea.mairie.web.dto.avancements.AvancementItemDto;
import nc.noumea.mairie.web.dto.avancements.AvancementsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvancementsService implements IAvancementsService {

	private static List<Integer> StatutsTerritoriaux = Arrays.asList(18, 20);
	private static List<Integer> StatutsCommunaux = Arrays.asList(1, 2);
	
	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;
	
	@Autowired
	private IEaesService eaesService;
	
	@Override
	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		CommissionAvancementDto result = new CommissionAvancementDto();
		Cap cap = getCap(idCap);
		
		if (cap == null)
			return result;
		
		List<Spgeng> corps = getCorpsForCadreEmploi(idCadreEmploi);
		
		for (Spgeng corp : corps) {
			List<AvancementFonctionnaire> avcts = getAvancementsForCommission(getAnnee(), cap.getIdCap(), corp.getCdgeng(), getStatutFromCap(cap));
			
			if (avcts.size() == 0)
				continue;
			
			CommissionAvancementCorpsDto comCorps = createCommissionCorps(cap, corp, avcts);
			result.getCommissionsParCorps().add(comCorps);
		}
		
		return result;
	}
	
	@Override
	public List<String> getAvancementsEaesForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		List<String> result = null;
		Cap cap = getCap(idCap);
		
		if (cap == null)
			return new ArrayList<String>();
		
		List<Spgeng> corps = getCorpsForCadreEmploi(idCadreEmploi);
		List<Integer> agentsIds = new ArrayList<Integer>();
		
		for (Spgeng corp : corps) {
			List<Integer> agents = getAgentsIdsForCommission(getAnnee(), cap.getIdCap(), corp.getCdgeng(), getStatutFromCap(cap));
			agentsIds.addAll(agents);
		}
		
		result = eaesService.getEaesGedIdsForAgents(agentsIds, getAnnee());
		
		return result;
	}
	
	@Override
	public List<AvancementFonctionnaire> getAvancementsForCommission(int annee, int idCap, String corps, List<Integer> codesCategories) {
		
		List<AvancementFonctionnaire> result = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append("INNER JOIN avct.grade AS spgradn ");
		sb.append("INNER JOIN spgradn.gradeGenerique AS spgeng ");
		sb.append("INNER JOIN spgeng.caps AS ca ");
		sb.append("JOIN FETCH avct.agent ");
		sb.append("JOIN FETCH avct.avisCap ");
		sb.append("JOIN FETCH avct.grade ");
		sb.append("where (avct.etat = 'C' or avct.etat = 'F') ");
		sb.append("and avct.codeCategporie IN (:codesCategories) ");
		sb.append("and avct.idModifAvancement IN (5, 7) ");
		sb.append("and avct.anneeAvancement = :annee ");
		sb.append("and ca.idCap = :idCap ");
		sb.append("and spgeng.cdgeng = :cdgeng");
		
		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(sb.toString(), AvancementFonctionnaire.class);
		qA.setParameter("codesCategories", codesCategories);
		qA.setParameter("annee", annee);
		qA.setParameter("idCap", idCap);
		qA.setParameter("cdgeng", corps);
		
		result = qA.getResultList();
		
		return result;
	}
	
	public List<Integer> getAgentsIdsForCommission(int annee, int idCap, String corps, List<Integer> codesCategories) {
		
		List<Integer> result = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct.agent.idAgent FROM AvancementFonctionnaire avct ");
		sb.append("INNER JOIN avct.grade AS spgradn ");
		sb.append("INNER JOIN spgradn.gradeGenerique AS spgeng ");
		sb.append("INNER JOIN spgeng.caps AS ca ");
		sb.append("where (avct.etat = 'C' or avct.etat = 'F') ");
		sb.append("and avct.codeCategporie IN (:codesCategories) ");
		sb.append("and avct.idModifAvancement IN (5, 7) ");
		sb.append("and avct.anneeAvancement = :annee ");
		sb.append("and ca.idCap = :idCap ");
		sb.append("and spgeng.cdgeng = :cdgeng");
		
		TypedQuery<Integer> qA = sirhEntityManager.createQuery(sb.toString(), Integer.class);
		qA.setParameter("codesCategories", codesCategories);
		qA.setParameter("annee", annee);
		qA.setParameter("idCap", idCap);
		qA.setParameter("cdgeng", corps);
		
		result = qA.getResultList();
		
		return result;
	}
	
	@Override
	public List<Spgeng> getCorpsForCadreEmploi(int idCadreEmploi) {

		List<Spgeng> result = null;

		TypedQuery<Spgeng> qCorps = sirhEntityManager.createNamedQuery(
				"getSpgengFromCadreEmploi", Spgeng.class);
		qCorps.setParameter("idCadreEmploi", idCadreEmploi);
		result = qCorps.getResultList();

		if (result.size() == 0)
			result = null;
		
		return result;
	}

	public CommissionAvancementCorpsDto createCommissionCorps(Cap cap, Spgeng spgeng, List<AvancementFonctionnaire> avancements) {
		
		CommissionAvancementCorpsDto result = new CommissionAvancementCorpsDto(spgeng);
		result.setAvancementsDifferencies(new AvancementsDto(cap, spgeng, getAnnee()));
		result.setChangementClasses(new AvancementsDto(cap, spgeng, getAnnee()));
		
		for (AvancementFonctionnaire avct : avancements) {
			
			AvancementItemDto aItem = new AvancementItemDto(avct);
			
			switch(avct.getIdModifAvancement()) {
			
				case 7:
					result.getAvancementsDifferencies().getAvancementsItems().add(aItem);
					break;
				
				case 5:
					result.getChangementClasses().getAvancementsItems().add(aItem);
					break;
			}
		}
		
		result.getAvancementsDifferencies().updateNbAgents();
		result.getChangementClasses().updateNbAgents();
		
		return result;
	}

	public static List<Integer> getStatutFromCap(Cap cap) {
		
		if (cap.getTypeCap().equals("COMMUNAL"))
			return StatutsCommunaux;
		else 
			return StatutsTerritoriaux;
	}

	@Override
	public Cap getCap(int idCap) {
		
		TypedQuery<Cap> qCap = sirhEntityManager.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class);
		qCap.setParameter("idCap", idCap);
		List<Cap> qR = qCap.getResultList();
		
		if (qR.size() == 0)
			return null;
		
		return qR.get(0);
	}
	
	@Override
	public ArreteListDto getArretesForUsers(String csvIdAgents, boolean isChangmentClasse) {
		
		ArreteListDto arretes = new ArreteListDto();
		
		String[] agentIds = csvIdAgents.split(",");
		int i = 1;
		
		for (String agentId : agentIds) {
			ArreteDto dto = new ArreteDto();
			dto.setNomComplet(String.valueOf(i++));
			arretes.getArretes().add(dto);
		}
		
//		ArreteDto dto = new ArreteDto();
//		dto.setChangementClasse(isChangmentClasse);
//		dto.setAnnee(getAnnee());
//		dto.setNomComplet("Monsieur Pascal WOLF");
//		dto.setRegularisation(true);
//		dto.setDeliberationLabel("n°65/CP du 17 novembre 2008");
//		dto.setDeliberationCapText("statut particulier des cadres d'emplois de la filière incendie des communes de Nouvelle-Calédonie et de leurs établissement publics");
//		dto.setNumeroArrete("2011/1843");
//		dto.setDateArrete(new DateTime(2011, 05, 31, 0, 0, 0).toDate());
//		dto.setDateCap(new DateTime(2012, 06, 7, 0, 0, 0).toDate());
//		dto.setDateAvct(new DateTime(2012, 2, 20, 0, 0, 0).toDate());
//		dto.setDureeAvct("moyenne");
//		dto.setGradeLabel("sergent 2ème échélon de la filière incendie");
//		dto.setIna("281");
//		dto.setIb("342");
//		dto.setAcc("épuisé");
//		dto.setDirectionAgent("DSIS");
//		dto.setFeminin(false);
//		arretes.getArretes().add(dto);
//		
//		dto = new ArreteDto();
//		dto.setChangementClasse(isChangmentClasse);
//		dto.setAnnee(getAnnee());
//		dto.setNomComplet("Madame Henriette BULOT épouse TATA");
//		dto.setRegularisation(false);
//		dto.setDeliberationLabel("n°65/CP du 17 novembre 2008");
//		dto.setDeliberationCapText("statut particulier des cadres d'emplois de la filière incendie des communes de Nouvelle-Calédonie et de leurs établissement publics");
//		dto.setNumeroArrete("2011/1843");
//		dto.setDateArrete(null);
//		dto.setDateCap(new DateTime(2012, 06, 7, 0, 0, 0).toDate());
//		dto.setDateAvct(new DateTime(2012, 2, 20, 0, 0, 0).toDate());
//		dto.setDureeAvct("maximum");
//		dto.setGradeLabel("sergent 2ème échélon de la filière incendie");
//		dto.setIna("281");
//		dto.setIb("342");
//		dto.setAcc("épuisé");
//		dto.setDirectionAgent("DSIS");
//		dto.setFeminin(true);
//		arretes.getArretes().add(dto);
		
		return arretes;
	}
	
	public static int getAnnee() {
		return new DateTime().getYear();
	}
}
