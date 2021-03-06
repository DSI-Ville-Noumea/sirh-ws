package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbarem;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.AvisCap;
import nc.noumea.mairie.model.bean.sirh.Cap;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.MotifAvct;
import nc.noumea.mairie.model.repository.sirh.AvancementRepository;
import nc.noumea.mairie.model.repository.sirh.IAvancementRepository;
import nc.noumea.mairie.web.dto.avancements.ArreteListDto;
import nc.noumea.mairie.web.dto.avancements.AvancementEaeDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;
import nc.noumea.mairie.ws.ISirhEaeWSConsumer;
import nc.noumea.mairie.ws.dto.CampagneEaeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AvancementsServiceTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testgetCorpsForCadreEmploi_noCorps_returnNull() {
		// Given
		int idCadreEmploi = 99;

		TypedQuery<Spgeng> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Spgeng>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<Spgeng> result = service.getCorpsForCadreEmploi(idCadreEmploi);

		// Then
		assertNull(result);
		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCadreEmploi", idCadreEmploi);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgetCorpsForCadreEmploi_2Corps_returnListOf3Strings() {
		// Given
		int idCadreEmploi = 99;
		Spgeng g1 = new Spgeng();
		g1.setCdgeng("toto");
		Spgeng g2 = new Spgeng();
		g2.setCdgeng("titi");

		TypedQuery<Spgeng> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(g1, g2));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		List<Spgeng> result = service.getCorpsForCadreEmploi(idCadreEmploi);

		// Then
		assertEquals(2, result.size());
		assertEquals("toto", result.get(0).getCdgeng());
		assertEquals("titi", result.get(1).getCdgeng());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCadreEmploi", idCadreEmploi);
	}

	@Test
	public void testcreateCommissionCorps_2AD_fill2AD() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(1);
		Cap cap = new Cap();
		Spgeng corps = new Spgeng();
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setIdMotifAvancement(new Integer(7));
		avct1.setAgent(ag);
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setIdMotifAvancement(new Integer(7));
		avct2.setAgent(ag);
		List<AvancementFonctionnaire> list = Arrays.asList(avct1, avct2);

		AvancementRepository sirhRepo = Mockito.mock(AvancementRepository.class);
		Mockito.when(sirhRepo.getDateAvancementsMinimaleAncienne(Mockito.anyInt())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", sirhRepo);

		// When
		CommissionAvancementCorpsDto dto = service.createCommissionCorps(cap, corps, list, false,null);

		// Then
		assertEquals(2, dto.getAvancementsDifferencies().getAvancementsItems().size());
		assertEquals(0, dto.getChangementClasses().getAvancementsItems().size());
	}

	@Test
	public void testcreateCommissionCorps_2CC_fill2CC() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(1);
		Cap cap = new Cap();
		Spgeng corps = new Spgeng();
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setIdMotifAvancement(new Integer(4));
		avct1.setAgent(ag);
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setIdMotifAvancement(new Integer(4));
		avct2.setAgent(ag);
		List<AvancementFonctionnaire> list = Arrays.asList(avct1, avct2);

		AvancementRepository sirhEMMock = Mockito.mock(AvancementRepository.class);
		Mockito.when(sirhEMMock.getDateAvancementsMinimaleAncienne(Mockito.anyInt())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", sirhEMMock);

		// When
		CommissionAvancementCorpsDto dto = service.createCommissionCorps(cap, corps, list, false,null);

		// Then
		assertEquals(0, dto.getAvancementsDifferencies().getAvancementsItems().size());
		assertEquals(2, dto.getChangementClasses().getAvancementsItems().size());
	}

	@Test
	public void testgetStatutFromCap_CapIsCOMMUNAL_Return12() {
		// Given
		Cap cap = new Cap();
		cap.setTypeCap("COMMUNAL");

		// When
		List<Integer> result = AvancementsService.getStatutFromCap(cap);

		// Then
		assertEquals(new Integer(1), result.get(0));
		assertEquals(new Integer(2), result.get(1));
	}

	@Test
	public void testgetStatutFromCap_CapIsTERRITORIAL_Return12() {
		// Given
		Cap cap = new Cap();
		cap.setTypeCap("TERRITORIAL");

		// When
		List<Integer> result = AvancementsService.getStatutFromCap(cap);

		// Then
		assertEquals(new Integer(18), result.get(0));
		assertEquals(new Integer(20), result.get(1));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgetCap_1Cap_returnCap() {
		// Given
		int idCap = 99;
		Cap cap = new Cap();

		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(cap));

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Cap result = service.getCap(idCap);

		// Then
		assertEquals(cap, result);

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgetCap_0Cap_returnNull() {
		// Given
		int idCap = 99;

		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		Cap result = service.getCap(idCap);

		// Then
		assertNull(result);

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}

	@Test
	public void testgetAvancementsEaesForCapAndCadreEmploi_CapDoesNotExist_returnEmptyList() {
		// Given
		int idCap = 99;

		@SuppressWarnings("unchecked")
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		ReturnMessageDto result = service.getAvancementsEaesForCapAndCadreEmploi(idCap, 0);

		// Then
		assertEquals(0, result.getInfos().size());

		Mockito.verify(mockQuery, Mockito.times(1)).setParameter("idCap", idCap);
	}

	@Test
	public void getCommissionsForCapAndCadreEmploi_CapDoesNotExist_returnNull() {
		// Given

		@SuppressWarnings("unchecked")
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		CommissionAvancementDto result = service.getCommissionsForCapAndCadreEmploi(1, 0, false, false,null);

		// Then
		assertEquals(0, result.getCommissionsParCorps().size());
	}

	@Test
	public void getCommissionsForCapAndCadreEmploi_returnCommissionAvancementDto() {
		// Given
		Cap cap = new Cap();
		cap.setIdCap(1);
		cap.setTypeCap("COMMUNAL");
		List<Cap> listeCap = new ArrayList<Cap>();
		listeCap.add(cap);

		Spgeng sp1 = new Spgeng();
		sp1.setLiGrad("LIB GRADE");
		sp1.setCdgeng("CDGENG");
		List<Spgeng> listeSpgeng = new ArrayList<Spgeng>();
		listeSpgeng.add(sp1);

		Agent agent = new Agent();
		agent.setIdAgent(9005138);
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setAgent(agent);
		avct1.setIdMotifAvancement(1);
		List<AvancementFonctionnaire> listeAvctFonct = new ArrayList<AvancementFonctionnaire>();
		listeAvctFonct.add(avct1);

		@SuppressWarnings("unchecked")
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeCap);
		@SuppressWarnings("unchecked")
		TypedQuery<Spgeng> mockQuerySpgeng = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuerySpgeng.getResultList()).thenReturn(listeSpgeng);
		@SuppressWarnings("unchecked")
		TypedQuery<AvancementFonctionnaire> mockQueryAvancementFonctionnaire = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryAvancementFonctionnaire.getResultList()).thenReturn(listeAvctFonct);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuerySpgeng);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AvancementFonctionnaire.class))).thenReturn(mockQueryAvancementFonctionnaire);

		AvancementRepository sirhRepo = Mockito.mock(AvancementRepository.class);
		Mockito.when(sirhRepo.getDateAvancementsMinimaleAncienne(Mockito.anyInt())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "avancementRepository", sirhRepo);

		// When
		CommissionAvancementDto result = service.getCommissionsForCapAndCadreEmploi(1, 0, false, false,null);

		// Then
		assertEquals(1, result.getCommissionsParCorps().size());
		assertEquals(sp1.getCdgeng(), result.getCommissionsParCorps().get(0).getCorps());
	}

	@Test
	public void getAvancementsEaesForCapAndCadreEmploi_CapDoesNotExist_returnNull() {
		// Given

		@SuppressWarnings("unchecked")
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Cap>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);

		// When
		ReturnMessageDto result = service.getAvancementsEaesForCapAndCadreEmploi(1, 0);

		// Then
		assertEquals(0, result.getInfos().size());
	}

	@Test
	public void getAvancementsEaesForCapAndCadreEmploi_returnList() {
		// Given
		Cap cap = new Cap();
		cap.setIdCap(1);
		cap.setTypeCap("COMMUNAL");
		List<Cap> listeCap = new ArrayList<Cap>();
		listeCap.add(cap);

		Spgeng sp1 = new Spgeng();
		sp1.setLiGrad("LIB GRADE");
		sp1.setCdgeng("CDGENG");
		List<Spgeng> listeSpgeng = new ArrayList<Spgeng>();
		listeSpgeng.add(sp1);

		List<Integer> listeIdAgent = new ArrayList<Integer>();
		listeIdAgent.add(9005138);

		List<String> listIdGed = new ArrayList<String>();
		listIdGed.add("MAINOU-1");

		@SuppressWarnings("unchecked")
		TypedQuery<Cap> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeCap);
		@SuppressWarnings("unchecked")
		TypedQuery<Spgeng> mockQuerySpgeng = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuerySpgeng.getResultList()).thenReturn(listeSpgeng);
		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQueryIdAgent = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryIdAgent.getResultList()).thenReturn(listeIdAgent);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery("getCapWithEmployeursAndRepresentants", Cap.class)).thenReturn(mockQuery);
		Mockito.when(sirhEMMock.createNamedQuery("getSpgengFromCadreEmploi", Spgeng.class)).thenReturn(mockQuerySpgeng);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(mockQueryIdAgent);

		ReturnMessageDto dto = new ReturnMessageDto();
		dto.getInfos().addAll(listIdGed);
		ISirhEaeWSConsumer sirhEaeWSConsumer = Mockito.mock(ISirhEaeWSConsumer.class);
		Mockito.when(sirhEaeWSConsumer.getEaesGedIdsForAgents(listeIdAgent, new DateTime().getYear())).thenReturn(dto);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "sirhEaeWSConsumer", sirhEaeWSConsumer);

		// When
		ReturnMessageDto result = service.getAvancementsEaesForCapAndCadreEmploi(1, 0);

		// Then
		assertEquals(1, result.getInfos().size());
		assertEquals(listIdGed.get(0), result.getInfos().get(0));
	}

	@Test
	public void createCommissionCorps_returnCommissionAvancementCorpsDto() {
		// Given
		Cap cap = new Cap();
		cap.setIdCap(1);
		cap.setRefCap("REF");
		cap.setTypeCap("COMMUNAL");
		List<Cap> listeCap = new ArrayList<Cap>();
		listeCap.add(cap);

		Spgeng sp1 = new Spgeng();
		sp1.setLiGrad("LIB GRADE");
		sp1.setCdgeng("CDGENG");

		Agent agent1 = new Agent();
		agent1.setIdAgent(9005138);
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setAgent(agent1);
		avct1.setIdMotifAvancement(4);

		Agent agent2 = new Agent();
		agent2.setIdAgent(9005131);
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setAgent(agent2);
		avct2.setIdMotifAvancement(6);
		List<AvancementFonctionnaire> listeAvctFonct = new ArrayList<AvancementFonctionnaire>();
		listeAvctFonct.add(avct1);
		listeAvctFonct.add(avct2);

		CampagneEaeDto campagne = new CampagneEaeDto();
		campagne.setAnnee(2014);

		ReturnMessageDto msg2 = new ReturnMessageDto();
		msg2.getInfos().add("Favorable");
		ReturnMessageDto msg1 = new ReturnMessageDto();
		msg1.getInfos().add("Durée minimale");

		ReturnMessageDto dto2 = new ReturnMessageDto();
		dto2.getInfos().add("2");
		ReturnMessageDto dto1 = new ReturnMessageDto();
		dto1.getInfos().add("1");

		ISirhEaeWSConsumer sirhEaeWSConsumer = Mockito.mock(ISirhEaeWSConsumer.class);
		Mockito.when(sirhEaeWSConsumer.getAvisSHDEae(1)).thenReturn(msg1);
		Mockito.when(sirhEaeWSConsumer.getAvisSHDEae(2)).thenReturn(msg2);
		Mockito.when(sirhEaeWSConsumer.getEaeCampagneOuverte()).thenReturn(campagne);
		Mockito.when(sirhEaeWSConsumer.findEaeByAgentAndYear(9005138, 2014)).thenReturn(dto1);
		Mockito.when(sirhEaeWSConsumer.findEaeByAgentAndYear(9005131, 2014)).thenReturn(dto2);

		AvancementRepository sirhEMMock = Mockito.mock(AvancementRepository.class);
		Mockito.when(sirhEMMock.getDateAvancementsMinimaleAncienne(Mockito.anyInt())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEaeWSConsumer", sirhEaeWSConsumer);
		ReflectionTestUtils.setField(service, "avancementRepository", sirhEMMock);

		// When
		CommissionAvancementCorpsDto result = service.createCommissionCorps(cap, sp1, listeAvctFonct, true,null);

		// Then
		assertEquals(cap.getRefCap(), result.getChangementClasses().getCap());
		assertEquals(cap.getRefCap(), result.getAvancementsDifferencies().getCap());
		assertEquals(1, result.getAvancementsDifferencies().getNbAgents());
		assertEquals(1, result.getChangementClasses().getNbAgents());
		assertEquals(1, result.getChangementClasses().getAvancementsItems().size());
		assertEquals(1, result.getAvancementsDifferencies().getNbAgents());
		assertEquals(1, result.getAvancementsDifferencies().getAvancementsItems().size());
	}

	@Test
	public void getArretesForUsers_returnArreteListDto() throws ParseException {
		// Given
		int idAgent1 = 9005138;
		int idAgent2 = 9005131;

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		Spbarem barem = new Spbarem();
		barem.setIban("iban");
		barem.setIna(20);

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("CDGRAD");
		gradeNouveau.setGradeGenerique(gradeGenerique);
		gradeNouveau.setGradeInitial("GRADE Initial");
		gradeNouveau.setBarem(barem);

		AvisCap avisCapEmployeur = new AvisCap();
		avisCapEmployeur.setIdAvisCap(1);
		avisCapEmployeur.setLibLong("Lib long avis employeur");

		Agent agent1 = new Agent();
		agent1.setIdAgent(idAgent1);
		agent1.setNomatr(5138);
		agent1.setTitre("1");
		agent1.setPrenomUsage("Prenom 1");
		agent1.setNomUsage("Nom 1");
		AvancementFonctionnaire avct1 = new AvancementFonctionnaire();
		avct1.setAgent(agent1);
		avct1.setIdMotifAvancement(4);
		avct1.setGradeNouveau(gradeNouveau);
		avct1.setNouvAccAnnee(1);
		avct1.setNouvAccMois(1);
		avct1.setNouvAccJour(1);
		avct1.setAnneeAvancement(2013);
		avct1.setDateAvctMini(new DateTime(2011, 03, 01, 0, 0, 0, 0).toDate());
		avct1.setDateAvctMoy(new DateTime(2011, 03, 02, 0, 0, 0, 0).toDate());
		avct1.setDateAvctMaxi(new DateTime(2011, 03, 03, 0, 0, 0, 0).toDate());
		avct1.setAvisCapEmployeur(avisCapEmployeur);

		Agent agent2 = new Agent();
		agent2.setIdAgent(idAgent2);
		agent2.setNomatr(5131);
		agent2.setTitre("0");
		agent2.setPrenomUsage("Prenom 2");
		agent2.setNomUsage("Nom 2");
		AvancementFonctionnaire avct2 = new AvancementFonctionnaire();
		avct2.setAgent(agent2);
		avct2.setIdMotifAvancement(6);
		avct2.setGradeNouveau(gradeNouveau);
		avct2.setNouvAccAnnee(0);
		avct2.setNouvAccMois(1);
		avct2.setNouvAccJour(2);
		avct2.setAnneeAvancement(2014);
		avct2.setDateAvctMini(new DateTime(2010, 04, 14, 0, 0, 0, 0).toDate());
		avct2.setDateAvctMoy(new DateTime(2010, 04, 15, 0, 0, 0, 0).toDate());
		avct2.setDateAvctMaxi(new DateTime(2010, 04, 16, 0, 0, 0, 0).toDate());
		avct2.setAvisCapEmployeur(avisCapEmployeur);
		List<AvancementFonctionnaire> listeAvctFonct = new ArrayList<AvancementFonctionnaire>();
		listeAvctFonct.add(avct1);
		listeAvctFonct.add(avct2);

		List<Integer> listId = new ArrayList<Integer>();
		listId.add(1);
		
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(4);

		Spcarr carr = new Spcarr();
		carr.setReferenceArrete(2013);
		carr.setDateArrete(0);
		carr.setCategorie(categorie);

		@SuppressWarnings("unchecked")
		TypedQuery<Spcarr> mockQuerySpcarr = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuerySpcarr.getSingleResult()).thenReturn(carr);
		@SuppressWarnings("unchecked")
		TypedQuery<AvancementFonctionnaire> mockQueryAvct = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryAvct.getResultList()).thenReturn(listeAvctFonct);
		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQueryFDP = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryFDP.getResultList()).thenReturn(listId);
		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQueryFDPFiche = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryFDPFiche.getResultList()).thenReturn(new ArrayList<FichePoste>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AvancementFonctionnaire.class))).thenReturn(mockQueryAvct);
		Mockito.when(sirhEMMock.createNamedQuery("getCurrentCarriere", Spcarr.class)).thenReturn(mockQuerySpcarr);

		EntityManager sirhFDPEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhFDPEMMock.createNamedQuery("getCurrentAffectation", Integer.class)).thenReturn(mockQueryFDP);
		Mockito.when(sirhFDPEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQueryFDPFiche);

		FichePosteService fpMock = new FichePosteService();
		ReflectionTestUtils.setField(fpMock, "sirhEntityManager", sirhFDPEMMock);
		Mockito.when(fpMock.getFichePosteById(listId.get(0))).thenReturn(null);
		Mockito.when(fpMock.getFichePosteById(listId.get(0))).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "fichePosteService", fpMock);

		// When
		ArreteListDto result = service.getArretesForUsers("9005138,9005131", false, 2013);

		// Then
		assertEquals(2, result.getArretes().size());
		assertEquals("Madame Prenom 1 NOM 1", result.getArretes().get(0).getNomComplet());
		assertEquals("de grade initial", result.getArretes().get(0).getGradeLabel());
		assertEquals("1 an, 1 mois, 1 jour ", result.getArretes().get(0).getAcc());
		assertEquals(2013, result.getArretes().get(0).getAnnee());
		assertEquals("C", result.getArretes().get(0).getBaseReglement());
		assertNull(result.getArretes().get(0).getDateArrete());
		assertEquals("1er mars 2011", result.getArretes().get(0).getDateAvct());
		assertNull(result.getArretes().get(0).getDateCap());
		assertEquals("lib long avis employeur", result.getArretes().get(0).getDureeAvct());
		assertEquals("20", result.getArretes().get(0).getIna().toString());
		assertEquals("iban", result.getArretes().get(0).getIb());
		assertEquals("5138", result.getArretes().get(0).getMatriculeAgent());

		assertEquals("Monsieur Prenom 2 NOM 2", result.getArretes().get(1).getNomComplet());
		assertEquals("de grade initial", result.getArretes().get(1).getGradeLabel());
		assertEquals("1 mois, 2 jours ", result.getArretes().get(1).getAcc());
		assertEquals(2014, result.getArretes().get(1).getAnnee());
		assertEquals("C", result.getArretes().get(1).getBaseReglement());
		assertNull(result.getArretes().get(1).getDateArrete());
		assertEquals("14 avril 2010", result.getArretes().get(1).getDateAvct());
		assertNull(result.getArretes().get(1).getDateCap());
		assertEquals("lib long avis employeur", result.getArretes().get(1).getDureeAvct());
		assertEquals("20", result.getArretes().get(1).getIna().toString());
		assertEquals("iban", result.getArretes().get(1).getIb());
		assertEquals("5131", result.getArretes().get(1).getMatriculeAgent());
	}

	@Test
	public void getArretesDetachesForUsers_returnArreteListDto() throws ParseException {
		// Given
		int idAgent1 = 9005138;
		int idAgent2 = 9005131;

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		Spbarem barem = new Spbarem();
		barem.setIban("iban");
		barem.setIna(20);

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("CDGRAD");
		gradeNouveau.setGradeGenerique(gradeGenerique);
		gradeNouveau.setGradeInitial("GRADE Initial");
		gradeNouveau.setBarem(barem);

		AvisCap avisCapEmployeur = new AvisCap();
		avisCapEmployeur.setIdAvisCap(1);
		avisCapEmployeur.setLibLong("Lib long avis employeur");

		Agent agent1 = new Agent();
		agent1.setIdAgent(idAgent1);
		agent1.setNomatr(5138);
		agent1.setTitre("1");
		agent1.setPrenomUsage("Prenom 1");
		agent1.setNomUsage("Nom 1");
		AvancementDetache avct1 = new AvancementDetache();
		avct1.setAgent(agent1);
		avct1.setIdMotifAvancement(4);
		avct1.setGradeNouveau(gradeNouveau);
		avct1.setNouvAccAnnee(1);
		avct1.setNouvAccMois(1);
		avct1.setNouvAccJour(1);
		avct1.setAnneeAvancement(2013);
		avct1.setDateAvctMoy(new DateTime(2011, 03, 02, 0, 0, 0, 0).toDate());

		Agent agent2 = new Agent();
		agent2.setIdAgent(idAgent2);
		agent2.setNomatr(5131);
		agent2.setTitre("0");
		agent2.setPrenomUsage("Prenom 2");
		agent2.setNomUsage("Nom 2");
		AvancementDetache avct2 = new AvancementDetache();
		avct2.setAgent(agent2);
		avct2.setIdMotifAvancement(6);
		avct2.setGradeNouveau(gradeNouveau);
		avct2.setNouvAccAnnee(0);
		avct2.setNouvAccMois(1);
		avct2.setNouvAccJour(2);
		avct2.setAnneeAvancement(2014);
		avct2.setDateAvctMoy(new DateTime(2010, 04, 15, 0, 0, 0, 0).toDate());
		List<AvancementDetache> listeAvctDetach = new ArrayList<AvancementDetache>();
		listeAvctDetach.add(avct1);
		listeAvctDetach.add(avct2);

		List<Integer> listId = new ArrayList<Integer>();
		listId.add(1);

		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(7);

		Spcarr carr = new Spcarr();
		carr.setReferenceArrete(2013);
		carr.setDateArrete(0);
		carr.setCategorie(categorie);

		@SuppressWarnings("unchecked")
		TypedQuery<Spcarr> mockQuerySpcarr = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuerySpcarr.getSingleResult()).thenReturn(carr);
		@SuppressWarnings("unchecked")
		TypedQuery<AvancementDetache> mockQueryAvct = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryAvct.getResultList()).thenReturn(listeAvctDetach);
		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQueryFDP = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryFDP.getResultList()).thenReturn(listId);
		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQueryFDPFiche = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQueryFDPFiche.getResultList()).thenReturn(new ArrayList<FichePoste>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(AvancementDetache.class))).thenReturn(mockQueryAvct);
		Mockito.when(sirhEMMock.createNamedQuery("getCurrentCarriere", Spcarr.class)).thenReturn(mockQuerySpcarr);

		EntityManager sirhFDPEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhFDPEMMock.createNamedQuery("getCurrentAffectation", Integer.class)).thenReturn(mockQueryFDP);
		Mockito.when(sirhFDPEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQueryFDPFiche);

		FichePosteService fpMock = new FichePosteService();
		ReflectionTestUtils.setField(fpMock, "sirhEntityManager", sirhFDPEMMock);
		Mockito.when(fpMock.getFichePosteById(listId.get(0))).thenReturn(null);
		Mockito.when(fpMock.getFichePosteById(listId.get(0))).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(service, "fichePosteService", fpMock);

		// When
		ArreteListDto result = service.getArretesDetachesForUsers("9005138,9005131", false, 2013);

		// Then
		assertEquals(2, result.getArretes().size());
		assertEquals("Madame Prenom 1 NOM 1", result.getArretes().get(0).getNomComplet());
		assertEquals("de grade initial", result.getArretes().get(0).getGradeLabel());
		assertEquals("1 an, 1 mois, 1 jour ", result.getArretes().get(0).getAcc());
		assertEquals(2013, result.getArretes().get(0).getAnnee());
		assertEquals("CC", result.getArretes().get(0).getBaseReglement());
		assertNull(result.getArretes().get(0).getDateArrete());
		assertEquals("1er mars 2011", result.getArretes().get(0).getDateAvct());
		assertNull(result.getArretes().get(0).getDateCap());
		assertEquals("", result.getArretes().get(0).getDureeAvct());
		assertEquals("20", result.getArretes().get(0).getIna().toString());
		assertEquals("iban", result.getArretes().get(0).getIb());
		assertEquals("5138", result.getArretes().get(0).getMatriculeAgent());

		assertEquals("Monsieur Prenom 2 NOM 2", result.getArretes().get(1).getNomComplet());
		assertEquals("de grade initial", result.getArretes().get(1).getGradeLabel());
		assertEquals("1 mois, 2 jours ", result.getArretes().get(1).getAcc());
		assertEquals(2014, result.getArretes().get(1).getAnnee());
		assertEquals("CC", result.getArretes().get(1).getBaseReglement());
		assertNull(result.getArretes().get(1).getDateArrete());
		assertEquals("1er avril 2010", result.getArretes().get(1).getDateAvct());
		assertNull(result.getArretes().get(1).getDateCap());
		assertEquals("", result.getArretes().get(1).getDureeAvct());
		assertEquals("20", result.getArretes().get(1).getIna().toString());
		assertEquals("iban", result.getArretes().get(1).getIb());
		assertEquals("5131", result.getArretes().get(1).getMatriculeAgent());
	}

	@Test
	public void getAvancement_return1dto() {

		Spclas classe = new Spclas();
		classe.setCodcla("codcla");
		classe.setLibCla("libelle classe");
		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdcadr("cdcadr");
		Speche echelon = new Speche();
		echelon.setCodEch("codech");
		echelon.setLibEch("libelle echelon");
		MotifAvct motifAvct = new MotifAvct();
		motifAvct.setCodeAvct("codeavct");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("CDGRAD");
		gradeNouveau.setLiGrad("libelle grade");
		gradeNouveau.setGradeInitial("grade initial");
		gradeNouveau.setClasse(classe);
		gradeNouveau.setDureeMinimum(18);
		gradeNouveau.setDureeMoyenne(24);
		gradeNouveau.setDureeMaximum(30);
		gradeNouveau.setGradeGenerique(gradeGenerique);
		gradeNouveau.setEchelon(echelon);

		Spgradn grade = new Spgradn();
		grade.setCdTava("  2");

		Date dateAvctMoy = new Date();
		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setEtat("etat");
		af.setDateAvctMoy(dateAvctMoy);
		af.setGradeNouveau(gradeNouveau);
		af.setGrade(grade);

		IAvancementRepository avancementRepository = Mockito.mock(IAvancementRepository.class);
		Mockito.when(avancementRepository.getAvancement(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(af);
		Mockito.when(avancementRepository.getMotifAvct(Mockito.anyInt())).thenReturn(motifAvct);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", avancementRepository);

		AvancementEaeDto result = service.getAvancement(1, 1, true);

		assertNotNull(result);
		assertEquals(1, result.getIdAvct().intValue());
		assertEquals("etat", result.getEtat());

		assertEquals(dateAvctMoy, result.getDateAvctMoy());

		assertEquals("codcla", result.getGrade().getCodeClasse());
		assertEquals("codech", result.getGrade().getCodeEchelon());
		assertEquals("CDGRAD", result.getGrade().getCodeGrade());
		assertEquals("cdcadr", result.getGrade().getCodeGradeGenerique());
		assertEquals("codeavct", result.getGrade().getCodeMotifAvancement());
		assertEquals(30, result.getGrade().getDureeMaximum().intValue());
		assertEquals(18, result.getGrade().getDureeMinimum().intValue());
		assertEquals(24, result.getGrade().getDureeMoyenne().intValue());
		assertEquals("grade initial", result.getGrade().getGradeInitial());
		assertEquals("libelle classe", result.getGrade().getLibelleClasse());
		assertEquals("libelle echelon", result.getGrade().getLibelleEchelon());
		assertEquals("libelle grade", result.getGrade().getLibelleGrade());
	}

	@Test
	public void getAvancement_returnNull() {

		IAvancementRepository avancementRepository = Mockito.mock(IAvancementRepository.class);
		Mockito.when(avancementRepository.getAvancement(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyBoolean())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", avancementRepository);

		AvancementEaeDto result = service.getAvancement(1, 1, true);

		assertNull(result);
	}

	@Test
	public void getAvancementDetache_return1dto() {

		Spclas classe = new Spclas();
		classe.setCodcla("codcla");
		classe.setLibCla("libelle classe");
		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdcadr("cdcadr");
		gradeGenerique.setCdgeng("cdgeng");
		Speche echelon = new Speche();
		echelon.setCodEch("codech");
		echelon.setLibEch("libelle echelon");

		MotifAvct motifAvct = new MotifAvct();
		motifAvct.setCodeAvct("codeavct");

		Spgradn grade = new Spgradn();
		grade.setCdTava("  1");

		Spgradn gradeNouveau = new Spgradn();
		gradeNouveau.setCdgrad("CDGRAD");
		gradeNouveau.setLiGrad("libelle grade");
		gradeNouveau.setGradeInitial("grade initial");
		gradeNouveau.setClasse(classe);
		gradeNouveau.setDureeMinimum(18);
		gradeNouveau.setDureeMoyenne(24);
		gradeNouveau.setDureeMaximum(30);
		gradeNouveau.setGradeGenerique(gradeGenerique);
		gradeNouveau.setEchelon(echelon);
		Date dateAvctMoy = new Date();
		AvancementDetache af = new AvancementDetache();
		af.setIdAvct(1);
		af.setEtat("etat");
		af.setDateAvctMoy(dateAvctMoy);
		af.setGradeNouveau(gradeNouveau);
		af.setGrade(grade);

		IAvancementRepository avancementRepository = Mockito.mock(IAvancementRepository.class);
		Mockito.when(avancementRepository.getAvancementDetache(Mockito.anyInt(), Mockito.anyInt())).thenReturn(af);
		Mockito.when(avancementRepository.getMotifAvct(Mockito.anyInt())).thenReturn(motifAvct);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", avancementRepository);

		AvancementEaeDto result = service.getAvancementDetache(1, 1);

		assertNotNull(result);

		assertEquals(dateAvctMoy, result.getDateAvctMoy());
		assertEquals("etat", result.getEtat());
		assertEquals(1, result.getIdAvct().intValue());
		assertEquals("codcla", result.getGrade().getCodeClasse());
		assertEquals("codech", result.getGrade().getCodeEchelon());
		assertEquals("CDGRAD", result.getGrade().getCodeGrade());
		assertEquals("cdcadr", result.getGrade().getCodeGradeGenerique());
		assertEquals("codeavct", result.getGrade().getCodeMotifAvancement());
		assertEquals(30, result.getGrade().getDureeMaximum().intValue());
		assertEquals(18, result.getGrade().getDureeMinimum().intValue());
		assertEquals(24, result.getGrade().getDureeMoyenne().intValue());
		assertEquals("grade initial", result.getGrade().getGradeInitial());
		assertEquals("libelle classe", result.getGrade().getLibelleClasse());
		assertEquals("libelle echelon", result.getGrade().getLibelleEchelon());
		assertEquals("libelle grade", result.getGrade().getLibelleGrade());
	}

	@Test
	public void getAvancementDetache_returnNull() {

		IAvancementRepository avancementRepository = Mockito.mock(IAvancementRepository.class);
		Mockito.when(avancementRepository.getAvancementDetache(Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);

		AvancementsService service = new AvancementsService();
		ReflectionTestUtils.setField(service, "avancementRepository", avancementRepository);

		AvancementEaeDto result = service.getAvancementDetache(1, 1);

		assertNull(result);
	}
}
