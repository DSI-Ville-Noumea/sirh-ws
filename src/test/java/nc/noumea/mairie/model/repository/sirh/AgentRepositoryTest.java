package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.PrimePointageAff;
import nc.noumea.mairie.model.pk.sirh.PrimePointageAffPK;
import nc.noumea.mairie.service.sirh.AgentService;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class AgentRepositoryTest {

	@Autowired
	IAgentRepository repository;

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
	public void getAgent_return1result() {

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

		Agent result = repository.getAgent(9005138);

		assertNotNull(result);
		assertEquals("9005138", result.getIdAgent().toString());
		assertEquals("USAGE", result.getDisplayNom());
		assertEquals("NONO", result.getPrenomUsage());
	}

	@Test
	@Transactional("sirhTransactionManager")
	public void getAgent_returnNull() {

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

		Agent result = repository.getAgent(9005184);

		assertNull(result);
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsByServicesAndListAgentsAndDate_testDate() {
		
		List<Integer> idServiceADS = null;
		List<Integer> idAgents = null;
		
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
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(11);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new DateTime().plusDays(10).toDate());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		// un jour apres le debut de l affectation
		List<Affectation> result = repository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, new DateTime().plusDays(1).toDate(), idAgents);
		assertEquals(1, result.size());
		
		// un jour avant le debut de l affectation
		result = repository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, new DateTime().minusDays(1).toDate(), idAgents);
		assertEquals(0, result.size());
		
		// un jour apres la fin de l affectation
		result = repository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, new DateTime().plusDays(11).toDate(), idAgents);
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsByServicesAndListAgentsAndDate_testAgents() {
		
		List<Integer> idServiceADS = null;
		
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
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(11);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new DateTime().plusDays(10).toDate());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		List<Affectation> result = repository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, new DateTime().plusDays(1).toDate(), Arrays.asList(9005138));
		assertEquals(1, result.size());
		
		result = repository.getListAgentsByServicesAndListAgentsAndDate(idServiceADS, new DateTime().plusDays(1).toDate(), Arrays.asList(9005138+10));
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsByServicesAndListAgentsAndDate_testIdServiceADS() {
		
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
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(11);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new DateTime().plusDays(10).toDate());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		List<Affectation> result = repository.getListAgentsByServicesAndListAgentsAndDate(Arrays.asList(fichePoste.getIdServiceADS()), new DateTime().plusDays(1).toDate(), Arrays.asList(9005138));
		assertEquals(1, result.size());
		
		result = repository.getListAgentsByServicesAndListAgentsAndDate(Arrays.asList(fichePoste.getIdServiceADS()+2), new DateTime().plusDays(1).toDate(), Arrays.asList(9005138+10));
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsWithoutAffectationByServicesAndListAgentsAndDate_testAgents() {
		
		List<Integer> idServiceADS = null;
		
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
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(11);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new DateTime().plusDays(10).toDate());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		List<Affectation> result = repository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(idServiceADS, Arrays.asList(9005138));
		assertEquals(1, result.size());
		
		result = repository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(idServiceADS, Arrays.asList(9005138+10));
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsWithoutAffectationByServicesAndListAgentsAndDate_testIdServiceADS() {
		
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
		fichePoste.setAnnee(2010);
		fichePoste.setMissions("missions");
		fichePoste.setNumFP("numFP");
		fichePoste.setOpi("opi");
		fichePoste.setNfa("nfa");
		fichePoste.setIdServiceADS(11);
		sirhPersistenceUnit.persist(fichePoste);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new Date());
		aff.setDateFinAff(new DateTime().plusDays(10).toDate());
		aff.setFichePoste(fichePoste);
		aff.setTempsTravail("temps travail");
		aff.setAgent(ag);
		sirhPersistenceUnit.persist(aff);
		
		List<Affectation> result = repository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(Arrays.asList(fichePoste.getIdServiceADS()), Arrays.asList(9005138));
		assertEquals(1, result.size());
		
		result = repository.getListAgentsWithoutAffectationByServicesAndListAgentsAndDate(Arrays.asList(fichePoste.getIdServiceADS()+2), Arrays.asList(9005138+10));
		assertEquals(0, result.size());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsEnActiviteByPrimeSurAffectation_noRubrNull() {
		
		assertNull(repository.getListAgentsEnActiviteByPrimeSurAffectation(null, null));
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getListAgentsEnActiviteByPrimeSurAffectation() {
		
		//////////////////////////////////////////////
		// agent avec affectation inactive mais avec la prime
		Agent agent1 = new Agent();
		agent1.setIdAgent(9005138);
		agent1.setNomatr(5138);
		agent1.setPrenom("NON");
		agent1.setDateNaissance(new Date());
		agent1.setNomPatronymique("TEST");
		agent1.setNomUsage("USagent1E");
		agent1.setPrenomUsage("NONO");
		agent1.setSexe("H");
		agent1.setTitre("Mr");
		sirhPersistenceUnit.persist(agent1);

		PrimePointageAffPK primePointageAffPK1 = new PrimePointageAffPK();
		primePointageAffPK1.setNumRubrique(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM);
		primePointageAffPK1.setIdAffectation(2);
		
		PrimePointageAff primePtg1 = new PrimePointageAff();
		primePtg1.setPrimePointageAffPK(primePointageAffPK1);

		Affectation aff = new Affectation();
		aff.setIdAffectation(1);
		aff.setDateDebutAff(new DateTime().minusDays(100).toDate());
		aff.setDateFinAff(new DateTime().minusDays(10).toDate());
		aff.setTempsTravail("temps travail");
		aff.setAgent(agent1);
		aff.getPrimePointageAff().add(primePtg1);
		sirhPersistenceUnit.persist(aff);
		
		primePtg1.setAffectation(aff);
		primePointageAffPK1.setIdAffectation(aff.getIdAffectation());
		sirhPersistenceUnit.persist(primePtg1);
		
		//////////////////////////////////////////////
		// agent avec affectation active mais sans la prime
		Agent agent2 = new Agent();
		agent2.setIdAgent(9005142);
		agent2.setNomatr(5142);
		agent2.setPrenom("NON");
		agent2.setDateNaissance(new Date());
		agent2.setNomPatronymique("TEST");
		agent2.setNomUsage("USagent2E");
		agent2.setPrenomUsage("NONO");
		agent2.setSexe("H");
		agent2.setTitre("Mr");
		sirhPersistenceUnit.persist(agent2);

		PrimePointageAffPK primePointageAffPK2 = new PrimePointageAffPK();
		primePointageAffPK2.setNumRubrique(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM+1);
		primePointageAffPK2.setIdAffectation(2);
		
		PrimePointageAff primePtg2 = new PrimePointageAff();
		primePtg2.setPrimePointageAffPK(primePointageAffPK2);
		
		Affectation aff2 = new Affectation();
		aff2.setIdAffectation(2);
		aff2.setDateDebutAff(new Date());
		aff2.setDateFinAff(null);
		aff2.setTempsTravail("temps travail");
		aff2.setAgent(agent2);
		aff2.getPrimePointageAff().add(primePtg2);
		sirhPersistenceUnit.persist(aff2);
		
		primePtg2.setAffectation(aff2);
		primePointageAffPK2.setIdAffectation(aff2.getIdAffectation());
		sirhPersistenceUnit.persist(primePtg2);

		//////////////////////////////////////////////
		// agent avec affectation active et la prime
		Agent agent3 = new Agent();
		agent3.setIdAgent(9005146);
		agent3.setNomatr(5146);
		agent3.setPrenom("NON");
		agent3.setDateNaissance(new Date());
		agent3.setNomPatronymique("TEST");
		agent3.setNomUsage("USagent3E");
		agent3.setPrenomUsage("NONO");
		agent3.setSexe("H");
		agent3.setTitre("Mr");
		sirhPersistenceUnit.persist(agent3);

		PrimePointageAffPK primePointageAffPK3 = new PrimePointageAffPK();
		primePointageAffPK3.setNumRubrique(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM);
		primePointageAffPK3.setIdAffectation(2);
		
		PrimePointageAff primePtg3 = new PrimePointageAff();
		primePtg3.setPrimePointageAffPK(primePointageAffPK3);

		Affectation aff3 = new Affectation();
		aff3.setIdAffectation(3);
		aff3.setDateDebutAff(new Date());
		aff3.setDateFinAff(null);
		aff3.setTempsTravail("temps travail");
		aff3.setAgent(agent3);
		aff3.getPrimePointageAff().add(primePtg3);
		sirhPersistenceUnit.persist(aff3);
		
		primePtg3.setAffectation(aff3);
		primePointageAffPK3.setIdAffectation(aff3.getIdAffectation());
		sirhPersistenceUnit.persist(primePtg3);
		
		List<AgentRecherche> listAgent = repository.getListAgentsEnActiviteByPrimeSurAffectation(
				Arrays.asList(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM, AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM), 
				null);
		
		assertEquals(1, listAgent.size());
		assertEquals(agent3.getIdAgent(), listAgent.get(0).getIdAgent());
		
		// test sur idAgent
		// KO
		listAgent = repository.getListAgentsEnActiviteByPrimeSurAffectation(Arrays.asList(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM, AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM), 
				Arrays.asList(agent3.getIdAgent()+1));
		
		assertEquals(0, listAgent.size());
		// OK
		listAgent = repository.getListAgentsEnActiviteByPrimeSurAffectation(Arrays.asList(AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_SAMEDI_DPM, AgentService.NO_RUBR_INDEMNITE_FORFAIT_TRAVAIL_DJF_DPM), 
				Arrays.asList(agent3.getIdAgent()));
		
		assertEquals(1, listAgent.size());
		assertEquals(agent3.getIdAgent(), listAgent.get(0).getIdAgent());
	}
}
