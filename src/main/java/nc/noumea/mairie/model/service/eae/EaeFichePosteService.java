package nc.noumea.mairie.model.service.eae;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.eae.EaeFichePoste;
import nc.noumea.mairie.model.service.IAgentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EaeFichePosteService implements IEaeFichePosteService {

	@PersistenceContext(unitName = "eaePersistenceUnit")
	private EntityManager eaeEntityManager;

	@Autowired
	private IAgentService agentService;

	@Override
	public void setService(EaeFichePoste eaeFichePoste, String codeService)
			throws EaeFichePosteServiceException {

		Siserv serviceFDP = Siserv.findSiserv(codeService);

		if (serviceFDP == null)
			throw new EaeFichePosteServiceException(
					String.format(
							"Impossible d'affecter le service '%d' en tant que service : ce service n'existe pas.",
							codeService));
		
		eaeFichePoste.setCodeService(codeService);

	}

}
