package nc.noumea.mairie.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.repository.ISpadmnRepository;

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

}
