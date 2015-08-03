package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
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
		fichePoste2.setIdFichePoste(1);
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
		fichePoste.setIdFichePoste(2);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		FichePoste fichePoste3 = new FichePoste();
		fichePoste3.setIdFichePoste(13);
		fichePoste3.setAnnee(2010);
		fichePoste3.setMissions("missions");
		fichePoste3.setNumFP("numFP");
		fichePoste3.setOpi("opi");
		fichePoste3.setNfa("nfa");
		fichePoste3.setIdServiceADS(3);
		fichePoste3.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste3);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1,3), null);

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
		assertEquals(fichePoste3.getIdFichePoste(), result.get(1).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_WithStatut_emptyList() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(1);
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
		fichePoste.setIdFichePoste(2);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(2));

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_WithStatut_returnResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(1);
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
		fichePoste.setIdFichePoste(2);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), Arrays.asList(1));

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getIdFichePoste());
		assertEquals(statutFP.getLibStatut(), result.get(0).getStatutFP().getLibStatut());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADSAndStatutFDP_returnEmptyList() {

		List<FichePoste> result = repository.getListFichePosteByIdServiceADSAndStatutFDP(Arrays.asList(1), null);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherFichePoste_returnResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(1);
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
		fichePoste.setIdFichePoste(2);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		fichePoste.setStatutFP(statutFP);
		sirhPersistenceUnit.persist(fichePoste);

		FichePoste result = repository.chercherFichePoste(1);

		assertNotNull(result);
		assertEquals(fichePoste2.getIdFichePoste(), result.getIdFichePoste());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherFichePoste_returnNoResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(3);
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
		fichePoste.setIdFichePoste(2);
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
		fichePoste3.setIdFichePoste(4);
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
		fichePoste2.setIdFichePoste(3);
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
		fichePoste.setIdFichePoste(2);
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
	}
}
