package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffectationService implements IAffectationService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IFichePosteRepository fdpRepo;

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private IAgentService agentSrv;

	@Override
	public Affectation getAffectationById(Integer idAffectation) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager.createQuery(
				"select aff from Affectation aff where aff.idAffectation=:idAffectation", Affectation.class);
		query.setParameter("idAffectation", idAffectation);

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public Affectation getAffectationActiveByIdAgent(Integer idAgent) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager
				.createQuery(
						"select aff from Affectation aff where aff.agent.idAgent=:idAgent and aff.dateDebutAff <= :dateJour and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour)",
						Affectation.class);
		query.setParameter("idAgent", idAgent);
		query.setParameter("dateJour", new Date());

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public List<Integer> getListChefsService() {

		StringBuilder sb = new StringBuilder();
		sb.append("select a.agent.nomatr from Affectation a ");
		sb.append("where a.dateDebutAff <= :today and (a.dateFinAff is null or a.dateFinAff >= :today) ");
		sb.append("and a.fichePoste.titrePoste.libTitrePoste like :lib");
		TypedQuery<Integer> query = sirhEntityManager.createQuery(sb.toString(), Integer.class);
		query.setParameter("today", new Date());
		query.setParameter("lib", "%CHEF%SERVICE%");

		return query.getResultList();
	}

	@Override
	public EasyVistaDto getChefServiceAgent(Affectation aff, EasyVistaDto result) {

		// si l'agent est chef de service alors on retourne lui-meme
		List<TitrePoste> titrePosteChefService = fdpRepo.getListeTitrePosteChefService();
		if (aff.getFichePoste().getTitrePoste() != null
				&& titrePosteChefService.contains(aff.getFichePoste().getTitrePoste())) {
			result.setNomatrChef(aff.getAgent().getNomatr());
			return result;
		}

		// si l'agent est directeur alors on retourne lui-meme
		List<TitrePoste> titrePosteDirecteur = fdpRepo.getListeTitrePosteDirecteur();
		if (aff.getFichePoste().getTitrePoste() != null
				&& titrePosteDirecteur.contains(aff.getFichePoste().getTitrePoste())) {
			result.setNomatrChef(aff.getAgent().getNomatr());
			return result;
		}

		// si autre agent alors on cherche son chef de service
		// on cherche tous les chefs de service
		List<Integer> agentChefServiceId = getListChefsService();
		List<Integer> agentIds = fpSrv.getListShdAgents(aff.getAgent().getIdAgent(), 6);
		for (Integer idAgent : agentIds) {
			if (agentChefServiceId.contains(idAgent)) {
				Agent ag = agentSrv.getAgent(idAgent);
				result.setNomatrChef(ag.getNomatr());
				return result;
			}
		}
		return result;
	}
}
