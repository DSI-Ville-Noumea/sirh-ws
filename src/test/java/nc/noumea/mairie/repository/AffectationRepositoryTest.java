package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AffectationRepositoryTest {

	@Autowired
	IAffectationRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnResult() {

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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);

		assertEquals(result.getIdAffectation().intValue(), 1);
	}

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_DateFin() {

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

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(cal.getTime());
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);

		assertNull(result);
	}

	// @Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_badAgent() {

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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005438);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_returnResult() {

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

		Siserv service = new Siserv();
		service.setLiServ("liServ");
		service.setSigle("sigle");
		service.setParentSigle("parentSigle");
		service.setCodeActif("codeActif");
		service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, "SERVICE1");

		assertEquals(result.size(), 1);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badService() {

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

		Siserv service = new Siserv();
		service.setLiServ("liServ");
		service.setSigle("sigle");
		service.setParentSigle("parentSigle");
		service.setCodeActif("codeActif");
		service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, "SERVICE2");

		assertEquals(result.size(), 0);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badAgent() {

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

		Siserv service = new Siserv();
		service.setLiServ("liServ");
		service.setSigle("sigle");
		service.setParentSigle("parentSigle");
		service.setCodeActif("codeActif");
		service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(1);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005130, "SERVICE1");

		assertEquals(result.size(), 0);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_1result() {

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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, 1);

		assertEquals(result.size(), 1);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badAgent() {

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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005118, 1);

		assertEquals(result.size(), 0);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badFP() {

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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(new Date());
		a.setDateFinAff(null);
		a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, 2);

		assertEquals(result.size(), 0);
	}
}