package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.repository.ISpprimRepository;
import nc.noumea.mairie.service.sirh.IAgentService;
import nc.noumea.mairie.service.sirh.IFichePosteService;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpprimService implements ISpprimService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IFichePosteService fpSrv;

	@Autowired
	private IAgentService agentSrv;

	@Autowired
	private ISpprimRepository primeRepo;

	@Override
	public EasyVistaDto getChefServiceAgent(Affectation aff, EasyVistaDto result) {

		// si l'agent est chef de service alors on retourne lui-meme
		List<Integer> agentChefServiceId = primeRepo.getListChefsService();
		if (agentChefServiceId.contains(aff.getAgent().getIdAgent())) {
			result.setNomatrChef(aff.getAgent().getNomatr());
			return result;
		}

		// si l'agent est directeur alors on retourne lui-meme
		List<Integer> agentDirecteurId = primeRepo.getListDirecteur();
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
