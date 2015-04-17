package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spposa;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.pk.SpadmnId;
import nc.noumea.mairie.model.repository.SpadmnRepository;
import nc.noumea.mairie.model.repository.sirh.AffectationRepository;
import nc.noumea.mairie.web.dto.InfosAlimAutoCongesAnnuelsDto;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AbsenceServiceTest {

	@Autowired
	private AbsenceService service;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHoraireAbsenceByAgent_1result() {
		
		Date date = new LocalDate(2014, 2, 1).toDate();
		
		Date dateDebutAff = new LocalDate(2014, 1, 10).toDate();
		Date dateFinAff = new LocalDate(2014, 2, 20).toDate();
		
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		sirhPersistenceUnit.persist(ag);
		
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setIdBaseHoraireAbsence(1);
		aff.setAgent(ag);
		aff.setDateDebutAff(dateDebutAff);
		aff.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(aff);
		
		RefTypeSaisiCongeAnnuelDto result = service.getBaseHoraireAbsenceByAgent(9005138, date);
		
		assertEquals(result.getIdRefTypeSaisiCongeAnnuel(), aff.getIdBaseHoraireAbsence());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHoraireAbsenceByAgent_1result_AffectationWithNoDateFin() {
		
		Date date = new LocalDate(2014, 2, 1).toDate();
		
		Date dateDebutAff = new LocalDate(2014, 1, 10).toDate();
		
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		sirhPersistenceUnit.persist(ag);
		
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setIdBaseHoraireAbsence(1);
		aff.setAgent(ag);
		aff.setDateDebutAff(dateDebutAff);
		aff.setDateFinAff(null);
		sirhPersistenceUnit.persist(aff);
		
		RefTypeSaisiCongeAnnuelDto result = service.getBaseHoraireAbsenceByAgent(9005138, date);
		
		assertEquals(result.getIdRefTypeSaisiCongeAnnuel(), aff.getIdBaseHoraireAbsence());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getBaseHoraireAbsenceByAgent_0result() {
		
		Date date = new LocalDate(2014, 3, 1).toDate();
		
		Date dateDebutAff = new LocalDate(2014, 1, 10).toDate();
		Date dateFinAff = new LocalDate(2014, 2, 20).toDate();
		
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		sirhPersistenceUnit.persist(ag);
		
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setIdBaseHoraireAbsence(1);
		aff.setAgent(ag);
		aff.setDateDebutAff(dateDebutAff);
		aff.setDateFinAff(dateFinAff);
		sirhPersistenceUnit.persist(aff);
		
		RefTypeSaisiCongeAnnuelDto result = service.getBaseHoraireAbsenceByAgent(9005138, date);
		
		assertNull(result.getIdRefTypeSaisiCongeAnnuel());
	}
	
	@Test
	public void calculMoisEntre2Dates_1mois() {
		
		Date dateDebut = new LocalDate(2014, 1, 10).toDate();
		Date dateFin = new LocalDate(2014, 2, 20).toDate();
		
		assertEquals(1, service.calculMoisEntre2Dates(dateDebut, dateFin));
	}
	
	@Test
	public void calculMoisEntre2Dates_12mois() {
		
		Date dateDebut = new LocalDate(2014, 1, 10).toDate();
		Date dateFin = new LocalDate(2015, 1, 10).toDate();
		
		assertEquals(12, service.calculMoisEntre2Dates(dateDebut, dateFin));
	}
	
	@Test
	public void calculMoisEntre2Dates_40mois() {
		
		Date dateDebut = new LocalDate(2012, 1, 1).toDate();
		Date dateFin = new LocalDate(2015, 4, 30).toDate();
		
		assertEquals(40, service.calculMoisEntre2Dates(dateDebut, dateFin));
	}
	
	@Test
	public void calculMoisEntre2Dates_0mois() {
		
		Date dateDebut = new LocalDate(2014, 1, 10).toDate();
		Date dateFin = new LocalDate(2014, 1, 15).toDate();
		
		assertEquals(0, service.calculMoisEntre2Dates(dateDebut, dateFin));
	}
	
	@Test
	public void findBasesCongesForPA_3AffectationPour1PA() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("N");
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		
		Affectation a = new Affectation();
		a.setIdAffectation(1);
		a.setDateDebutAff(sdf.parse("20140101"));
		a.setDateFinAff(sdf.parse("20140210"));
		a.setIdBaseHoraireAbsence(1);
		
		Affectation a2 = new Affectation();
		a2.setIdAffectation(2);
		a2.setDateDebutAff(sdf.parse("20140211"));
		a2.setDateFinAff(sdf.parse("20140220"));
		a2.setIdBaseHoraireAbsence(2);
		
		Affectation a3 = new Affectation();
		a3.setIdAffectation(3);
		a3.setDateDebutAff(sdf.parse("20140221"));
		a3.setDateFinAff(sdf.parse("20140310"));
		a3.setIdBaseHoraireAbsence(3);
		
		Affectation a4 = new Affectation();
		a4.setIdAffectation(3);
		a4.setDateDebutAff(sdf.parse("20140221"));
		a4.setDateFinAff(null);
		a4.setIdBaseHoraireAbsence(3);
		
		listAffectation.add(a);
		listAffectation.add(a2);
		listAffectation.add(a3);
		listAffectation.add(a4);
		
		AffectationRepository affectationRepository = Mockito.mock(AffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentByPeriode(9005138, dateDebutPA, dateFinPA)).thenReturn(listAffectation);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.findBasesCongesForPA(spAdmn, 9005138, dateDebutPA, dateFinPA);
		
		assertEquals(4, result.size());
		assertEquals(result.get(0).getDateDebut(), sdf.parse("20140201"));
		assertEquals(result.get(0).getDateFin(), sdf.parse("20140210"));
		assertEquals(result.get(0).getIdBaseCongeAbsence(), a.getIdBaseHoraireAbsence());

		assertEquals(result.get(1).getDateDebut(), sdf.parse("20140211"));
		assertEquals(result.get(1).getDateFin(), sdf.parse("20140220"));
		assertEquals(result.get(1).getIdBaseCongeAbsence(), a2.getIdBaseHoraireAbsence());

		assertEquals(result.get(2).getDateDebut(), sdf.parse("20140221"));
		assertEquals(result.get(2).getDateFin(), sdf.parse("20140228"));
		assertEquals(result.get(2).getIdBaseCongeAbsence(), a3.getIdBaseHoraireAbsence());

		assertEquals(result.get(3).getDateDebut(), sdf.parse("20140221"));
		assertEquals(result.get(3).getDateFin(), sdf.parse("20140228"));
		assertEquals(result.get(3).getIdBaseCongeAbsence(), a3.getIdBaseHoraireAbsence());
	}
	
	@Test
	public void findBasesCongesForPA_noResult() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("N");
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		
		AffectationRepository affectationRepository = Mockito.mock(AffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentByPeriode(9005138, dateDebutPA, dateFinPA)).thenReturn(listAffectation);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.findBasesCongesForPA(spAdmn, 9005138, dateDebutPA, dateFinPA);
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void checkDroitCongesAnnuels_DroitCongeN() throws ParseException {
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("N");
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		assertFalse(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void checkDroitCongesAnnuels_PasLimite_true() throws ParseException {
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(0);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		assertTrue(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_false() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130210");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130210")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(12);
		posaOld2.setDroitConges("O");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130210);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertFalse(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_false_PAActiveDateFin0() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130210");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(0);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130210")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(12);
		posaOld2.setDroitConges("O");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130210);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertFalse(service.checkDroitCongesAnnuels(spAdmn, sdf.parse("20140228")));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_true() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130301");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130301")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(12);
		posaOld2.setDroitConges("O");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130301);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertTrue(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_true_PAActiveDateFin0() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130301");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(0);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130301")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(12);
		posaOld2.setDroitConges("O");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130301);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertTrue(service.checkDroitCongesAnnuels(spAdmn, sdf.parse("20140228")));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_true_withPAOkBetween2KO() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130210");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130210")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(0);
		posaOld2.setDroitConges("O");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130701);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);
		
		Spposa posaOld3 = new Spposa();
		posaOld3.setDuree(12);
		posaOld3.setDroitConges("O");
		SpadmnId idOld3 = new SpadmnId();
		idOld3.setDatdeb(20130210);
		Spadmn spAdmnOld3 = new Spadmn();
		spAdmnOld3.setPositionAdministrative(posaOld3);
		spAdmnOld3.setId(idOld3);
		spAdmnOld3.setDatfin(20130630);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		listPAAncienne.add(spAdmnOld3);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertTrue(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void checkDroitCongesAnnuels_Limite12_false_withPAKOBetween2KO() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Date dateDebutPA = sdf.parse("20140201");
		Date dateFinPA = sdf.parse("20140228");
		Date dateDebutPAOld = sdf.parse("20130801");
		Date dateDebutPAOld2 = sdf.parse("20130210");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebutPA);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFinPA);
		Mockito.when(sdfMairie.parse("20130801")).thenReturn(dateDebutPAOld);
		Mockito.when(sdfMairie.parse("20130210")).thenReturn(dateDebutPAOld2);
		
		Spposa posaOld = new Spposa();
		posaOld.setDuree(12);
		posaOld.setDroitConges("O");
		SpadmnId idOld = new SpadmnId();
		idOld.setDatdeb(20130801);
		Spadmn spAdmnOld = new Spadmn();
		spAdmnOld.setPositionAdministrative(posaOld);
		spAdmnOld.setId(idOld);
		spAdmnOld.setDatfin(20140131);
		
		Spposa posaOld2 = new Spposa();
		posaOld2.setDuree(0);
		posaOld2.setDroitConges("N");
		SpadmnId idOld2 = new SpadmnId();
		idOld2.setDatdeb(20130701);
		Spadmn spAdmnOld2 = new Spadmn();
		spAdmnOld2.setPositionAdministrative(posaOld2);
		spAdmnOld2.setId(idOld2);
		spAdmnOld2.setDatfin(20130731);
		
		Spposa posaOld3 = new Spposa();
		posaOld3.setDuree(12);
		posaOld3.setDroitConges("O");
		SpadmnId idOld3 = new SpadmnId();
		idOld3.setDatdeb(20130210);
		Spadmn spAdmnOld3 = new Spadmn();
		spAdmnOld3.setPositionAdministrative(posaOld3);
		spAdmnOld3.setId(idOld3);
		spAdmnOld3.setDatfin(20130630);

		List<Spadmn> listPAAncienne = new ArrayList<Spadmn>();
		listPAAncienne.add(spAdmnOld);
		listPAAncienne.add(spAdmnOld2);
		listPAAncienne.add(spAdmnOld3);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentAncienne(5138, spAdmn.getId().getDatdeb())).thenReturn(listPAAncienne);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		
		assertFalse(service.checkDroitCongesAnnuels(spAdmn, new Date()));
	}
	
	@Test
	public void getListPAPourAlimAutoCongesAnnuels_noResult() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateDebut = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		List<Spadmn> listPA = new ArrayList<Spadmn>();
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin)).thenReturn(listPA);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.getListPAPourAlimAutoCongesAnnuels(9005138, dateDebut, dateFin);
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void getListPAPourAlimAutoCongesAnnuels_1result() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateDebut = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		List<Spadmn> listPA = new ArrayList<Spadmn>();
		listPA.add(spAdmn);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin)).thenReturn(listPA);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebut);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFin);
		
		HelperService helper = Mockito.mock(HelperService.class); 
		Mockito.when(helper.getMairieMatrFromIdAgent(9005138)).thenReturn(new Integer(5138));
		
		Affectation a = new Affectation();
		a.setIdAffectation(1);
		a.setDateDebutAff(sdf.parse("20140101"));
		a.setDateFinAff(sdf.parse("20140210"));
		a.setIdBaseHoraireAbsence(1);
		
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		listAffectation.add(a);
		
		AffectationRepository affectationRepository = Mockito.mock(AffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin)).thenReturn(listAffectation);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "helper", helper);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.getListPAPourAlimAutoCongesAnnuels(9005138, dateDebut, dateFin);
		
		assertEquals(1, result.size());
		assertEquals(sdf.parse("20140201"), result.get(0).getDateDebut());
		assertEquals(sdf.parse("20140210"), result.get(0).getDateFin());
		assertEquals(9005138, result.get(0).getIdAgent().intValue());
		assertEquals(a.getIdBaseHoraireAbsence(), result.get(0).getIdBaseCongeAbsence());
	}
	
	@Test
	public void getListPAPourAlimAutoCongesAnnuels_1result_periodeAffectationPlusGrandeQuePA() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateDebut = sdf.parse("20140201");
		Date dateFin = sdf.parse("20140228");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(12);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(20140201);
		id.setNomatr(5138);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20140228);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		List<Spadmn> listPA = new ArrayList<Spadmn>();
		listPA.add(spAdmn);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentSurPeriodeDonnee(5138, dateDebut, dateFin)).thenReturn(listPA);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("20140201")).thenReturn(dateDebut);
		Mockito.when(sdfMairie.parse("20140228")).thenReturn(dateFin);
		
		HelperService helper = Mockito.mock(HelperService.class); 
		Mockito.when(helper.getMairieMatrFromIdAgent(9005138)).thenReturn(new Integer(5138));
		
		Affectation a = new Affectation();
		a.setIdAffectation(1);
		a.setDateDebutAff(sdf.parse("20140101"));
		a.setDateFinAff(sdf.parse("20140310"));
		a.setIdBaseHoraireAbsence(1);
		
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		listAffectation.add(a);
		
		AffectationRepository affectationRepository = Mockito.mock(AffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentByPeriode(9005138, dateDebut, dateFin)).thenReturn(listAffectation);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "helper", helper);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.getListPAPourAlimAutoCongesAnnuels(9005138, dateDebut, dateFin);
		
		assertEquals(1, result.size());
		assertEquals(sdf.parse("20140201"), result.get(0).getDateDebut());
		assertEquals(sdf.parse("20140228"), result.get(0).getDateFin());
		assertEquals(9005138, result.get(0).getIdAgent().intValue());
		assertEquals(a.getIdBaseHoraireAbsence(), result.get(0).getIdBaseCongeAbsence());
	}
	
	// #15074 cas concret rencontre en QUALIF : agent 9002189
	@Test
	public void getListPAPourAlimAutoCongesAnnuels_casConcretRecette() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date dateDebut = sdf.parse("20150301");
		Date dateFin = sdf.parse("20150331");
		
		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setDroitConges("O");
		positionAdministrative.setDuree(0);
		
		SpadmnId id = new SpadmnId();
		id.setDatdeb(19990628);
		id.setNomatr(2189);
		Spadmn spAdmn = new Spadmn();
		spAdmn.setId(id);
		spAdmn.setDatfin(20151101);
		spAdmn.setPositionAdministrative(positionAdministrative);
		
		List<Spadmn> listPA = new ArrayList<Spadmn>();
		listPA.add(spAdmn);
		
		SpadmnRepository spadmnRepository = Mockito.mock(SpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherListPositionAdmAgentSurPeriodeDonnee(2189, dateDebut, dateFin)).thenReturn(listPA);
		
		SimpleDateFormat sdfMairie = Mockito.mock(SimpleDateFormat.class);
		Mockito.when(sdfMairie.parse("19990628")).thenReturn(dateDebut);
		Mockito.when(sdfMairie.parse("20151101")).thenReturn(dateFin);
		
		HelperService helper = Mockito.mock(HelperService.class); 
		Mockito.when(helper.getMairieMatrFromIdAgent(9005138)).thenReturn(new Integer(5138));
		
		Affectation a = new Affectation();
		a.setIdAffectation(1);
		a.setDateDebutAff(sdf.parse("20130101"));
		a.setDateFinAff(null);
		a.setIdBaseHoraireAbsence(1);
		
		List<Affectation> listAffectation = new ArrayList<Affectation>();
		listAffectation.add(a);
		
		AffectationRepository affectationRepository = Mockito.mock(AffectationRepository.class);
		Mockito.when(affectationRepository.getListeAffectationsAgentByPeriode(9002189, sdf.parse("20150301"), sdf.parse("20150331"))).thenReturn(listAffectation);
		
		ReflectionTestUtils.setField(service, "sdfMairie", sdfMairie);
		ReflectionTestUtils.setField(service, "helper", helper);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		
		List<InfosAlimAutoCongesAnnuelsDto> result = service.getListPAPourAlimAutoCongesAnnuels(9002189, dateDebut, dateFin);
		
		assertEquals(1, result.size());
		assertEquals(sdf.parse("20150301"), result.get(0).getDateDebut());
		assertEquals(sdf.parse("20150331"), result.get(0).getDateFin());
		assertEquals(9002189, result.get(0).getIdAgent().intValue());
		assertEquals(a.getIdBaseHoraireAbsence(), result.get(0).getIdBaseCongeAbsence());
	}
}
