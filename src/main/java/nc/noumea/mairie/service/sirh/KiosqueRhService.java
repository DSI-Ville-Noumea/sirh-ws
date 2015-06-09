package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.AlerteRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;
import nc.noumea.mairie.ws.ISirhAbsWSConsumer;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class KiosqueRhService implements IKiosqueRhService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private ISirhAbsWSConsumer sirhAbsWSConsumer;

	@Autowired
	private ISirhPtgWSConsumer sirhPtgWSConsumer;

	@Override
	public List<AccueilRh> getListeAccueilRh() {
		TypedQuery<AccueilRh> query = sirhEntityManager.createQuery("select ref from AccueilRh ref ", AccueilRh.class);
		List<AccueilRh> lc = query.getResultList();

		return lc;
	}

	@Override
	public ReferentRh getReferentRH(String codeService) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ref from ReferentRh ref ");
		sb.append("where ref.servi = :codeService ");

		TypedQuery<ReferentRh> q = sirhEntityManager.createQuery(sb.toString(), ReferentRh.class);
		q.setParameter("codeService", codeService);

		ReferentRh result = null;
		try {
			result = (ReferentRh) q.getSingleResult();
		} catch (Exception e) {
			// on a pas trouvé alors on cherche le referent global
			StringBuilder sbGlobal = new StringBuilder();
			sbGlobal.append("select ref from ReferentRh ref ");
			sbGlobal.append("where ref.servi is null ");
			Query qGlobal = sirhEntityManager.createQuery(sbGlobal.toString(), ReferentRh.class);
			try {
				result = (ReferentRh) qGlobal.getSingleResult();
			} catch (Exception e2) {
				// on ne fait rien
			}

		}

		return result;
	}

	@Override
	public ReturnMessageDto getAlerteRHByAgent(Integer idAgent) {
		// ABSENCES
		boolean approABS = false;
		boolean operateurABS = false;
		boolean viseurABS = false;
		boolean approPTG = false;
		boolean operateurPTG = false;
		try {
			approABS = sirhAbsWSConsumer.isUserApprobateur(idAgent);
		} catch (Exception e) {
		}
		try {
			operateurABS = sirhAbsWSConsumer.isUserOperateur(idAgent);
		} catch (Exception e) {
		}
		try {
			viseurABS = sirhAbsWSConsumer.isUserViseur(idAgent);
		} catch (Exception e) {
		}
		// POINTAGES
		try {
			approPTG = sirhPtgWSConsumer.isUserApprobateur(idAgent);
		} catch (Exception e) {
		}
		try {
			operateurPTG = sirhPtgWSConsumer.isUserOperateur(idAgent);
		} catch (Exception e) {
		}
		// on cherche si il y a des alertes
		List<AlerteRh> listeAlerte = getListeAlerte(approABS, approPTG, operateurABS, operateurPTG, viseurABS);
		// on construite le DTO
		ReturnMessageDto dto = new ReturnMessageDto();
		for (AlerteRh a : listeAlerte) {
			dto.getInfos().add(a.getTexteAlerteKiosque());
		}
		return dto;
	}

	@Override
	public List<AlerteRh> getListeAlerte(boolean approABS, boolean approPTG, boolean operateurABS,
			boolean operateurPTG, boolean viseurABS) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct a from AlerteRh a ");
		sb.append("where a.agent = :agent ");
		if (approABS)
			sb.append("or a.approbateurABS =:approABS ");
		if (approPTG)
			sb.append("or a.approbateurPTG =:approPTG ");
		if (operateurABS)
			sb.append("or a.operateurABS =:operateurABS ");
		if (operateurPTG)
			sb.append("or a.operateurPTG =:operateurPTG ");
		if (viseurABS)
			sb.append("or a.viseurABS =:viseurABS ");
		sb.append("and :date between a.dateDebut and a.dateFin ");

		TypedQuery<AlerteRh> q = sirhEntityManager.createQuery(sb.toString(), AlerteRh.class);
		q.setParameter("agent", true);
		if (approABS)
			q.setParameter("approABS", approABS);
		if (approPTG)
			q.setParameter("approPTG", approPTG);
		if (operateurABS)
			q.setParameter("operateurABS", operateurABS);
		if (operateurPTG)
			q.setParameter("operateurPTG", operateurPTG);
		if (viseurABS)
			q.setParameter("viseurABS", viseurABS);
		q.setParameter("date", new Date());

		return q.getResultList();
	}
}
