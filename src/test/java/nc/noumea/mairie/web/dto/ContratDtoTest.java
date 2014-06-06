package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.sirh.Contrat;

import org.junit.Test;

public class ContratDtoTest {

	@Test
	public void ContratDto_cst() {
		// Given
		Integer nbJoursPeriodeEssai = 25;

		List<DiplomeDto> listeDip = new ArrayList<DiplomeDto>();

		Contrat c = new Contrat();
		c.setIdContrat(1);
		c.setDateDebutContrat(new Date());
		c.setDateFinContrat(new Date());
		c.setDateFinPeriodeEssai(new Date());

		FichePosteDto fpDto = new FichePosteDto();
		fpDto.setIdFichePoste(2);

		AgentDto ag = new AgentDto();
		ag.setIdAgent(9005138);
		ag.setNom("NOM");

		Sibanq banque = new Sibanq();
		banque.setLiBanque("LIBANQ");

		// When
		ContratDto dto = new ContratDto(c, ag, fpDto, nbJoursPeriodeEssai, listeDip);

		// Then
		assertEquals(c.getIdContrat(), dto.getIdContrat());
		assertEquals(c.getIdMotif(), dto.getIdMotif());
		assertEquals(ag.getNom(), dto.getAgent().getNom());
		assertEquals(fpDto.getIdFichePoste(), dto.getFichePoste().getIdFichePoste());
		assertEquals(c.getDateDebutContrat(), dto.getDateDebutContrat());
		assertEquals(c.getDateFinContrat(), dto.getDateFinContrat());
		assertEquals(nbJoursPeriodeEssai, dto.getNbJoursPeriodeEssai());
		assertEquals(c.getDateFinPeriodeEssai(), dto.getDateFinPeriodeEssai());
		assertEquals(0, dto.getListeDiplomes().size());
	}
}
