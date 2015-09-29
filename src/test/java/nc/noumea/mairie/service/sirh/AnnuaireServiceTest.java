package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spposa;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.repository.ISpadmnRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.web.dto.AgentAnnuaireDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class AnnuaireServiceTest {

	@Test
	public void listAgentActiviteAnnuaire_returnEmptyList() {
		ISpadmnRepository spadmnRepository = Mockito.mock(ISpadmnRepository.class);
		Mockito.when(spadmnRepository.listAgentActiviteAnnuaire()).thenReturn(new ArrayList<Integer>());

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);

		// When
		List<Integer> result = service.listAgentActiviteAnnuaire();

		// Then
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	@Test
	public void listAgentActiviteAnnuaire_returnList() {
		// Given
		Affectation aff = new Affectation();
		aff.setIdAffectation(1);

		ISpadmnRepository spadmnRepository = Mockito.mock(ISpadmnRepository.class);
		Mockito.when(spadmnRepository.listAgentActiviteAnnuaire()).thenReturn(Arrays.asList(5138, 5131));

		IAgentMatriculeConverterService agentMatriculeConverterService = Mockito.mock(IAgentMatriculeConverterService.class);
		Mockito.when(agentMatriculeConverterService.tryConvertFromADIdAgentToSIRHIdAgent(5138)).thenReturn(9005138);
		Mockito.when(agentMatriculeConverterService.tryConvertFromADIdAgentToSIRHIdAgent(5131)).thenReturn(9005131);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getAffectationActiveByAgent(9005138)).thenReturn(aff);
		Mockito.when(affectationRepository.getAffectationActiveByAgent(9005131)).thenReturn(null);

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "agentMatriculeConverterService", agentMatriculeConverterService);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);

		// When
		List<Integer> result = service.listAgentActiviteAnnuaire();

		// Then
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(new Integer(9005138), result.get(0));
	}

	@Test
	public void getInfoAgent_noAgent() {
		// Given
		Integer idAgent = 9005138;

		IAgentService agSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agSrv.getAgent(idAgent)).thenReturn(null);

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "agSrv", agSrv);

		// When
		AgentAnnuaireDto result = service.getInfoAgent(idAgent);

		// Then
		assertNull(result);
	}

	@Test
	public void getInfoAgent_noPositionAdm() {
		// Given
		Integer idAgent = 9005138;
		Agent ag = new Agent();
		ag.setIdAgent(idAgent);
		ag.setNomatr(5138);

		IAgentService agSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agSrv.getAgent(idAgent)).thenReturn(ag);

		ISpadmnRepository spadmnRepository = Mockito.mock(ISpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherPositionAdmAgentEnCours(ag.getNomatr())).thenReturn(null);

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "agSrv", agSrv);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);

		// When
		AgentAnnuaireDto result = service.getInfoAgent(idAgent);

		// Then
		assertNull(result);
	}

	@Test
	public void getInfoAgent_noAffectationActive() {
		// Given
		Integer idAgent = 9005138;

		Agent ag = new Agent();
		ag.setIdAgent(idAgent);
		ag.setNomatr(5138);

		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setPosition("AC");

		Spadmn spAdm = new Spadmn();
		spAdm.setPositionAdministrative(positionAdministrative);

		PositDesc posit = new PositDesc();

		IAgentService agSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agSrv.getAgent(idAgent)).thenReturn(ag);

		ISpadmnRepository spadmnRepository = Mockito.mock(ISpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherPositionAdmAgentEnCours(ag.getNomatr())).thenReturn(spAdm);
		Mockito.when(spadmnRepository.chercherPositDescByPosit(Mockito.anyString())).thenReturn(posit);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getAffectationActiveByAgent(idAgent)).thenReturn(null);

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "agSrv", agSrv);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);

		// When
		AgentAnnuaireDto result = service.getInfoAgent(idAgent);

		// Then
		assertNull(result);
	}

	@Test
	public void getInfoAgent_ok_noSuperieur() {
		// Given
		Integer idAgent = 9005138;

		Agent ag = new Agent();
		ag.setIdAgent(idAgent);
		ag.setNomatr(5138);

		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setCdpAdm("01");
		positionAdministrative.setLibelle("normale");
		positionAdministrative.setPosition("AC");

		Spadmn spAdm = new Spadmn();
		spAdm.setPositionAdministrative(positionAdministrative);

		TitrePoste titrePoste = new TitrePoste();
		titrePoste.setLibTitrePoste("ingenieur");

		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("hotel de ville");

		FichePoste fichePoste = new FichePoste();
		fichePoste.setTitrePoste(titrePoste);
		fichePoste.setIdServiceADS(2);
		fichePoste.setLieuPoste(lieuPoste);

		Affectation affAgent = new Affectation();
		affAgent.setIdAffectation(12);
		affAgent.setFichePoste(fichePoste);
		affAgent.setAgent(ag);

		EntiteDto infoSiserv = new EntiteDto();
		infoSiserv.setCodeServi("DDCA");

		EntiteDto entite = new EntiteDto();
		entite.setCodeServi("DDCAAAAAAAAAAAAA");
		entite.setSigle("DSI");
		entite.setLabel("Systeme info");

		PositDesc posit = new PositDesc();
		posit.setDescription("En activite");

		IAgentService agSrv = Mockito.mock(IAgentService.class);
		Mockito.when(agSrv.getAgent(idAgent)).thenReturn(ag);

		ISpadmnRepository spadmnRepository = Mockito.mock(ISpadmnRepository.class);
		Mockito.when(spadmnRepository.chercherPositionAdmAgentEnCours(ag.getNomatr())).thenReturn(spAdm);
		Mockito.when(spadmnRepository.chercherPositDescByPosit(spAdm.getPositionAdministrative().getPosition())).thenReturn(posit);

		IFichePosteService fichePosteService = Mockito.mock(IFichePosteService.class);
		Mockito.when(fichePosteService.getIdFichePostePrimaireAgentAffectationEnCours(Mockito.anyInt(), Mockito.any(Date.class))).thenReturn(1);
		Mockito.when(fichePosteService.getFichePosteById(1)).thenReturn(fichePoste);

		IAffectationRepository affectationRepository = Mockito.mock(IAffectationRepository.class);
		Mockito.when(affectationRepository.getAffectationActiveByAgent(idAgent)).thenReturn(affAgent);

		IContactService contactSrv = Mockito.mock(IContactService.class);
		Mockito.when(contactSrv.getContactsDiffusableAgent(Long.valueOf(idAgent))).thenReturn(new ArrayList<Contact>());

		IADSWSConsumer adsWSConsumer = Mockito.mock(IADSWSConsumer.class);
		Mockito.when(adsWSConsumer.getInfoSiservByIdEntite(Mockito.anyInt())).thenReturn(infoSiserv);
		Mockito.when(adsWSConsumer.getEntiteByIdEntite(Mockito.anyInt())).thenReturn(entite);
		Mockito.when(adsWSConsumer.getAffichageDirection(Mockito.anyInt())).thenReturn(null);

		AnnuaireService service = new AnnuaireService();
		ReflectionTestUtils.setField(service, "agSrv", agSrv);
		ReflectionTestUtils.setField(service, "spadmnRepository", spadmnRepository);
		ReflectionTestUtils.setField(service, "fichePosteService", fichePosteService);
		ReflectionTestUtils.setField(service, "affectationRepository", affectationRepository);
		ReflectionTestUtils.setField(service, "contactSrv", contactSrv);
		ReflectionTestUtils.setField(service, "adsWSConsumer", adsWSConsumer);

		// When
		AgentAnnuaireDto dto = service.getInfoAgent(idAgent);

		// Then
		assertNotNull(dto);
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getNomatr(), dto.getNomatr());
		assertEquals(ag.getNomUsage(), dto.getNomUsage());
		assertEquals(ag.getPrenom(), dto.getPrenom());
		assertEquals(ag.getPrenomUsage(), dto.getPrenomUsage());
		assertEquals(ag.getNomPatronymique(), dto.getNomPatronymique());
		assertEquals(ag.getDateNaissance(), dto.getDateNaissance());
		assertEquals(ag.getSexe(), dto.getSexe());
		assertEquals(0, dto.getContacts().size());
		assertEquals(spAdm.getPositionAdministrative().getCdpAdm(), dto.getCdpadm());
		assertEquals(spAdm.getPositionAdministrative().getLibelle(), dto.getLipadm());
		assertEquals(posit.getDescription(), dto.getPositDesc());
		assertEquals(titrePoste.getLibTitrePoste(), dto.getPoste().getLibTitrePoste());
		assertNull(dto.getIdAgentSuperieurHierarchique());
		assertEquals(fichePoste.getLieuPoste().getLibelleLieu(), dto.getLieuPoste());
		assertEquals("DDCA", dto.getServi4());
		assertEquals("DDCAAAAAAAAAAAAA", dto.getServi16());
		assertEquals(entite.getSigle(), dto.getSigleEntite());
		assertEquals(entite.getLabel(), dto.getLibelleEntite());
		assertNull(dto.getSigleDirection());
		assertNull(dto.getLibelleDirection());
	}
}
