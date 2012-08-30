package nc.noumea.mairie.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Agent;

import org.json.simple.JSONObject;
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
	public List<Agent> getAgentService(String servi, Integer idAgent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String codeService = null;
		if (servi.endsWith("A")) {
			codeService = servi.substring(0, servi.length() - 1);
		} else {
			codeService = servi;
		}

		Query query = entityManager
				.createQuery(
						"select ag from Agent ag , Affectation aff, FichePoste fp where aff.agent.idAgent = ag.idAgent  "
								+ " and fp.idFichePoste = aff.fichePoste.idFichePoste "
								+ "and fp.service.servi like :codeServ and aff.agent.idAgent != :idAgent "
								+ "and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						Agent.class);
		query.setParameter("codeServ", codeService + "%");
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", dateJour);
		List<Agent> lag = query.getResultList();

		return lag;
	}

	@Override
	public JSONObject removeAll(JSONObject json) {
		json.remove("idAgent");
		json.remove("nomatr");
		json.remove("nomPatronymique");
		json.remove("nomMarital");
		json.remove("nomUsage");
		json.remove("prenomUsage");
		json.remove("prenom");
		json.remove("civilite");
		json.remove("dateNaissance");
		json.remove("situationFamiliale");
		json.remove("numCafat");
		json.remove("numRuamm");
		json.remove("numMutuelle");
		json.remove("numCre");
		json.remove("numIrcafex");
		json.remove("numClr");
		json.remove("codeCommuneNaissFr");
		json.remove("codeCommuneNaissEt");
		json.remove("codePaysNaissEt");
		json.remove("intituleCompte");
		json.remove("rib");
		json.remove("numCompte");
		json.remove("codeBanque");
		json.remove("codeGuichet");
		json.remove("lieuNaissance");
		json.remove("banque");
		json.remove("voie");
		json.remove("rueNonNoumea");
		json.remove("BP");
		json.remove("adresseComplementaire");
		json.remove("numRue");
		json.remove("bisTer");
		json.remove("codeCommuneVilleDom");
		json.remove("codeCommuneVilleBP");
		json.remove("codePostalVilleDom");
		json.remove("codePostalVilleBP");
		json.remove("version");
		return json;
	}

}
