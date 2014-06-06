package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;

public class CompteDtoTest {

	@Test
	public void CompteDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setCodeBanque(1);
		ag.setCodeGuichet(2);
		ag.setNumCompte("125");
		ag.setRib(1);

		Sibanq banque = new Sibanq();
		banque.setLiBanque("LIBANQ");

		// When
		CompteDto dto = new CompteDto(ag, banque);

		// Then
		assertEquals(ag.getCodeBanque(), dto.getCodeBanque());
		assertEquals(ag.getCodeGuichet(), dto.getCodeGuichet());
		assertEquals(ag.getNumCompte(), dto.getNumCompte());
		assertEquals(ag.getRib(), dto.getRib());
		assertEquals(banque.getLiBanque(), dto.getLibelleBanque());
	}

	@Test
	public void CompteDto_cstNull() {
		// Given
		Agent ag = null;
		Sibanq banque = new Sibanq();
		banque.setLiBanque("LIBANQ");

		// When
		CompteDto dto = new CompteDto(ag, banque);

		// Then
		assertNull(dto.getCodeBanque());
		assertNull(dto.getCodeGuichet());
		assertNull(dto.getNumCompte());
		assertNull(dto.getRib());
		assertEquals(banque.getLiBanque(), dto.getLibelleBanque());
	}
}
