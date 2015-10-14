package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.BaseHorairePointage;
import nc.noumea.mairie.model.bean.sirh.PrimePointageAff;
import nc.noumea.mairie.model.pk.sirh.PrimePointageAffPK;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class PointageServiceTest {

	@Autowired
	private PointageService service;
	
	@Autowired
	IAffectationRepository repository;
	
	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNotNull_dateDebutEgaleDateFinAffectation() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,8,15,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateDebut);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(baseHorairePointage.getBaseCalculee(), result.get(0).getBaseCalculee());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNotNull_dateFinEgaleDateDebutAffectation() {
		// Given
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateFin);
		a.setDateFinAff(null);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(baseHorairePointage.getBaseCalculee(), result.get(0).getBaseCalculee());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNotNull_AffectationEntreDateDebutEtDateFin() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,9,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,9,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(baseHorairePointage.getBaseCalculee(), result.get(0).getBaseCalculee());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNotNull_AffectationPlusGrandeQueDateDebutEtDateFin() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,10,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(baseHorairePointage.getBaseCalculee(), result.get(0).getBaseCalculee());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNull_badAgent() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,10,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138+1, dateDebut, dateFin);
		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHorairePointageByAgent_returnDtoNull_badDate() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,8,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
		BaseHorairePointage baseHorairePointage = new BaseHorairePointage();
		baseHorairePointage.setIdBaseHorairePointage(1);
		baseHorairePointage.setBaseLegale(39.0);
		sirhPersistenceUnit.persist(baseHorairePointage);
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		a.setBaseHorairePointage(baseHorairePointage);
		sirhPersistenceUnit.persist(a);

		// When
		List<BaseHorairePointageDto> result = service.getBaseHorairePointageByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNotNull_dateDebutEgaleDateFinAffectation() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,8,15,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateDebut);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(7701, result.get(0).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNotNull_dateFinEgaleDateDebutAffectation() {
		// Given
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateFin);
		a.setDateFinAff(null);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(7701, result.get(0).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNotNull_AffectationEntreDateDebutEtDateFin() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,9,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,9,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(7701, result.get(0).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNotNull_AffectationPlusGrandeQueDateDebutEtDateFin() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,10,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(7701, result.get(0).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNull_badDate() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,8,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138, dateDebut, dateFin);
		// Then
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getPrimePointagesByAgent_returnDtoNull_badAgent() {
		// Given

		Date dateDebutAffectation = new DateTime(2015,7,1,0,0,0).toDate();
		Date dateFinAffectation = new DateTime(2015,10,3,0,0,0).toDate();
		
		Date dateDebut = new DateTime(2015,8,31,0,0,0).toDate();
		Date dateFin = new DateTime(2015,9,6,0,0,0).toDate();
		
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

		Affectation a = new Affectation();
		a.setAgent(ag);
		a.setIdAffectation(1);
		a.setTempsTravail("tempsTravail");
		a.setDateDebutAff(dateDebutAffectation);
		a.setDateFinAff(dateFinAffectation);
		sirhPersistenceUnit.persist(a);

		PrimePointageAffPK primePK = new PrimePointageAffPK();
		primePK.setIdAffectation(a.getIdAffectation());
		primePK.setNumRubrique(7701);
		
		PrimePointageAff prime = new PrimePointageAff();
		prime.setAffectation(a);
		prime.setPrimePointageAffPK(primePK);
		sirhPersistenceUnit.persist(prime);

		// When
		List<Integer> result = service.getPrimePointagesByAgent(9005138+1, dateDebut, dateFin);
		// Then
		assertEquals(0, result.size());
	}
}