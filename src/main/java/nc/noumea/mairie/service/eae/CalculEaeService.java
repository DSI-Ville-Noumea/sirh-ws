package nc.noumea.mairie.service.eae;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.ISpCarrService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.DiplomeDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FormationDto;
import nc.noumea.mairie.web.dto.ParcoursProDto;
import nc.noumea.mairie.web.dto.TitrePosteDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculEaeService implements ICalculEaeService {

	@Autowired
	private ISiservService siservSrv;

	@Autowired
	private IMairieRepository mairieRepository;

	@Autowired
	private ISpcarrRepository spcarrRepository;

	@Autowired
	private ISirhRepository sirhRepository;

	@Autowired
	private ISpadmnService spadmnService;

	@Autowired
	private ISpCarrService spCarrService;

	@Override
	public CalculEaeInfosDto getAffectationActiveByAgent(Integer idAgent, Integer anneeFormation) {

		Affectation affectation = sirhRepository.getAffectationActiveByAgent(idAgent);

		CalculEaeInfosDto dto = new CalculEaeInfosDto();

		if (null != affectation) {

			dto.setDateDebut(affectation.getDateDebutAff());
			dto.setDateFin(affectation.getDateFinAff());

			Siserv direction = siservSrv.getDirectionPourEAE(affectation.getFichePoste().getService().getServi());
			affectation.getFichePoste().getService().setDirection(direction == null ? "" : direction.getLiServ());

			Siserv section = siservSrv.getSection(affectation.getFichePoste().getService().getServi());
			affectation.getFichePoste().getService().setSection(section == null ? "" : section.getLiServ());

			TitrePosteDto titrePoste = new TitrePosteDto();
			titrePoste.setLibTitrePoste(affectation.getFichePoste().getTitrePoste().getLibTitrePoste());

			FichePosteDto fichePostePrincipale = new FichePosteDto(affectation.getFichePoste());
			fichePostePrincipale.setTitrePoste(titrePoste);

			dto.setFichePostePrincipale(fichePostePrincipale);
			if (null != affectation.getFichePosteSecondaire()) {
				FichePosteDto fichePosteSecondaire = new FichePosteDto(affectation.getFichePosteSecondaire());
				dto.setFichePosteSecondaire(fichePosteSecondaire);
			}

			if (null != affectation.getFichePoste().getSuperieurHierarchique()) {
				FichePoste fichePosteSuperieur = affectation.getFichePoste().getSuperieurHierarchique();
				FichePosteDto fichePosteResponsable = new FichePosteDto(fichePosteSuperieur.getService(),
						fichePosteSuperieur.getIdFichePoste(), fichePosteSuperieur.getAgent());
				if (null != fichePosteSuperieur.getTitrePoste()) {
					TitrePosteDto titrePosteResponable = new TitrePosteDto();
					titrePosteResponable.setLibTitrePoste(fichePosteSuperieur.getTitrePoste().getLibTitrePoste());
					fichePosteResponsable.setTitrePoste(titrePosteResponable);
				}
				dto.setFichePosteResponsable(fichePosteResponsable);
			}
		}

		Agent agent = sirhRepository.getAgent(idAgent);

		if (null != agent) {
			dto.setListDiplome(getListDiplomeDto(idAgent));
			dto.setListParcoursPro(getListParcoursPro(agent.getNomatr()));
			dto.setListFormation(getListFormation(idAgent, anneeFormation));
			dto.setPositionAdmAgentEnCours(spadmnService.chercherPositionAdmAgentEnCours(agent.getNomatr()));
			dto.setPositionAdmAgentAncienne(spadmnService.chercherPositionAdmAgentAncienne(agent.getNomatr()));
			dto.setCarriereFonctionnaireAncienne(spCarrService.getCarriereFonctionnaireAncienne(agent.getNomatr()));
			dto.setCarriereActive(spCarrService.getCarriereActive(agent.getNomatr()));
		}

		return dto;
	}

	@Override
	public List<DiplomeDto> getListDiplomeDto(Integer idAgent) {
		List<DiplomeDto> listDiplomeDto = new ArrayList<DiplomeDto>();
		List<DiplomeAgent> lstDiplome = sirhRepository.getListDiplomeByAgent(idAgent);
		if (null != lstDiplome) {
			for (DiplomeAgent diplome : lstDiplome) {
				DiplomeDto diplomeDto = new DiplomeDto(diplome);
				listDiplomeDto.add(diplomeDto);
			}
		}

		return listDiplomeDto;
	}

	private List<ParcoursProDto> getListParcoursPro(Integer noMatr) {

		List<ParcoursProDto> listParcoursPro = new ArrayList<ParcoursProDto>();

		List<Spmtsr> listSpmtsr = mairieRepository.getListSpmtsr(noMatr);
		if (null != listSpmtsr) {
			for (Spmtsr spMtsr : listSpmtsr) {
				ParcoursProDto parcoursProDto = new ParcoursProDto(spMtsr);

				Siserv direction = siservSrv.getDirectionPourEAE(spMtsr.getId().getServi());
				parcoursProDto.setDirection(direction == null ? "" : direction.getLiServ());

				Siserv service = siservSrv.getService(spMtsr.getId().getServi());
				parcoursProDto.setService(service == null ? "" : service.getLiServ());

				listParcoursPro.add(parcoursProDto);
			}
		}

		return listParcoursPro;
	}

	private List<FormationDto> getListFormation(Integer idAgent, Integer anneeFormation) {

		List<FormationDto> listFormation = new ArrayList<FormationDto>();

		List<FormationAgent> listFormationAgent = sirhRepository.getListFormationAgentByAnnee(idAgent, anneeFormation);
		if (null != listFormationAgent) {
			for (FormationAgent fa : listFormationAgent) {
				FormationDto formationDto = new FormationDto(fa);
				listFormation.add(formationDto);
			}
		}

		return listFormation;
	}

	@Override
	public List<CalculEaeInfosDto> getListeAffectationsAgentAvecService(Integer idAgent, String idService) {

		List<CalculEaeInfosDto> listDto = new ArrayList<CalculEaeInfosDto>();

		List<Affectation> listAffectation = sirhRepository.getListeAffectationsAgentAvecService(idAgent, idService);

		if (null != listAffectation) {
			for (Affectation affectation : listAffectation) {
				CalculEaeInfosDto dto = new CalculEaeInfosDto();
				dto.setDateDebut(affectation.getDateDebutAff());
				dto.setDateFin(affectation.getDateFinAff());
				listDto.add(dto);
			}
		}

		return listDto;
	}

	@Override
	public List<CalculEaeInfosDto> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste) {

		List<CalculEaeInfosDto> listDto = new ArrayList<CalculEaeInfosDto>();

		List<Affectation> listAffectation = sirhRepository.getListeAffectationsAgentAvecFP(idAgent, idFichePoste);

		if (null != listAffectation) {
			for (Affectation affectation : listAffectation) {
				CalculEaeInfosDto dto = new CalculEaeInfosDto();
				dto.setDateDebut(affectation.getDateDebutAff());
				dto.setDateFin(affectation.getDateFinAff());
				listDto.add(dto);
			}
		}

		return listDto;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAESansAffectes() {

		List<Integer> listNoMatr = spcarrRepository.getListeCarriereActiveAvecPA();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = sirhRepository.getAgentEligibleEAESansAffectes(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
					if (!result.contains(dto))
						result.add(dto);
				}
			}
		}

		return result;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAEAffectes() {

		List<Integer> listNoMatr = spcarrRepository.getListeCarriereActiveAvecPAAffecte();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = sirhRepository.getAgentWithListNomatr(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
					if (!result.contains(dto))
						result.add(dto);
				}
			}
		}

		return result;
	}

	@Override
	public AutreAdministrationAgentDto chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire) {

		AutreAdministrationAgent autreAdministrationAgent = sirhRepository.chercherAutreAdministrationAgentAncienne(
				idAgent, isFonctionnaire);

		AutreAdministrationAgentDto result = null;
		if (null != autreAdministrationAgent) {
			result = new AutreAdministrationAgentDto(autreAdministrationAgent);
		}

		return result;
	}

	@Override
	public List<AutreAdministrationAgentDto> getListeAutreAdministrationAgent(Integer idAgent) {

		List<AutreAdministrationAgent> list = sirhRepository.getListeAutreAdministrationAgent(idAgent);

		List<AutreAdministrationAgentDto> result = new ArrayList<AutreAdministrationAgentDto>();
		if (null != list) {
			for (AutreAdministrationAgent aaa : list) {
				AutreAdministrationAgentDto dto = new AutreAdministrationAgentDto(aaa);
				result.add(dto);
			}
		}

		return result;
	}
}
