package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.Medecin;

import org.junit.Test;

public class MedecinDtoTest {

	@Test
	public void MedecinDto_cst() {
		// Given

		Medecin m = new Medecin();
		m.setNomMedecin("DOC");
		m.setPrenomMedecin("prenom");
		m.setTitreMedecin("titre");

		// When
		MedecinDto dto = new MedecinDto(m);

		// Then
		assertEquals(m.getNomMedecin(), dto.getNom());
		assertEquals(m.getPrenomMedecin(), dto.getPrenom());
		assertEquals(m.getTitreMedecin(), dto.getTitre());
	}
}
