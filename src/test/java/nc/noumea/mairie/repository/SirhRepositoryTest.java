package nc.noumea.mairie.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministration;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.pk.sirh.AutreAdministrationAgentPK;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;

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
	public void getAgentWithListNomatr_return1result() {
		
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
		
		Agent result = repository.getAgentWithListNomatr(5138);
		
		assertNotNull(result);
		assertEquals("9005138", result.getIdAgent().toString());
		assertEquals("USAGE", result.getDisplayNom());
		assertEquals("NONO", result.getPrenomUsage());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentWithListNomatr_returnNull() {
		
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
		
		Agent result = repository.getAgentWithListNomatr(5184);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_return1result() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation aff = new Affectation();
			aff.setIdAffectation(1);
			aff.setDateDebutAff(new Date());
			aff.setDateFinAff(null);
			aff.setFichePoste(fichePoste);
			aff.setTempsTravail("temps travail");
			aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		Agent result = repository.getAgentEligibleEAESansAffectes(5138);
		
		assertEquals(result.getNomPatronymique(), "TEST");
		assertEquals(result.getPrenomUsage(), "NONO");
		assertEquals(result.getSexe(), "H"); 
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_returnNull_dateFinKo() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
			
		Affectation aff = new Affectation();
			aff.setIdAffectation(1);
			aff.setDateDebutAff(new Date());
			aff.setDateFinAff(new Date());
			aff.setFichePoste(fichePoste);
			aff.setTempsTravail("temps travail");
			aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		Agent result = repository.getAgentEligibleEAESansAffectes(5138);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAgentEligibleEAESansAffectes_returnNull_badNoMatr() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
			
		Affectation aff = new Affectation();
			aff.setIdAffectation(1);
			aff.setDateDebutAff(new Date());
			aff.setDateFinAff(new Date());
			aff.setFichePoste(fichePoste);
			aff.setTempsTravail("temps travail");
			aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		Agent result = repository.getAgentEligibleEAESansAffectes(5158);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_return1result(){
		
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
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_returnNull_badFonctionnaire(){
		
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
	public void chercherAutreAdministrationAgentAncienne_fonctionnaire_returnNull_badAgent(){
		
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
	public void chercherAutreAdministrationAgentAncienne_returnNull_badAgent(){
		
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
	public void chercherAutreAdministrationAgentAncienne_return1result(){
		
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
	public void getListeAutreAdministrationAgent_return2result(){
		
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
	public void getListeAutreAdministrationAgent_return1result(){
		
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
	public void getListDiplomeByAgent_return2result(){
		
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
	public void getListDiplomeByAgent_return1result(){
		
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
	public void getListFormationAgentByAnnee_return1result(){
		
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
	public void getAvancement_fonctionnaire() {
		
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
		
		AvancementFonctionnaire af = new AvancementFonctionnaire();
			af.setIdAvct(1);
			af.setAnneeAvancement(2010);
			af.setAgent(ag);
			af.setCodeCategporie(1);
			sirhPersistenceUnit.persist(af);
		
		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, true);
		
		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(result.getCodeCategporie(), 1);
		assertEquals(2010, result.getAnneeAvancement());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badYear() {
		
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
		
		AvancementFonctionnaire af = new AvancementFonctionnaire();
			af.setIdAvct(1);
			af.setAnneeAvancement(2010);
			af.setAgent(ag);
			af.setCodeCategporie(1);
			sirhPersistenceUnit.persist(af);
		
		AvancementFonctionnaire result = repository.getAvancement(9005138, 2012, true);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badIdAgent() {
		
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
		
		AvancementFonctionnaire af = new AvancementFonctionnaire();
			af.setIdAvct(1);
			af.setAnneeAvancement(2010);
			af.setAgent(ag);
			af.setCodeCategporie(1);
			sirhPersistenceUnit.persist(af);
		
		AvancementFonctionnaire result = repository.getAvancement(9003138, 2010, true);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_fonctionnaire_returnNull_badCodeCategorie() {
		
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
		
		AvancementFonctionnaire af = new AvancementFonctionnaire();
			af.setIdAvct(1);
			af.setAnneeAvancement(2010);
			af.setAgent(ag);
			af.setCodeCategporie(10);
			sirhPersistenceUnit.persist(af);
		
		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, true);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancement_returnResult_badCodeCategorie() {
		
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
		
		AvancementFonctionnaire af = new AvancementFonctionnaire();
			af.setIdAvct(1);
			af.setAnneeAvancement(2010);
			af.setAgent(ag);
			af.setCodeCategporie(10);
			sirhPersistenceUnit.persist(af);
		
		AvancementFonctionnaire result = repository.getAvancement(9005138, 2010, false);
		
		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(result.getCodeCategporie(), 10);
		assertEquals(2010, result.getAnneeAvancement());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnResult() {
		
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
	
		AvancementDetache ad = new AvancementDetache();
			ad.setIdAvct(1);
			ad.setAnneeAvancement(2010);
			ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);
		
		AvancementDetache result = repository.getAvancementDetache(9005138, 2010);
		
		assertEquals(1, result.getIdAvct().intValue());
		assertEquals(2010, result.getAnneeAvancement());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnNull_badYear() {
		
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
	
		AvancementDetache ad = new AvancementDetache();
			ad.setIdAvct(1);
			ad.setAnneeAvancement(2010);
			ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);
		
		AvancementDetache result = repository.getAvancementDetache(9005138, 2011);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getAvancementDetache_returnNull_badAgent() {
		
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
	
		AvancementDetache ad = new AvancementDetache();
			ad.setIdAvct(1);
			ad.setAnneeAvancement(2010);
			ad.setAgent(ag);
		sirhPersistenceUnit.persist(ad);
		
		AvancementDetache result = repository.getAvancementDetache(9005131, 2010);
		
		assertNull(result);
	}
	
//	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnResult() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);
		
		assertEquals(result.getIdAffectation().intValue(), 1);
	}
	
//	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_DateFin() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(cal.getTime());
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005138);
		
		assertNull(result);
	}
	
//	@Test
	@Transactional("sirhTransactionManager")
	public void getAffectationActiveByAgent_returnNull_badAgent() {
		
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
	
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);

		Affectation result = repository.getAffectationActiveByAgent(9005438);
		
		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_returnResult() {
		
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
	
		Siserv service = new Siserv();
			service.setLiServ("liServ");
			service.setSigle("sigle");
			service.setParentSigle("parentSigle");
			service.setCodeActif("codeActif");
			service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
			fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, "SERVICE1");

		assertEquals(result.size(), 1);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badService() {
		
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
	
		Siserv service = new Siserv();
			service.setLiServ("liServ");
			service.setSigle("sigle");
			service.setParentSigle("parentSigle");
			service.setCodeActif("codeActif");
			service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
			fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005138, "SERVICE2");

		assertEquals(result.size(), 0);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecService_noResult_badAgent() {
		
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
	
		Siserv service = new Siserv();
			service.setLiServ("liServ");
			service.setSigle("sigle");
			service.setParentSigle("parentSigle");
			service.setCodeActif("codeActif");
			service.setServi("SERVICE1");
		sirhPersistenceUnit.persist(service);
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
			fichePoste.setService(service);
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecService(9005130, "SERVICE1");

		assertEquals(result.size(), 0);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_1result() {
		
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
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, 1);
	
		assertEquals(result.size(), 1);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badAgent() {
		
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
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005118, 1);
	
		assertEquals(result.size(), 0);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListeAffectationsAgentAvecFP_noResult_badFP() {
		
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
		
		FichePoste fichePoste = new FichePoste();
			fichePoste.setIdFichePoste(1);
			fichePoste.setAnnee(2010);
			fichePoste.setMissions("missions");
			fichePoste.setNumFP("numFP");
			fichePoste.setOpi("opi");
			fichePoste.setNfa("nfa");
		sirhPersistenceUnit.persist(fichePoste);
		
		Affectation a = new Affectation();
			a.setAgent(ag);
			a.setIdAffectation(1);
			a.setTempsTravail("tempsTravail");
			a.setDateDebutAff(new Date());
			a.setDateFinAff(null);
			a.setFichePoste(fichePoste);
		sirhPersistenceUnit.persist(a);
		
		List<Affectation> result = repository.getListeAffectationsAgentAvecFP(9005138, 2);
	
		assertEquals(result.size(), 0);
	}
}
