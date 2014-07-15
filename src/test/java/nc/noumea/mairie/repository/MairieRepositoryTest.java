package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.pk.SpmtsrId;
import nc.noumea.mairie.model.repository.IMairieRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class MairieRepositoryTest {

	@Autowired
	IMairieRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@Test
	@Transactional("sirhTransactionManager")
	public void chercherPositionAdmAgentAncienne_return1result() {

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
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

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
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

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
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

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
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

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
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

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 2);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(new Integer(sdf.format(cal.getTime()).toString()));
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		Spadmn result = repository.chercherPositionAdmAgentEnCours(5138);

		assertNull(result);

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListSpmtsr_return1result() {

		SpmtsrId spmtsrId = new SpmtsrId();
		spmtsrId.setNomatr(5138);
		spmtsrId.setDatdeb(2010);
		spmtsrId.setServi("service");

		Spmtsr spmtsr = new Spmtsr();
		spmtsr.setId(spmtsrId);
		spmtsr.setCdecol(1);
		spmtsr.setDatfin(2014);
		spmtsr.setRefarr(1);
		sirhPersistenceUnit.persist(spmtsr);

		List<Spmtsr> result = repository.getListSpmtsr(5138);

		assertNotNull(result);
		assertEquals(1, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListSpmtsr_returnNoResult() {

		SpmtsrId spmtsrId = new SpmtsrId();
		spmtsrId.setNomatr(5138);
		spmtsrId.setDatdeb(2010);
		spmtsrId.setServi("service");

		Spmtsr spmtsr = new Spmtsr();
		spmtsr.setId(spmtsrId);
		spmtsr.setCdecol(1);
		spmtsr.setDatfin(2014);
		spmtsr.setRefarr(1);
		sirhPersistenceUnit.persist(spmtsr);

		List<Spmtsr> result = repository.getListSpmtsr(5145);

		assertNotNull(result);
		assertEquals(0, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListSpbhor_2results() {

		Spbhor hor1 = new Spbhor();
		hor1.setCdThor(1);
		hor1.setTaux(0.0);
		sirhPersistenceUnit.persist(hor1);

		Spbhor hor2 = new Spbhor();
		hor2.setCdThor(2);
		hor2.setTaux(1.0);
		sirhPersistenceUnit.persist(hor2);

		Spbhor hor3 = new Spbhor();
		hor3.setCdThor(3);
		hor3.setTaux(0.9);
		sirhPersistenceUnit.persist(hor3);

		Spbhor hor4 = new Spbhor();
		hor4.setCdThor(4);
		hor4.setTaux(0.1);
		sirhPersistenceUnit.persist(hor4);

		List<Spbhor> result = repository.getListSpbhor();

		assertEquals(2, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getSpbhorById_oneResult() {

		Spbhor hor1 = new Spbhor();
		hor1.setCdThor(1);
		hor1.setTaux(0.0);
		sirhPersistenceUnit.persist(hor1);

		Spbhor result = repository.getSpbhorById(1);

		assertNotNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getSpbhorById_noResult() {

		Spbhor hor1 = new Spbhor();
		hor1.setCdThor(1);
		hor1.setTaux(0.0);
		sirhPersistenceUnit.persist(hor1);

		Spbhor result = repository.getSpbhorById(2);

		assertNull(result);
	}

}
