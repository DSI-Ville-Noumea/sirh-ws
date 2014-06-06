package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import nc.noumea.mairie.model.bean.Sibanq;
import nc.noumea.mairie.model.bean.Sicomm;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AgentRecherche;
import nc.noumea.mairie.model.bean.sirh.SituationFamiliale;

import org.junit.Test;

public class AgentDtoTest {

	@Test
	public void testAgentDto_cst() {
		// Given
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setTitre("0");

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals(ag.getTitre(), dto.getCivilite());
	}

	@Test
	public void testAgentDto_cstNull() {
		// Given
		Agent ag = null;

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getIdAgent());
		assertNull(dto.getCivilite());
	}

	@Test
	public void testAgentDto_cst_withAgentRecherche() {
		// Given
		AgentRecherche ag = new AgentRecherche();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
	}

	@Test
	public void testAgentDto_cstNull_withAgentRecherche() {
		// Given
		AgentRecherche ag = null;

		// When
		AgentDto dto = new AgentDto(ag);

		// Then
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getIdAgent());
	}

	@Test
	public void testAgentDto_cst_withAgentEtCompte() {
		// Given
		SituationFamiliale situationFamiliale = new SituationFamiliale();
		situationFamiliale.setIdSituationFamiliale(1);
		situationFamiliale.setLibSituationFamiliale("lib situ");
		Sicomm codeCommuneVilleBP = new Sicomm();
		codeCommuneVilleBP.setLibVil("lib ville");
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setNomUsage("TITI");
		ag.setPrenomUsage("Nono");
		ag.setRib(1);
		ag.setNumCompte("2");
		ag.setCodeBanque(1);
		ag.setCodeGuichet(11);
		ag.setTitre("0");
		ag.setSituationFamiliale(situationFamiliale);
		ag.setAdresseComplementaire("adresse comp");
		ag.setCodePostalVilleBP(12);
		ag.setCodeCommuneVilleBP(codeCommuneVilleBP);
		ag.setNumMutuelle("125");
		ag.setNationalite("F");

		Sibanq banque = new Sibanq();
		banque.setIdBanque(1);
		banque.setLiBanque("test");

		CompteDto cpt = new CompteDto(ag, banque);

		// When
		AgentDto dto = new AgentDto(ag, cpt);

		// Then
		assertEquals(ag.getDisplayNom(), dto.getNom());
		assertEquals(ag.getDisplayPrenom(), dto.getPrenom());
		assertEquals(ag.getIdAgent(), dto.getIdAgent());
		assertEquals("Inconnu", dto.getNumCafat());
		assertEquals("125", dto.getNumMutuelle());
		assertEquals(ag.getTitre(), dto.getCivilite());
		assertEquals(ag.getNationalite(), dto.getNationalite());
		assertEquals(ag.getDateNaissance(), dto.getDateNaissance());
		assertEquals(ag.getSituationFamiliale().getLibSituationFamiliale(), dto.getSituationFamiliale());
		assertEquals(ag.getNbEnfantsACharge(), dto.getNbEnfantCharge());
		assertEquals("En cours d'affiliation", dto.getNumCre());
		assertEquals(ag.getNumRue(), dto.getNumRue());
		assertEquals(ag.getBisTer(), dto.getBisTer());
		assertEquals(ag.getAdresseComplementaire(), dto.getNomRue());
		assertEquals(ag.getCodePostalVilleBP(), dto.getCodePostal());
		assertEquals(ag.getCodeCommuneVilleBP().getLibVil(), dto.getVille());
	}

	@Test
	public void testAgentDto_cstNull_withAgentEtCompte() {
		// Given
		Agent ag = null;

		Sibanq banque = new Sibanq();
		banque.setIdBanque(1);
		banque.setLiBanque("test");

		CompteDto cpt = new CompteDto(ag, banque);

		// When
		AgentDto dto = new AgentDto(ag, cpt);

		// Then
		assertNull(dto.getNom());
		assertNull(dto.getPrenom());
		assertNull(dto.getIdAgent());
	}
}
