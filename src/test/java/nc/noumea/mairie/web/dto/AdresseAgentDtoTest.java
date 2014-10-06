package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import nc.noumea.mairie.model.bean.Sicomm;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;

public class AdresseAgentDtoTest {

	@Test
	public void testAdresseAgentDto_cst() {
		// Given
		Sicomm bp = new Sicomm();
		bp.setCodeCommune(new BigDecimal(98800));
		bp.setLibVil("ville 1");
		Sicomm comm = new Sicomm();
		comm.setCodeCommune(new BigDecimal(98800));
		comm.setLibVil("ville 1");
		Agent ag = new Agent();
		ag.setAdresseComplementaire("adresse comp");
		ag.setBisTer("ter");
		ag.setbP("bp");
		ag.setCodePostalVilleBP(98800);
		ag.setCodePostalVilleDom(98800);
		ag.setNumRue("num rue");
		ag.setRue("rue");
		ag.setCodeCommuneVilleBP(bp);
		ag.setCodeCommuneVilleDom(comm);

		// When
		AdresseAgentDto dto = new AdresseAgentDto(ag);

		// Then
		assertEquals(ag.getAdresseComplementaire(), dto.getAdresseComplementaire());
		assertEquals(ag.getBisTer(), dto.getBisTer());
		assertEquals(ag.getbP(), dto.getBp());
		assertEquals(ag.getCodePostalVilleBP(), dto.getCodePostalBp());
		assertEquals(ag.getCodePostalVilleDom(), dto.getCodePostalDomicile());
		assertEquals(ag.getNumRue(), dto.getNumRue());
		assertEquals(ag.getRue(), dto.getRue());
		assertEquals(ag.getCodeCommuneVilleBP().getLibVil(), dto.getVilleBp());
		assertEquals(ag.getCodeCommuneVilleDom().getLibVil(), dto.getVilleDomicile());
	}

	@Test
	public void testAdresseAgentDto_cstNull() {
		// Given
		Agent ag = null;

		// When
		AdresseAgentDto dto = new AdresseAgentDto(ag);

		// Then
		assertNull(dto.getAdresseComplementaire());
		assertNull(dto.getBisTer());
		assertNull(dto.getBp());
		assertNull(dto.getCodePostalBp());
		assertNull(dto.getCodePostalDomicile());
		assertNull(dto.getNumRue());
		assertNull(dto.getRue());
		assertNull(dto.getVilleBp());
		assertNull(dto.getVilleDomicile());
	}
}
