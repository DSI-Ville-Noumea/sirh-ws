package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.sirh.IAgentRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AgentRepositoryTest {

	@Autowired
	IAgentRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentWithListNomatr_return1result() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Agent result = repository.getAgentWithListNomatr(5138);

		assertNotNull(result);
		assertEquals("9005138", result.getIdAgent().toString());
		assertEquals("USAGE", result.getDisplayNom());
		assertEquals("NONO", result.getPrenomUsage());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentWithListNomatr_returnNull() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Agent result = repository.getAgentWithListNomatr(5184);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_return1result() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(null);
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);

		Agent result = repository.getAgentEligibleEAESansAffectes(5138);

		assertEquals(result.getNomPatronymique(), "TEST");
		assertEquals(result.getPrenomUsage(), "NONO");
		assertEquals(result.getSexe(), "H");
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_returnNull_dateFinKo() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new Date());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);

		Agent result = repository.getAgentEligibleEAESansAffectes(5138);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_returnNull_badNoMatr() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new Date());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);

		Agent result = repository.getAgentEligibleEAESansAffectes(5158);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgent_return1result() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Agent result = repository.getAgent(9005138);

		assertNotNull(result);
		assertEquals("9005138", result.getIdAgent().toString());
		assertEquals("USAGE", result.getDisplayNom());
		assertEquals("NONO", result.getPrenomUsage());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgent_returnNull() {

		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setPrenom("NON");
		ag.setDateNaissance(new Date());
		ag.setNomPatronymique("TEST");
		ag.setNomUsage("USAGE");
		ag.setPrenomUsage("NONO");
		ag.setSexe("H");
		ag.setTitre("Mr");
		sirhPersistenceUnit.persist(ag);

		Agent result = repository.getAgent(9005184);

		assertNull(result);
	}
}
