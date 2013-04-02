package nc.noumea.mairie.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.service.IFichePosteService;
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
	
	@Autowired
	private IFichePosteService fichePosteService;

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

		TypedQuery<Spgeng> qCorps = sirhEntityManager.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class);
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

			switch (avct.getIdModifAvancement()) {

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
	public ArreteListDto getArretesForUsers(String csvIdAgents, boolean isChangmentClasse, int year) throws ParseException {

		List<Integer> agentIds = new ArrayList<Integer>();

		for (String id : csvIdAgents.split(",")) {
			agentIds.add(Integer.valueOf(id));
		}

		// requete
		List<AvancementFonctionnaire> avcts = getAvancementsForArretes(agentIds, year);

		ArreteListDto arretes = new ArreteListDto();

		for (AvancementFonctionnaire avct : avcts) {

			Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(avct.getAgent().getIdAgent(), new DateTime().toDate());			
			FichePoste fp = fichePosteService.getFichePosteById(fpId);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			int dateFormatMairie = Integer.valueOf(sdf.format(new DateTime().toDate()));
			TypedQuery<Spcarr> qCarr = sirhEntityManager.createNamedQuery("getCurrentCarriere", Spcarr.class);
			qCarr.setParameter("nomatr", avct.getAgent().getNomatr());
			qCarr.setParameter("todayFormatMairie", dateFormatMairie);
			Spcarr carr = qCarr.getSingleResult();

			ArreteDto dto = new ArreteDto(avct, fp, carr);
			arretes.getArretes().add(dto);
		}

		return arretes;
	}

	@Override
	public List<AvancementFonctionnaire> getAvancementsForArretes(List<Integer> agentIds, int year) {

		List<AvancementFonctionnaire> result = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT avct FROM AvancementFonctionnaire avct ");
		sb.append("JOIN FETCH avct.agent ag ");
		sb.append("JOIN FETCH avct.gradeNouveau gr ");
		sb.append("JOIN FETCH gr.gradeGenerique gn ");
		sb.append("JOIN FETCH gn.filiere ");
		sb.append("JOIN FETCH gn.deliberationCommunale ");
		sb.append("JOIN FETCH gr.barem ");
		sb.append("where avct.anneeAvancement = :year ");
		sb.append("and ag.idAgent IN (:agentIds) ");

		TypedQuery<AvancementFonctionnaire> qA = sirhEntityManager.createQuery(sb.toString(), AvancementFonctionnaire.class);
		qA.setParameter("agentIds", agentIds);
		qA.setParameter("year", year);

		result = qA.getResultList();

		return result;
	}

	public static int getAnnee() {
		return new DateTime().getYear();
	}
}
