package nc.noumea.mairie.service.eae;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.Budget;
import nc.noumea.mairie.model.bean.sirh.CadreEmploi;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.pk.SpcarrId;
import nc.noumea.mairie.model.pk.SpmtsrId;
import nc.noumea.mairie.model.pk.sirh.AutreAdministrationAgentPK;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.model.repository.sirh.IAgentRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.service.ISpCarrService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.CarriereDto;
import nc.noumea.mairie.web.dto.DateAvctDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.GradeDto;
import nc.noumea.mairie.web.dto.ParcoursProDto;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class CalculEaeServiceTest {

	@Test
	public void getAffectationActiveByAgent() throws ParseException {

		Agent agent = new Agent();
		agent.setNomatr(5138);
		agent.setIdAgent(9005138);

		TitrePoste titrePoste = new TitrePoste();
		titrePoste.setLibTitrePoste("libTitrePoste");

		Budget budget = new Budget();
		budget.setLibelleBudget("libelleBudget");
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("libHor");
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("libHorRegl");

		CadreEmploi cadreEmploiGrade = new CadreEmploi();
		cadreEmploiGrade.setLibelleCadreEmploi("libelleCadreEmploi");
		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCadreEmploiGrade(cadreEmploiGrade);

		Spgradn gradePoste = new Spgradn();
		gradePoste.setGradeInitial("gradeInitial");
		gradePoste.setGradeGenerique(gradeGenerique);

		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("libelleLieu");

		NiveauEtude niveauEtude = new NiveauEtude();
		niveauEtude.setLibelleNiveauEtude("libelleNiveauEtude");

		FichePoste superieurHierarchique = new FichePoste();
		superieurHierarchique.setIdServiceADS(2);
		superieurHierarchique.setTitrePoste(titrePoste);
		superieurHierarchique.setBudget(budget);
		superieurHierarchique.setBudgete(budgete);
		superieurHierarchique.setReglementaire(reglementaire);
		superieurHierarchique.setGradePoste(gradePoste);
		superieurHierarchique.setLieuPoste(lieuPoste);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setSuperieurHierarchique(superieurHierarchique);
		fichePoste.setIdServiceADS(2);
		fichePoste.setTitrePoste(titrePoste);
		fichePoste.setBudget(budget);
		fichePoste.setBudgete(budgete);
		fichePoste.setReglementaire(reglementaire);
		fichePoste.setGradePoste(gradePoste);
		fichePoste.setLieuPoste(lieuPoste);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("NumFP");
		fichePoste.setNiveauEtude(niveauEtude);

		FichePoste fichePosteSecondaire = new FichePoste();
		fichePosteSecondaire.setSuperieurHierarchique(superieurHierarchique);
		fichePosteSecondaire.setIdServiceADS(2);
		fichePosteSecondaire.setTitrePoste(titrePoste);
		fichePosteSecondaire.setBudget(budget);
		fichePosteSecondaire.setBudgete(budgete);
		fichePosteSecondaire.setReglementaire(reglementaire);
		fichePosteSecondaire.setGradePoste(gradePoste);
		fichePosteSecondaire.setLieuPoste(lieuPoste);
		fichePosteSecondaire.setMissions("missions2");
		fichePosteSecondaire.setNumFP("NumFP2");
		fichePosteSecondaire.setNiveauEtude(niveauEtude);

		Date dateDebutAff = new Date();
		Date dateFinAff = new Date();

		Affectation aff = new Affectation();
		aff.setAgent(agent);
		aff.setFichePoste(fichePoste);
		aff.setFichePosteSecondaire(fichePosteSecondaire);
		aff.setFichePoste(fichePoste);
		aff.setDateDebutAff(dateDebutAff);
		aff.setDateFinAff(dateFinAff);
		Set<Affectation> agents = new HashSet<Affectation>();
		agents.add(aff);

		fichePoste.setAgent(agents);
		fichePosteSecondaire.setAgent(agents);

		PositionAdmAgentDto positionAdmAgentDto = new PositionAdmAgentDto();
		positionAdmAgentDto.setCdpadm("cdpadm");
		PositionAdmAgentDto positionAdmAgentDtoAncienne = new PositionAdmAgentDto();
		positionAdmAgentDtoAncienne.setCdpadm("cdpadmAncienne");

		CarriereDto carriereDto = new CarriereDto();
		carriereDto.setCodeCategorie(1);
		carriereDto.setGrade(new GradeDto(gradePoste));
		carriereDto.setLibelleCategorie("libelleCategorie");
		CarriereDto carriereDtoAncienne = new CarriereDto();
		carriereDtoAncienne.setCodeCategorie(2);
		carriereDtoAncienne.setLibelleCategorie("libelleCategorie2");

		Date dateObtention = new Date();
		DiplomeAgent diplomeAgent = new DiplomeAgent();
		diplomeAgent.setDateObtention(dateObtention);
		diplomeAgent.setIdDiplome(10);
		List<DiplomeAgent> lstDiplome = new ArrayList<DiplomeAgent>();
		lstDiplome.add(diplomeAgent);

		FormationAgent formationAgent = new FormationAgent();
		formationAgent.setIdFormation(11);
		formationAgent.setDureeFormation(10);
		List<FormationAgent> listFormationAgent = new ArrayList<FormationAgent>();
		listFormationAgent.add(formationAgent);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getAffectationActiveByAgentPourEAE(Mockito.anyInt())).thenReturn(aff);

		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
		Mockito.when(sirhRepository.getListDiplomeByAgent(Mockito.anyInt())).thenReturn(lstDiplome);
		Mockito.when(sirhRepository.getListFormationAgentByAnnee(Mockito.anyInt(), Mockito.anyInt())).thenReturn(listFormationAgent);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgent(Mockito.anyInt())).thenReturn(agent);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(Mockito.anyInt())).thenReturn(positionAdmAgentDto);
		Mockito.when(spadmnService.chercherPositionAdmAgentAncienne(Mockito.anyInt())).thenReturn(positionAdmAgentDtoAncienne);

		ISpCarrService spCarrService = Mockito.mock(ISpCarrService.class);
		Mockito.when(spCarrService.getCarriereFonctionnaireAncienne(Mockito.anyInt())).thenReturn(carriereDtoAncienne);
		Mockito.when(spCarrService.getCarriereActive(Mockito.anyInt())).thenReturn(carriereDto);

		SpmtsrId spMtsrId = new SpmtsrId();
		spMtsrId.setDatdeb(20101010);
		spMtsrId.setServi("servi");
		Spmtsr spMtsr = new Spmtsr();
		spMtsr.setId(spMtsrId);
		List<Spmtsr> listParcoursPro = new ArrayList<Spmtsr>();
		listParcoursPro.add(spMtsr);
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListSpmtsr(Mockito.anyInt())).thenReturn(listParcoursPro);

		EntiteDto siservDirection = new EntiteDto();
		siservDirection.setIdEntite(2);
		siservDirection.setLabel("direction");
		EntiteDto siservSection = new EntiteDto();
		siservSection.setIdEntite(3);
		siservSection.setLabel("section");
		EntiteDto siservService = new EntiteDto();
		siservService.setIdEntite(1);
		siservService.setLabel("liServ");

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(siservService);
		Mockito.when(adsWSConsumer.getAffichageDirection(Mockito.anyInt())).thenReturn(siservDirection);
		Mockito.when(adsWSConsumer.getAffichageSection(Mockito.anyInt())).thenReturn(siservSection);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(siservService);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(spMtsr.getId().getServi())).thenReturn(siservService);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);
		ReflectionTestUtils.setField(calculEaeService, "spCarrService", spCarrService);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);

		CalculEaeInfosDto result = calculEaeService.getAffectationActiveByAgent(9005138, 2010);

		assertEquals(result.getCarriereActive().getCodeCategorie().intValue(), 1);

		assertEquals(result.getFichePostePrincipale().getNumero(), "NumFP");
		assertEquals(result.getFichePostePrincipale().getIdAgent().intValue(), 9005138);
		assertEquals(result.getFichePostePrincipale().getDirection(), "direction");
		assertEquals(result.getFichePostePrincipale().getTitre(), "libTitrePoste");
		assertEquals(result.getFichePostePrincipale().getBudget(), "libelleBudget");
		assertEquals(result.getFichePostePrincipale().getBudgete(), "libHor");
		assertEquals(result.getFichePostePrincipale().getReglementaire(), "libHorRegl");
		assertEquals(result.getFichePostePrincipale().getCadreEmploi(), "libelleCadreEmploi");
		assertEquals(result.getFichePostePrincipale().getNiveauEtudes(), "libelleNiveauEtude");
		assertEquals(result.getFichePostePrincipale().getIdServiceADS(), new Integer(2));
		assertEquals(result.getFichePostePrincipale().getSection(), "section");
		assertEquals(result.getFichePostePrincipale().getLieu(), "libelleLieu");
		assertEquals(result.getFichePostePrincipale().getGradePoste(), "gradeInitial");
		assertEquals(result.getFichePostePrincipale().getMissions(), "missions");

		assertEquals(result.getFichePosteSecondaire().getNumero(), "NumFP2");
		assertEquals(result.getFichePosteSecondaire().getIdAgent().intValue(), 9005138);
		assertEquals(result.getFichePosteSecondaire().getDirection(), "direction");
		assertEquals(result.getFichePosteSecondaire().getTitre(), "libTitrePoste");
		assertEquals(result.getFichePosteSecondaire().getBudget(), "libelleBudget");
		assertEquals(result.getFichePosteSecondaire().getBudgete(), "libHor");
		assertEquals(result.getFichePosteSecondaire().getReglementaire(), "libHorRegl");
		assertEquals(result.getFichePosteSecondaire().getCadreEmploi(), "libelleCadreEmploi");
		assertEquals(result.getFichePosteSecondaire().getNiveauEtudes(), "libelleNiveauEtude");
		assertEquals(result.getFichePosteSecondaire().getIdServiceADS(), new Integer(2));
		assertEquals(result.getFichePosteSecondaire().getService(), "liServ");
		assertEquals(result.getFichePosteSecondaire().getSection(), "section");
		assertEquals(result.getFichePosteSecondaire().getLieu(), "libelleLieu");
		assertEquals(result.getFichePosteSecondaire().getGradePoste(), "gradeInitial");
		assertEquals(result.getFichePosteSecondaire().getMissions(), "missions2");

		assertEquals(result.getFichePosteResponsable().getTitrePoste().getLibTitrePoste(), "libTitrePoste");

		assertEquals(result.getListParcoursPro().size(), 1);
		assertEquals(result.getListParcoursPro().get(0).getDirection(), "direction");
		assertEquals(result.getListParcoursPro().get(0).getService(), "liServ");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		assertEquals(result.getListParcoursPro().get(0).getDateDebut(), sdf.parse("20101010"));

		assertEquals(result.getListDiplome().size(), 1);
		assertEquals(result.getListDiplome().get(0).getDateObtention(), dateObtention);
		assertEquals(result.getListDiplome().get(0).getIdDiplome().intValue(), 10);

		assertEquals(result.getListFormation().size(), 1);
		assertEquals(result.getListFormation().get(0).getIdFormation().intValue(), 11);
		assertEquals(result.getListFormation().get(0).getDureeFormation().intValue(), 10);

		assertEquals(result.getPositionAdmAgentEnCours().getCdpadm(), "cdpadm");
		assertEquals(result.getPositionAdmAgentAncienne().getCdpadm(), "cdpadmAncienne");

		assertEquals(result.getCarriereActive().getCodeCategorie().intValue(), 1);
		assertEquals(result.getCarriereActive().getLibelleCategorie(), "libelleCategorie");

		assertEquals(result.getCarriereFonctionnaireAncienne().getCodeCategorie().intValue(), 2);
		assertEquals(result.getCarriereFonctionnaireAncienne().getLibelleCategorie(), "libelleCategorie2");

		assertEquals(result.getDateDebut(), dateDebutAff);
		assertEquals(result.getDateFin(), dateFinAff);
	}

	@Test
	public void getListeAffectationsAgentAvecService_returnNoResult() {

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentAvecService(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(9005138, 1);

		assertEquals(0, result.size());
	}

	@Test
	public void getListeAffectationsAgentAvecService_return1Result() {

		Date dateDebutAff = new Date();
		Date dateFinAff = new Date();

		Affectation affectation = new Affectation();
		affectation.setDateDebutAff(dateDebutAff);
		affectation.setDateFinAff(dateFinAff);

		List<Affectation> listAffectation = new ArrayList<Affectation>();
		listAffectation.add(affectation);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentAvecService(Mockito.anyInt(), Mockito.anyInt())).thenReturn(listAffectation);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(9005138, 1);

		assertEquals(1, result.size());
		assertEquals(result.get(0).getDateDebut(), dateDebutAff);
		assertEquals(result.get(0).getDateFin(), dateFinAff);
	}

	@Test
	public void getListeAffectationsAgentAvecFP_returnNoResult() {

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentAvecFP(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecFP(9005138, 1);

		assertEquals(0, result.size());
	}

	@Test
	public void getListeAffectationsAgentAvecFP_return1Result() {

		Date dateDebutAff = new Date();
		Date dateFinAff = new Date();

		Affectation affectation = new Affectation();
		affectation.setDateDebutAff(dateDebutAff);
		affectation.setDateFinAff(dateFinAff);

		List<Affectation> listAffectation = new ArrayList<Affectation>();
		listAffectation.add(affectation);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentAvecFP(Mockito.anyInt(), Mockito.anyInt())).thenReturn(listAffectation);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);

		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecFP(9005138, 1);

		assertEquals(1, result.size());
		assertEquals(result.get(0).getDateDebut(), dateDebutAff);
		assertEquals(result.get(0).getDateFin(), dateFinAff);
	}

	@Test
	public void getListeAgentEligibleEAESansAffectes_return1Result() {

		Agent agent = new Agent();
		agent.setPrenomUsage("prenomUsage");
		agent.setNomUsage("nomUsage");
		agent.setIdAgent(1);
		agent.setTitre("0");

		List<Integer> listNoMatr = new ArrayList<Integer>();
		listNoMatr.add(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentEligibleEAESansAffectes(Mockito.anyInt())).thenReturn(agent);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPA()).thenReturn(listNoMatr);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAESansAffectes();

		assertEquals(1, result.size());
		assertEquals(result.get(0).getIdAgent().intValue(), 1);
		assertEquals(result.get(0).getNom(), "nomUsage");
		assertEquals(result.get(0).getPrenom(), "prenomUsage");
	}

	@Test
	public void getListeAgentEligibleEAESansAffectes_returnNoResult_noAgent() {

		List<Integer> listNoMatr = new ArrayList<Integer>();
		listNoMatr.add(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentEligibleEAESansAffectes(Mockito.anyInt())).thenReturn(null);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPA()).thenReturn(listNoMatr);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAESansAffectes();

		assertEquals(0, result.size());
	}

	@Test
	public void getListeAgentEligibleEAESansAffectes_returnNoResult_noListAgent() {

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentEligibleEAESansAffectes(Mockito.anyInt())).thenReturn(null);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPA()).thenReturn(null);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAESansAffectes();

		assertEquals(0, result.size());
	}

	@Test
	public void chercherAutreAdministrationAgentAncienne_return1result() {

		AutreAdministrationAgentPK autreAdministrationAgentPK = new AutreAdministrationAgentPK();
		autreAdministrationAgentPK.setIdAutreAdmin(1);

		AutreAdministrationAgent autreAdministrationAgent = new AutreAdministrationAgent();
		autreAdministrationAgent.setAutreAdministrationAgentPK(autreAdministrationAgentPK);

		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
		Mockito.when(sirhRepository.chercherAutreAdministrationAgentAncienne(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(autreAdministrationAgent);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);

		AutreAdministrationAgentDto result = calculEaeService.chercherAutreAdministrationAgentAncienne(9005138, true);

		assertEquals(1, result.getIdAutreAdmin().intValue());
	}

	@Test
	public void chercherAutreAdministrationAgentAncienne_returnNoResult() {

		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
		Mockito.when(sirhRepository.chercherAutreAdministrationAgentAncienne(Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(null);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);

		AutreAdministrationAgentDto result = calculEaeService.chercherAutreAdministrationAgentAncienne(9005138, true);

		assertNull(result);
	}

	@Test
	public void getListeAutreAdministrationAgent_return1result() {

		AutreAdministrationAgentPK autreAdministrationAgentPK = new AutreAdministrationAgentPK();
		autreAdministrationAgentPK.setIdAutreAdmin(1);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setAutreAdministrationAgentPK(autreAdministrationAgentPK);

		List<AutreAdministrationAgent> list = new ArrayList<AutreAdministrationAgent>();
		list.add(aaa);

		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
		Mockito.when(sirhRepository.getListeAutreAdministrationAgent(Mockito.anyInt())).thenReturn(list);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);

		List<AutreAdministrationAgentDto> result = calculEaeService.getListeAutreAdministrationAgent(9005138);

		assertEquals(1, result.size());
	}

	@Test
	public void getListeAutreAdministrationAgent_returnNoResult() {

		List<AutreAdministrationAgent> list = new ArrayList<AutreAdministrationAgent>();

		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
		Mockito.when(sirhRepository.getListeAutreAdministrationAgent(Mockito.anyInt())).thenReturn(list);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);

		List<AutreAdministrationAgentDto> result = calculEaeService.getListeAutreAdministrationAgent(9005138);

		assertEquals(0, result.size());
	}

	@Test
	public void getListeAgentEligibleEAEAffectes_return1Result() {

		Agent agent = new Agent();
		agent.setPrenomUsage("prenomUsage");
		agent.setNomUsage("nomUsage");
		agent.setIdAgent(1);
		agent.setTitre("0");

		List<Integer> listNoMatr = new ArrayList<Integer>();
		listNoMatr.add(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(agent);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(listNoMatr);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();

		assertEquals(1, result.size());
		assertEquals(result.get(0).getIdAgent().intValue(), 1);
		assertEquals(result.get(0).getNom(), "nomUsage");
		assertEquals(result.get(0).getPrenom(), "prenomUsage");
	}

	@Test
	public void getListeAgentEligibleEAEAffectes_returnNoResult_noAgent() {

		List<Integer> listNoMatr = new ArrayList<Integer>();
		listNoMatr.add(1);

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(null);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(listNoMatr);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();

		assertEquals(0, result.size());
	}

	@Test
	public void getListeAgentEligibleEAEAffectes_returnNoResult_noListAgent() {

		IAgentRepository agentRepository = Mockito.mock(IAgentRepository.class);
		Mockito.when(agentRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(null);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(null);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "agentRepository", agentRepository);
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);

		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();

		assertEquals(0, result.size());
	}

	@Test
	public void getCalculDateAvancement_returnContractuel() {
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(4);
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setId(id);
		carr.setCategorie(categorie);

		PositionAdmAgentDto paDto = new PositionAdmAgentDto();
		paDto.setCdpadm("01");

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getCarriereActive(5138)).thenReturn(carr);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(5138)).thenReturn(paDto);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);

		DateAvctDto result = calculEaeService.getCalculDateAvancement(9005138);

		assertEquals(new DateTime(2016, 1, 1, 0, 0, 0, 0).toDate(), result.getDateAvct());
	}

	@Test
	public void getCalculDateAvancement_returnStagiaire() {
		Spgradn gradeSuivant = new Spgradn();
		gradeSuivant.setCdgrad("suiv");
		Spgradn grade = new Spgradn();
		grade.setCdgrad("grade");
		grade.setGradeSuivant(gradeSuivant);
		grade.setDureeMoyenne(24);
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(18);
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setId(id);
		carr.setCategorie(categorie);
		carr.setGrade(grade);

		PositionAdmAgentDto paDto = new PositionAdmAgentDto();
		paDto.setCdpadm("01");

		List<Spcarr> list = new ArrayList<>();
		list.add(carr);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getCarriereActive(5138)).thenReturn(carr);
		Mockito.when(spcarrRepository.listerCarriereAvecGradeEtStatut(5138, "grade", 18)).thenReturn(list);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(5138)).thenReturn(paDto);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);

		DateAvctDto result = calculEaeService.getCalculDateAvancement(9005138);

		assertEquals(new DateTime(2015, 1, 1, 0, 0, 0, 0).toDate(), result.getDateAvct());
	}

	@Test
	public void getCalculDateAvancement_returnFonctionnaire() {
		Spgradn gradeSuivant = new Spgradn();
		gradeSuivant.setCdgrad("suiv");
		Spgradn grade = new Spgradn();
		grade.setCdgrad("grade");
		grade.setGradeSuivant(gradeSuivant);
		grade.setDureeMoyenne(20);
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(20);
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setId(id);
		carr.setCategorie(categorie);
		carr.setGrade(grade);
		carr.setAccAnnee(0);
		carr.setAccMois(0);
		carr.setAccJour(0);
		carr.setBmAnnee(0);
		carr.setBmMois(0);
		carr.setBmJour(0);

		PositionAdmAgentDto paDto = new PositionAdmAgentDto();
		paDto.setCdpadm("01");

		List<Spcarr> list = new ArrayList<>();
		list.add(carr);

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getCarriereActive(5138)).thenReturn(carr);
		Mockito.when(spcarrRepository.listerCarriereAvecGradeEtStatut(5138, "grade", 20)).thenReturn(list);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(5138)).thenReturn(paDto);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);

		DateAvctDto result = calculEaeService.getCalculDateAvancement(9005138);

		assertEquals(new DateTime(2015, 9, 1, 0, 0, 0, 0).toDate(), result.getDateAvct());
	}

	@Test
	public void getCalculDateAvancement_returnNotEligible() {
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(3);
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setId(id);
		carr.setCategorie(categorie);

		PositionAdmAgentDto paDto = new PositionAdmAgentDto();
		paDto.setCdpadm("01");

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getCarriereActive(5138)).thenReturn(carr);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(5138)).thenReturn(paDto);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);

		DateAvctDto result = calculEaeService.getCalculDateAvancement(9005138);

		assertNull(result.getDateAvct());
	}

	@Test
	public void getCalculDateAvancement_returnNotPAEligible() {
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(3);
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setId(id);
		carr.setCategorie(categorie);

		PositionAdmAgentDto paDto = new PositionAdmAgentDto();
		paDto.setCdpadm("DA");

		ISpcarrRepository spcarrRepository = Mockito.mock(ISpcarrRepository.class);
		Mockito.when(spcarrRepository.getCarriereActive(5138)).thenReturn(carr);

		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
		Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(5138)).thenReturn(paDto);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "spcarrRepository", spcarrRepository);
		ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);

		DateAvctDto result = calculEaeService.getCalculDateAvancement(9005138);

		assertNull(result.getDateAvct());
	}

	@Test
	public void getListParcoursPro_MultiAff() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(120);
		fichePoste.setIdServiceADS(1);
		fichePoste.setIdServi("DCCA");

		Affectation aff2 = new Affectation();
		aff2.setAgent(ag);
		aff2.setFichePoste(fichePoste);
		aff2.setDateDebutAff(new DateTime(2010, 01, 02, 0, 0, 0).toDate());
		aff2.setDateFinAff(null);

		Affectation aff = new Affectation();
		aff.setAgent(ag);
		aff.setFichePoste(fichePoste);
		aff.setDateDebutAff(new DateTime(2009, 01, 01, 0, 0, 0).toDate());
		aff.setDateFinAff(new DateTime(2010, 01, 01, 0, 0, 0).toDate());

		List<Affectation> listAff = new ArrayList<Affectation>();
		listAff.add(aff);
		listAff.add(aff2);

		EntiteDto entiteDto = new EntiteDto();
		entiteDto.setLabel("Laben entite");

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(listAff);
		Mockito.when(affectationRepository.chercherAffectationAgentAvecDateDebut(ag.getIdAgent(), new DateTime(aff.getDateFinAff()).plusDays(1).toDate())).thenReturn(aff2);

		IFichePosteRepository fichePosteRepository = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteRepository.chercherFichePoste(120)).thenReturn(fichePoste);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteDto);
		Mockito.when(adsWSConsumer.getAffichageDirection(1)).thenReturn(null);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.listerSpmtsrAvecAgentAPartirDateOrderDateDeb(5138, 20090101)).thenReturn(new ArrayList<Spmtsr>());

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "fichePosteRepository", fichePosteRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), "Laben entite");
		assertEquals(result.get(0).getDateDebut(), aff.getDateDebutAff());
		assertNull(result.get(0).getDateFin());

	}

	@Test
	public void getListParcoursPro_MultiAff_WithTrouParcours() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(121);
		fichePoste2.setIdServiceADS(12);
		fichePoste2.setIdServi("DCCB");

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(120);
		fichePoste.setIdServiceADS(1);
		fichePoste.setIdServi("DCCA");

		Affectation aff3 = new Affectation();
		aff3.setAgent(ag);
		aff3.setFichePoste(fichePoste2);
		aff3.setDateDebutAff(new DateTime(2008, 01, 01, 0, 0, 0).toDate());
		aff3.setDateFinAff(new DateTime(2008, 12, 29, 0, 0, 0).toDate());

		Affectation aff2 = new Affectation();
		aff2.setAgent(ag);
		aff2.setFichePoste(fichePoste);
		aff2.setDateDebutAff(new DateTime(2010, 01, 02, 0, 0, 0).toDate());
		aff2.setDateFinAff(null);

		Affectation aff = new Affectation();
		aff.setAgent(ag);
		aff.setFichePoste(fichePoste);
		aff.setDateDebutAff(new DateTime(2009, 01, 01, 0, 0, 0).toDate());
		aff.setDateFinAff(new DateTime(2010, 01, 01, 0, 0, 0).toDate());

		List<Affectation> listAff = new ArrayList<Affectation>();
		listAff.add(aff3);
		listAff.add(aff);
		listAff.add(aff2);

		EntiteDto entiteDto2 = new EntiteDto();
		entiteDto2.setLabel("Laben entite 12");

		EntiteDto entiteDto = new EntiteDto();
		entiteDto.setLabel("Laben entite");

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(listAff);
		Mockito.when(affectationRepository.chercherAffectationAgentAvecDateDebut(ag.getIdAgent(), new DateTime(aff.getDateFinAff()).plusDays(1).toDate())).thenReturn(aff2);

		IFichePosteRepository fichePosteRepository = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteRepository.chercherFichePoste(120)).thenReturn(fichePoste);
		Mockito.when(fichePosteRepository.chercherFichePoste(121)).thenReturn(fichePoste2);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteDto);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(12)).thenReturn(entiteDto2);
		Mockito.when(adsWSConsumer.getAffichageDirection(1)).thenReturn(null);
		Mockito.when(adsWSConsumer.getAffichageDirection(12)).thenReturn(null);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.listerSpmtsrAvecAgentAPartirDateOrderDateDeb(5138, new Integer(new SimpleDateFormat("yyyyMMdd").format(aff3.getDateDebutAff())))).thenReturn(
				new ArrayList<Spmtsr>());

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "fichePosteRepository", fichePosteRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 2);

		assertEquals(result.get(1).getDirection(), "");
		assertEquals(result.get(1).getDateDebut(), aff3.getDateDebutAff());
		assertEquals(result.get(1).getDateFin(), aff3.getDateFinAff());
		assertEquals(result.get(1).getService(), "Laben entite 12");

		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), "Laben entite");
		assertEquals(result.get(0).getDateDebut(), aff.getDateDebutAff());
		assertNull(result.get(0).getDateFin());

	}

	@Test
	public void getListParcoursPro_MultiAff_WithTrouParcours_WithSpmtsr() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(121);
		fichePoste2.setIdServiceADS(12);
		fichePoste2.setIdServi("DCCB");

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(120);
		fichePoste.setIdServiceADS(1);
		fichePoste.setIdServi("DCCA");

		Affectation aff3 = new Affectation();
		aff3.setAgent(ag);
		aff3.setFichePoste(fichePoste2);
		aff3.setDateDebutAff(new DateTime(2008, 01, 01, 0, 0, 0).toDate());
		aff3.setDateFinAff(new DateTime(2008, 12, 29, 0, 0, 0).toDate());

		Affectation aff2 = new Affectation();
		aff2.setAgent(ag);
		aff2.setFichePoste(fichePoste);
		aff2.setDateDebutAff(new DateTime(2010, 01, 02, 0, 0, 0).toDate());
		aff2.setDateFinAff(null);

		Affectation aff = new Affectation();
		aff.setAgent(ag);
		aff.setFichePoste(fichePoste);
		aff.setDateDebutAff(new DateTime(2009, 01, 01, 0, 0, 0).toDate());
		aff.setDateFinAff(new DateTime(2010, 01, 01, 0, 0, 0).toDate());

		List<Affectation> listAff = new ArrayList<Affectation>();
		listAff.add(aff3);
		listAff.add(aff);
		listAff.add(aff2);

		EntiteDto entiteDto2 = new EntiteDto();
		entiteDto2.setLabel("Laben entite 12");

		EntiteDto entiteDto = new EntiteDto();
		entiteDto.setLabel("Laben entite");

		EntiteDto directionDTO = new EntiteDto();
		directionDTO.setLabel("Direction");

		SpmtsrId s1Id = new SpmtsrId();
		s1Id.setNomatr(5138);
		s1Id.setDatdeb(20070101);
		s1Id.setServi("DCCA");
		Spmtsr s1 = new Spmtsr();
		s1.setId(s1Id);
		s1.setDatfin(20070605);

		List<Spmtsr> listSpmtsr = new ArrayList<Spmtsr>();
		listSpmtsr.add(s1);

		EntiteDto siservDto = new EntiteDto();
		siservDto.setLabel("SISERV AS400");

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(listAff);
		Mockito.when(affectationRepository.chercherAffectationAgentAvecDateDebut(ag.getIdAgent(), new DateTime(aff.getDateFinAff()).plusDays(1).toDate())).thenReturn(aff2);

		IFichePosteRepository fichePosteRepository = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteRepository.chercherFichePoste(120)).thenReturn(fichePoste);
		Mockito.when(fichePosteRepository.chercherFichePoste(121)).thenReturn(fichePoste2);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteDto);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(12)).thenReturn(entiteDto2);
		Mockito.when(adsWSConsumer.getAffichageDirection(1)).thenReturn(null);
		Mockito.when(adsWSConsumer.getAffichageDirection(12)).thenReturn(directionDTO);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(s1Id.getServi())).thenReturn(siservDto);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.listerSpmtsrAvecAgentAPartirDateOrderDateDeb(5138, new Integer(new SimpleDateFormat("yyyyMMdd").format(aff3.getDateDebutAff())))).thenReturn(listSpmtsr);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "fichePosteRepository", fichePosteRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 3);

		assertEquals(result.get(2).getDirection(), "");
		assertEquals(result.get(2).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s1.getId().getDatdeb().toString()));
		assertEquals(result.get(2).getDateFin(), new SimpleDateFormat("yyyyMMdd").parse(s1.getDatfin().toString()));
		assertEquals(result.get(2).getService(), "SISERV AS400");

		assertEquals(result.get(1).getDirection(), "Direction");
		assertEquals(result.get(1).getDateDebut(), aff3.getDateDebutAff());
		assertEquals(result.get(1).getDateFin(), aff3.getDateFinAff());
		assertEquals(result.get(1).getService(), "Laben entite 12");

		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), "Laben entite");
		assertEquals(result.get(0).getDateDebut(), aff.getDateDebutAff());
		assertNull(result.get(0).getDateFin());

	}

	@Test
	public void getListParcoursPro_NoAff_MultiSpmtsr() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		EntiteDto siserv2Dto = new EntiteDto();
		siserv2Dto.setLabel("SISERV2 AS400");

		EntiteDto siservDto = new EntiteDto();
		siservDto.setLabel("SISERV AS400");

		SpmtsrId s2Id = new SpmtsrId();
		s2Id.setNomatr(5138);
		s2Id.setDatdeb(20060101);
		s2Id.setServi("DCCB");
		Spmtsr s2 = new Spmtsr();
		s2.setId(s2Id);
		s2.setDatfin(20070603);

		SpmtsrId s1Id = new SpmtsrId();
		s1Id.setNomatr(5138);
		s1Id.setDatdeb(20070604);
		s1Id.setServi("DCCA");
		Spmtsr s1 = new Spmtsr();
		s1.setId(s1Id);
		s1.setDatfin(20070605);

		List<Spmtsr> listSpmtsr = new ArrayList<Spmtsr>();
		listSpmtsr.add(s1);
		listSpmtsr.add(s2);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(new ArrayList<Affectation>());

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(s2Id.getServi())).thenReturn(siserv2Dto);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(s1Id.getServi())).thenReturn(siservDto);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListSpmtsr(5138)).thenReturn(listSpmtsr);
		Mockito.when(mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(5138, s1.getDatfin() + 1)).thenReturn(s2);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 2);

		assertEquals(result.get(1).getDirection(), "");
		assertEquals(result.get(1).getService(), siserv2Dto.getLabel());
		assertEquals(result.get(1).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s2.getId().getDatdeb().toString()));
		assertEquals(result.get(1).getDateFin(), new SimpleDateFormat("yyyyMMdd").parse(s2.getDatfin().toString()));

		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), siservDto.getLabel());
		assertEquals(result.get(0).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s1.getId().getDatdeb().toString()));
		assertEquals(result.get(0).getDateFin(), new SimpleDateFormat("yyyyMMdd").parse(s1.getDatfin().toString()));

	}

	@Test
	public void getListParcoursPro_NoAff_MultiSpmtsr_bis() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		EntiteDto siservDto = new EntiteDto();
		siservDto.setLabel("SISERV AS400");

		SpmtsrId s2Id = new SpmtsrId();
		s2Id.setNomatr(5138);
		s2Id.setDatdeb(20060101);
		s2Id.setServi("DCCA");
		Spmtsr s2 = new Spmtsr();
		s2.setId(s2Id);
		s2.setDatfin(20070603);

		SpmtsrId s1Id = new SpmtsrId();
		s1Id.setNomatr(5138);
		s1Id.setDatdeb(20070604);
		s1Id.setServi("DCCA");
		Spmtsr s1 = new Spmtsr();
		s1.setId(s1Id);
		s1.setDatfin(20070605);

		List<Spmtsr> listSpmtsr = new ArrayList<Spmtsr>();
		listSpmtsr.add(s2);
		listSpmtsr.add(s1);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(new ArrayList<Affectation>());

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(s1Id.getServi())).thenReturn(siservDto);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListSpmtsr(5138)).thenReturn(listSpmtsr);
		Mockito.when(mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(5138, s2.getDatfin() + 1)).thenReturn(s1);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 1);

		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), siservDto.getLabel());
		assertEquals(result.get(0).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s2.getId().getDatdeb().toString()));
		assertEquals(result.get(0).getDateFin(), new SimpleDateFormat("yyyyMMdd").parse(s1.getDatfin().toString()));

	}

	@Test
	public void getListParcoursPro_NoAff_MultiSpmtsr_TrouParcours() throws ParseException {
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		EntiteDto siservDto = new EntiteDto();
		siservDto.setLabel("SISERV AS400");

		SpmtsrId s3Id = new SpmtsrId();
		s3Id.setNomatr(5138);
		s3Id.setDatdeb(20070708);
		s3Id.setServi("DCCA");
		Spmtsr s3 = new Spmtsr();
		s3.setId(s3Id);
		s3.setDatfin(0);

		SpmtsrId s2Id = new SpmtsrId();
		s2Id.setNomatr(5138);
		s2Id.setDatdeb(20060101);
		s2Id.setServi("DCCA");
		Spmtsr s2 = new Spmtsr();
		s2.setId(s2Id);
		s2.setDatfin(20070603);

		SpmtsrId s1Id = new SpmtsrId();
		s1Id.setNomatr(5138);
		s1Id.setDatdeb(20070604);
		s1Id.setServi("DCCA");
		Spmtsr s1 = new Spmtsr();
		s1.setId(s1Id);
		s1.setDatfin(20070605);

		List<Spmtsr> listSpmtsr = new ArrayList<Spmtsr>();
		listSpmtsr.add(s2);
		listSpmtsr.add(s1);
		listSpmtsr.add(s3);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentOrderByDateAsc(ag.getIdAgent())).thenReturn(new ArrayList<Affectation>());

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByCodeServiceSISERV(s1Id.getServi())).thenReturn(siservDto);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListSpmtsr(5138)).thenReturn(listSpmtsr);
		Mockito.when(mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(5138, s2.getDatfin() + 1)).thenReturn(s1);

		CalculEaeService calculEaeService = new CalculEaeService();
		ReflectionTestUtils.setField(calculEaeService, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(calculEaeService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);

		List<ParcoursProDto> result = calculEaeService.getListParcoursPro(9005138, 5138);
		assertEquals(result.size(), 2);

		assertEquals(result.get(1).getDirection(), "");
		assertEquals(result.get(1).getService(), siservDto.getLabel());
		assertEquals(result.get(1).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s2.getId().getDatdeb().toString()));
		assertEquals(result.get(1).getDateFin(), new SimpleDateFormat("yyyyMMdd").parse(s1.getDatfin().toString()));

		assertEquals(result.get(0).getDirection(), "");
		assertEquals(result.get(0).getService(), siservDto.getLabel());
		assertEquals(result.get(0).getDateDebut(), new SimpleDateFormat("yyyyMMdd").parse(s3.getId().getDatdeb().toString()));
		assertNull(result.get(0).getDateFin());

	}
}
