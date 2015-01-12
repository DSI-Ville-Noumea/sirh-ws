package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.AutreAdministration;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.bean.sirh.JourFerie;
import nc.noumea.mairie.model.pk.sirh.AutreAdministrationAgentPK;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class SirhRepositoryTest {

	@Autowired
	ISirhRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_return1result() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(1);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministrationAgent result = repository.chercherAutreAdministrationAgentAncienne(9005138, true);

		assertEquals(result.getFonctionnaire().intValue(), 1);
		assertEquals(result.getAutreAdministrationAgentPK().getIdAgent().intValue(), 9005138);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_returnNull_badFonctionnaire() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(0);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministrationAgent result = repository.chercherAutreAdministrationAgentAncienne(9005138, true);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_returnNull_badAgent() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(1);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministrationAgent result = repository.chercherAutreAdministrationAgentAncienne(9005234, true);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_returnNull_badAgent() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(1);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministrationAgent result = repository.chercherAutreAdministrationAgentAncienne(9005234, false);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_return1result() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(0);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministrationAgent result = repository.chercherAutreAdministrationAgentAncienne(9005138, false);

		assertEquals(result.getFonctionnaire().intValue(), 0);
		assertEquals(result.getAutreAdministrationAgentPK().getIdAgent().intValue(), 9005138);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAutreAdministrationAgent_return2result() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(0);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministration aa2 = new AutreAdministration();
		aa2.setIdAutreAdmin(2);
		aa2.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa2);

		AutreAdministrationAgentPK id2 = new AutreAdministrationAgentPK();
		id2.setDateEntree(new Date());
		id2.setIdAutreAdmin(2);
		id2.setIdAgent(9005138);

		AutreAdministrationAgent aaa2 = new AutreAdministrationAgent();
		aaa2.setFonctionnaire(0);
		aaa2.setDateSortie(new Date());
		aaa2.setAutreAdministration(aa2);
		aaa2.setAutreAdministrationAgentPK(id2);
		sirhPersistenceUnit.persist(aaa2);

		AutreAdministration aa3 = new AutreAdministration();
		aa3.setIdAutreAdmin(3);
		aa3.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa3);

		AutreAdministrationAgentPK id3 = new AutreAdministrationAgentPK();
		id3.setDateEntree(new Date());
		id3.setIdAutreAdmin(3);
		id3.setIdAgent(9005148);

		AutreAdministrationAgent aaa3 = new AutreAdministrationAgent();
		aaa3.setFonctionnaire(0);
		aaa3.setDateSortie(new Date());
		aaa3.setAutreAdministration(aa3);
		aaa3.setAutreAdministrationAgentPK(id3);
		sirhPersistenceUnit.persist(aaa3);

		List<AutreAdministrationAgent> result = repository.getListeAutreAdministrationAgent(9005138);

		assertEquals(2, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAutreAdministrationAgent_return1result() {

		AutreAdministration aa = new AutreAdministration();
		aa.setIdAutreAdmin(1);
		aa.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa);

		AutreAdministrationAgentPK id = new AutreAdministrationAgentPK();
		id.setDateEntree(new Date());
		id.setIdAutreAdmin(1);
		id.setIdAgent(9005138);

		AutreAdministrationAgent aaa = new AutreAdministrationAgent();
		aaa.setFonctionnaire(0);
		aaa.setDateSortie(new Date());
		aaa.setAutreAdministration(aa);
		aaa.setAutreAdministrationAgentPK(id);
		sirhPersistenceUnit.persist(aaa);

		AutreAdministration aa2 = new AutreAdministration();
		aa2.setIdAutreAdmin(2);
		aa2.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa2);

		AutreAdministrationAgentPK id2 = new AutreAdministrationAgentPK();
		id2.setDateEntree(new Date());
		id2.setIdAutreAdmin(2);
		id2.setIdAgent(9005138);

		AutreAdministrationAgent aaa2 = new AutreAdministrationAgent();
		aaa2.setFonctionnaire(0);
		aaa2.setDateSortie(new Date());
		aaa2.setAutreAdministration(aa2);
		aaa2.setAutreAdministrationAgentPK(id2);
		sirhPersistenceUnit.persist(aaa2);

		AutreAdministration aa3 = new AutreAdministration();
		aa3.setIdAutreAdmin(3);
		aa3.setLibAutreAdmin("libAutreAdmin");
		sirhPersistenceUnit.persist(aa3);

		AutreAdministrationAgentPK id3 = new AutreAdministrationAgentPK();
		id3.setDateEntree(new Date());
		id3.setIdAutreAdmin(3);
		id3.setIdAgent(9005148);

		AutreAdministrationAgent aaa3 = new AutreAdministrationAgent();
		aaa3.setFonctionnaire(0);
		aaa3.setDateSortie(new Date());
		aaa3.setAutreAdministration(aa3);
		aaa3.setAutreAdministrationAgentPK(id3);
		sirhPersistenceUnit.persist(aaa3);

		List<AutreAdministrationAgent> result = repository.getListeAutreAdministrationAgent(9005148);

		assertEquals(1, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListDiplomeByAgent_return2result() {

		DiplomeAgent da = new DiplomeAgent();
		da.setIdDiplome(1);
		da.setNomEcole("nomEcole");
		da.setIdDocument(1);
		da.setDateObtention(new Date());
		da.setIdAgent(9005138);
		sirhPersistenceUnit.persist(da);

		DiplomeAgent da2 = new DiplomeAgent();
		da2.setIdDiplome(2);
		da2.setNomEcole("nomEcole");
		da2.setIdDocument(1);
		da2.setDateObtention(new Date());
		da2.setIdAgent(9005138);
		sirhPersistenceUnit.persist(da2);

		DiplomeAgent da3 = new DiplomeAgent();
		da3.setIdDiplome(3);
		da3.setNomEcole("nomEcole");
		da3.setIdDocument(1);
		da3.setDateObtention(new Date());
		da3.setIdAgent(9005178);
		sirhPersistenceUnit.persist(da3);

		List<DiplomeAgent> result = repository.getListDiplomeByAgent(9005138);

		assertEquals(2, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListDiplomeByAgent_return1result() {

		DiplomeAgent da = new DiplomeAgent();
		da.setIdDiplome(1);
		da.setNomEcole("nomEcole");
		da.setIdDocument(1);
		da.setDateObtention(new Date());
		da.setIdAgent(9005138);
		sirhPersistenceUnit.persist(da);

		DiplomeAgent da2 = new DiplomeAgent();
		da2.setIdDiplome(2);
		da2.setNomEcole("nomEcole");
		da2.setIdDocument(1);
		da2.setDateObtention(new Date());
		da2.setIdAgent(9005138);
		sirhPersistenceUnit.persist(da2);

		DiplomeAgent da3 = new DiplomeAgent();
		da3.setIdDiplome(3);
		da3.setNomEcole("nomEcole");
		da3.setIdDocument(1);
		da3.setDateObtention(new Date());
		da3.setIdAgent(9005178);
		sirhPersistenceUnit.persist(da3);

		List<DiplomeAgent> result = repository.getListDiplomeByAgent(9005178);

		assertEquals(1, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFormationAgentByAnnee_return1result() {

		FormationAgent fa = new FormationAgent();
		fa.setIdFormation(1);
		fa.setAnneeFormation(2010);
		fa.setIdAgent(9005138);
		fa.setDureeFormation(10);
		sirhPersistenceUnit.persist(fa);

		FormationAgent fa2 = new FormationAgent();
		fa2.setIdFormation(2);
		fa2.setAnneeFormation(2010);
		fa2.setIdAgent(9005158);
		fa2.setDureeFormation(10);
		sirhPersistenceUnit.persist(fa2);

		FormationAgent fa3 = new FormationAgent();
		fa3.setIdFormation(3);
		fa3.setAnneeFormation(2011);
		fa3.setIdAgent(9005138);
		fa3.setDureeFormation(10);
		sirhPersistenceUnit.persist(fa3);

		List<FormationAgent> result = repository.getListFormationAgentByAnnee(9005138, 2010);

		assertEquals(1, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeJoursFeries_2results() {
		
		JourFerie jour1 = new JourFerie();
		jour1.setIdJourFerie(1);
		jour1.setDateJour(new DateTime(2014, 12, 25, 0, 0, 0).toDate());
		jour1.setIdTypeJourFerie(1);
		sirhPersistenceUnit.persist(jour1);
		JourFerie jour2 = new JourFerie();
		jour2.setIdJourFerie(2);
		jour2.setDateJour(new DateTime(2014, 12, 26, 0, 0, 0).toDate());
		jour2.setIdTypeJourFerie(2);
		sirhPersistenceUnit.persist(jour2);
		
		List<JourFerie> result = repository.getListeJoursFeries(new DateTime(2014, 12, 1, 0, 0, 0).toDate(), new DateTime(2014, 12, 31, 0, 0, 0).toDate());
		
		assertEquals(result.size(), 2);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeJoursFeries_badDates() {
		
		JourFerie jour1 = new JourFerie();
		jour1.setIdJourFerie(1);
		jour1.setDateJour(new DateTime(2014, 12, 25, 0, 0, 0).toDate());
		jour1.setIdTypeJourFerie(1);
		sirhPersistenceUnit.persist(jour1);
		JourFerie jour2 = new JourFerie();
		jour2.setIdJourFerie(2);
		jour2.setDateJour(new DateTime(2014, 12, 26, 0, 0, 0).toDate());
		jour2.setIdTypeJourFerie(2);
		sirhPersistenceUnit.persist(jour2);
		
		List<JourFerie> result = repository.getListeJoursFeries(new DateTime(2014, 11, 1, 0, 0, 0).toDate(), new DateTime(2014, 11, 30, 0, 0, 0).toDate());
		
		assertEquals(result.size(), 0);
	}
}
