package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Contrat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ContratService implements IContratService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

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

}
