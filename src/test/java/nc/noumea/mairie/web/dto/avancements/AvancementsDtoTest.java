package nc.noumea.mairie.web.dto.avancements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import nc.noumea.mairie.model.bean.Spfili;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.sirh.Cap;
import nc.noumea.mairie.model.bean.sirh.CapEmployeur;
import nc.noumea.mairie.model.bean.sirh.CapRepresentant;
import nc.noumea.mairie.model.bean.sirh.Deliberation;
import nc.noumea.mairie.model.bean.sirh.Employeur;
import nc.noumea.mairie.model.bean.sirh.Representant;
import nc.noumea.mairie.model.bean.sirh.TypeRepresentant;
import nc.noumea.mairie.web.dto.avancements.AvancementsDto;
import nc.noumea.mairie.web.dto.avancements.AvancementItemDto;

import org.junit.Test;

public class AvancementsDtoTest {

	@Test
	public void testAvancementDifferencieDto_COMMUNAL() {

		// Given
		Cap cap = new Cap();
		cap.setTypeCap("COMMUNAL");
		cap.setRefCap("c a p");
		Spgeng spgeng = new Spgeng();
		spgeng.setFiliere(new Spfili());
		spgeng.getFiliere().setLibelleFili("filiere");
		spgeng.setDeliberationCommunale(new Deliberation());
		spgeng.getDeliberationCommunale().setTexteCap("Texte CAP");
		spgeng.setTexteCapCadreEmploi("texte cap");
		spgeng.setCdcadr("B");
		int annee = 2014;

		// When
		AvancementsDto dto = new AvancementsDto(cap, spgeng, annee);

		// Then
		assertEquals("Texte CAP", dto.getDeliberationLibelle());
		assertEquals("filiere", dto.getFiliere());
		assertEquals("texte cap", dto.getCadreEmploiLibelle());
		assertEquals("c a p", dto.getCap());
		assertEquals("B", dto.getCategorie());
		assertEquals("Ville de Nouméa", dto.getEmployeur());
		assertEquals(annee, dto.getAnnee());
	}

	@Test
	public void testAvancementDifferencieDto_TERRITORIAL() {

		// Given
		Cap cap = new Cap();
		cap.setTypeCap("TERRITORIAL");
		cap.setRefCap("c a p");
		Spgeng spgeng = new Spgeng();
		spgeng.setFiliere(new Spfili());
		spgeng.getFiliere().setLibelleFili("filiere");
		spgeng.setDeliberationTerritoriale(new Deliberation());
		spgeng.getDeliberationTerritoriale().setTexteCap("Texte CAP");
		spgeng.setTexteCapCadreEmploi("texte cap");
		spgeng.setCdcadr("A");
		int annee = 2014;

		// When
		AvancementsDto dto = new AvancementsDto(cap, spgeng, annee);

		// Then
		assertEquals("Texte CAP", dto.getDeliberationLibelle());
		assertEquals("filiere", dto.getFiliere());
		assertEquals("texte cap", dto.getCadreEmploiLibelle());
		assertEquals("c a p", dto.getCap());
		assertEquals("A", dto.getCategorie());
		assertEquals("Ville de Nouméa", dto.getEmployeur());
		assertEquals(annee, dto.getAnnee());
	}

	@Test
	public void testAvancementDifferencieDto_Employeurs() {

		// Given
		Cap cap = new Cap();
		cap.setTypeCap("TERRITORIAL");
		Spgeng spgeng = new Spgeng();
		int annee = 2014;

		CapEmployeur cE = new CapEmployeur();
		cE.setEmployeur(new Employeur());
		cE.getEmployeur().setLibelle("libelle");
		cE.getEmployeur().setTitre("titre");

		CapEmployeur cE2 = new CapEmployeur();
		cE2.setEmployeur(new Employeur());
		cE2.getEmployeur().setLibelle("libelle2");
		cE2.getEmployeur().setTitre("titre2");

		cap.getEmployeurs().add(cE2);
		cap.getEmployeurs().add(cE);

		// When
		AvancementsDto dto = new AvancementsDto(cap, spgeng, annee);

		// Then
		assertEquals(2, dto.getEmployeurs().size());
		assertTrue(dto.getEmployeurs().contains("libelle\ntitre"));
		assertTrue(dto.getEmployeurs().contains("libelle2\ntitre2"));
	}

	@Test
	public void testAvancementDifferencieDto_Representants() {

		// Given
		Cap cap = new Cap();
		cap.setTypeCap("TERRITORIAL");
		Spgeng spgeng = new Spgeng();
		int annee = 2014;

		TypeRepresentant typeRepresentantSupp = new TypeRepresentant();
		typeRepresentantSupp.setLibTypeRepresentant("SUPP");

		CapRepresentant cR = new CapRepresentant();
		cR.setRepresentant(new Representant());
		cR.getRepresentant().setNom("libelle");
		cR.getRepresentant().setPrenom("titre");
		cR.getRepresentant().setTypeRepresentant(typeRepresentantSupp);

		TypeRepresentant typeRepresentantTitu = new TypeRepresentant();
		typeRepresentantTitu.setLibTypeRepresentant("TITULAIRE");

		CapRepresentant cR2 = new CapRepresentant();
		cR2.setRepresentant(new Representant());
		cR2.getRepresentant().setNom("libelle2");
		cR2.getRepresentant().setPrenom("titre2");
		cR2.getRepresentant().setTypeRepresentant(typeRepresentantTitu);

		cap.getRepresentants().add(cR2);
		cap.getRepresentants().add(cR);

		// When
		AvancementsDto dto = new AvancementsDto(cap, spgeng, annee);

		// Then
		assertEquals(1, dto.getRepresentantsTitulaires().size());
		assertEquals(1, dto.getRepresentantsSuppleants().size());
		assertTrue(dto.getRepresentantsSuppleants().contains("libelle titre"));
		assertTrue(dto.getRepresentantsTitulaires().contains("libelle2 titre2"));
	}

	@Test
	public void testupdateNbAgents_3Agents() {
		// Given
		AvancementsDto dto = new AvancementsDto();
		dto.getAvancementsItems().add(new AvancementItemDto());
		dto.getAvancementsItems().add(new AvancementItemDto());
		dto.getAvancementsItems().add(new AvancementItemDto());

		// When
		dto.updateNbAgents();

		// Then
		assertEquals(3, dto.getNbAgents());
		assertEquals(0, dto.getQuotaAvancementDureeMinimale());
	}

	@Test
	public void testupdateNbAgents_4Agents() {
		// Given
		AvancementsDto dto = new AvancementsDto();
		dto.getAvancementsItems().add(new AvancementItemDto());
		dto.getAvancementsItems().add(new AvancementItemDto());
		dto.getAvancementsItems().add(new AvancementItemDto());
		dto.getAvancementsItems().add(new AvancementItemDto());

		// When
		dto.updateNbAgents();

		// Then
		assertEquals(4, dto.getNbAgents());
		assertEquals(1, dto.getQuotaAvancementDureeMinimale());
	}

}
