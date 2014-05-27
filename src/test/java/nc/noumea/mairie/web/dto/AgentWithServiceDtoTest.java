package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Agent;

import org.junit.Test;

public class AgentWithServiceDtoTest extends AgentDto {

	@Test
	public void testAgentWithServiceDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setTitre("0");

		// When
		AgentWithServiceDto dto = new AgentWithServiceDto(ag);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getTitre(), dto.getCivilite());
	}

	@Test
	public void testAgentWithServiceDto_cstSiserv() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setTitre("0");

		Siserv service = new Siserv();
		service.setLiServ("test service");
		service.setServi("DD");

		// When
		AgentWithServiceDto dto = new AgentWithServiceDto(ag, service);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getTitre(), dto.getCivilite());
		assertEquals(service.getLiServ(), dto.getService());
		assertEquals(service.getServi(), dto.getCodeService());
	}
}
