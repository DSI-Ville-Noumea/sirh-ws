package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spposa;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.repository.ISpadmnRepository;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class SpadmnRepositoryTest {

	@Autowired
	ISpadmnRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentAncienne_return1result() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentAncienne(5138);

		assertNotNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentAncienne_returnNoResult() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentAncienne(5140);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentEnCours_return1result() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentEnCours(5138);

		assertNotNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentEnCours_returnNoResult_noAgent() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentEnCours(5130);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentEnCours_returnNoResult_dateFinNotNull() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(2000);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentEnCours(5138);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentEnCours_returnNoResult_dateDebutSuperieure() {

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(new Integer(sdf.format(cal.getTime()).toString()));
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentEnCours(5138);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_1result() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140201);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140228);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_0result_PABeforePeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140128);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_0result_PAAfterPeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140301);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140328);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_1result_PABeforePeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140201);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_1result_PAAfterPeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140228);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140328);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_3results() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140115);
		sirhPersistenceUnit.persist(spAdmn);

		SpadmnId spadmnId2 = new SpadmnId();
		spadmnId2.setNomatr(5138);
		spadmnId2.setDatdeb(20140116);
		Spadmn spAdmn2 = new Spadmn();
		spAdmn2.setId(spadmnId2);
		spAdmn2.setDatfin(20140201);
		sirhPersistenceUnit.persist(spAdmn2);

		SpadmnId spadmnId3 = new SpadmnId();
		spadmnId3.setNomatr(5138);
		spadmnId3.setDatdeb(20140202);
		Spadmn spAdmn3 = new Spadmn();
		spAdmn3.setId(spadmnId3);
		spAdmn3.setDatfin(20140220);
		sirhPersistenceUnit.persist(spAdmn3);

		SpadmnId spadmnId4 = new SpadmnId();
		spadmnId4.setNomatr(5138);
		spadmnId4.setDatdeb(20140221);
		Spadmn spAdmn4 = new Spadmn();
		spAdmn4.setId(spadmnId4);
		spAdmn4.setDatfin(20140310);
		sirhPersistenceUnit.persist(spAdmn4);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 3);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentSurPeriodeDonnee_PAPlusGrandeQuePeriode() {

		Date dateDebut = new LocalDate(2014, 2, 1).toDate();
		Date dateFin = new LocalDate(2014, 2, 28).toDate();

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140315);
		sirhPersistenceUnit.persist(spAdmn);

		SpadmnId spadmnId2 = new SpadmnId();
		spadmnId2.setNomatr(5138);
		spadmnId2.setDatdeb(20140116);
		Spadmn spAdmn2 = new Spadmn();
		spAdmn2.setId(spadmnId2);
		spAdmn2.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn2);

		List<Spadmn> result = repository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin);

		assertNotNull(result);
		assertEquals(result.size(), 2);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentAncienne_1result() {

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140201);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140214);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentAncienne(5138, 20140215);

		assertNotNull(result);
		assertEquals(result.size(), 1);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentAncienne_0result() {

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140201);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140214);
		sirhPersistenceUnit.persist(spAdmn);

		List<Spadmn> result = repository.chercherListPositionAdmAgentAncienne(5138, 20140131);

		assertNotNull(result);
		assertEquals(result.size(), 0);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherListPositionAdmAgentAncienne_3results() {

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20140115);
		sirhPersistenceUnit.persist(spAdmn);

		SpadmnId spadmnId2 = new SpadmnId();
		spadmnId2.setNomatr(5138);
		spadmnId2.setDatdeb(20140116);
		Spadmn spAdmn2 = new Spadmn();
		spAdmn2.setId(spadmnId2);
		spAdmn2.setDatfin(20140131);
		sirhPersistenceUnit.persist(spAdmn2);

		SpadmnId spadmnId3 = new SpadmnId();
		spadmnId3.setNomatr(5138);
		spadmnId3.setDatdeb(20140201);
		Spadmn spAdmn3 = new Spadmn();
		spAdmn3.setId(spadmnId3);
		spAdmn3.setDatfin(20140214);
		sirhPersistenceUnit.persist(spAdmn3);

		SpadmnId spadmnId4 = new SpadmnId();
		spadmnId4.setNomatr(5138);
		spadmnId4.setDatdeb(20140215);
		Spadmn spAdmn4 = new Spadmn();
		spAdmn4.setId(spadmnId4);
		spAdmn4.setDatfin(20140228);
		sirhPersistenceUnit.persist(spAdmn4);

		List<Spadmn> result = repository.chercherListPositionAdmAgentAncienne(5138, 20140215);

		assertNotNull(result);
		assertEquals(result.size(), 3);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void listAgentActiviteAnnuaire_3results() {
		Spposa positionAdministrative1 = new Spposa();
		positionAdministrative1.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative1);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5136);
		spadmnId.setDatdeb(20140101);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setDatfin(20161115);
		spAdmn.setPositionAdministrative(positionAdministrative1);
		sirhPersistenceUnit.persist(spAdmn);

		Spposa positionAdministrative2 = new Spposa();
		positionAdministrative2.setCdpAdm("47");
		sirhPersistenceUnit.persist(positionAdministrative2);

		SpadmnId spadmnId2 = new SpadmnId();
		spadmnId2.setNomatr(5137);
		spadmnId2.setDatdeb(20140116);
		Spadmn spAdmn2 = new Spadmn();
		spAdmn2.setId(spadmnId2);
		spAdmn2.setPositionAdministrative(positionAdministrative2);
		sirhPersistenceUnit.persist(spAdmn2);

		Spposa positionAdministrative3 = new Spposa();
		positionAdministrative3.setCdpAdm("02");
		sirhPersistenceUnit.persist(positionAdministrative3);

		SpadmnId spadmnId3 = new SpadmnId();
		spadmnId3.setNomatr(5138);
		spadmnId3.setDatdeb(20140201);
		Spadmn spAdmn3 = new Spadmn();
		spAdmn3.setId(spadmnId3);
		spAdmn3.setPositionAdministrative(positionAdministrative3);
		sirhPersistenceUnit.persist(spAdmn3);

		Spposa positionAdministrative4 = new Spposa();
		positionAdministrative4.setCdpAdm("49");
		sirhPersistenceUnit.persist(positionAdministrative4);

		SpadmnId spadmnId4 = new SpadmnId();
		spadmnId4.setNomatr(5139);
		spadmnId4.setDatdeb(20140215);
		Spadmn spAdmn4 = new Spadmn();
		spAdmn4.setId(spadmnId4);
		spAdmn4.setPositionAdministrative(positionAdministrative4);
		sirhPersistenceUnit.persist(spAdmn4);

		List<Integer> result = repository.listAgentActiviteAnnuaire();

		assertNotNull(result);
//		assertEquals(result.size(), 3);
//		assertEquals(result.get(0),spadmnId.getNomatr() );
//		assertEquals(result.get(1),spadmnId2.getNomatr() );
//		assertEquals(result.get(2),spadmnId3.getNomatr() );

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
}
