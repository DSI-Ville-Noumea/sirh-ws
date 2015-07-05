package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.FichePoste;

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
	public void getListFichePosteByIdServiceADS_returnResult() {

		FichePoste fichePoste2 = new FichePoste();
		fichePoste2.setIdFichePoste(1);
		fichePoste2.setAnnee(2010);
		fichePoste2.setMissions("missions");
		fichePoste2.setNumFP("numFP");
		fichePoste2.setOpi("opi");
		fichePoste2.setNfa("nfa");
		fichePoste2.setIdServiceADS(2);
		sirhPersistenceUnit.persist(fichePoste2);

		FichePoste fichePoste = new FichePoste();
		fichePoste.setIdFichePoste(2);
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(1);
		sirhPersistenceUnit.persist(fichePoste);

		List<FichePoste> result = repository.getListFichePosteByIdServiceADS(1);

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(fichePoste.getIdFichePoste(), result.get(0).getIdFichePoste());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListFichePosteByIdServiceADS_returnEmptyList() {

		List<FichePoste> result = repository.getListFichePosteByIdServiceADS(1);

		assertNotNull(result);
		assertEquals(0, result.size());
	}
}
