package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.Medecin;
import nc.noumea.mairie.model.bean.sirh.MotifVM;
import nc.noumea.mairie.model.bean.sirh.SuiviMedical;

import org.junit.Test;

public class ConvocationVMDtoTest {

	@Test
	public void ConvocationVMDto_cst() {
		// Given
		MotifVM motif = new MotifVM();
		motif.setLibMotifVisiteMedicale("lib");

		SuiviMedical sm = new SuiviMedical();
		sm.setIdSuiviMedical(1);
		sm.setNbVisitesRatees(2);
		sm.setDateProchaineVisite(new Date());
		sm.setHeureProchaineVisite("07:00");
		sm.setMotif(motif);

		AgentWithServiceDto ag = new AgentWithServiceDto();
		ag.setIdAgent(9005138);
		ag.setNom("NOM");

		Medecin m = new Medecin();
		m.setNomMedecin("DOC");
		MedecinDto med = new MedecinDto(m);

		// When
		ConvocationVMDto dto = new ConvocationVMDto(sm, ag, med);

		// Then
		assertEquals(sm.getIdSuiviMedical(), dto.getIdSuiviMedical());
		assertEquals(sm.getNbVisitesRatees(), dto.getNbVisitesRatees());
		assertEquals(ag.getNom(), dto.getAgentConvoque().getNom());
		assertEquals(m.getNomMedecin(), dto.getMedecin().getNom());
		assertEquals(sm.getDateProchaineVisite(), dto.getDateVisite());
		assertEquals(sm.getHeureProchaineVisite(), dto.getHeureVisite());
		assertEquals(sm.getMotif().getLibMotifVisiteMedicale(), dto.getMotif());
	}
}
