package nc.noumea.mairie.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.FichePoste;

import org.springframework.stereotype.Service;

@Service
public class AgentService implements IAgentService {

	@PersistenceContext
	transient EntityManager entityManager;

	@Override
	public Agent getAgent(Integer id) {
		Agent res = null;

		Query query = entityManager.createQuery(
				"select ag from Agent ag where ag.idAgent = :idAgent",
				Agent.class);

		query.setParameter("idAgent", id);
		List<Agent> lfp = query.getResultList();

		for (Agent fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public Agent getAgentAffectationCourante(Integer id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Agent res = null;
		Query query = entityManager
				.createQuery(
						"select ag from Agent ag, Affectation aff "
								+ "where aff.agent.idAgent = ag.idAgent and "
								+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						Agent.class);
		query.setParameter("idAgent", id);
		query.setParameter("dateJour", dateJour);
		List<Agent> la = query.getResultList();

		for (Agent a : la)
			res = a;

		return res;
	}

	@Override
	public List<FichePoste> getFichePosteEnfant(Integer id) {
		Query query = entityManager.createQuery("select fp from FichePoste fp "
				+ "where fp.responsable.idFichePoste in " + "("
				+ "select aff.fichePoste.idFichePoste from Affectation aff "
				+ "where aff.agent.idAgent = :idAgent" + ")", FichePoste.class);
		query.setParameter("idAgent", id);
		List<FichePoste> lfp = query.getResultList();

		return lfp;
	}

}
