package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.ISpadmnRepository;
import nc.noumea.mairie.service.ISivietService;
import nc.noumea.mairie.web.dto.AgentAnnuaireDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.TitrePosteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private IAffectationService affectationSrv;

	@Autowired
	private IFichePosteService fichePosteService;

	@Autowired
	private IAgentMatriculeConverterService agentMatriculeConverterService;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private ISivietService sivietSrv;

	@Override
	public List<Integer> listAgentActiviteAnnuaire() {
		List<Integer> result = new ArrayList<Integer>();
		// #17922
		// on cherche tous les agents dans une certaine PA
		// TODO : s'appuyer sur une table de paramétrage SPPOSA_ANNUAIRE qui
		// contient la liste des PA à prendre en compte
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

		Affectation affAgent = affectationSrv.getAffectationActiveByIdAgent(idAgent);
		if (affAgent == null || affAgent.getIdAffectation() == null) {
			logger.error("L'agent id={} n'a pas d'affectation active.", idAgent);
			return null;
		}

		Integer fpId = fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(affAgent.getAgent().getIdAgent(), new DateTime().toDate());
		FichePoste fp = fichePosteService.getFichePosteById(fpId);
		if (fp == null || fp.getIdServiceADS() == null) {
			logger.error("L'agent id={} n'a pas d'affectation sur une entité ADS.", idAgent);
			return null;
		}

		FichePoste ficheSuperieur = affAgent.getFichePoste().getSuperieurHierarchique();
		Integer idSuperieur = null;
		if (ficheSuperieur != null && ficheSuperieur.getIdFichePoste() != null) {
			Affectation affSuperieur = affectationSrv.getAffectationByIdFichePoste(ficheSuperieur.getIdFichePoste());
			if (affSuperieur != null && affSuperieur.getAgent() != null) {
				idSuperieur = affSuperieur.getAgent().getIdAgent();
			}
		}

		TitrePosteDto titrePoste = new TitrePosteDto(fp);
		List<Contact> lc = contactSrv.getContactsDiffusableAgent(Long.valueOf(idAgent));
		EntiteDto infoSiserv = adsWSConsumer.getInfoSiservByIdEntite(fp.getIdServiceADS());
		EntiteDto entite = adsWSConsumer.getEntiteByIdEntite(fp.getIdServiceADS());
		EntiteDto direction = adsWSConsumer.getAffichageDirection(fp.getIdServiceADS());
		AgentAnnuaireDto dto = new AgentAnnuaireDto(ag, spAdm, lc, titrePoste, idSuperieur, infoSiserv.getCodeServi(), entite.getCodeServi(), entite, direction, fp, positDesc);

		return dto;
	}
}
