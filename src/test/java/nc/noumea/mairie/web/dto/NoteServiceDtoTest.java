package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.model.bean.sirh.FichePoste;

import org.junit.Test;

public class NoteServiceDtoTest {

	@Test
	public void NoteServiceDto_cstWithParameter() {
		// Given
		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("libelle lieu");
		FichePoste fp = new FichePoste();
		fp.setLieuPoste(lieuPoste);
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setTitre("0");
		Affectation aff = new Affectation();
		aff.setAgent(ag);
		aff.setIdAffectation(5);
		aff.setFichePoste(fp);
		AgentWithServiceDto agDto = new AgentWithServiceDto(ag);

		TitrePosteDto titrePoste = new TitrePosteDto();
		titrePoste.setLibTitrePoste("lib test");
		Integer nbJoursPeriodeEssai = 0;
		Contrat contrat = new Contrat();
		contrat.setAgent(ag);
		contrat.setIdContrat(1);
		String typeNoteService = "nomination";

		// When
		NoteServiceDto dto = new NoteServiceDto(aff, agDto, titrePoste, nbJoursPeriodeEssai, contrat, typeNoteService);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getAgent().getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getAgent().getPrenom());
		assertEquals(ag.getIdAgent(), dto.getAgent().getIdAgent());
		assertEquals(ag.getTitre(), dto.getAgent().getCivilite());
		assertEquals(aff.getIdAffectation(), dto.getIdAffectation());
		assertEquals(aff.getAgent().getIdAgent(), dto.getAgent().getIdAgent());
		assertEquals(contrat.getAgent().getIdAgent(), dto.getAgent().getIdAgent());
		assertEquals(titrePoste.getLibTitrePoste(), dto.getTitrePoste().getLibTitrePoste());
		assertEquals(fp.getLieuPoste().getLibelleLieu(), dto.getLieu());
		assertNull(dto.getDateFinAffectation());
		assertNull(dto.getDateDebutAffectation());
		assertNull(dto.getDateFinPeriodeEssai());
	}

	@Test
	public void NoteServiceDto_cst() {

		// When
		NoteServiceDto dto = new NoteServiceDto();

		// Then
		assertNull(dto.getAgent());
		assertNull(dto.getIdAffectation());
	}
}
