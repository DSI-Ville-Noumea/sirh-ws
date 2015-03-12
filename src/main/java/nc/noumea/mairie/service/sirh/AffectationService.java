package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
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
	private IAffectationRepository affRepo;

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
	public EasyVistaDto getChefServiceAgent(Affectation aff, EasyVistaDto result) {

		// si l'agent est chef de service alors on retourne lui-meme
		List<Integer> agentChefServiceId = affRepo.getListChefsService();
		if (agentChefServiceId.contains(aff.getAgent().getIdAgent())) {
			result.setNomatrChef(aff.getAgent().getNomatr());
			return result;
		}

		// si l'agent est directeur alors on retourne lui-meme
		List<Integer> agentDirecteurId = affRepo.getListDirecteur();
		if (agentDirecteurId.contains(aff.getAgent().getIdAgent())) {
			result.setNomatrChef(aff.getAgent().getNomatr());
			return result;
		}

		// si autre agent alors on cherche son chef de service
		List<Integer> agentIds = fpSrv.getListShdAgents(aff.getAgent().getIdAgent(), 6);
		for (Integer idAgent : agentIds) {
			if (agentChefServiceId.contains(idAgent)) {
				Agent ag = agentSrv.getAgent(idAgent);
				result.setNomatrChef(ag.getNomatr());
				return result;
			}
		}
		if (result.getNomatrChef() == null) {
			result.getErrors().add("Aucun chef de service trouvé.");
		}
		return result;
	}
}
