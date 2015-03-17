package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spprim;
import nc.noumea.mairie.model.pk.SpprimId;
import nc.noumea.mairie.model.repository.ISpprimRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class SpprimRepositoryTest {

	@Autowired
	ISpprimRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;

	@Test
	@Transactional("sirhTransactionManager")
	public void getListChefsService_Results() {

		SpprimId id = new SpprimId();
		id.setNomatr(2990);
		id.setNorubr(7079);
		id.setDatdeb(20140101);
		Spprim prime = new Spprim();
		prime.setId(id);
		prime.setDatfin(0);
		prime.setMontantPrime(48.0);
		sirhPersistenceUnit.persist(prime);

		List<Integer> result = repository.getListChefsService();

		assertEquals(result.size(), 1);
		assertEquals(result.get(0), new Integer(9002990));
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListChefsService_NOResults() {

		SpprimId id = new SpprimId();
		id.setNomatr(2990);
		id.setNorubr(7079);
		id.setDatdeb(20140101);
		Spprim prime = new Spprim();
		prime.setId(id);
		prime.setDatfin(20140525);
		prime.setMontantPrime(48.0);
		sirhPersistenceUnit.persist(prime);

		List<Integer> result = repository.getListChefsService();

		assertEquals(result.size(), 0);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListDirecteur_Results() {

		SpprimId id = new SpprimId();
		id.setNomatr(2990);
		id.setNorubr(7079);
		id.setDatdeb(20140101);
		Spprim prime = new Spprim();
		prime.setId(id);
		prime.setDatfin(0);
		prime.setMontantPrime(88.0);
		sirhPersistenceUnit.persist(prime);

		List<Integer> result = repository.getListDirecteur();

		assertEquals(result.size(), 1);
		assertEquals(result.get(0), new Integer(9002990));
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListDirecteur_NOResults() {

		SpprimId id = new SpprimId();
		id.setNomatr(2990);
		id.setNorubr(7079);
		id.setDatdeb(20140101);
		Spprim prime = new Spprim();
		prime.setId(id);
		prime.setDatfin(20140606);
		prime.setMontantPrime(88.0);
		sirhPersistenceUnit.persist(prime);

		List<Integer> result = repository.getListDirecteur();

		assertEquals(result.size(), 0);
	}
}
