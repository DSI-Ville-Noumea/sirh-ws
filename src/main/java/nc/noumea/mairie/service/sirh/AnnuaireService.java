package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.repository.ISpadmnRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnnuaireService implements IAnnuaireService {

	@Autowired
	private ISpadmnRepository spadmnRepository;

	@Autowired
	private IAffectationService affectationSrv;

	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;

	@Override
	public List<Integer> listAgentActiviteAnnuaire() {
		List<Integer> result = new ArrayList<Integer>();
		// #17922
		// on cherche tous les agents dans une certaine PA
		List<Integer> listNomatrPAActiveAnnuaire = spadmnRepository.listAgentActiviteAnnuaire();

		// pour chacun on regarde si son affectation est active
		for (Integer nomatr : listNomatrPAActiveAnnuaire) {
			try {
				Integer idAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToSIRHIdAgent(nomatr);
				Affectation affAgent = affectationSrv.getAffectationActiveByIdAgent(idAgent);
				if (affAgent != null && affAgent.getIdAffectation() != null) {
					result.add(idAgent);
				}
			} catch (Exception e) {
				// on n'ajoute pas l'agent
			}
		}
		return result;
	}
}
