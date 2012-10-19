package nc.noumea.mairie.model.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.FichePoste;

import org.springframework.stereotype.Service;

@Service
public class FichePosteService implements IFichePosteService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public FichePoste getFichePosteAgentAffectationEnCours(Integer id) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		FichePoste res = null;
		Query query = sirhEntityManager
				.createQuery(
						"select fp from FichePoste fp, Affectation aff "
								+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and "
								+ "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						FichePoste.class);
		query.setParameter("idAgent", id);
		query.setParameter("dateJour", dateJour);
		List<FichePoste> lfp = query.getResultList();

		for (FichePoste fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public FichePoste getFichePoste(Integer idFichePoste) {
		FichePoste res = null;
		Query query = sirhEntityManager.createQuery(
				"select fp from FichePoste fp "
						+ "where fp.idFichePoste =:idFP)", FichePoste.class);
		query.setParameter("idFP", idFichePoste);
		List<FichePoste> lfp = query.getResultList();

		for (FichePoste fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public List<FichePoste> getFichePosteAgentService(String servi,
			Integer idAgent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		/*
		 * String codeService = null; if (servi.endsWith("A")) { codeService =
		 * servi.substring(0, servi.length() - 1); } else { codeService = servi;
		 * }
		 */
		while (servi.endsWith("A")) {
			servi = servi.substring(0, servi.length() - 1);
		}

		Query query = sirhEntityManager
				.createQuery(
						"select fp from FichePoste fp, Agent ag , Affectation aff where aff.agent.idAgent = ag.idAgent  "
								+ " and fp.idFichePoste = aff.fichePoste.idFichePoste "
								+ "and fp.service.servi like :codeServ and aff.agent.idAgent != :idAgentResp "
								+ "and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						FichePoste.class);
		query.setParameter("codeServ", servi + "%");
		query.setParameter("idAgentResp", idAgent);
		query.setParameter("dateJour", dateJour);
		List<FichePoste> lfpag = query.getResultList();

		return lfpag;
	}

	@Override
	public List<FichePoste> listerFichePosteAgentResp(Integer idAgentResp,
			Integer idFichePosteResp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Query query = sirhEntityManager
				.createQuery(
						"select fp from FichePoste fp,  Affectation aff where "
								+ "fp.idFichePoste = aff.fichePoste.idFichePoste and fp.responsable.idFichePoste=:idFDPResp "
								+ "and aff.agent.idAgent != :idAgentResp "
								+ "and aff.dateDebutAff<=:dateJour and "
								+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)",
						FichePoste.class);
		query.setParameter("idAgentResp", idAgentResp);
		query.setParameter("idFDPResp", idFichePosteResp);
		query.setParameter("dateJour", dateJour);
		List<FichePoste> lfpag = query.getResultList();

		return lfpag;
	}
}
