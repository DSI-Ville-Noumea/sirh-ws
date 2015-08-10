package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.HistoFichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.InfoEntiteDto;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;
import nc.noumea.mairie.web.dto.SpbhorDto;
import nc.noumea.mairie.ws.IADSWSConsumer;
import nc.noumea.mairie.ws.ISirhPtgWSConsumer;
import nc.noumea.mairie.ws.dto.LightUserDto;
import nc.noumea.mairie.ws.dto.RefPrimeDto;
import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class FichePosteServiceTest {

	@Test
	public void getFichePostePrimaireAgentAffectationEnCours_returnFichePoste() {
		// Given
		FichePoste fp = new FichePoste();

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fp);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePostePrimaireAgentAffectationEnCours(9005138, new Date(), true);

		// Then
		assertEquals(fp.getIdFichePoste(), result.getIdFichePoste());

	}

	@Test
	public void estResponsable_returnTrue() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(1);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = ficheService.estResponsable(9005138);

		// Then
		assertTrue(result);
	}

	@Test
	public void estResponsable_returnFAlse() {
		// Given
		Query mockQuery = Mockito.mock(Query.class);
		Mockito.when(mockQuery.getSingleResult()).thenReturn(0);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNativeQuery(Mockito.anyString())).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		boolean result = ficheService.estResponsable(9005138);

		// Then
		assertFalse(result);
	}

	@Test
	public void getFichePosteSecondaireAgentAffectationEnCours_returnNull() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<FichePoste>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteSecondaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertNull(result);

	}

	@Test
	public void getFichePosteSecondaireAgentAffectationEnCours_returnFichePoste() {
		// Given
		FichePoste fp = new FichePoste();

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fp);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteSecondaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals(fp.getIdFichePoste(), result.getIdFichePoste());

	}

	@Test
	public void getTitrePosteAgent_returnTitre() {
		// Given
		String titre1 = "TEST1";
		String titre2 = "TEST2";

		List<String> listeFDP = new ArrayList<String>();
		listeFDP.add(titre1);
		listeFDP.add(titre2);

		@SuppressWarnings("unchecked")
		TypedQuery<String> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(String.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		String result = ficheService.getTitrePosteAgent(9005138, new Date());

		// Then
		assertEquals(titre1, result);
	}

	@Test
	public void getIdFichePostePrimaireAgentAffectationEnCours_return0() {
		// Given
		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(new ArrayList<Integer>());

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		Integer result = ficheService.getIdFichePostePrimaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals("0", result.toString());

	}

	@Test
	public void getIdFichePostePrimaireAgentAffectationEnCours_returnIdFDP() {
		// Given
		List<Integer> listeFDP = new ArrayList<Integer>();
		listeFDP.add(12);

		@SuppressWarnings("unchecked")
		TypedQuery<Integer> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createNamedQuery(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		Integer result = ficheService.getIdFichePostePrimaireAgentAffectationEnCours(9005138, new Date());

		// Then
		assertEquals("12", result.toString());

	}

	@Test
	public void getFichePosteById_returnFDP() {
		// Given
		FichePoste fdp1 = new FichePoste();
		fdp1.setAnnee(2013);

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fdp1);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);

		// When
		FichePoste result = ficheService.getFichePosteById(12);

		// Then
		assertEquals(fdp1.getIdFichePoste(), result.getIdFichePoste());
		assertEquals(fdp1.getAnnee(), result.getAnnee());

	}

	@Test
	public void getFichePosteDetailleSIRHByIdWithRefPrime_returnFDP() {
		// Given
		PrimePointageFPPK primePointageFPPK1 = new PrimePointageFPPK();
		primePointageFPPK1.setNumRubrique(1010);
		PrimePointageFP pp1 = new PrimePointageFP();
		pp1.setPrimePointageFPPK(primePointageFPPK1);
		pp1.setLibelle("prime 1");

		FichePoste fdp1 = new FichePoste();
		fdp1.setAnnee(2013);
		pp1.setFichePoste(fdp1);
		fdp1.getPrimePointageFP().add(pp1);

		RefPrimeDto refDto1 = new RefPrimeDto();
		refDto1.setLibelle("Nono1");

		List<FichePoste> listeFDP = new ArrayList<FichePoste>();
		listeFDP.add(fdp1);

		@SuppressWarnings("unchecked")
		TypedQuery<FichePoste> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeFDP);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(FichePoste.class))).thenReturn(mockQuery);

		ISirhPtgWSConsumer sirhPtgConsumerMock = Mockito.mock(ISirhPtgWSConsumer.class);
		Mockito.when(sirhPtgConsumerMock.getPrime(1010)).thenReturn(refDto1);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "sirhEntityManager", sirhEMMock);
		ReflectionTestUtils.setField(ficheService, "sirhPtgWSConsumer", sirhPtgConsumerMock);

		// When
		FichePoste result = ficheService.getFichePosteDetailleSIRHByIdWithRefPrime(12);

		// Then
		assertEquals(fdp1.getIdFichePoste(), result.getIdFichePoste());
		assertEquals(fdp1.getAnnee(), result.getAnnee());
		assertEquals(1, result.getPrimePointageFP().size());

		Iterator<PrimePointageFP> i = result.getPrimePointageFP().iterator();
		while (i.hasNext()) {
			PrimePointageFP r = i.next();
			assertEquals(refDto1.getLibelle(), r.getLibelle());

		}
	}

	@Test
	public void getListSpbhorDto() {

		Spbhor hor1 = new Spbhor();
		Spbhor hor2 = new Spbhor();

		List<Spbhor> listSpbhor = new ArrayList<Spbhor>();
		listSpbhor.add(hor1);
		listSpbhor.add(hor2);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getListSpbhor()).thenReturn(listSpbhor);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "mairieRepository", mairieRepository);

		List<SpbhorDto> result = ficheService.getListSpbhorDto();

		assertEquals(2, result.size());
	}

	@Test
	public void getSpbhorById() {

		Spbhor hor1 = new Spbhor();

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
		Mockito.when(mairieRepository.getSpbhorById(1)).thenReturn(hor1);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "mairieRepository", mairieRepository);

		SpbhorDto result = ficheService.getSpbhorDtoById(1);

		assertNotNull(result);
	}

	@Test
	public void getListFichePosteByIdServiceADSAndStatutFDP_EmptyListe() {

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(
				new ArrayList<FichePoste>());

		EntiteDto entite = new EntiteDto();

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		List<FichePosteDto> result = ficheService.getListFichePosteByIdServiceADSAndStatutFDP(1, null, false);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void getListFichePosteByIdServiceADSAndStatutFDP_ListeWithNoStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En creation");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		EntiteDto entite = new EntiteDto();

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(
				listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn(
				"sigle");

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteDto> result = ficheService.getListFichePosteByIdServiceADSAndStatutFDP(1, null, false);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fiche.getNumFP(), result.get(0).getNumero());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFDP());
	}

	@Test
	public void getListFichePosteByIdServiceADSAndStatutFDP_ListeWithStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En creation");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		EntiteDto entite = new EntiteDto();

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(1, 2)))
				.thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn(
				"sigle");

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteDto> result = ficheService.getListFichePosteByIdServiceADSAndStatutFDP(1, Arrays.asList(1, 2),
				false);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fiche.getNumFP(), result.get(0).getNumero());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFDP());
	}

	@Test
	public void getListFichePosteByIdServiceADSAndStatutFDP_WithChildren_ListeWithNoStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En creation");

		FichePoste fiche2 = new FichePoste();
		fiche2.setNumFP("201/2");
		fiche2.setIdServiceADS(2);
		fiche2.setStatutFP(statutFP);

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setIdServiceADS(1);
		fiche.setStatutFP(statutFP);

		EntiteDto enfant1 = new EntiteDto();
		enfant1.setIdEntite(2);

		EntiteDto entite = new EntiteDto();
		entite.setIdEntite(1);
		entite.getEnfants().add(enfant1);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);
		listFP.add(fiche2);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(2, 1), null)).thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(2)).thenReturn(enfant1);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(2)).thenReturn(enfant1);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn(
				"sigle");
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn(
				"sigle");

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteDto> result = ficheService.getListFichePosteByIdServiceADSAndStatutFDP(1, null, true);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(fiche2.getNumFP(), result.get(1).getNumero());
		assertEquals(statutFP.getLibStatut(), result.get(1).getStatutFDP());
		assertEquals(fiche.getNumFP(), result.get(0).getNumero());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFDP());
	}

	@Test
	public void deleteFichePosteByIdEntite_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En création");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(
				listFP);

		IAffectationService affectationSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affectationSrv.getAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(
				new ArrayList<Affectation>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affectationSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdEntite(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP vont être supprimées. Merci d'aller regarder le resultat de cette suppression dans SIRH.",
				result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(ActionFdpJob.class));
	}

	@Test
	public void deleteFichePosteByIdEntite_Errors() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		statutFP.setLibStatut("Validée");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(
				listFP);

		IAffectationService affectationSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affectationSrv.getAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(
				new ArrayList<Affectation>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affectationSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdEntite(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(ActionFdpJob.class));
	}

	@Test
	public void getInfoFDP_MultipleEnfant_Result() {
		EntiteDto enfant1 = new EntiteDto();
		enfant1.setIdEntite(2);

		EntiteDto entite = new EntiteDto();
		entite.setIdEntite(1);
		entite.getEnfants().add(enfant1);

		List<InfoFichePosteDto> listInfoParaent = new ArrayList<InfoFichePosteDto>();
		InfoFichePosteDto listInfo1 = new InfoFichePosteDto();
		InfoFichePosteDto listInfo2 = new InfoFichePosteDto();
		listInfoParaent.add(listInfo1);
		listInfoParaent.add(listInfo2);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Arrays.asList(2, 1)))
				.thenReturn(listInfoParaent);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		InfoEntiteDto result = ficheService.getInfoFDP(1, true);

		assertNotNull(result);
		assertEquals(2, result.getListeInfoFDP().size());
	}

	@Test
	public void getInfoFDP_NoEnfant_Result() {

		EntiteDto entite = new EntiteDto();
		entite.setIdEntite(1);

		List<InfoFichePosteDto> listInfoParaent = new ArrayList<InfoFichePosteDto>();
		InfoFichePosteDto listInfo1 = new InfoFichePosteDto();
		listInfoParaent.add(listInfo1);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Arrays.asList(1)))
				.thenReturn(listInfoParaent);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		InfoEntiteDto result = ficheService.getInfoFDP(1, true);

		assertNotNull(result);
		assertEquals(1, result.getListeInfoFDP().size());
	}

	@Test
	public void dupliqueFichePosteByIdEntite_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En création");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(2), Arrays.asList(2)))
				.thenReturn(listFP);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdEntite(1, 2, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP vont être dupliquées. Merci d'aller regarder le resultat de cette duplication dans SIRH.",
				result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(ActionFdpJob.class));
	}

	@Test
	public void deleteFichePosteByIdFichePoste_NoFichePoste() {

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(null);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'existe pas.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).removeEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void deleteFichePosteByIdFichePoste_BadStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'est pas en statut 'en création'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).removeEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void deleteFichePosteByIdFichePoste_ListAffectation() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		List<Affectation> listAff = new ArrayList<Affectation>();
		listAff.add(new Affectation());

		IAffectationService affSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affSrv.getAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(listAff);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 est affectée.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).removeEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void deleteFichePosteByIdFichePoste_NoLogin() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		List<Affectation> listAff = new ArrayList<Affectation>();

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(null);

		IAffectationService affSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affSrv.getAffectationByIdFichePoste(1)).thenReturn(listAff);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affSrv);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("L'agent qui tente de faire l'action n'a pas de login dans l'AD.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).removeEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void deleteFichePosteByIdFichePoste_OK() {
		List<ActiviteFP> listActi = new ArrayList<ActiviteFP>();
		listActi.add(new ActiviteFP());

		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setNumFP("2015/3");

		List<Affectation> listAff = new ArrayList<Affectation>();

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		IAffectationService affSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affSrv.getAffectationByIdFichePoste(1)).thenReturn(listAff);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);
		Mockito.when(fichePosteDao.listerFEFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<FeFp>());
		Mockito.when(fichePosteDao.listerNiveauEtudeFPAvecFP(Mockito.anyInt())).thenReturn(
				new ArrayList<NiveauEtudeFP>());
		Mockito.when(fichePosteDao.listerActiviteFPAvecFP(Mockito.anyInt())).thenReturn(listActi);
		Mockito.when(fichePosteDao.listerCompetenceFPAvecFP(Mockito.anyInt()))
				.thenReturn(new ArrayList<CompetenceFP>());
		Mockito.when(fichePosteDao.listerAvantageNatureFPAvecFP(Mockito.anyInt())).thenReturn(
				new ArrayList<AvantageNatureFP>());
		Mockito.when(fichePosteDao.listerDelegationFPAvecFP(Mockito.anyInt()))
				.thenReturn(new ArrayList<DelegationFP>());
		Mockito.when(fichePosteDao.listerPrimePointageFP(Mockito.anyInt()))
				.thenReturn(new ArrayList<PrimePointageFP>());
		Mockito.when(fichePosteDao.listerRegIndemFPFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<RegIndemFP>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affSrv);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("La FDP id 2015/3 est supprimée.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).removeEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).removeEntity(Mockito.isA(ActiviteFP.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_NoFichePoste() {

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(null);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'existe pas.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_BadStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'est pas en statut 'validée'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_BadStatutEntite() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(2);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'est pas en statut 'validée'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_NoLogin() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(0);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(null);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("L'agent qui tente de faire l'action n'a pas de login dans l'AD.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_OK() {
		Activite acti = new Activite();
		acti.setIdActivite(1);

		ActiviteFPPK activiteFPPK = new ActiviteFPPK();
		activiteFPPK.setIdActivite(1);
		activiteFPPK.setIdFichePoste(1);
		ActiviteFP lien = new ActiviteFP();
		lien.setActiviteFPPK(activiteFPPK);
		List<ActiviteFP> listActi = new ArrayList<ActiviteFP>();
		listActi.add(lien);

		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setNumFP("2015/3");

		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(0);

		PrimePointageFPPK primePointageFPPK = new PrimePointageFPPK();
		primePointageFPPK.setNumRubrique(5117);
		PrimePointageFP pp = new PrimePointageFP();
		pp.setFichePoste(fiche);
		pp.setPrimePointageFPPK(primePointageFPPK);
		List<PrimePointageFP> listPrime = new ArrayList<PrimePointageFP>();
		listPrime.add(pp);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);
		Mockito.when(fichePosteDao.listerFEFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<FeFp>());
		Mockito.when(fichePosteDao.listerNiveauEtudeFPAvecFP(Mockito.anyInt())).thenReturn(
				new ArrayList<NiveauEtudeFP>());
		Mockito.when(fichePosteDao.listerActiviteFPAvecFP(Mockito.anyInt())).thenReturn(listActi);
		Mockito.when(fichePosteDao.listerCompetenceFPAvecFP(Mockito.anyInt()))
				.thenReturn(new ArrayList<CompetenceFP>());
		Mockito.when(fichePosteDao.listerAvantageNatureFPAvecFP(Mockito.anyInt())).thenReturn(
				new ArrayList<AvantageNatureFP>());
		Mockito.when(fichePosteDao.listerDelegationFPAvecFP(Mockito.anyInt()))
				.thenReturn(new ArrayList<DelegationFP>());
		Mockito.when(fichePosteDao.listerPrimePointageFP(Mockito.anyInt())).thenReturn(listPrime);
		Mockito.when(fichePosteDao.listerRegIndemFPFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<RegIndemFP>());
		Mockito.when(fichePosteDao.chercherActivite(Mockito.anyInt())).thenReturn(acti);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("La FDP id 2015/3 est dupliquée en 2015/1.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(PrimePointageFP.class));
	}

	@Test
	public void activeFichesPosteByIdEntite_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En création");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(1)))
				.thenReturn(listFP);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.activeFichesPosteByIdEntite(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP vont être activées. Merci d'aller regarder le resultat de cette activation dans SIRH.",
				result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(ActionFdpJob.class));
	}
}