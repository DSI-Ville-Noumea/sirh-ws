package nc.noumea.mairie.service.eae;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.model.repository.sirh.IAgentRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.service.ISiservService;
import nc.noumea.mairie.service.ISpCarrService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.DateAvctDto;
import nc.noumea.mairie.web.dto.DiplomeDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FormationDto;
import nc.noumea.mairie.web.dto.ParcoursProDto;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;
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
	private IAffectationRepository affectationRepository;

	@Autowired
	private IFichePosteRepository fichePosteRepository;

	@Autowired
	private IAgentRepository agentRepository;

	@Autowired
	private ISpadmnService spadmnService;

	@Autowired
	private ISpCarrService spCarrService;

	@Override
	public CalculEaeInfosDto getAffectationActiveByAgent(Integer idAgent, Integer anneeFormation) {

		Affectation affectation = affectationRepository.getAffectationActiveByAgent(idAgent);

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

		Agent agent = agentRepository.getAgent(idAgent);

		if (null != agent) {
			dto.setListDiplome(getListDiplomeDto(idAgent));
			dto.setListParcoursPro(getListParcoursPro(agent.getNomatr()));
			dto.setListFormation(getListFormation(idAgent, anneeFormation));
			dto.setPositionAdmAgentEnCours(spadmnService.chercherPositionAdmAgentEnCours(agent.getNomatr()));
			dto.setPositionAdmAgentAncienne(spadmnService.chercherPositionAdmAgentAncienne(agent.getNomatr()));
			dto.setCarriereFonctionnaireAncienne(spCarrService.getCarriereFonctionnaireAncienne(agent.getNomatr()));
			dto.setCarriereActive(spCarrService.getCarriereActive(agent.getNomatr()));
			if (dto.getCarriereActive().getGrade() != null && dto.getCarriereActive().getCodeCategorie() != null) {
				dto.setCarriereAncienneDansGrade(spCarrService.getCarriereAvecGrade(agent.getNomatr(), dto
						.getCarriereActive().getGrade().getCodeGrade(), dto.getCarriereActive().getCodeCategorie()));
			}
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

		List<Affectation> listAffectation = affectationRepository.getListeAffectationsAgentAvecService(idAgent,
				idService);

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

		List<Affectation> listAffectation = affectationRepository
				.getListeAffectationsAgentAvecFP(idAgent, idFichePoste);

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
				Agent agent = agentRepository.getAgentEligibleEAESansAffectes(noMatr);

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
				Agent agent = agentRepository.getAgentWithListNomatr(noMatr);

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

	@Override
	public DateAvctDto getCalculDateAvancement(Integer idAgent) {
		DateAvctDto result = new DateAvctDto();
		Integer nomatr = Integer.valueOf(idAgent.toString().substring(3, idAgent.toString().length()));

		// on lance le calcul de l'avancement prev
		// on regarde de quelle categorie est l'agent (Fonctionnaire ou
		// Contractuel)
		Spcarr carr = spcarrRepository.getCarriereActive(nomatr);
		if (carr != null && carr.getCategorie() != null) {
			// on regarde si sa derniere PA n'est pas inactive
			PositionAdmAgentDto posAdmn = spadmnService.chercherPositionAdmAgentEnCours(nomatr);
			try {
				// on regarde si la position administrative est de type
				// numerique
				@SuppressWarnings("unused")
				Integer codePositionAdm = Integer.valueOf(posAdmn.getCdpadm());
				String codeCategorie = carr.getCategorie().getCodeCategorie().toString();
				if (codeCategorie.equals("4")) {
					// alors on est dans les contractuels
					// on ajoute 2 ans à la date de carriere
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
					cal.add(Calendar.YEAR, 2);
					result.setDateAvct(cal.getTime());
					return result;
				} else if (codeCategorie.equals("1") || codeCategorie.equals("2") || codeCategorie.equals("18")
						|| codeCategorie.equals("20")) {
					// alors on est dans les fonctionnaires
					result.setDateAvct(performCalculFonctionnaire(carr, idAgent));
					return result;
				} else if (codeCategorie.equals("6") || codeCategorie.equals("16") || codeCategorie.equals("17")
						|| codeCategorie.equals("19")) {
					// alors on est dans les détachés
					result.setDateAvct(performCalculFonctionnaire(carr, idAgent));
					return result;
				}

			} catch (Exception e) {
				// si non , alors on ne peut pas calculer la date d'avct
				return result;
			}
		}
		return result;
	}

	private Date performCalculFonctionnaire(Spcarr carr, Integer idAgent) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// on regarde si il y a d'autre carrieres avec le meme grade
		// si oui on prend la carriere plus lointaine
		ArrayList<Spcarr> listeCarrMemeGrade = (ArrayList<Spcarr>) spcarrRepository.listerCarriereAvecGradeEtStatut(
				Integer.valueOf(idAgent.toString().substring(3, idAgent.toString().length())), carr.getGrade()
						.getCdgrad(), carr.getCategorie().getCodeCategorie());
		if (listeCarrMemeGrade != null && listeCarrMemeGrade.size() > 0) {
			carr = (Spcarr) listeCarrMemeGrade.get(0);
		}

		Spgradn gradeActuel = carr.getGrade();
		// Si pas de grade suivant, agent non éligible
		if (gradeActuel.getGradeSuivant() != null
				&& null != gradeActuel.getGradeSuivant().getCdgrad()
				&& !"".equals(gradeActuel.getGradeSuivant().getCdgrad().trim())) {

			if ((carr.getCategorie().getCodeCategorie().toString().equals("2") || carr.getCategorie()
					.getCodeCategorie().toString().equals("18"))
					&& gradeActuel.getDureeMoyenne() != null
					&& (!gradeActuel.getDureeMoyenne().toString().equals("12"))) {
				// si stagiaire
				// la date d'avancement est la meme +1an.
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
				cal2.add(Calendar.YEAR, 1);
				return cal2.getTime();

			}

			// calcul BM/ACC applicables
			int nbJoursBM = calculJourBM(gradeActuel, carr);
			int nbJoursACC = calculJourACC(gradeActuel, carr);

			int nbJoursBonus = nbJoursBM + nbJoursACC;

			// Calcul date avancement au Grade actuel
			if (gradeActuel.getDureeMoyenne() != null) {
				if (nbJoursBonus > (gradeActuel.getDureeMoyenne() * 30)) {
					return sdf.parse(carr.getId().getDatdeb().toString());
				} else {
					return calculDateAvctMoy(gradeActuel, carr);
				}
			}
		} else {
			return null;
		}
		return null;
	}

	private Date calculDateAvctMoy(Spgradn gradeActuel, Spcarr carr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Calendar calSansBmAcc = Calendar.getInstance();
		calSansBmAcc.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
		calSansBmAcc.add(Calendar.MONTH, gradeActuel.getDureeMoyenne());

		Date dateSansAccBm = calSansBmAcc.getTime();

		Integer annee = carr.getAccAnnee() + carr.getBmAnnee();
		Calendar calAvcAnne = Calendar.getInstance();
		calAvcAnne.setTime(dateSansAccBm);
		calAvcAnne.add(Calendar.YEAR, -annee);

		Date dateAvcAnne = calAvcAnne.getTime();

		Integer mois = carr.getAccMois() + carr.getBmMois();
		Calendar calAvcMois = Calendar.getInstance();
		calAvcMois.setTime(dateAvcAnne);
		calAvcMois.add(Calendar.MONTH, -mois);

		Date dateAvcMois = calAvcMois.getTime();

		Integer jour = carr.getAccJour() + carr.getBmJour();
		Calendar calAvcJour = Calendar.getInstance();
		calAvcJour.setTime(dateAvcMois);
		calAvcJour.add(Calendar.DAY_OF_MONTH, -jour);

		Date dateAvcJour = calAvcJour.getTime();

		return dateAvcJour;
	}

	private int calculJourACC(Spgradn gradeActuel, Spcarr carr) {
		Integer nbJoursACC = 0;
		if (gradeActuel.getAcc() != null && gradeActuel.getAcc().equals("O")) {
			nbJoursACC += (carr.getAccAnnee() * 360) + (carr.getAccMois() * 30) + carr.getAccJour();
		}
		return nbJoursACC;
	}

	private int calculJourBM(Spgradn gradeActuel, Spcarr carr) {
		Integer nbJoursBM = 0;
		if (gradeActuel.getBm() != null && gradeActuel.getBm().equals("O")) {
			nbJoursBM = (carr.getBmAnnee() * 360) + (carr.getBmMois() * 30) + carr.getBmJour();
		}
		return nbJoursBM;
	}
}
