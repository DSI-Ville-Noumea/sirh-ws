package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spposa;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.TypeContact;

import org.junit.Test;

public class AgentAnnuaireDtoTest {

	@Test
	public void testAgentDto_cstNull() {
		// Given
		Agent ag = new Agent();

		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setCdpAdm("01");
		positionAdministrative.setLibelle("ACTIVITE NORMALE");

		Spadmn pa = new Spadmn();
		pa.setPositionAdministrative(positionAdministrative);

		List<Contact> lc = new ArrayList<Contact>();

		TitrePosteDto posteDto = new TitrePosteDto();
		posteDto.setLibTitrePoste("INGENIEUR");

		EntiteDto entite = new EntiteDto();
		entite.setSigle("SED-DMD");
		entite.setLabel("Service etude et dev et maintenance");
		EntiteDto direction = new EntiteDto();
		direction.setSigle("SED");
		direction.setLabel("Service etude et dev");

		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("Hotel de ville");

		FichePoste fichePoste = new FichePoste();
		fichePoste.setLieuPoste(lieuPoste);

		PositDesc positDesc = new PositDesc();
		positDesc.setPosition("AC");
		positDesc.setDescription("Activite");

		// When
		AgentAnnuaireDto dto = new AgentAnnuaireDto(ag, pa, lc, posteDto, 9002990, "DDCA", "DDCAAAAAAAAAAAAA", entite,
				direction, fichePoste, positDesc);

		// Then
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getNomatr(), dto.getNomatr());
		assertEquals(ag.getNomUsage(), dto.getNomUsage());
		assertEquals(ag.getPrenom(), dto.getPrenom());
		assertEquals(ag.getPrenomUsage(), dto.getPrenomUsage());
		assertEquals(ag.getNomPatronymique(), dto.getNomPatronymique());
		assertEquals(ag.getDateNaissance(), dto.getDateNaissance());
		assertEquals(ag.getSexe(), dto.getSexe());
		assertEquals(lc.size(), dto.getContacts().size());
		assertEquals(pa.getPositionAdministrative().getCdpAdm(), dto.getCdpadm());
		assertEquals(pa.getPositionAdministrative().getLibelle(), dto.getLipadm());
		assertEquals("Activite", dto.getPositDesc());
		assertEquals(posteDto.getLibTitrePoste(), dto.getPoste().getLibTitrePoste());
		assertEquals(new Integer(9002990), dto.getIdAgentSuperieurHierarchique());
		assertEquals(fichePoste.getLieuPoste().getLibelleLieu(), dto.getLieuPoste());
		assertEquals("DDCA", dto.getServi4());
		assertEquals("DDCAAAAAAAAAAAAA", dto.getServi16());
		assertEquals(entite.getSigle(), dto.getSigleEntite());
		assertEquals(entite.getLabel(), dto.getLibelleEntite());
		assertEquals(direction.getSigle(), dto.getSigleDirection());
		assertEquals(direction.getLabel(), dto.getLibelleDirection());
	}

	@Test
	public void testAgentDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);

		Spposa positionAdministrative = new Spposa();
		positionAdministrative.setCdpAdm("01");
		positionAdministrative.setLibelle("ACTIVITE NORMALE");

		Spadmn pa = new Spadmn();
		pa.setPositionAdministrative(positionAdministrative);

		TypeContact typeContact = new TypeContact();
		typeContact.setLibelle("perso");
		Contact c1 = new Contact();
		c1.setContactPrioritaire(0);
		c1.setDescription("Telephone");
		c1.setDiffusable("1");
		c1.setIdAgent(ag.getIdAgent());
		c1.setTypeContact(typeContact);
		List<Contact> lc = new ArrayList<Contact>();
		lc.add(c1);

		TitrePosteDto posteDto = new TitrePosteDto();
		posteDto.setLibTitrePoste("INGENIEUR");

		EntiteDto entite = new EntiteDto();
		entite.setSigle("SED-DMD");
		entite.setLabel("Service etude et dev et maintenance");
		EntiteDto direction = new EntiteDto();
		direction.setSigle("SED");
		direction.setLabel("Service etude et dev");

		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("Hotel de ville");

		FichePoste fichePoste = new FichePoste();
		fichePoste.setLieuPoste(lieuPoste);

		PositDesc positDesc = new PositDesc();
		positDesc.setPosition("AC");
		positDesc.setDescription("Activite");

		// When
		AgentAnnuaireDto dto = new AgentAnnuaireDto(ag, pa, lc, posteDto, 9002990, "DDCA", "DDCAAAAAAAAAAAAA", entite,
				direction, fichePoste, positDesc);

		// Then
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getNomatr(), dto.getNomatr());
		assertEquals(ag.getNomUsage(), dto.getNomUsage());
		assertEquals(ag.getPrenom(), dto.getPrenom());
		assertEquals(ag.getPrenomUsage(), dto.getPrenomUsage());
		assertEquals(ag.getNomPatronymique(), dto.getNomPatronymique());
		assertEquals(ag.getDateNaissance(), dto.getDateNaissance());
		assertEquals(ag.getSexe(), dto.getSexe());
		assertEquals(lc.size(), dto.getContacts().size());
		assertEquals(pa.getPositionAdministrative().getCdpAdm(), dto.getCdpadm());
		assertEquals(pa.getPositionAdministrative().getLibelle(), dto.getLipadm());
		assertEquals("Activite", dto.getPositDesc());
		assertEquals(posteDto.getLibTitrePoste(), dto.getPoste().getLibTitrePoste());
		assertEquals(new Integer(9002990), dto.getIdAgentSuperieurHierarchique());
		assertEquals(fichePoste.getLieuPoste().getLibelleLieu(), dto.getLieuPoste());
		assertEquals("DDCA", dto.getServi4());
		assertEquals("DDCAAAAAAAAAAAAA", dto.getServi16());
		assertEquals(entite.getSigle(), dto.getSigleEntite());
		assertEquals(entite.getLabel(), dto.getLibelleEntite());
		assertEquals(direction.getSigle(), dto.getSigleDirection());
		assertEquals(direction.getLabel(), dto.getLibelleDirection());
	}
}
