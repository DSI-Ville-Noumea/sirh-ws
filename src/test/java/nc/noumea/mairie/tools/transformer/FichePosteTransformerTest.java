package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Activite;
import nc.noumea.mairie.model.bean.Budget;
import nc.noumea.mairie.model.bean.CadreEmploi;
import nc.noumea.mairie.model.bean.Competence;
import nc.noumea.mairie.model.bean.FichePoste;
import nc.noumea.mairie.model.bean.NiveauEtude;
import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.StatutFichePoste;
import nc.noumea.mairie.model.bean.TitrePoste;
import nc.noumea.mairie.model.bean.TypeCompetence;

import org.junit.Test;

import flexjson.JSONSerializer;

public class FichePosteTransformerTest {

	@Test
	public void testTransformNullFichePoste() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		FichePosteTransformer tr = new FichePosteTransformer();
		FichePoste fp = null;

		// When
		String json = serializer.transform(tr, FichePoste.class).serialize(fp);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidFichePoste() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		FichePosteTransformer tr = new FichePosteTransformer();
		Activite acti1 = new Activite();
		acti1.setNomActivite("acti1");
		acti1.setIdActivite(1);
		Activite acti2 = new Activite();
		acti2.setNomActivite("acti2");
		acti2.setIdActivite(2);
		TypeCompetence typeC = new TypeCompetence();
		typeC.setLibTypeCompetence("savoir");
		TypeCompetence typeC2 = new TypeCompetence();
		typeC2.setLibTypeCompetence("savoir faire");
		Competence comp1 = new Competence();
		comp1.setNomCompetence("comp1");
		comp1.setTypeCompetence(typeC);
		Competence comp2 = new Competence();
		comp2.setNomCompetence("comp2");
		comp2.setTypeCompetence(typeC2);
		CadreEmploi cadreEmploiGrade = new CadreEmploi();
		cadreEmploiGrade.setLibelleCadreEmploi("cadre A");
		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCadreEmploiGrade(cadreEmploiGrade);
		Spgradn gradePoste = new Spgradn();
		gradePoste.setGradeGenerique(gradeGenerique);
		gradePoste.setGradeInitial("grade ini");
		NiveauEtude niveauEtude = new NiveauEtude();
		niveauEtude.setLibelleNiveauEtude("niveau etude");
		Siserv service = new Siserv();
		service.setDirection("DIRECTION");
		service.setDirectionSigle("DIR");
		service.setDivision("DIV");
		service.setLiServ("LIB SERV");
		service.setSection("SECTION");
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("regl");
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("budgete");
		Budget budget = new Budget();
		budget.setLibelleBudget("budget");
		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("lieu");
		TitrePoste titrePoste = new TitrePoste();
		titrePoste.setLibTitrePoste("titre poste");
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setLibStatut("actif");

		FichePoste fp = new FichePoste();
		fp.getActivites().add(acti1);
		fp.getActivites().add(acti2);
		fp.getCompetencesFDP().add(comp1);
		fp.getCompetencesFDP().add(comp2);
		fp.setGradePoste(gradePoste);
		fp.setNiveauEtude(niveauEtude);
		fp.setService(service);
		fp.setReglementaire(reglementaire);
		fp.setBudgete(budgete);
		fp.setBudget(budget);
		fp.setLieuPoste(lieuPoste);
		fp.setTitrePoste(titrePoste);
		fp.setStatutFP(statutFP);
		fp.setOpi("20");
		fp.setNfa("41");
		fp.setMissions("mission de la FDP");
		fp.setNumFP("2013/85");
		fp.setAnnee(2013);
		fp.setIdFichePoste(12);

		// When
		String json = serializer.transform(tr, FichePoste.class).transform(new ActiviteTransformer(), Activite.class)
				.serialize(fp);

		// Then
		assertEquals(
				json,
				"{\"activites\":[{\"nomActivite\":\"acti2\"},{\"nomActivite\":\"acti1\"}],\"competences\":[{\"typeCompetence\":\"savoir faire\",\"nomCompetence\":[\"comp2\"]},{\"typeCompetence\":\"savoir\",\"nomCompetence\":[\"comp1\"]}],\"cadreEmploi\":\"cadre A\",\"niveauEtude\":\"niveau etude\",\"service\":\"LIB SERV\",\"direction\":\"DIRECTION\",\"section\":\"SECTION\",\"gradePoste\":\"grade ini\",\"reglementaire\":\"regl\",\"budgete\":\"budgete\",\"budget\":\"budget\",\"lieuPoste\":\"lieu\",\"titrePoste\":\"titre poste\",\"statutFP\":\"actif\",\"opi\":\"20\",\"nfa\":\"41\",\"missions\":\"mission de la FDP\",\"numFP\":\"2013/85\",\"annee\":2013,\"idFichePoste\":12}");
	}
}
