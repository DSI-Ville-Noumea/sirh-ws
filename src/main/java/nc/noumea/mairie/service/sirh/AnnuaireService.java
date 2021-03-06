package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.ISpadmnRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.web.dto.AgentAnnuaireDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.TitrePosteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

@Service
public class AnnuaireService implements IAnnuaireService {

	private Logger logger = LoggerFactory.getLogger(AnnuaireService.class);

	@Autowired
	private IAgentService agSrv;

	@Autowired
	private IContactService contactSrv;

	@Autowired
	private ISpadmnRepository spadmnRepository;

	@Autowired
	private IAffectationRepository affectationRepository;

	@Autowired
	private IAffectationService affSrv;

	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Override
	public List<Integer> listAgentActiviteAnnuaire() {
		List<Integer> result = new ArrayList<Integer>();
		// #17922
		// on cherche tous les agents dans une certaine PA
		// Pour cela on va s'appuyer sur une table de paramétrage
		// SPPOSA_ANNUAIRE qui
		// contient la liste des PA à prendre en compte
		List<Integer> listNomatrPAActiveAnnuaire = spadmnRepository.listAgentActiviteAnnuaire();

		// pour chacun on regarde si son affectation est active
		for (Integer nomatr : listNomatrPAActiveAnnuaire) {
			try {
				Integer idAgent = agentMatriculeConverterService.tryConvertFromADIdAgentToSIRHIdAgent(nomatr);
				Affectation affAgent = affectationRepository.getAffectationActiveByAgent(idAgent);
				if (affAgent != null && affAgent.getIdAffectation() != null) {
					result.add(idAgent);
				}
			} catch (Exception e) {
				// on n'ajoute pas l'agent
			}
		}
		return result;
	}

	@Override
	public AgentAnnuaireDto getInfoAgent(Integer idAgent) {

		Agent ag = agSrv.getAgent(idAgent);
		if (ag == null) {
			logger.error("L'agent id={} n'existe pas.", idAgent);
			return null;
		}

		Spadmn spAdm = spadmnRepository.chercherPositionAdmAgentEnCours(ag.getNomatr());
		if (spAdm == null) {
			logger.error("L'agent id={} n'a pas de PA.", idAgent);
			return null;
		}
		// on cherche la descrition de posit
		PositDesc positDesc = spadmnRepository.chercherPositDescByPosit(spAdm.getPositionAdministrative().getPosition());

		Affectation affAgent = affectationRepository.getAffectationActiveByAgent(idAgent);
		if (affAgent == null || affAgent.getIdAffectation() == null || affAgent.getFichePoste() == null || affAgent.getFichePoste().getIdServiceADS() == null) {
			logger.error("L'agent id={} n'a pas d'affectation active.", idAgent);
			return null;
		}

		FichePoste ficheSuperieur = affAgent.getFichePoste().getSuperieurHierarchique();
		Integer idSuperieur = null;
		if (ficheSuperieur != null && ficheSuperieur.getIdFichePoste() != null) {
			Affectation affSuperieur = affSrv.getAffectationByIdFichePoste(ficheSuperieur.getIdFichePoste());
			if (affSuperieur != null && affSuperieur.getAgent() != null) {
				idSuperieur = affSuperieur.getAgent().getIdAgent();
			}
		}

		TitrePosteDto titrePoste = new TitrePosteDto(affAgent.getFichePoste());
		List<Contact> lc = contactSrv.getContactsDiffusableAgent(Long.valueOf(idAgent));
		EntiteDto infoSiserv = adsWSConsumer.getInfoSiservByIdEntite(affAgent.getFichePoste().getIdServiceADS());
		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(affAgent.getFichePoste().getIdServiceADS());
		EntiteDto direction = adsWSConsumer.getAffichageDirection(affAgent.getFichePoste().getIdServiceADS());
		AgentAnnuaireDto dto = new AgentAnnuaireDto(ag, spAdm, lc, titrePoste, idSuperieur, infoSiserv == null ? null :  infoSiserv.getCodeServi(), entite == null ? null : entite.getCodeServi(), entite, direction, affAgent.getFichePoste(), positDesc);

		return dto;
	}
}
