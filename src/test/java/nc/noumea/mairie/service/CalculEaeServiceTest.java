package nc.noumea.mairie.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.Budget;
import nc.noumea.mairie.model.bean.CadreEmploi;
import nc.noumea.mairie.model.bean.DiplomeAgent;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.FormationAgent;
import nc.noumea.mairie.model.bean.NiveauEtude;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.TitrePoste;
import nc.noumea.mairie.model.pk.AutreAdministrationAgentPK;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISirhRepository;
import nc.noumea.mairie.model.service.CalculEaeService;
import nc.noumea.mairie.model.service.ISiservService;
import nc.noumea.mairie.model.service.ISpCarrService;
import nc.noumea.mairie.model.service.ISpadmnService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.CarriereDto;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;

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
		
		Siserv service = new Siserv();
			service.setServi("servi");
			service.setLiServ("liServ");
		
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
			superieurHierarchique.setService(service);
			superieurHierarchique.setTitrePoste(titrePoste);
			superieurHierarchique.setBudget(budget);
			superieurHierarchique.setBudgete(budgete);
			superieurHierarchique.setReglementaire(reglementaire);
			superieurHierarchique.setGradePoste(gradePoste);
			superieurHierarchique.setLieuPoste(lieuPoste);
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setSuperieurHierarchique(superieurHierarchique);
			fichePoste.setService(service);
			fichePoste.setTitrePoste(titrePoste);
			fichePoste.setBudget(budget);
			fichePoste.setBudgete(budgete);
			fichePoste.setReglementaire(reglementaire);
			fichePoste.setGradePoste(gradePoste);
			fichePoste.setLieuPoste(lieuPoste);
			fichePoste.setIdFichePoste(2);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("NumFP");
			fichePoste.setNiveauEtude(niveauEtude);

		FichePoste fichePosteSecondaire = new FichePoste();
			fichePosteSecondaire.setSuperieurHierarchique(superieurHierarchique);
			fichePosteSecondaire.setService(service);
			fichePosteSecondaire.setTitrePoste(titrePoste);
			fichePosteSecondaire.setBudget(budget);
			fichePosteSecondaire.setBudgete(budgete);
			fichePosteSecondaire.setReglementaire(reglementaire);
			fichePosteSecondaire.setGradePoste(gradePoste);
			fichePosteSecondaire.setLieuPoste(lieuPoste);
			fichePosteSecondaire.setIdFichePoste(3);
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
		
		Siserv siservDirection = new Siserv();
			siservDirection.setLiServ("direction");
		Siserv siservSection = new Siserv();
			siservSection.setLiServ("section");
		Siserv siservService = new Siserv();
			siservService.setLiServ("liServ");
		
		PositionAdmAgentDto positionAdmAgentDto = new PositionAdmAgentDto();
			positionAdmAgentDto.setCdpadm("cdpadm");
		PositionAdmAgentDto positionAdmAgentDtoAncienne = new PositionAdmAgentDto();
			positionAdmAgentDtoAncienne.setCdpadm("cdpadmAncienne");
		
		CarriereDto carriereDto = new CarriereDto();
			carriereDto.setCodeCategorie(1);
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
		List<FormationAgent> listFormationAgent =  new ArrayList<FormationAgent>();
			listFormationAgent.add(formationAgent);
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getAffectationActiveByAgent(Mockito.anyInt())).thenReturn(aff);
			Mockito.when(sirhRepository.getListDiplomeByAgent(Mockito.anyInt())).thenReturn(lstDiplome);
			Mockito.when(sirhRepository.getListFormationAgentByAnnee(Mockito.anyInt(), Mockito.anyInt())).thenReturn(listFormationAgent);
			
		ISiservService siservSrv = Mockito.mock(ISiservService.class);
			Mockito.when(siservSrv.getDirection(Mockito.anyString())).thenReturn(siservDirection);
			Mockito.when(siservSrv.getSection(Mockito.anyString())).thenReturn(siservSection);
			Mockito.when(siservSrv.getService(Mockito.anyString())).thenReturn(siservService);
		
		ISpadmnService spadmnService = Mockito.mock(ISpadmnService.class);
			Mockito.when(spadmnService.chercherPositionAdmAgentEnCours(Mockito.anyInt())).thenReturn(positionAdmAgentDto);
			Mockito.when(spadmnService.chercherPositionAdmAgentAncienne(Mockito.anyInt())).thenReturn(positionAdmAgentDtoAncienne);
		
		ISpCarrService spCarrService = Mockito.mock(ISpCarrService.class);
			Mockito.when(spCarrService.getCarriereFonctionnaireAncienne(Mockito.anyInt())).thenReturn(carriereDtoAncienne);
			Mockito.when(spCarrService.getCarriereActive(Mockito.anyInt())).thenReturn(carriereDto);
		
		Spmtsr spMtsr = new Spmtsr();
			spMtsr.setDatdeb(20101010);
			spMtsr.setServi("servi");
		List<Spmtsr> listParcoursPro = new ArrayList<Spmtsr>();
			listParcoursPro.add(spMtsr);
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
			Mockito.when(mairieRepository.getListSpmtsr(Mockito.anyInt())).thenReturn(listParcoursPro);
		
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
			ReflectionTestUtils.setField(calculEaeService, "siservSrv", siservSrv);
			ReflectionTestUtils.setField(calculEaeService, "spadmnService", spadmnService);
			ReflectionTestUtils.setField(calculEaeService, "spCarrService", spCarrService);
			ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);
		
		CalculEaeInfosDto result = calculEaeService.getAffectationActiveByAgent(9005138, 2010);
		
		assertEquals(result.getCarriereActive().getCodeCategorie().intValue(), 1);
		
		assertEquals(result.getFichePostePrincipale().getIdFichePoste().intValue(), 2);
		assertEquals(result.getFichePostePrincipale().getNumero(), "NumFP");
		assertEquals(result.getFichePostePrincipale().getIdAgent().intValue(), 9005138);
		assertEquals(result.getFichePostePrincipale().getDirection(), "direction");
		assertEquals(result.getFichePostePrincipale().getTitre(), "libTitrePoste");
		assertEquals(result.getFichePostePrincipale().getBudget(), "libelleBudget");
		assertEquals(result.getFichePostePrincipale().getBudgete(), "libHor");
		assertEquals(result.getFichePostePrincipale().getReglementaire(), "libHorRegl");
		assertEquals(result.getFichePostePrincipale().getCadreEmploi(), "libelleCadreEmploi");
		assertEquals(result.getFichePostePrincipale().getNiveauEtudes(), "libelleNiveauEtude");
		assertEquals(result.getFichePostePrincipale().getCodeService(), "servi");
		assertEquals(result.getFichePostePrincipale().getService(), "liServ");
		assertEquals(result.getFichePostePrincipale().getSection(), "section");
		assertEquals(result.getFichePostePrincipale().getLieu(), "libelleLieu");
		assertEquals(result.getFichePostePrincipale().getGradePoste(), "gradeInitial");
		assertEquals(result.getFichePostePrincipale().getMissions(), "missions");
		
		assertEquals(result.getFichePosteSecondaire().getIdFichePoste().intValue(), 3);
		assertEquals(result.getFichePosteSecondaire().getNumero(), "NumFP2");
		assertEquals(result.getFichePosteSecondaire().getIdAgent().intValue(), 9005138);
		assertEquals(result.getFichePosteSecondaire().getDirection(), "direction");
		assertEquals(result.getFichePosteSecondaire().getTitre(), "libTitrePoste");
		assertEquals(result.getFichePosteSecondaire().getBudget(), "libelleBudget");
		assertEquals(result.getFichePosteSecondaire().getBudgete(), "libHor");
		assertEquals(result.getFichePosteSecondaire().getReglementaire(), "libHorRegl");
		assertEquals(result.getFichePosteSecondaire().getCadreEmploi(), "libelleCadreEmploi");
		assertEquals(result.getFichePosteSecondaire().getNiveauEtudes(), "libelleNiveauEtude");
		assertEquals(result.getFichePosteSecondaire().getCodeService(), "servi");
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
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getListeAffectationsAgentAvecService(Mockito.anyInt(), Mockito.anyString())).thenReturn(null);
		
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
		
		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(9005138, "codeService");
		
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
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getListeAffectationsAgentAvecService(Mockito.anyInt(), Mockito.anyString())).thenReturn(listAffectation);
		
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
		
		List<CalculEaeInfosDto> result = calculEaeService.getListeAffectationsAgentAvecService(9005138, "codeService");
		
		assertEquals(1, result.size());
		assertEquals(result.get(0).getDateDebut(), dateDebutAff);
		assertEquals(result.get(0).getDateFin(), dateFinAff);
	}
	
	@Test
	public void getListeAffectationsAgentAvecFP_returnNoResult() {
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getListeAffectationsAgentAvecFP(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
		
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
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getListeAffectationsAgentAvecFP(Mockito.anyInt(), Mockito.anyInt())).thenReturn(listAffectation);
		
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
		
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
		
		List<java.math.BigDecimal> listNoMatr = new ArrayList<java.math.BigDecimal>();
			listNoMatr.add(new BigDecimal(1));
			
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(agent);
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(listNoMatr);
			
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
			ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);
		
		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();
		
		assertEquals(1, result.size());
		assertEquals(result.get(0).getIdAgent().intValue(), 1);
		assertEquals(result.get(0).getNom(), "nomUsage");
		assertEquals(result.get(0).getPrenom(), "prenomUsage");
	}
	
	@Test
	public void getListeAgentEligibleEAESansAffectes_returnNoResult_noAgent() {
		
		List<java.math.BigDecimal> listNoMatr = new ArrayList<java.math.BigDecimal>();
			listNoMatr.add(new BigDecimal(1));
			
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(null);
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(listNoMatr);
			
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
			ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);
		
		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void getListeAgentEligibleEAESansAffectes_returnNoResult_noListAgent() {
		
		ISirhRepository sirhRepository = Mockito.mock(ISirhRepository.class);
			Mockito.when(sirhRepository.getAgentWithListNomatr(Mockito.anyInt())).thenReturn(null);
		
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListeCarriereActiveAvecPAAffecte()).thenReturn(null);
			
		CalculEaeService calculEaeService = new CalculEaeService();
			ReflectionTestUtils.setField(calculEaeService, "sirhRepository", sirhRepository);
			ReflectionTestUtils.setField(calculEaeService, "mairieRepository", mairieRepository);
		
		List<AgentDto> result = calculEaeService.getListeAgentEligibleEAEAffectes();
		
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
}
