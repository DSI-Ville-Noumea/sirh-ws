package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spposa;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.pk.SpcarrId;
import nc.noumea.mairie.model.repository.ISpcarrRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class SpcarrRepositoryTest {

	@Autowired
	ISpcarrRepository repository;

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

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(1, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("10");
		sirhPersistenceUnit.persist(spPosa);
		
		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);
		
		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);;
		spAdmn.setDatfin(0);
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		Spposa spPosa = new Spposa();
		spPosa.setCdpAdm("58");
		sirhPersistenceUnit.persist(spPosa);

		SpadmnId spadmnId = new SpadmnId();
		spadmnId.setNomatr(5138);
		spadmnId.setDatdeb(2010);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(spadmnId);
		spAdmn.setPositionAdministrative(spPosa);
		spAdmn.setDatfin(new Integer(sdf.format(cal.getTime()).toString()));
		sirhPersistenceUnit.persist(spAdmn);

		List<Integer> result = repository.getListeCarriereActiveAvecPAAffecte();

		assertEquals(0, result.size());

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
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

		sirhPersistenceUnit.flush();
		sirhPersistenceUnit.clear();
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void listerCarriereAvecGradeEtStatut_ok() {
		
		Spgradn grade = new Spgradn();
		grade.setCdgrad("cdgrad");
		sirhPersistenceUnit.persist(grade);
		
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
		// CDCATE
		carr.setCategorie(categorie);
		carr.setId(id);
		carr.setGrade(grade);
		sirhPersistenceUnit.persist(carr);
		
		List<Spcarr> listCarr = repository.listerCarriereAvecGradeEtStatut(5138, "cdgrad", 1);
		
		assertEquals(1, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void listerCarriereAvecGradeEtStatut_badAgent() {
		
		Spgradn grade = new Spgradn();
		grade.setCdgrad("cdgrad");
		sirhPersistenceUnit.persist(grade);
		
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
		// CDCATE
		carr.setCategorie(categorie);
		carr.setId(id);
		carr.setGrade(grade);
		sirhPersistenceUnit.persist(carr);
		
		List<Spcarr> listCarr = repository.listerCarriereAvecGradeEtStatut(5130, "cdgrad", 1);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void listerCarriereAvecGradeEtStatut_badCDGRAD() {
		
		Spgradn grade = new Spgradn();
		grade.setCdgrad("cdgrad");
		sirhPersistenceUnit.persist(grade);
		
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
		// CDCATE
		carr.setCategorie(categorie);
		carr.setId(id);
		carr.setGrade(grade);
		sirhPersistenceUnit.persist(carr);
		
		List<Spcarr> listCarr = repository.listerCarriereAvecGradeEtStatut(5138, "cdgraderror", 1);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void listerCarriereAvecGradeEtStatut_badCategorie() {
		
		Spgradn grade = new Spgradn();
		grade.setCdgrad("cdgrad");
		sirhPersistenceUnit.persist(grade);
		
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
		// CDCATE
		carr.setCategorie(categorie);
		carr.setId(id);
		carr.setGrade(grade);
		sirhPersistenceUnit.persist(carr);
		
		List<Spcarr> listCarr = repository.listerCarriereAvecGradeEtStatut(5138, "cdgrad", 9);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels_1result() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(1, listCarr.size());
		assertEquals(5138, listCarr.get(0).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels_2results_datesChevauchent() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		/////////////// 1er resultat /////////////////
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140110);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140210);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140110);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140210);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		/////////////////// 2e resultat /////////////////
		Spcatg categorie2 = new Spcatg();
			categorie2.setCodeCategorie(5);
		sirhPersistenceUnit.persist(categorie2);
		
		SpcarrId id2 = new SpcarrId();
			id2.setNomatr(5138);
			id2.setDatdeb(20140210);
		Spcarr carr2 = new Spcarr();
			carr2.setId(id2);
			carr2.setDateFin(20140220);
			carr2.setCategorie(categorie2);
		sirhPersistenceUnit.persist(carr2);
		
		Spposa positionAdministrative2 = new Spposa();
			positionAdministrative2.setCdpAdm("65");
		sirhPersistenceUnit.persist(positionAdministrative2);
		
		SpadmnId idAdmn2 = new SpadmnId();
			idAdmn2.setDatdeb(20140210);
			idAdmn2.setNomatr(5138);
		Spadmn pa2 = new Spadmn();
			pa2.setDatfin(20140220);
			pa2.setId(idAdmn2);
			pa2.setPositionAdministrative(positionAdministrative2);
		sirhPersistenceUnit.persist(pa2);
		
		/////////////////// 3e resultat /////////////////
		Spcatg categorie3 = new Spcatg();
			categorie3.setCodeCategorie(8);
		sirhPersistenceUnit.persist(categorie3);
		
		SpcarrId id3 = new SpcarrId();
			id3.setNomatr(2990);
			id3.setDatdeb(20140220);
		Spcarr carr3 = new Spcarr();
			carr3.setId(id3);
			carr3.setDateFin(20140320);
			carr3.setCategorie(categorie3);
		sirhPersistenceUnit.persist(carr3);
		
		Spposa positionAdministrative3 = new Spposa();
			positionAdministrative3.setCdpAdm("02");
		sirhPersistenceUnit.persist(positionAdministrative3);
		
		SpadmnId idAdmn3 = new SpadmnId();
			idAdmn3.setDatdeb(20140220);
			idAdmn3.setNomatr(2990);
		Spadmn pa3 = new Spadmn();
			pa3.setDatfin(20140328);
			pa3.setId(idAdmn3);
			pa3.setPositionAdministrative(positionAdministrative3);
		sirhPersistenceUnit.persist(pa3);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(2, listCarr.size());
		assertEquals(2990, listCarr.get(0).intValue());
		assertEquals(5138, listCarr.get(1).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels_2results_datesChevauchent_SpAdmnDateFinZero() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		//////////////// 1er resultat ////////////////////
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140110);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140210);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140110);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140210);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		//////////////// 2e resultat ////////////////////
		Spcatg categorie2 = new Spcatg();
			categorie2.setCodeCategorie(5);
		sirhPersistenceUnit.persist(categorie2);
		
		SpcarrId id2 = new SpcarrId();
			id2.setNomatr(5138);
			id2.setDatdeb(20140210);
		Spcarr carr2 = new Spcarr();
			carr2.setId(id2);
			carr2.setDateFin(0);
			carr2.setCategorie(categorie2);
		sirhPersistenceUnit.persist(carr2);
		
		Spposa positionAdministrative2 = new Spposa();
			positionAdministrative2.setCdpAdm("65");
		sirhPersistenceUnit.persist(positionAdministrative2);
		
		SpadmnId idAdmn2 = new SpadmnId();
			idAdmn2.setDatdeb(20140210);
			idAdmn2.setNomatr(5138);
		Spadmn pa2 = new Spadmn();
			pa2.setDatfin(20140220);
			pa2.setId(idAdmn2);
			pa2.setPositionAdministrative(positionAdministrative2);
		sirhPersistenceUnit.persist(pa2);
		
		//////////////// 3e resultat ////////////////
		Spcatg categorie3 = new Spcatg();
			categorie3.setCodeCategorie(8);
		sirhPersistenceUnit.persist(categorie3);
		
		SpcarrId id3 = new SpcarrId();
			id3.setNomatr(2990);
			id3.setDatdeb(20140220);
		Spcarr carr3 = new Spcarr();
			carr3.setId(id3);
			carr3.setDateFin(20140320);
			carr3.setCategorie(categorie3);
		sirhPersistenceUnit.persist(carr3);
		
		Spposa positionAdministrative3 = new Spposa();
			positionAdministrative3.setCdpAdm("02");
		sirhPersistenceUnit.persist(positionAdministrative3);
		
		SpadmnId idAdmn3 = new SpadmnId();
			idAdmn3.setDatdeb(20140220);
			idAdmn3.setNomatr(2990);
		Spadmn pa3 = new Spadmn();
			pa3.setDatfin(0);
			pa3.setId(idAdmn3);
			pa3.setPositionAdministrative(positionAdministrative3);
		sirhPersistenceUnit.persist(pa3);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(2, listCarr.size());
		assertEquals(2990, listCarr.get(0).intValue());
		assertEquals(5138, listCarr.get(1).intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels_badDates() throws ParseException {
		
		Date dateDeb = sdf.parse("20140301");
		Date dateFin = sdf.parse("20140328");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels_PAAlpha() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("CA");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels__badCategorieMaire() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(9);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels__badCategorieAdjointMaire() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(10);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(0, listCarr.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAgentsPourAlimAutoCongesAnnuels__badCategorieConseillersMunicipaux() throws ParseException {
		
		Date dateDeb = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(11);
		sirhPersistenceUnit.persist(categorie);
		
		SpcarrId id = new SpcarrId();
			id.setNomatr(5138);
			id.setDatdeb(20140201);
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setDateFin(20140228);
			carr.setCategorie(categorie);
		sirhPersistenceUnit.persist(carr);
		
		Spposa positionAdministrative = new Spposa();
			positionAdministrative.setCdpAdm("01");
		sirhPersistenceUnit.persist(positionAdministrative);
		
		SpadmnId idAdmn = new SpadmnId();
			idAdmn.setDatdeb(20140201);
			idAdmn.setNomatr(5138);
		Spadmn pa = new Spadmn();
			pa.setDatfin(20140228);
			pa.setId(idAdmn);
			pa.setPositionAdministrative(positionAdministrative);
		sirhPersistenceUnit.persist(pa);
		
		List<Integer> listCarr = repository.getListeAgentsPourAlimAutoCongesAnnuels(dateDeb, dateFin);
		
		assertEquals(0, listCarr.size());
	}

//	b.append("select cast(carr.nomatr as int) nomatr from Spcarr carr ");
//	sb.append(" inner join SPADMN pa on carr.nomatr = pa.nomatr ");
//	sb.append(" where LENGTH(TRIM(TRANSLATE(pa.cdpadm ,' ', ' +-.0123456789'))) = 0 ");
//	sb.append(" and carr.CDCATE not in (9,10,11) ");
//	sb.append(" and pa.cdpadm not in('CA','DC','DE','FC','LI','RF','RT','RV','SC','FI') ");
//	sb.append(" and ((pa.datdeb between :datdeb and :datfin) or (pa.datfin between :datdeb and :datfin)) ");

	
}
