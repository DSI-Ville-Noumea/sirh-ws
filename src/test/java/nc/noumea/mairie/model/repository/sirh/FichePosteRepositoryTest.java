package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.AvantageNatureFP;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.Delegation;
import nc.noumea.mairie.model.bean.sirh.DelegationFP;
import nc.noumea.mairie.model.bean.sirh.FeFp;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.NiveauEtudeFP;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.RegIndemFP;
import nc.noumea.mairie.model.bean.sirh.RegimeIndemnitaire;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.pk.sirh.ActiviteFPPK;
import nc.noumea.mairie.model.pk.sirh.AvantageNatureFPPK;
import nc.noumea.mairie.model.pk.sirh.CompetenceFPPK;
import nc.noumea.mairie.model.pk.sirh.DelegationFPPK;
import nc.noumea.mairie.model.pk.sirh.FeFpPK;
import nc.noumea.mairie.model.pk.sirh.NiveauEtudeFPPK;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;
import nc.noumea.mairie.model.pk.sirh.RegIndemFPPK;
import nc.noumea.mairie.web.dto.InfoFichePosteDto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class FichePosteRepositoryTest {

	@Autowired
	IFichePosteRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_NoStatut_returnResults() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("lib statut");
		sirhPersistenceUnit.persist(statutFP);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		FichePoste fichePoste3 = new FichePoste();
		fichePoste3.setAnnee(2010);
		fichePoste3.setMissions("missions");
		fichePoste3.setNumFP("numFP");
		fichePoste3.setOpi("opi");
		fichePoste3.setNfa("nfa");
		fichePoste3.setIdServiceADS(3);
		fichePoste3.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste3);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1, 3), null);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
		assertEquals(fichePoste3.getIdFichePoste(), result.get(1).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_WithStatut_emptyList() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("lib statut");
		sirhPersistenceUnit.persist(statutFP);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1),
				Arrays.asList(2));

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_WithStatut_returnResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("lib statut");
		sirhPersistenceUnit.persist(statutFP);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1),
				Arrays.asList(1));

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_returnEmptyList() {

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherFichePoste_returnResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("lib statut");
		sirhPersistenceUnit.persist(statutFP);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		FichePoste result = repository.chercherFichePoste(fichePoste2.getIdFichePoste());

		assertNotNull(result);
		assertEquals(fichePoste2.getIdFichePoste(), result.getIdFichePoste());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherFichePoste_returnNoResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(1);
		statutFP.setLibStatut("lib statut");
		sirhPersistenceUnit.persist(statutFP);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		FichePoste result = repository.chercherFichePoste(1);

		assertNull(result);
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste_returnResult() {
		Spbhor reglementaire = new Spbhor();
		reglementaire.setCdThor(1);
		reglementaire.setTaux(0.75);
		sirhPersistenceUnit.persist(reglementaire);

		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setIdStatutFp(2);
		sirhPersistenceUnit.persist(statutFP);

		TitrePoste titrePoste = new TitrePoste();
		titrePoste.setIdTitrePoste(2);
		titrePoste.setLibTitrePoste("LIB 2");
		sirhPersistenceUnit.persist(titrePoste);

		TitrePoste titrePoste2 = new TitrePoste();
		titrePoste2.setIdTitrePoste(1);
		titrePoste2.setLibTitrePoste("LIB 1");
		sirhPersistenceUnit.persist(titrePoste2);

		FichePoste fichePoste3 = new FichePoste();
		fichePoste3.setAnnee(2010);
		fichePoste3.setMissions("missions");
		fichePoste3.setNumFP("numFP");
		fichePoste3.setOpi("opi");
		fichePoste3.setNfa("nfa");
		fichePoste3.setIdServiceADS(1);
		fichePoste3.setReglementaire(reglementaire);
		fichePoste3.setStatutFP(statutFP);
		fichePoste3.setTitrePoste(titrePoste2);
		sirhPersistenceUnit.persist(fichePoste3);

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(1);
		fichePoste2.setReglementaire(reglementaire);
		fichePoste2.setStatutFP(statutFP);
		fichePoste2.setTitrePoste(titrePoste2);
		sirhPersistenceUnit.persist(fichePoste2);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setReglementaire(reglementaire);
		fichePoste.setStatutFP(statutFP);
		fichePoste.setTitrePoste(titrePoste);
		sirhPersistenceUnit.persist(fichePoste);

		List<InfoFichePosteDto> result = repository
				.getInfoFichePosteForOrganigrammeByIdServiceADSGroupByTitrePoste(Arrays.asList(1));

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(new Double(1.5), result.get(0).getTauxETP());
		assertEquals("LIB 1", result.get(0).getTitreFDP());
		assertEquals(new Integer(2), result.get(0).getNbFDP());
		assertEquals(new Double(0.75), result.get(1).getTauxETP());
		assertEquals("LIB 2", result.get(1).getTitreFDP());
		assertEquals(new Integer(1), result.get(1).getNbFDP());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerFEFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);
		FicheEmploi ficheEmploi = new FicheEmploi();
		ficheEmploi.setIdFicheEmploi(1);
		sirhPersistenceUnit.persist(ficheEmploi);

		FeFpPK id2 = new FeFpPK();
		id2.setIdFichePoste(fichePoste.getIdFichePoste());
		id2.setIdFicheEmploi(ficheEmploi.getIdFicheEmploi());
		FeFp lien2 = new FeFp();
		lien2.setId(id2);
		lien2.setFePrimaire(0);
		sirhPersistenceUnit.persist(lien2);

		List<FeFp> result = repository.listerFEFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerFEFPAvecFP_NoResults() {

		List<FeFp> result = repository.listerFEFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerNiveauEtudeFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();		
		sirhPersistenceUnit.persist(fichePoste);

		NiveauEtude niveau1 = new NiveauEtude();
		niveau1.setIdNiveauEtude(1);
		niveau1.setLibelleNiveauEtude("1");
		sirhPersistenceUnit.persist(niveau1);

		NiveauEtudeFPPK niveauEtudeFPPK1 = new NiveauEtudeFPPK();
		niveauEtudeFPPK1.setIdNiveauEtude(niveau1.getIdNiveauEtude());
		niveauEtudeFPPK1.setIdFichePoste(fichePoste.getIdFichePoste());

		NiveauEtudeFP lien2 = new NiveauEtudeFP();
		lien2.setNiveauEtudeFPPK(niveauEtudeFPPK1);
		sirhPersistenceUnit.persist(lien2);

		List<NiveauEtudeFP> result = repository.listerNiveauEtudeFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerNiveauEtudeFPAvecFP_NoResults() {

		List<NiveauEtudeFP> result = repository.listerNiveauEtudeFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerActiviteFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);
		Activite niveau1 = new Activite();
		niveau1.setIdActivite(1);
		sirhPersistenceUnit.persist(niveau1);

		ActiviteFPPK activiteFPPK = new ActiviteFPPK();
		activiteFPPK.setIdFichePoste(fichePoste.getIdFichePoste());
		activiteFPPK.setIdActivite(niveau1.getIdActivite());
		ActiviteFP lien2 = new ActiviteFP();
		lien2.setActiviteFPPK(activiteFPPK);
		sirhPersistenceUnit.persist(lien2);

		List<ActiviteFP> result = repository.listerActiviteFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerActiviteFPAvecFP_NoResults() {

		List<ActiviteFP> result = repository.listerActiviteFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerCompetenceFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);
		Competence niveau1 = new Competence();
		niveau1.setIdCompetence(1);
		sirhPersistenceUnit.persist(niveau1);

		CompetenceFPPK competenceFPPK = new CompetenceFPPK();
		competenceFPPK.setIdFichePoste(fichePoste.getIdFichePoste());
		competenceFPPK.setIdCompetence(niveau1.getIdCompetence());
		CompetenceFP lien2 = new CompetenceFP();
		lien2.setCompetenceFPPK(competenceFPPK);
		sirhPersistenceUnit.persist(lien2);

		List<CompetenceFP> result = repository.listerCompetenceFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerCompetenceFPAvecFP_NoResults() {

		List<CompetenceFP> result = repository.listerCompetenceFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerAvantageNatureFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);
		AvantageNature niveau1 = new AvantageNature();
		niveau1.setIdAvantage(1);
		sirhPersistenceUnit.persist(niveau1);

		AvantageNatureFPPK avantageNaturePK = new AvantageNatureFPPK();
		avantageNaturePK.setIdFichePoste(fichePoste.getIdFichePoste());
		avantageNaturePK.setIdAvantage(niveau1.getIdAvantage());
		AvantageNatureFP lien2 = new AvantageNatureFP();
		lien2.setAvantageNaturePK(avantageNaturePK);
		sirhPersistenceUnit.persist(lien2);

		List<AvantageNatureFP> result = repository.listerAvantageNatureFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerAvantageNatureFPAvecFP_NoResults() {

		List<AvantageNatureFP> result = repository.listerAvantageNatureFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerDelegationFPAvecFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);
		Delegation niveau1 = new Delegation();
		niveau1.setIdDelegation(1);
		sirhPersistenceUnit.persist(niveau1);

		DelegationFPPK delegationFPPK = new DelegationFPPK();
		delegationFPPK.setIdFichePoste(fichePoste.getIdFichePoste());
		delegationFPPK.setIdDelegation(niveau1.getIdDelegation());
		DelegationFP lien2 = new DelegationFP();
		lien2.setDelegationFPPK(delegationFPPK);
		sirhPersistenceUnit.persist(lien2);

		List<DelegationFP> result = repository.listerDelegationFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerDelegationFPAvecFP_NoResults() {

		List<DelegationFP> result = repository.listerDelegationFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerPrimePointageFP_returnResults() {

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);

		PrimePointageFPPK delegationFPPK = new PrimePointageFPPK();
		delegationFPPK.setNumRubrique(2543);
		PrimePointageFP lien2 = new PrimePointageFP();
		lien2.setPrimePointageFPPK(delegationFPPK);
		lien2.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(lien2);

		List<PrimePointageFP> result = repository.listerPrimePointageFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerPrimePointageFP_NoResults() {

		List<PrimePointageFP> result = repository.listerPrimePointageFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerRegIndemFPFPAvecFP_returnResults() {

		RegimeIndemnitaire regime = new RegimeIndemnitaire();
		regime.setIdRegimeIndemnitaire(1);
		sirhPersistenceUnit.persist(regime);

		FichePoste fichePoste = new FichePoste();
		sirhPersistenceUnit.persist(fichePoste);

		RegIndemFPPK regIndemFPPK = new RegIndemFPPK();
		regIndemFPPK.setIdFichePoste(fichePoste.getIdFichePoste());
		regIndemFPPK.setIdRegime(regime.getIdRegimeIndemnitaire());
		RegIndemFP lien2 = new RegIndemFP();
		lien2.setRegIndemFPPK(regIndemFPPK);
		sirhPersistenceUnit.persist(lien2);

		List<RegIndemFP> result = repository.listerRegIndemFPFPAvecFP(fichePoste.getIdFichePoste());

		assertNotNull(result);
		assertEquals(1, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listerRegIndemFPFPAvecFP_NoResults() {

		List<RegIndemFP> result = repository.listerRegIndemFPFPAvecFP(1);

		assertNotNull(result);
		assertEquals(0, result.size());
		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
}
