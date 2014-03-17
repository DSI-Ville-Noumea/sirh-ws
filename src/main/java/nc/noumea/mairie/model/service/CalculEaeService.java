package nc.noumea.mairie.model.service;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.DiplomeAgent;
import nc.noumea.mairie.model.bean.FormationAgent;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISirhRepository;
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
	private ISirhRepository sirhRepository;

	@Autowired
	private ISpadmnService spadmnService;

	@Autowired
	private ISpCarrService spCarrService;

	@Override
	public CalculEaeInfosDto getAffectationActiveByAgent(Integer idAgent, Integer anneeFormation) {

		Affectation affectation = sirhRepository.getAffectationActiveByAgent(idAgent);

		CalculEaeInfosDto dto = new CalculEaeInfosDto(affectation);

		affectation
				.getFichePoste()
				.getService()
				.setDirection(
						siservSrv.getDirection(affectation.getFichePoste().getService().getServi()) == null ? ""
								: siservSrv.getDirection(affectation.getFichePoste().getService().getServi())
										.getLiServ());
		affectation
				.getFichePoste()
				.getService()
				.setSection(
						siservSrv.getSection(affectation.getFichePoste().getService().getServi()) == null ? ""
								: siservSrv.getSection(affectation.getFichePoste().getService().getServi()).getLiServ());

		FichePosteDto fichePostePrincipale = new FichePosteDto(affectation.getFichePoste());
		dto.setFichePostePrincipale(fichePostePrincipale);
		FichePosteDto fichePosteSecondaire = new FichePosteDto(affectation.getFichePosteSecondaire());
		dto.setFichePosteSecondaire(fichePosteSecondaire);

		FichePosteDto fichePosteResponsable = new FichePosteDto(affectation.getFichePoste().getSuperieurHierarchique());
		if (null != affectation.getFichePoste().getSuperieurHierarchique().getTitrePoste()) {
			TitrePosteDto titrePosteResponable = new TitrePosteDto();
			titrePosteResponable.setLibTitrePoste(affectation.getFichePoste().getSuperieurHierarchique()
					.getTitrePoste().getLibTitrePoste());
			fichePosteResponsable.setTitrePoste(titrePosteResponable);
		}
		dto.setFichePosteResponsable(fichePosteResponsable);
		dto.setListDiplome(getListDiplomeDto(idAgent));
		dto.setListParcoursPro(getListParcoursPro(affectation.getAgent().getNomatr()));
		dto.setListFormation(getListFormation(idAgent, anneeFormation));

		if (null != affectation.getAgent()) {
			dto.setPositionAdmAgentEnCours(spadmnService.chercherPositionAdmAgentEnCours(affectation.getAgent()
					.getNomatr()));
			dto.setPositionAdmAgentAncienne(spadmnService.chercherPositionAdmAgentAncienne(affectation.getAgent()
					.getNomatr()));
			dto.setCarriereFonctionnaireAncienne(spCarrService.getCarriereFonctionnaireAncienne(affectation.getAgent()
					.getNomatr()));
			dto.setCarriereActive(spCarrService.getCarriereActive(affectation.getAgent().getNomatr()));
		}

		return dto;
	}

	private List<DiplomeDto> getListDiplomeDto(Integer idAgent) {
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
				parcoursProDto.setDirection(siservSrv.getDirection(spMtsr.getServi()) == null ? "" : siservSrv
						.getDirection(spMtsr.getServi()).getLiServ());
				parcoursProDto.setService(siservSrv.getService(spMtsr.getServi()) == null ? "" : siservSrv.getService(
						spMtsr.getServi()).getLiServ());
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
				CalculEaeInfosDto dto = new CalculEaeInfosDto(affectation);
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
				CalculEaeInfosDto dto = new CalculEaeInfosDto(affectation);
				listDto.add(dto);
			}
		}

		return listDto;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAESansAffectes() {

		List<Integer> listNoMatr = mairieRepository.getListeCarriereActiveAvecPA();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = sirhRepository.getAgentEligibleEAESansAffectes(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
					result.add(dto);
				}
			}
		}

		return result;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAEAffectes() {

		List<Integer> listNoMatr = mairieRepository.getListeCarriereActiveAvecPAAffecte();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = sirhRepository.getAgentWithListNomatr(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
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
