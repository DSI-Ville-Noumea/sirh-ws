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
		Query query = sirhEntityManager.createQuery("select fp from FichePoste fp, Affectation aff "
				+ "where aff.fichePoste.idFichePoste = fp.idFichePoste and " + "aff.agent.idAgent = :idAgent and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", FichePoste.class);
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
		Query query = sirhEntityManager.createQuery("select fp from FichePoste fp " + "where fp.idFichePoste =:idFP)", FichePoste.class);
		query.setParameter("idFP", idFichePoste);
		List<FichePoste> lfp = query.getResultList();

		for (FichePoste fp : lfp)
			res = fp;

		return res;
	}

	@Override
	public List<FichePoste> getFichePosteAgentService(String servi, Integer idAgent) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateTemp = sdf.format(new Date());
		Date dateJour = null;
		try {
			dateJour = sdf.parse(dateTemp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		while (servi.endsWith("A")) {
			servi = servi.substring(0, servi.length() - 1);
		}

		Query query = sirhEntityManager.createQuery("select fp from FichePoste fp, Agent ag , Affectation aff where aff.agent.idAgent = ag.idAgent  "
				+ " and fp.idFichePoste = aff.fichePoste.idFichePoste "
				+ "and fp.service.servi like :codeServ and aff.agent.idAgent != :idAgentResp " + "and aff.dateDebutAff<=:dateJour and "
				+ "(aff.dateFinAff is null or aff.dateFinAff='01/01/0001' or aff.dateFinAff>=:dateJour)", FichePoste.class);
		query.setParameter("codeServ", servi + "%");
		query.setParameter("idAgentResp", idAgent);
		query.setParameter("dateJour", dateJour);
		List<FichePoste> lfpag = query.getResultList();

		return lfpag;
	}

	@Override
	public boolean estResponsable(Integer idAgent) {
		String sql = "select count(fp.id_fiche_poste) as nb from sirh.fiche_poste fp inner join sirh.affectation aff on aff.id_fiche_poste = fp.id_fiche_poste where fp.id_responsable = (select fp.id_fiche_poste from sirh.affectation  a inner join sirh.fiche_poste fp on a.id_fiche_poste = fp.id_fiche_poste where a.id_agent=:idAgent and a.date_Debut_Aff<=:dateJour and (a.date_Fin_Aff is null or a.date_Fin_Aff='01/01/0001' or a.date_Fin_Aff>=:dateJour) ) and aff.date_Debut_Aff<=:dateJour and (aff.date_Fin_Aff is null or aff.date_Fin_Aff='01/01/0001' or aff.date_Fin_Aff>=:dateJour)";
		Query query = sirhEntityManager.createNativeQuery(sql);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());
		Integer nbRes = (Integer) query.getSingleResult();
		return nbRes > 0;
	}
}
