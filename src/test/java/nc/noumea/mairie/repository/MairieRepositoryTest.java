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
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.pk.SpcarrId;
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
	public void getListeCarriereActiveAvecPAAffecte_return1result() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(1, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeCarriereActiveAvecPAAffecte_returnNoResult_badCdpadm() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("10");
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeCarriereActiveAvecPAAffecte_returnNoResult_badCdcate() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(5);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListeCarriereActiveAvecPAAffecte_returnNoResult_badDateFin() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(2);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setCdpadm("58");
		spAdmn.setDatfin(new Integer(sdf.format(cal.getTime()).toString()));
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());
	}

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
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListSpmtsr_return1result() {

		Spmtsr spmtsr = new Spmtsr();
		spmtsr.setNomatr(5138);
		spmtsr.setCdecol(1);
		spmtsr.setDatdeb(2010);
		spmtsr.setDatfin(2014);
		spmtsr.setRefarr(1);
		spmtsr.setServi("service");
		sirhPersistenceUnit.persist(spmtsr);

		List<Spmtsr> result = repository.getListSpmtsr(5138);

		assertNotNull(result);
		assertEquals(1, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getListSpmtsr_returnNoResult() {

		Spmtsr spmtsr = new Spmtsr();
		spmtsr.setNomatr(5138);
		spmtsr.setCdecol(1);
		spmtsr.setDatdeb(2010);
		spmtsr.setDatfin(2014);
		spmtsr.setRefarr(1);
		spmtsr.setServi("service");
		sirhPersistenceUnit.persist(spmtsr);

		List<Spmtsr> result = repository.getListSpmtsr(5145);

		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereFonctionnaireAncienne_returnResult() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereFonctionnaireAncienne(5138);

		assertNotNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereFonctionnaireAncienne_noResult_badCategorie() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(5);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereFonctionnaireAncienne(5138);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereFonctionnaireAncienne_noResult_badAgent() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setCategorie(spCatg);
		spCarr.setDateArrete(2000);
		spCarr.setGrade(grade);
		spCarr.setReferenceArrete(1);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereFonctionnaireAncienne(5131);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereActive_returnResult() {
		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setDateArrete(2000);
		spCarr.setReferenceArrete(1);
		spCarr.setCategorie(spCatg);
		spCarr.setGrade(grade);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereActive(5138);

		assertNotNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereActive_noResult_badDateFin() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -1);

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(new Integer(sdf.format(cal.getTime()).toString()));
		spCarr.setDateArrete(2000);
		spCarr.setReferenceArrete(1);
		spCarr.setCategorie(spCatg);
		spCarr.setGrade(grade);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereActive(5138);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereActive_noResult_badDateDebut() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, 1);

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(new Integer(sdf.format(cal.getTime()).toString()));
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setDateArrete(2000);
		spCarr.setReferenceArrete(1);
		spCarr.setCategorie(spCatg);
		spCarr.setGrade(grade);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereActive(5138);

		assertNull(result);
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getCarriereActive_noResult_badAgent() {

		Spcatg spCatg = new Spcatg();
		spCatg.setCodeCategorie(1);
		spCatg.setLibelleCategorie("libelle categorie");
		sirhPersistenceUnit.persist(spCatg);

		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdgeng("CDGENG");
		gradeGenerique.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(gradeGenerique);

		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		grade.setGradeGenerique(gradeGenerique);
		grade.setGradeInitial("GRADE Initial");
		grade.setLiGrad("ligrad");
		sirhPersistenceUnit.persist(grade);

		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(2010);
		Spcarr spCarr = new Spcarr();
		spCarr.setId(id);
		spCarr.setDateFin(0);
		spCarr.setDateArrete(2000);
		spCarr.setReferenceArrete(1);
		spCarr.setCategorie(spCatg);
		spCarr.setGrade(grade);
		sirhPersistenceUnit.persist(spCarr);

		Spcarr result = repository.getCarriereActive(2138);

		assertNull(result);
	}
}
