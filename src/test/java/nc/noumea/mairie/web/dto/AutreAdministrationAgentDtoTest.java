package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.AutreAdministration;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.pk.sirh.AutreAdministrationAgentPK;

import org.joda.time.DateTime;
import org.junit.Test;

public class AutreAdministrationAgentDtoTest extends AgentDto {

	@Test
	public void testAutreAdministrationAgentDto_cst() {
		// Given
		AutreAdministration autreAdministration = new AutreAdministration();
		autreAdministration.setIdAutreAdmin(1);
		autreAdministration.setLibAutreAdmin("Autre admin");
		AutreAdministrationAgentPK autreAdministrationAgentPK = new AutreAdministrationAgentPK();
		autreAdministrationAgentPK.setIdAgent(9005138);
		autreAdministrationAgentPK.setDateEntree(new DateTime(2014, 01, 01, 0, 0, 0).toDate());
		AutreAdministrationAgent autreAdm = new AutreAdministrationAgent();
		autreAdm.setAutreAdministration(autreAdministration);
		autreAdm.setAutreAdministrationAgentPK(autreAdministrationAgentPK);
		autreAdm.setDateSortie(new DateTime(2014, 02, 01, 0, 0, 0).toDate());
		autreAdm.setFonctionnaire(1);

		// When
		AutreAdministrationAgentDto dto = new AutreAdministrationAgentDto(autreAdm);

		// Then
		assertEquals(autreAdm.getAutreAdministrationAgentPK().getDateEntree(), dto.getDateEntree());
		assertEquals(autreAdm.getDateSortie(), dto.getDateSortie());
		assertEquals(autreAdm.getFonctionnaire(), dto.getFonctionnaire());
		assertEquals(autreAdm.getAutreAdministrationAgentPK().getIdAgent(), dto.getIdAgent());
		assertEquals(autreAdm.getAutreAdministrationAgentPK().getIdAutreAdmin(), dto.getIdAutreAdmin());
		assertEquals(autreAdm.getAutreAdministration().getLibAutreAdmin(), dto.getLibelleAdministration());
	}
}
