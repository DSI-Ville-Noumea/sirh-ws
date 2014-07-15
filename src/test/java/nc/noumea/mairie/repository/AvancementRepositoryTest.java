package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.MotifAvct;
import nc.noumea.mairie.model.repository.sirh.IAvancementRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AvancementRepositoryTest {

	@Autowired
	IAvancementRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire() {

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

		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setAnneeAvancement(2010);
		af.setAgent(ag);
		af.setCodeCategporie(1);
		sirhPersistenceUnit.persist(af);

		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, true);

		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(result.getCodeCategporie(), 1);
		assertEquals(2010, result.getAnneeAvancement());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badYear() {

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

		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setAnneeAvancement(2010);
		af.setAgent(ag);
		af.setCodeCategporie(1);
		sirhPersistenceUnit.persist(af);

		AvancementFonctionnaire result = repository.getAvancement(9005138, 2012, true);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badIdAgent() {

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

		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setAnneeAvancement(2010);
		af.setAgent(ag);
		af.setCodeCategporie(1);
		sirhPersistenceUnit.persist(af);

		AvancementFonctionnaire result = repository.getAvancement(9003138, 2010, true);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badCodeCategorie() {

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

		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setAnneeAvancement(2010);
		af.setAgent(ag);
		af.setCodeCategporie(10);
		sirhPersistenceUnit.persist(af);

		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, true);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_returnResult_badCodeCategorie() {

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

		AvancementFonctionnaire af = new AvancementFonctionnaire();
		af.setIdAvct(1);
		af.setAnneeAvancement(2010);
		af.setAgent(ag);
		af.setCodeCategporie(10);
		sirhPersistenceUnit.persist(af);

		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, false);

		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(result.getCodeCategporie(), 10);
		assertEquals(2010, result.getAnneeAvancement());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnResult() {

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

		AvancementDetache ad = new AvancementDetache();
		ad.setIdAvct(1);
		ad.setAnneeAvancement(2010);
		ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);

		AvancementDetache result = repository.getAvancementDetache(9005138, 2010);

		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(2010, result.getAnneeAvancement());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnNull_badYear() {

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

		AvancementDetache ad = new AvancementDetache();
		ad.setIdAvct(1);
		ad.setAnneeAvancement(2010);
		ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);

		AvancementDetache result = repository.getAvancementDetache(9005138, 2011);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnNull_badAgent() {

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

		AvancementDetache ad = new AvancementDetache();
		ad.setIdAvct(1);
		ad.setAnneeAvancement(2010);
		ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);

		AvancementDetache result = repository.getAvancementDetache(9005131, 2010);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getMotifAvct_return1result() {

		MotifAvct ag = new MotifAvct();
		ag.setIdMotifAvct(1);
		sirhPersistenceUnit.persist(ag);

		MotifAvct result = repository.getMotifAvct(1);

		assertNotNull(result);
		assertEquals("1", result.getIdMotifAvct().toString());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getMotifAvct_returnNull() {

		MotifAvct ag = new MotifAvct();
		ag.setIdMotifAvct(1);
		sirhPersistenceUnit.persist(ag);

		MotifAvct result = repository.getMotifAvct(2);

		assertNull(result);
	}
}
