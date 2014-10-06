package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.SituationFamiliale;

import org.joda.time.DateTime;
import org.junit.Test;

public class ProfilAgentDtoTest {

	@Test
	public void testProfilAgentDto_cst() throws ParseException {
		// Given
		SituationFamiliale situationFamiliale = new SituationFamiliale();
		situationFamiliale.setLibSituationFamiliale("test");
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setSexe("f");
		ag.setSituationFamiliale(situationFamiliale);
		ag.setDateNaissance(new DateTime(2014, 02, 01, 0, 0, 0).toDate());
		ag.setTitre("0");
		ag.setLieuNaissance("angers");
		ag.setRue("rue");
		ag.setNumCompte("compte");
		ag.setNumCafat("cafat");
		List<ContactAgentDto> listContact = new ArrayList<>();
		List<EnfantDto> listeEnfant = new ArrayList<>();
		Sibanq banq = new Sibanq();
		banq.setLiBanque("lib banque");

		// When
		ProfilAgentDto dto = new ProfilAgentDto(ag, listContact, listeEnfant, banq);

		// Then
		assertEquals(ag.getIdAgent(), dto.getAgent().getIdAgent());
		assertEquals(ag.getSexe(), dto.getSexe());
		assertEquals(ag.getSituationFamiliale().getLibSituationFamiliale(), dto.getSituationFamiliale());
		assertEquals(ag.getDateNaissance(), dto.getDateNaissance());
		assertEquals(ag.getTitre(), dto.getTitre());
		assertEquals(ag.getLieuNaissance(), dto.getLieuNaissance());
		assertEquals(ag.getRue(), dto.getAdresse().getRue());
		assertEquals(listContact.size(), dto.getListeContacts().size());
		assertEquals(listeEnfant.size(), dto.getListeEnfants().size());
		assertEquals(ag.getNumCompte(), dto.getCompte().getNumCompte());
		assertEquals(ag.getNumCafat(), dto.getCouvertureSociale().getNumCafat());
	}

	@Test
	public void testProfilAgentDto_cstNull() throws ParseException {
		// Given
		Agent ag = null;
		Sibanq banq = null;
		// When
		ProfilAgentDto dto = new ProfilAgentDto(ag, null, null, banq);

		// Then
		assertNull(dto.getAgent());
		assertNull(dto.getSexe());
		assertNull(dto.getSituationFamiliale());
		assertNull(dto.getDateNaissance());
		assertNull(dto.getTitre());
		assertNull(dto.getLieuNaissance());
		assertNull(dto.getAdresse());
		assertNull(dto.getListeContacts());
		assertNull(dto.getListeEnfants());
		assertNull(dto.getCompte());
		assertNull(dto.getCouvertureSociale());
	}
}
