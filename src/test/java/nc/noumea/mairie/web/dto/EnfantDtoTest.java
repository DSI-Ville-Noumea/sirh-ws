package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.sirh.Enfant;
import nc.noumea.mairie.model.bean.sirh.ParentEnfant;

import org.joda.time.DateTime;
import org.junit.Test;

public class EnfantDtoTest {

	@Test
	public void testEnfantDto_cst() {
		// Given
		Enfant enfant = new Enfant();
		enfant.setDateNaissance(new DateTime(2014, 02, 01, 0, 0, 0).toDate());
		enfant.setNom("nom");
		enfant.setPrenom("prenom");
		enfant.setSexe("m");
		enfant.setLieuNaissance("angers");
		ParentEnfant c = new ParentEnfant();
		c.setEnfant(enfant);
		c.setEnfantACharge(true);

		// When
		EnfantDto dto = new EnfantDto(c);

		// Then
		assertEquals(c.getEnfantACharge(), dto.getaCharge());
		assertEquals(enfant.getDateNaissance(), dto.getDateNaissance());
		assertEquals(enfant.getLieuNaissance(), dto.getLieuNaissance());
		assertEquals(enfant.getNom(), dto.getNom());
		assertEquals(enfant.getPrenom(), dto.getPrenom());
		assertEquals(enfant.getSexe(), dto.getSexe());
	}

	@Test
	public void testEnfantDto_cstNull() {
		// Given
		ParentEnfant c = null;

		// When
		EnfantDto dto = new EnfantDto(c);

		// Then
		assertNull(dto.getaCharge());
		assertNull(dto.getDateNaissance());
		assertNull(dto.getLieuNaissance());
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getSexe());
	}
}
