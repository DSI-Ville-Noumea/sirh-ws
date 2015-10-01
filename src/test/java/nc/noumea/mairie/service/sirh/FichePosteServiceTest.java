package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.ads.StatutEntiteEnum;
import nc.noumea.mairie.model.bean.sirh.ActionFdpJob;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.Budget;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.EnumStatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.EnumTypeHisto;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.HistoFichePoste;
import nc.noumea.mairie.model.bean.sirh.NatureCredit;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.service.ads.IAdsService;
import nc.noumea.mairie.tools.FichePosteTreeNode;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FichePosteTreeNodeDto;
import nc.noumea.mairie.web.dto.GroupeInfoFichePosteDto;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

public class FichePosteServiceTest {

	private FichePosteTreeNode fpNiv1 = null;
	private FichePosteTreeNode fpNiv2 = null;
	private FichePosteTreeNode fpNiv2Bis = null;
	private FichePosteTreeNode fpNiv2Niv3 = null;
	private FichePosteTreeNode fpNiv2BisNiv3 = null;
	private FichePosteTreeNode fpNiv2Niv3Bis = null;
	private FichePosteTreeNode fpNiv2BisNiv3Bis = null;

	private FichePoste fp1 = null;
	private FichePoste fp2 = null;
	private FichePoste fp3 = null;
	private FichePoste fp4 = null;
	private FichePoste fp5 = null;
	private FichePoste fp6 = null;
	private FichePoste fp7 = null;

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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(new ArrayList<FichePoste>());

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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn("sigle");

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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(1, 2))).thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn("sigle");

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteDto> result = ficheService.getListFichePosteByIdServiceADSAndStatutFDP(1, Arrays.asList(1, 2), false);

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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(Arrays.asList(2, 1), null)).thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(2)).thenReturn(enfant1);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(2)).thenReturn(enfant1);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn("sigle");
		Mockito.when(adsService.getSigleEntityInEntiteDtoTreeByIdEntite(entite, fiche.getIdServiceADS())).thenReturn("sigle");

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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(listFP);

		IAffectationService affectationSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affectationSrv.getListAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(new ArrayList<Affectation>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affectationSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdEntite(1, 9005138, "DSI");

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP va être supprimée sur l'entité DSI. Merci d'aller regarder le resultat de cette suppression dans SIRH.", result.getInfos().get(0));
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
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null)).thenReturn(listFP);

		IAffectationService affectationSrv = Mockito.mock(IAffectationService.class);
		Mockito.when(affectationSrv.getListAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(new ArrayList<Affectation>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affectationSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdEntite(1, 9005138, "DSI");

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

		List<GroupeInfoFichePosteDto> listInfoParaent = new ArrayList<GroupeInfoFichePosteDto>();
		GroupeInfoFichePosteDto listInfo1 = new GroupeInfoFichePosteDto();
		listInfo1.setTitreFDP("titreFDP");
		GroupeInfoFichePosteDto listInfo2 = new GroupeInfoFichePosteDto();
		listInfo2.setTitreFDP("titreFDP2");
		listInfoParaent.add(listInfo1);
		listInfoParaent.add(listInfo2);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Arrays.asList(2, 1))).thenReturn(listInfoParaent);

		InfoFichePosteDto infoFichePosteDto = new InfoFichePosteDto();
		infoFichePosteDto.setNumFP("numFP");
		InfoFichePosteDto infoFichePosteDto2 = new InfoFichePosteDto();
		infoFichePosteDto2.setNumFP("numFP2");

		Mockito.when(fichePosteDao.getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(Arrays.asList(2, 1), listInfo1.getTitreFDP(), new Date())).thenReturn(Arrays.asList(infoFichePosteDto));
		Mockito.when(fichePosteDao.getListInfoFichePosteDtoByIdServiceADSAndTitrePoste(Arrays.asList(2, 1), listInfo2.getTitreFDP(), new Date())).thenReturn(Arrays.asList(infoFichePosteDto2));

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		InfoEntiteDto result = ficheService.getInfoFDP(1, true);

		assertNotNull(result);
		assertEquals(2, result.getListeInfoFDP().size());
		assertEquals("numFP", result.getListeInfoFDP().get(0).getListInfoFichePosteDto().get(0).getNumFP());
		assertEquals("numFP2", result.getListeInfoFDP().get(1).getListInfoFichePosteDto().get(0).getNumFP());
	}

	@Test
	public void getInfoFDP_NoEnfant_Result() {

		EntiteDto entite = new EntiteDto();
		entite.setIdEntite(1);

		List<GroupeInfoFichePosteDto> listInfoParaent = new ArrayList<GroupeInfoFichePosteDto>();
		GroupeInfoFichePosteDto listInfo1 = new GroupeInfoFichePosteDto();
		listInfoParaent.add(listInfo1);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteWithChildrenByIdEntite(1)).thenReturn(entite);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Arrays.asList(1))).thenReturn(listInfoParaent);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		InfoEntiteDto result = ficheService.getInfoFDP(1, true);

		assertNotNull(result);
		assertEquals(1, result.getListeInfoFDP().size());
	}

	@Test
	public void dupliqueFichePosteByIdEntite_0FDP_OK() {

		List<FichePoste> listFP = new ArrayList<FichePoste>();

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(2), Arrays.asList(2))).thenReturn(listFP);

		EntiteDto entite = new EntiteDto();
		entite.setSigle("TEST");

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdEntite(1, 2, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("Aucune FDP ne va être dupliquée sur l'entité TEST.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(ActionFdpJob.class));
	}

	@Test
	public void dupliqueFichePosteByIdEntite_1FDP_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En création");

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(2), Arrays.asList(2, 1, 5, 6))).thenReturn(listFP);

		EntiteDto entite = new EntiteDto();
		entite.setSigle("TEST");

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdEntite(1, 2, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP va être dupliquée sur l'entité TEST. Merci d'aller regarder le resultat de cette duplication dans SIRH.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(ActionFdpJob.class));
	}

	@Test
	public void dupliqueFichePosteByIdEntite_MultiFDP_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("En création");

		FichePoste fiche2 = new FichePoste();
		fiche2.setNumFP("201/1");
		fiche2.setStatutFP(statutFP);

		FichePoste fiche = new FichePoste();
		fiche.setNumFP("201/1");
		fiche.setStatutFP(statutFP);

		List<FichePoste> listFP = new ArrayList<FichePoste>();
		listFP.add(fiche);
		listFP.add(fiche2);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(2), Arrays.asList(2, 1, 5, 6))).thenReturn(listFP);

		EntiteDto entite = new EntiteDto();
		entite.setSigle("TEST");

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdEntite(1, 2, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("2 FDP vont être dupliquées sur l'entité TEST. Merci d'aller regarder le resultat de cette duplication dans SIRH.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(2)).persisEntity(Mockito.isA(ActionFdpJob.class));
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
		Mockito.when(affSrv.getListAffectationByIdFichePoste(Mockito.anyInt())).thenReturn(listAff);

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
		Mockito.when(affSrv.getListAffectationByIdFichePoste(1)).thenReturn(listAff);

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
		Mockito.when(affSrv.getListAffectationByIdFichePoste(1)).thenReturn(listAff);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(fiche);
		Mockito.when(fichePosteDao.listerFEFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<FeFp>());
		Mockito.when(fichePosteDao.listerNiveauEtudeFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<NiveauEtudeFP>());
		Mockito.when(fichePosteDao.listerActiviteFPAvecFP(Mockito.anyInt())).thenReturn(listActi);
		Mockito.when(fichePosteDao.listerCompetenceFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<CompetenceFP>());
		Mockito.when(fichePosteDao.listerAvantageNatureFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<AvantageNatureFP>());
		Mockito.when(fichePosteDao.listerDelegationFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<DelegationFP>());
		Mockito.when(fichePosteDao.listerPrimePointageFP(Mockito.anyInt())).thenReturn(new ArrayList<PrimePointageFP>());
		Mockito.when(fichePosteDao.listerRegIndemFPFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<RegIndemFP>());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "affSrv", affSrv);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.deleteFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("La FDP 2015/3 est supprimée.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).removeEntity(Mockito.isA(FichePoste.class));
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
		statutFP.setIdStatutFp(new Integer(EnumStatutFichePoste.INACTIVE.getId()));
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setNumFP("2011/11");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP 2011/11 n'est pas en statut 'validée','en création','transitoire' ou 'gelée'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_NoEntite() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(null);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		ReturnMessageDto result = ficheService.dupliqueFichePosteByIdFichePoste(1, 1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("L'entite id 1 n'existe pas ou plus.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_BadStatutEntite() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
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
		assertEquals("L'entite id 1 n'est pas en statut 'prévision'.", result.getErrors().get(0));
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
	public void dupliqueFichePosteByIdFichePoste_OK_NoSuperieur() {
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

		FichePoste superieurHierarchique = new FichePoste();
		superieurHierarchique.setIdFichePoste(12);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setNumFP("2015/3");
		fiche.setSuperieurHierarchique(superieurHierarchique);

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
		Mockito.when(fichePosteDao.listerNiveauEtudeFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<NiveauEtudeFP>());
		Mockito.when(fichePosteDao.listerActiviteFPAvecFP(Mockito.anyInt())).thenReturn(listActi);
		Mockito.when(fichePosteDao.listerCompetenceFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<CompetenceFP>());
		Mockito.when(fichePosteDao.listerAvantageNatureFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<AvantageNatureFP>());
		Mockito.when(fichePosteDao.listerDelegationFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<DelegationFP>());
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
		assertEquals(2, result.getInfos().size());
		assertEquals("Attention, la FDP 2015/1 n'a pas de supérieur hiérarchique.", result.getInfos().get(0));
		assertEquals("La FDP 2015/3 est dupliquée en 2015/1.", result.getInfos().get(1));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void dupliqueFichePosteByIdFichePoste_OK_WithSuperieur() {
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

		ActionFdpJob action = new ActionFdpJob();
		action.setNewIdFichePoste(1);

		FichePoste superieurHierarchique = new FichePoste();
		superieurHierarchique.setIdFichePoste(12);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(12);
		fiche.setStatutFP(statutFP);
		fiche.setNumFP("2015/3");
		fiche.setSuperieurHierarchique(superieurHierarchique);

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
		Mockito.when(fichePosteDao.chercherActionFDPParentDuplication(Mockito.anyInt())).thenReturn(action);
		Mockito.when(fichePosteDao.listerFEFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<FeFp>());
		Mockito.when(fichePosteDao.listerNiveauEtudeFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<NiveauEtudeFP>());
		Mockito.when(fichePosteDao.listerActiviteFPAvecFP(Mockito.anyInt())).thenReturn(listActi);
		Mockito.when(fichePosteDao.listerCompetenceFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<CompetenceFP>());
		Mockito.when(fichePosteDao.listerAvantageNatureFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<AvantageNatureFP>());
		Mockito.when(fichePosteDao.listerDelegationFPAvecFP(Mockito.anyInt())).thenReturn(new ArrayList<DelegationFP>());
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
		assertEquals("La FDP 2015/3 est dupliquée en 2015/1.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(FichePoste.class));
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

		EntiteDto entite = new EntiteDto();
		entite.setSigle("DSI");
		entite.setIdEntite(1);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(1))).thenReturn(listFP);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.activeFichesPosteByIdEntite(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("1 FDP va être activée sur l'entité DSI. Merci d'aller regarder le resultat de cette activation dans SIRH.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(ActionFdpJob.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadFichePoste() {
		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(null);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'existe pas.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadStatut() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("La FDP id 1 n'est pas en statut 'en création'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_EntiteNoActive() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.PREVISION.getIdRefStatutEntite());

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("L'entite id 1 n'est pas en statut 'actif'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadLogin() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(null);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("L'agent qui tente de faire l'action n'a pas de login dans l'AD.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadAnnee() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ année est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadGrade() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ grade est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadTitre() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ titre du poste est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadBudget() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ budget est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadLieu() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ lieu est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadNiveauEtude() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ niveau d'étude est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadEntiteDelib() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le service associé n'a pas de delibération.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadNFA() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ NFA est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadSupHierarchique() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ supérieur hiérarchique est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadMission() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		fiche.setSuperieurHierarchique(new FichePoste());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ mission est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadActivite() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		fiche.setSuperieurHierarchique(new FichePoste());
		fiche.setMissions("mission");
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Il doit au moins y avoir 1 activité.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadPointage() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		fiche.setSuperieurHierarchique(new FichePoste());
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ base horaire pointage est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadAbsence() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		fiche.setSuperieurHierarchique(new FichePoste());
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ base horaire absence est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRemplace() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		fiche.setRemplace(fiche);
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Une FDP ne peut être en remplacement d'elle-même.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadReglementaire() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ reglementaire est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadBudgete() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ budgeté est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadNatureCredit() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		fiche.setBudgete(new Spbhor());
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le champ nature des crédits est obligatoire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("OUI");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("NON");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Si la nature des crédits est 'NON', alors budgété doit être 'Non'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire2() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("NON");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("PERMANENT");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Si la nature des crédits est 'PERMANENT', alors budgété doit être 'Non'.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire3() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("oui");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("REMPLACEMENT");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Budget de remplacement : fiche de poste remplacee necessaire.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire4() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		fiche.setReglementaire(new Spbhor());
		FichePoste remplacee = new FichePoste();
		remplacee.setIdFichePoste(4);
		fiche.setRemplace(remplacee);
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("oui");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("TEMPORAIRE");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Fiche de poste remplacee mais budget different de remplacement.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire5() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("non");
		fiche.setReglementaire(reglementaire);
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("oui");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("PERMANENT");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le poste n'est pas reglementaire, le budget ne peut pas être permanent.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_BadRGReglementaire6() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		fiche.setLieuPoste(new Silieu());
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("oui");
		fiche.setReglementaire(reglementaire);
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("oui");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("REMPLACEMENT");
		fiche.setNatureCredit(natureCredit);
		FichePoste remplacee = new FichePoste();
		remplacee.setIdFichePoste(4);
		fiche.setRemplace(remplacee);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
		assertEquals("Le poste est reglementaire, le budget doit être permanent.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void activeFichePosteByIdFichePoste_OK() {
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		FichePoste fiche = new FichePoste();
		fiche.setNumFP("2005/1");
		fiche.setIdFichePoste(1);
		fiche.setStatutFP(statutFP);
		fiche.setIdServiceADS(1);
		fiche.setAnnee(2015);
		fiche.setGradePoste(new Spgradn());
		fiche.setTitrePoste(new TitrePoste());
		fiche.setBudget(new Budget());
		Silieu lieu = new Silieu();
		lieu.setCodeLieu(new Long(12));
		fiche.setLieuPoste(lieu);
		fiche.setNiveauEtude(new NiveauEtude());
		fiche.setNfa("blbl");
		FichePoste superieur = new FichePoste();
		superieur.setIdFichePoste(2);
		fiche.setSuperieurHierarchique(superieur);
		fiche.setMissions("mission");
		HashSet<ActiviteFP> actiFDP = new HashSet<ActiviteFP>();
		actiFDP.add(new ActiviteFP());
		fiche.setActivites(actiFDP);
		fiche.setIdBaseHorairePointage(1);
		fiche.setIdBaseHoraireAbsence(1);
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("oui");
		fiche.setReglementaire(reglementaire);
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("oui");
		fiche.setBudgete(budgete);
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("PERMANENT");
		fiche.setNatureCredit(natureCredit);
		EntiteDto entite = new EntiteDto();
		entite.setIdStatut(StatutEntiteEnum.ACTIF.getIdRefStatutEntite());
		entite.setDateDeliberationActif(new Date());
		entite.setRefDeliberationActif("blabl");
		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("chata73");
		StatutFichePoste statutValide = new StatutFichePoste();
		statutValide.setIdStatutFp(2);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(fiche);
		Mockito.when(fichePosteDao.chercherStatutFPByIdStatut(2)).thenReturn(statutValide);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(1, new ArrayList<EntiteDto>())).thenReturn(entite);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(9005138)).thenReturn(user);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);

		ReturnMessageDto result = ficheService.activeFichePosteByIdFichePoste(1, 9005138);

		assertNotNull(result);
		assertEquals(0, result.getErrors().size());
		assertEquals(1, result.getInfos().size());
		assertEquals("La FDP 2005/1 est activée.", result.getInfos().get(0));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(FichePoste.class));
		Mockito.verify(fichePosteDao, Mockito.times(1)).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void rechercheFichesPosteParent_unParent() {

		Integer idEntite = 1;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();
		FichePoste fpNiv1 = new FichePoste();
		fpNiv1.setIdFichePoste(1);
		fpNiv1.setNumFP("2010/1");
		listFichesPoste.add(fpNiv1);

		FichePoste fpNiv2 = new FichePoste();
		fpNiv2.setIdFichePoste(2);
		fpNiv2.setNumFP("2011/1");
		fpNiv2.setSuperieurHierarchique(fpNiv1);
		listFichesPoste.add(fpNiv2);

		FichePoste fpNiv2Bis = new FichePoste();
		fpNiv2Bis.setIdFichePoste(3);
		fpNiv2Bis.setNumFP("2011/2");
		fpNiv2Bis.setSuperieurHierarchique(fpNiv1);
		listFichesPoste.add(fpNiv2Bis);

		FichePoste fpNiv2Niv3 = new FichePoste();
		fpNiv2Niv3.setIdFichePoste(4);
		fpNiv2Niv3.setNumFP("2012/1");
		fpNiv2Niv3.setSuperieurHierarchique(fpNiv2);
		listFichesPoste.add(fpNiv2Niv3);

		FichePoste fpNiv2BisNiv3 = new FichePoste();
		fpNiv2BisNiv3.setIdFichePoste(5);
		fpNiv2BisNiv3.setNumFP("2012/2");
		fpNiv2BisNiv3.setSuperieurHierarchique(fpNiv2Bis);
		listFichesPoste.add(fpNiv2BisNiv3);

		FichePoste fpNiv2Niv3Bis = new FichePoste();
		fpNiv2Niv3Bis.setIdFichePoste(6);
		fpNiv2Niv3Bis.setNumFP("2012/3");
		fpNiv2Niv3Bis.setSuperieurHierarchique(fpNiv2);
		listFichesPoste.add(fpNiv2Niv3Bis);

		FichePoste fpNiv2BisNiv3Bis = new FichePoste();
		fpNiv2BisNiv3Bis.setIdFichePoste(7);
		fpNiv2BisNiv3Bis.setNumFP("2012/4");
		fpNiv2BisNiv3Bis.setSuperieurHierarchique(fpNiv2Bis);
		listFichesPoste.add(fpNiv2BisNiv3Bis);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(idEntite),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
								EnumStatutFichePoste.TRANSITOIRE.getStatut()))).thenReturn(listFichesPoste);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		List<Integer> result = ficheService.rechercheFichesPosteParent(listFichesPoste);

		assertEquals(1, result.size());
		assertEquals(result.get(0).intValue(), 1);
	}

	@Test
	public void rechercheFichesPosteParent_3Parents() {

		Integer idEntite = 1;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();
		FichePoste fp = new FichePoste();
		fp.setIdFichePoste(1);
		fp.setNumFP("2010/1");
		listFichesPoste.add(fp);

		FichePoste fp2 = new FichePoste();
		fp2.setIdFichePoste(2);
		fp2.setNumFP("2010/2");
		listFichesPoste.add(fp2);

		FichePoste fp3 = new FichePoste();
		fp3.setIdFichePoste(3);
		fp3.setNumFP("2010/3");
		listFichesPoste.add(fp3);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(idEntite),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
								EnumStatutFichePoste.TRANSITOIRE.getStatut()))).thenReturn(listFichesPoste);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		List<Integer> result = ficheService.rechercheFichesPosteParent(listFichesPoste);

		assertEquals(3, result.size());
		assertEquals(result.get(0).intValue(), 3);
		assertEquals(result.get(1).intValue(), 2);
		assertEquals(result.get(2).intValue(), 1);
	}

	private void getAllFichePosteAndAffectedAgents(Hashtable<Integer, FichePosteTreeNode> hTree) {

		FichePosteTreeNode fpNiv1 = new FichePosteTreeNode();
		fpNiv1.setIdFichePoste(1);
		fpNiv1.setIdFichePosteParent(null);

		FichePosteTreeNode fpNiv2 = new FichePosteTreeNode();
		fpNiv2.setIdFichePoste(2);
		fpNiv2.setIdFichePosteParent(1);

		FichePosteTreeNode fpNiv2Bis = new FichePosteTreeNode();
		fpNiv2Bis.setIdFichePoste(3);
		fpNiv2Bis.setIdFichePosteParent(1);

		FichePosteTreeNode fpNiv2Niv3 = new FichePosteTreeNode();
		fpNiv2Niv3.setIdFichePoste(4);
		fpNiv2Niv3.setIdFichePosteParent(2);

		FichePosteTreeNode fpNiv2BisNiv3 = new FichePosteTreeNode();
		fpNiv2BisNiv3.setIdFichePoste(5);
		fpNiv2BisNiv3.setIdFichePosteParent(3);

		FichePosteTreeNode fpNiv2Niv3Bis = new FichePosteTreeNode();
		fpNiv2Niv3Bis.setIdFichePoste(6);
		fpNiv2Niv3Bis.setIdFichePosteParent(2);

		FichePosteTreeNode fpNiv2BisNiv3Bis = new FichePosteTreeNode();
		fpNiv2BisNiv3Bis.setIdFichePoste(7);
		fpNiv2BisNiv3Bis.setIdFichePosteParent(3);

		hTree.put(fpNiv1.getIdFichePoste(), fpNiv1);
		hTree.put(fpNiv2.getIdFichePoste(), fpNiv2);
		hTree.put(fpNiv2Bis.getIdFichePoste(), fpNiv2Bis);
		hTree.put(fpNiv2Niv3.getIdFichePoste(), fpNiv2Niv3);
		hTree.put(fpNiv2BisNiv3.getIdFichePoste(), fpNiv2BisNiv3);
		hTree.put(fpNiv2Niv3Bis.getIdFichePoste(), fpNiv2Niv3Bis);
		hTree.put(fpNiv2BisNiv3Bis.getIdFichePoste(), fpNiv2BisNiv3Bis);
	}

	@Test
	public void construitArbreFichePostes_3niveaux() {

		Hashtable<Integer, FichePosteTreeNode> hTree = new Hashtable<Integer, FichePosteTreeNode>();
		getAllFichePosteAndAffectedAgents(hTree);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getAllFichePosteAndAffectedAgents(Mockito.any(Date.class))).thenReturn(hTree);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ficheService.construitArbreFichePostes();

		assertEquals(7, ficheService.hFpTree.size());
		// 1er niveau
		assertEquals(ficheService.hFpTree.get(1).getIdFichePoste().intValue(), 1);
		// 2e niveau
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 3);
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 2);
		// 3e niveau
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(1).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 4);
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(0).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 5);
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(1).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 6);
		assertEquals(ficheService.hFpTree.get(1).getFichePostesEnfant().get(0).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 7);
	}

	private void getAllFichePoste(TreeMap<Integer, FichePosteTreeNode> hTree) {

		fpNiv1 = new FichePosteTreeNode();
		fpNiv1.setIdFichePoste(1);
		fpNiv1.setIdFichePosteParent(null);

		fpNiv2 = new FichePosteTreeNode();
		fpNiv2.setIdFichePoste(2);
		fpNiv2.setIdFichePosteParent(1);

		fpNiv2Bis = new FichePosteTreeNode();
		fpNiv2Bis.setIdFichePoste(3);
		fpNiv2Bis.setIdFichePosteParent(1);

		fpNiv2Niv3 = new FichePosteTreeNode();
		fpNiv2Niv3.setIdFichePoste(4);
		fpNiv2Niv3.setIdFichePosteParent(2);

		fpNiv2BisNiv3 = new FichePosteTreeNode();
		fpNiv2BisNiv3.setIdFichePoste(5);
		fpNiv2BisNiv3.setIdFichePosteParent(3);

		fpNiv2Niv3Bis = new FichePosteTreeNode();
		fpNiv2Niv3Bis.setIdFichePoste(6);
		fpNiv2Niv3Bis.setIdFichePosteParent(2);

		fpNiv2BisNiv3Bis = new FichePosteTreeNode();
		fpNiv2BisNiv3Bis.setIdFichePoste(7);
		fpNiv2BisNiv3Bis.setIdFichePosteParent(3);

		hTree.put(fpNiv1.getIdFichePoste(), fpNiv1);
		hTree.put(fpNiv2.getIdFichePoste(), fpNiv2);
		hTree.put(fpNiv2Bis.getIdFichePoste(), fpNiv2Bis);
		hTree.put(fpNiv2Niv3.getIdFichePoste(), fpNiv2Niv3);
		hTree.put(fpNiv2BisNiv3.getIdFichePoste(), fpNiv2BisNiv3);
		hTree.put(fpNiv2Niv3Bis.getIdFichePoste(), fpNiv2Niv3Bis);
		hTree.put(fpNiv2BisNiv3Bis.getIdFichePoste(), fpNiv2BisNiv3Bis);

		fp1 = new FichePoste();
		fp1.setIdFichePoste(1);

		fp2 = new FichePoste();
		fp2.setIdFichePoste(2);

		fp3 = new FichePoste();
		fp3.setIdFichePoste(3);

		fp4 = new FichePoste();
		fp4.setIdFichePoste(4);

		fp5 = new FichePoste();
		fp5.setIdFichePoste(5);

		fp6 = new FichePoste();
		fp6.setIdFichePoste(6);

		fp7 = new FichePoste();
		fp7.setIdFichePoste(7);
	}

	@Test
	public void getFichePosteTreeAffecteesEtNonAffectees_3niveaux() {

		TreeMap<Integer, FichePosteTreeNode> hTree = new TreeMap<Integer, FichePosteTreeNode>();
		getAllFichePoste(hTree);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getAllFichePoste(Mockito.any(Date.class))).thenReturn(hTree);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);

		ficheService.getFichePosteTreeAffecteesEtNonAffectees();

		assertEquals(7, ficheService.hFpTreeWithFPAffecteesEtNonAffectees.size());
		// 1er niveau
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getIdFichePoste().intValue(), 1);
		// 2e niveau
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 2);
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 3);
		// 3e niveau
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(1).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 7);
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(0).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 6);
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(1).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 5);
		assertEquals(ficheService.hFpTreeWithFPAffecteesEtNonAffectees.get(1).getFichePostesEnfant().get(0).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 4);
	}

	@Test
	public void getTreeFichesPosteByIdEntite_3Niveaux() {

		Integer idEntite = 1;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();
		FichePoste fpNiv1 = new FichePoste();
		fpNiv1.setIdFichePoste(1);
		fpNiv1.setNumFP("2010/1");
		listFichesPoste.add(fpNiv1);

		FichePoste fpNiv2 = new FichePoste();
		fpNiv2.setIdFichePoste(2);
		fpNiv2.setNumFP("2011/1");
		fpNiv2.setSuperieurHierarchique(fpNiv1);
		listFichesPoste.add(fpNiv2);

		FichePoste fpNiv2Bis = new FichePoste();
		fpNiv2Bis.setIdFichePoste(3);
		fpNiv2Bis.setNumFP("2011/2");
		fpNiv2Bis.setSuperieurHierarchique(fpNiv1);
		listFichesPoste.add(fpNiv2Bis);

		FichePoste fpNiv2Niv3 = new FichePoste();
		fpNiv2Niv3.setIdFichePoste(4);
		fpNiv2Niv3.setNumFP("2012/1");
		fpNiv2Niv3.setSuperieurHierarchique(fpNiv2);
		listFichesPoste.add(fpNiv2Niv3);

		FichePoste fpNiv2BisNiv3 = new FichePoste();
		fpNiv2BisNiv3.setIdFichePoste(5);
		fpNiv2BisNiv3.setNumFP("2012/2");
		fpNiv2BisNiv3.setSuperieurHierarchique(fpNiv2Bis);
		listFichesPoste.add(fpNiv2BisNiv3);

		FichePoste fpNiv2Niv3Bis = new FichePoste();
		fpNiv2Niv3Bis.setIdFichePoste(6);
		fpNiv2Niv3Bis.setNumFP("2012/3");
		fpNiv2Niv3Bis.setSuperieurHierarchique(fpNiv2);
		listFichesPoste.add(fpNiv2Niv3Bis);

		FichePoste fpNiv2BisNiv3Bis = new FichePoste();
		fpNiv2BisNiv3Bis.setIdFichePoste(7);
		fpNiv2BisNiv3Bis.setNumFP("2012/4");
		fpNiv2BisNiv3Bis.setSuperieurHierarchique(fpNiv2Bis);
		listFichesPoste.add(fpNiv2BisNiv3Bis);

		TreeMap<Integer, FichePosteTreeNode> hFpTreeWithFPAffecteesEtNonAffectees = new TreeMap<Integer, FichePosteTreeNode>();
		getAllFichePoste(hFpTreeWithFPAffecteesEtNonAffectees);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getAllFichePoste(Mockito.any(Date.class))).thenReturn(hFpTreeWithFPAffecteesEtNonAffectees);

		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(idEntite),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
								EnumStatutFichePoste.TRANSITOIRE.getStatut()))).thenReturn(listFichesPoste);

		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(new FichePoste());

		Mockito.when(fichePosteDao.chercherListFichesPosteByListIdsFichePoste(Mockito.anyList())).thenReturn(listFichesPoste);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(Mockito.anyInt(), Mockito.any(List.class))).thenReturn(new EntiteDto());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteTreeNodeDto> result = ficheService.getTreeFichesPosteByIdEntite(idEntite, false);

		assertEquals(1, result.size());
		// 1er niveau
		assertEquals(result.get(0).getIdFichePoste().intValue(), 1);
		// 2e niveau
		assertEquals(result.get(0).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 2);
		assertEquals(result.get(0).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 3);
		// 3e niveau
		assertEquals(result.get(0).getFichePostesEnfant().get(0).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 4);
		assertEquals(result.get(0).getFichePostesEnfant().get(0).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 6);
		assertEquals(result.get(0).getFichePostesEnfant().get(1).getFichePostesEnfant().get(0).getIdFichePoste().intValue(), 5);
		assertEquals(result.get(0).getFichePostesEnfant().get(1).getFichePostesEnfant().get(1).getIdFichePoste().intValue(), 7);
	}

	@Test
	public void getTreeFichesPosteByIdEntite_2FichesDePosteMemeNiveau() {

		Integer idEntite = 1;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();

		fp2 = new FichePoste();
		fp2.setIdFichePoste(2);
		fp2.setNumFP("2011/1");
		listFichesPoste.add(fp2);

		fp3 = new FichePoste();
		fp3.setIdFichePoste(3);
		fp3.setNumFP("2011/2");
		listFichesPoste.add(fp3);

		TreeMap<Integer, FichePosteTreeNode> hTree = new TreeMap<Integer, FichePosteTreeNode>();
		getAllFichePoste(hTree);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getAllFichePoste(Mockito.any(Date.class))).thenReturn(hTree);

		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(idEntite),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
								EnumStatutFichePoste.TRANSITOIRE.getStatut()))).thenReturn(listFichesPoste);

		Mockito.when(fichePosteDao.chercherFichePoste(Mockito.anyInt())).thenReturn(new FichePoste());

		Mockito.when(fichePosteDao.chercherListFichesPosteByListIdsFichePoste(Arrays.asList(3, 5, 7, 2, 4, 6))).thenReturn(Arrays.asList(fp2, fp4, fp6, fp3, fp5, fp7));

		Mockito.when(fichePosteDao.chercherListFichesPosteByListIdsFichePoste(Arrays.asList(3, 5, 7))).thenReturn(Arrays.asList(fp3, fp5, fp7));

		Mockito.when(fichePosteDao.chercherListFichesPosteByListIdsFichePoste(Arrays.asList(2, 4, 6))).thenReturn(Arrays.asList(fp2, fp4, fp6));

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(Mockito.anyInt(), Mockito.any(List.class))).thenReturn(new EntiteDto());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		// ficheService.construitArbreFichePostes();
		List<FichePosteTreeNodeDto> result = ficheService.getTreeFichesPosteByIdEntite(idEntite, false);

		assertEquals(2, result.size());
		// 1er niveau
		assertEquals(result.get(0).getIdFichePoste().intValue(), 3);
		assertEquals(result.get(1).getIdFichePoste().intValue(), 2);
	}

	@Test
	public void getTreeFichesPosteByIdEntite_3FichesDePosteMemeNiveau_FDPReglementaire() {

		Integer idEntite = 1;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();
		FichePoste fp = new FichePoste();
		fp.setIdFichePoste(1);
		fp.setNumFP("2010/1");
		listFichesPoste.add(fp);

		FichePoste fp2 = new FichePoste();
		fp2.setIdFichePoste(2);
		fp2.setNumFP("2011/1");
		listFichesPoste.add(fp2);

		Spbhor reglementaire = new Spbhor();
		reglementaire.setCdThor(0);

		FichePoste fpNonReglementaire = new FichePoste();
		fpNonReglementaire.setIdFichePoste(3);
		fpNonReglementaire.setNumFP("2011/2");
		fpNonReglementaire.setReglementaire(reglementaire);
		listFichesPoste.add(fpNonReglementaire);

		TreeMap<Integer, FichePosteTreeNode> hTree = new TreeMap<Integer, FichePosteTreeNode>();
		getAllFichePoste(hTree);

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(fichePosteDao.getAllFichePoste(Mockito.any(Date.class))).thenReturn(hTree);

		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDPWithJointurePourOptimisation(
						Arrays.asList(idEntite),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.EN_CREATION.getStatut(), EnumStatutFichePoste.GELEE.getStatut(),
								EnumStatutFichePoste.TRANSITOIRE.getStatut()))).thenReturn(listFichesPoste);

		Mockito.when(fichePosteDao.chercherFichePoste(1)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(2)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(7)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(4)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(5)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(6)).thenReturn(new FichePoste());
		Mockito.when(fichePosteDao.chercherFichePoste(3)).thenReturn(fpNonReglementaire);

		Mockito.when(fichePosteDao.chercherListFichesPosteByListIdsFichePoste(Mockito.anyList())).thenReturn(listFichesPoste);

		IAdsService adsService = Mockito.mock(IAdsService.class);
		Mockito.when(adsService.getEntiteByIdEntiteOptimise(Mockito.anyInt(), Mockito.any(List.class))).thenReturn(new EntiteDto());

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "adsService", adsService);

		List<FichePosteTreeNodeDto> result = ficheService.getTreeFichesPosteByIdEntite(idEntite, false);

		assertEquals(2, result.size());
		// 1er niveau
		assertEquals(result.get(0).getIdFichePoste().intValue(), 2);
		assertEquals(result.get(1).getIdFichePoste().intValue(), 1);

		result = ficheService.getTreeFichesPosteByIdEntite(idEntite, true);

		assertEquals(3, result.size());
		// 1er niveau
		assertEquals(result.get(0).getIdFichePoste().intValue(), 3);
		assertEquals(result.get(1).getIdFichePoste().intValue(), 2);
		assertEquals(result.get(2).getIdFichePoste().intValue(), 1);
	}

	@Test
	public void deplaceFichePosteFromEntityToOtherEntity_UserNonReconnu() {

		Integer idEntiteSource = 1;
		EntiteDto entiteSource = new EntiteDto();
		entiteSource.setSigle("SOURCE");
		Integer idEntiteCible = 2;
		EntiteDto entiteCible = new EntiteDto();
		entiteCible.setSigle("CIBLE");
		Integer idAgent = 9005138;

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(
						Arrays.asList(idEntiteSource),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.GELEE.getStatut(), EnumStatutFichePoste.TRANSITOIRE.getStatut(),
								EnumStatutFichePoste.EN_CREATION.getStatut()))).thenReturn(null);

		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(idAgent)).thenReturn(null);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteSource);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(2)).thenReturn(entiteCible);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.deplaceFichePosteFromEntityToOtherEntity(idEntiteSource, idEntiteCible, idAgent);
		assertTrue(result.getInfos().isEmpty());
		assertEquals("L'agent qui tente de faire l'action n'a pas de login dans l'AD.", result.getErrors().get(0));
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void deplaceFichePosteFromEntityToOtherEntity_pasFichePoste() {

		Integer idEntiteSource = 1;
		EntiteDto entiteSource = new EntiteDto();
		entiteSource.setSigle("SOURCE");
		Integer idEntiteCible = 2;
		EntiteDto entiteCible = new EntiteDto();
		entiteCible.setSigle("CIBLE");

		Integer idAgent = 9005138;

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(
						Arrays.asList(idEntiteSource),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.GELEE.getStatut(), EnumStatutFichePoste.TRANSITOIRE.getStatut(),
								EnumStatutFichePoste.EN_CREATION.getStatut()))).thenReturn(null);

		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("rebjo84");
		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(idAgent)).thenReturn(user);

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteSource);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(2)).thenReturn(entiteCible);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.deplaceFichePosteFromEntityToOtherEntity(idEntiteSource, idEntiteCible, idAgent);
		assertEquals("Aucune FDP ne sont déplacées de l'entité SOURCE vers l'entité CIBLE.", result.getInfos().get(0));
		assertTrue(result.getErrors().isEmpty());
		Mockito.verify(fichePosteDao, Mockito.never()).persisEntity(Mockito.isA(HistoFichePoste.class));
	}

	@Test
	public void deplaceFichePosteFromEntityToOtherEntity_badEntite() {

		Integer idEntiteSource = 1;
		Integer idEntiteCible = 2;

		Integer idAgent = 9005138;

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(null);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.deplaceFichePosteFromEntityToOtherEntity(idEntiteSource, idEntiteCible, idAgent);
		assertEquals("L'entité 1 n'existe pas.", result.getErrors().get(0));
		assertEquals(1, result.getErrors().size());
		assertEquals(0, result.getInfos().size());
	}

	@Test
	public void deplaceFichePosteFromEntityToOtherEntity_3FichesPoste() {

		Integer idEntiteSource = 1;
		EntiteDto entiteSource = new EntiteDto();
		entiteSource.setSigle("SOURCE");
		Integer idEntiteCible = 2;
		EntiteDto entiteCible = new EntiteDto();
		entiteCible.setSigle("CIBLE");
		Integer idAgent = 9005138;

		List<FichePoste> listFichesPoste = new ArrayList<FichePoste>();
		FichePoste fp = new FichePoste();
		FichePoste fp2 = new FichePoste();
		FichePoste fp3 = new FichePoste();

		listFichesPoste.addAll(Arrays.asList(fp, fp2, fp3));

		IFichePosteRepository fichePosteDao = Mockito.mock(IFichePosteRepository.class);
		Mockito.when(
				fichePosteDao.getListFichePosteByIdServiceADSAndStatutFDP(
						Arrays.asList(idEntiteSource),
						Arrays.asList(EnumStatutFichePoste.VALIDEE.getStatut(), EnumStatutFichePoste.GELEE.getStatut(), EnumStatutFichePoste.TRANSITOIRE.getStatut(),
								EnumStatutFichePoste.EN_CREATION.getStatut()))).thenReturn(listFichesPoste);

		LightUserDto user = new LightUserDto();
		user.setsAMAccountName("rebjo84");
		IUtilisateurService utilisateurSrv = Mockito.mock(IUtilisateurService.class);
		Mockito.when(utilisateurSrv.getLoginByIdAgent(idAgent)).thenReturn(user);

		Mockito.doAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) {
				HistoFichePoste histo = (HistoFichePoste) invocation.getArguments()[0];
				assertEquals(histo.getUserHisto(), "rebjo84");
				assertEquals(histo.getTypeHisto(), EnumTypeHisto.MODIFICATION.getValue());
				return null;
			}
		}).when(fichePosteDao).persisEntity(Mockito.isA(HistoFichePoste.class));

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(1)).thenReturn(entiteSource);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(2)).thenReturn(entiteCible);

		FichePosteService ficheService = new FichePosteService();
		ReflectionTestUtils.setField(ficheService, "fichePosteDao", fichePosteDao);
		ReflectionTestUtils.setField(ficheService, "utilisateurSrv", utilisateurSrv);
		ReflectionTestUtils.setField(ficheService, "adsWSConsumer", adsWSConsumer);

		ReturnMessageDto result = ficheService.deplaceFichePosteFromEntityToOtherEntity(idEntiteSource, idEntiteCible, idAgent);
		assertEquals("3 FDP sont déplacées de l'entité SOURCE vers l'entité CIBLE.", result.getInfos().get(0));
		assertTrue(result.getErrors().isEmpty());
		Mockito.verify(fichePosteDao, Mockito.times(3)).persisEntity(Mockito.isA(HistoFichePoste.class));
	}
}