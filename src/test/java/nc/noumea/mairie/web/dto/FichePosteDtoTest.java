package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import nc.noumea.mairie.model.bean.Silieu;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AvantageNature;
import nc.noumea.mairie.model.bean.sirh.Budget;
import nc.noumea.mairie.model.bean.sirh.CadreEmploi;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.FicheEmploi;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.NatureAvantage;
import nc.noumea.mairie.model.bean.sirh.NatureCredit;
import nc.noumea.mairie.model.bean.sirh.NiveauEtude;
import nc.noumea.mairie.model.bean.sirh.PrimePointageFP;
import nc.noumea.mairie.model.bean.sirh.StatutFichePoste;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.bean.sirh.TypeAvantage;
import nc.noumea.mairie.model.bean.sirh.TypeCompetence;
import nc.noumea.mairie.model.pk.sirh.PrimePointageFPPK;

import org.joda.time.DateTime;
import org.junit.Test;

public class FichePosteDtoTest {

	@Test
	public void FichePosteDto_ctor() {
		// Given

		// When
		FichePosteDto dto = new FichePosteDto();

		// Then
		assertEquals(0, dto.getActivites().size());
		assertEquals(0, dto.getSavoirs().size());
		assertEquals(0, dto.getSavoirsFaire().size());
		assertEquals(0, dto.getComportementsProfessionnels().size());
		assertEquals(0, dto.getAvantages().size());
		assertEquals(0, dto.getDelegations().size());
		assertEquals(0, dto.getRegimesIndemnitaires().size());
		assertEquals(0, dto.getPrimes().size());
	}

	@Test
	public void FichePosteDto_ctor_FichePoste() throws ParseException {
		// Given
		FichePoste fp = getFichePoste();
		List<Competence> listComp = new ArrayList<Competence>();

		TypeCompetence typeCompetenceSavoir = new TypeCompetence();
		typeCompetenceSavoir.setIdTypeCompetence(1);
		TypeCompetence typeCompetenceSavoirFaire = new TypeCompetence();
		typeCompetenceSavoirFaire.setIdTypeCompetence(2);
		TypeCompetence typeCompetenceComportement = new TypeCompetence();
		typeCompetenceComportement.setIdTypeCompetence(3);
		Competence comp1 = new Competence();
		comp1.setTypeCompetence(typeCompetenceSavoir);
		Competence comp2 = new Competence();
		comp2.setTypeCompetence(typeCompetenceSavoirFaire);
		Competence comp3 = new Competence();
		comp3.setTypeCompetence(typeCompetenceSavoirFaire);
		Competence comp4 = new Competence();
		comp4.setTypeCompetence(typeCompetenceComportement);
		Competence comp5 = new Competence();
		comp5.setTypeCompetence(typeCompetenceComportement);
		Competence comp6 = new Competence();
		comp6.setTypeCompetence(typeCompetenceComportement);
		listComp.add(comp1);
		listComp.add(comp2);
		listComp.add(comp3);
		listComp.add(comp4);
		listComp.add(comp5);
		listComp.add(comp6);

		List<Activite> listActi = new ArrayList<Activite>();
		for (ActiviteFP actiFP : fp.getActivites()) {
			Activite acti = new Activite();
			listActi.add(acti);
		}

		// When
		FichePosteDto dto = new FichePosteDto(fp, "", "", "", "sigle", listActi, listComp);

		// Then
		assertEquals(fp.getIdFichePoste(), dto.getIdFichePoste());
		assertEquals(fp.getNumFP(), dto.getNumero());
		// assertEquals(fp.getService().getDirection(), dto.getDirection());
		assertEquals(fp.getTitrePoste().getLibTitrePoste(), dto.getTitre());
		assertEquals(fp.getBudget().getLibelleBudget(), dto.getBudget());
		assertEquals(fp.getBudgete().getLibHor(), dto.getBudgete());
		assertEquals(fp.getReglementaire().getLibHor(), dto.getReglementaire());
		assertEquals(fp.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi(), dto.getCadreEmploi());
		assertEquals(fp.getNiveauEtude().getLibelleNiveauEtude(), dto.getNiveauEtudes());
		// assertEquals(fp.getService().getDivision(), dto.getService());
		// assertEquals(fp.getService().getSection(), dto.getSection());
		assertEquals(fp.getLieuPoste().getLibelleLieu(), dto.getLieu());
		assertEquals(fp.getGradePoste().getGradeInitial(), dto.getGradePoste());
//		assertEquals(fp.getMissions(), dto.getMissions());
		assertEquals(fp.getObservation(), dto.getCommentaire());

		assertEquals(2, dto.getActivites().size());
		assertEquals(1, dto.getSavoirs().size());
		assertEquals(2, dto.getSavoirsFaire().size());
		assertEquals(3, dto.getComportementsProfessionnels().size());
		assertEquals(0, dto.getAvantages().size());
		assertEquals(0, dto.getDelegations().size());
		assertEquals(0, dto.getRegimesIndemnitaires().size());
		assertEquals(0, dto.getPrimes().size());
		assertEquals("sigle", dto.getSigle());
	}

	@Test
	public void FichePosteDto_ctor_FichePoste_InfoCompl() throws ParseException {
		// Given
		Agent agentSup = new Agent();
		agentSup.setNomatr(2990);
		agentSup.setNomUsage("Usage sup");
		agentSup.setPrenomUsage("Prenom sup");
		Affectation affSup = new Affectation();
		affSup.setAgent(agentSup);
		TitrePoste titrePosteSup = new TitrePoste();
		titrePosteSup.setLibTitrePoste("Poste sup");
		FichePoste superieurHierarchique = new FichePoste();
		superieurHierarchique.setTitrePoste(titrePosteSup);
		superieurHierarchique.getAgent().add(affSup);
		superieurHierarchique.setNumFP("2011/8");

		Agent agent = new Agent();
		agent.setPrenomUsage("Prenom Usage");
		agent.setNomUsage("Nom usage");
		agent.setNomatr(5138);
		agent.setIdAgent(5138);
		Affectation aff = new Affectation();
		aff.setDateDebutAff(new DateTime(2013, 02, 25, 0, 0, 0).toDate());
		aff.setAgent(agent);
		StatutFichePoste statutFP = new StatutFichePoste();
		statutFP.setLibStatut("Statut");
		NatureCredit natureCredit = new NatureCredit();
		natureCredit.setLibNatureCredit("Nature Credit");
		FicheEmploi fe = new FicheEmploi();
		fe.setNomEmploi("Nom emploi");
		TypeAvantage typeAvantage = new TypeAvantage();
		typeAvantage.setLibTypeAvantage("LIB TYPE AVANTAGE");
		NatureAvantage natureAvantage = new NatureAvantage();
		natureAvantage.setLibNatureAvantage("Nature avantage");
		AvantageNature avNature = new AvantageNature();
		avNature.setTypeAvantage(typeAvantage);
		avNature.setMontant(new BigDecimal(2013));
		avNature.setNatureAvantage(natureAvantage);
		PrimePointageFPPK pk1 = new PrimePointageFPPK();
		pk1.setNumRubrique(1589);
		PrimePointageFPPK pk2 = new PrimePointageFPPK();
		pk2.setNumRubrique(1579);
		PrimePointageFP pp1 = new PrimePointageFP();
		pp1.setPrimePointageFPPK(pk1);
		PrimePointageFP pp2 = new PrimePointageFP();
		pp2.setPrimePointageFPPK(pk2);
		FichePoste fp = getFichePoste();
		fp.setStatutFP(statutFP);
		fp.setNatureCredit(natureCredit);
		fp.getAgent().add(aff);
		fp.setSuperieurHierarchique(superieurHierarchique);
		fp.getFicheEmploiPrimaire().add(fe);
		fp.getAvantagesNature().add(avNature);
		fp.getPrimePointageFP().add(pp1);
		fp.getPrimePointageFP().add(pp2);
		List<Competence> listComp = new ArrayList<Competence>();

		TypeCompetence typeCompetenceSavoir = new TypeCompetence();
		typeCompetenceSavoir.setIdTypeCompetence(1);
		TypeCompetence typeCompetenceSavoirFaire = new TypeCompetence();
		typeCompetenceSavoirFaire.setIdTypeCompetence(2);
		TypeCompetence typeCompetenceComportement = new TypeCompetence();
		typeCompetenceComportement.setIdTypeCompetence(3);
		Competence comp1 = new Competence();
		comp1.setTypeCompetence(typeCompetenceSavoir);
		Competence comp2 = new Competence();
		comp2.setTypeCompetence(typeCompetenceSavoirFaire);
		Competence comp3 = new Competence();
		comp3.setTypeCompetence(typeCompetenceSavoirFaire);
		Competence comp4 = new Competence();
		comp4.setTypeCompetence(typeCompetenceComportement);
		Competence comp5 = new Competence();
		comp5.setTypeCompetence(typeCompetenceComportement);
		Competence comp6 = new Competence();
		comp6.setTypeCompetence(typeCompetenceComportement);
		listComp.add(comp1);
		listComp.add(comp2);
		listComp.add(comp3);
		listComp.add(comp4);
		listComp.add(comp5);
		listComp.add(comp6);
		List<Activite> listActi = new ArrayList<Activite>();
		for (ActiviteFP actiFP : fp.getActivites()) {
			Activite acti = new Activite();
			listActi.add(acti);
		}
		// When
		FichePosteDto dto = new FichePosteDto(fp, true, "", "", "", null, listActi, listComp);

		// Then
		assertEquals(fp.getIdFichePoste(), dto.getIdFichePoste());
		assertEquals(fp.getNumFP(), dto.getNumero());
		// assertEquals(fp.getService().getDirection(), dto.getDirection());
		assertEquals(fp.getTitrePoste().getLibTitrePoste(), dto.getTitre());
		assertEquals(fp.getBudget().getLibelleBudget(), dto.getBudget());
		assertEquals(fp.getBudgete().getLibHor(), dto.getBudgete());
		assertEquals(fp.getReglementaire().getLibHor(), dto.getReglementaire());
		assertEquals(fp.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi(), dto.getCadreEmploi());
		assertEquals(fp.getNiveauEtude().getLibelleNiveauEtude(), dto.getNiveauEtudes());
		// assertEquals(fp.getService().getDivision(), dto.getService());
		// assertEquals(fp.getService().getSection(), dto.getSection());
		assertEquals(fp.getLieuPoste().getLibelleLieu(), dto.getLieu());
		assertEquals(fp.getGradePoste().getGradeInitial(), dto.getGradePoste());
//		assertEquals(fp.getMissions(), dto.getMissions());
		assertEquals(fp.getObservation(), dto.getCommentaire());
		assertEquals(fp.getStatutFP().getLibStatut(), dto.getStatutFDP());
		assertEquals(fp.getNatureCredit().getLibNatureCredit(), dto.getNatureCredit());
		assertEquals(fp.getGradePoste().getGradeGenerique().getCdcadr(), dto.getCategorie());
		assertEquals("Nom usage Prenom usage (5138)", dto.getAgent());
		assertEquals("25/02/2013", dto.getDateDebutAffectation());
		assertEquals("", dto.getFiliere());
		assertEquals("Usage sup Prenom sup", dto.getSuperieurHierarchiqueAgent());
		assertEquals("Poste sup", dto.getSuperieurHierarchiqueFP());
		assertEquals("", dto.getRemplaceAgent());
		assertEquals("", dto.getRemplaceFP());
		assertEquals("Nom emploi", dto.getEmploiPrimaire());
		assertEquals("", dto.getNFA());
		assertEquals("", dto.getOPI());
		assertEquals("2013", dto.getAnneeEmploi());

		assertEquals(2, dto.getActivites().size());
		assertEquals(1, dto.getSavoirs().size());
		assertEquals(2, dto.getSavoirsFaire().size());
		assertEquals(3, dto.getComportementsProfessionnels().size());
		assertEquals(1, dto.getAvantages().size());
		assertEquals(0, dto.getDelegations().size());
		assertEquals(0, dto.getRegimesIndemnitaires().size());
		assertEquals(2, dto.getPrimes().size());

		assertEquals(5138, dto.getIdAgent().intValue());
	}

	private FichePoste getFichePoste() {
		// Siserv service = new Siserv();
		// service.setServi("TATA");
		// service.setSigle("S");
		// service.setLiServ("TEST DIRECTION SERV");
		// service.setDirection("TEST");
		// service.setDirectionSigle("SN");
		// service.setSection("SECETION SERVICE");

		TitrePoste tp = new TitrePoste();
		tp.setLibTitrePoste("Lib nono");
		Budget budget = new Budget();
		budget.setLibelleBudget("lib budget");
		Spbhor budgete = new Spbhor();
		budgete.setLibHor("lib budgete");
		Spbhor reglementaire = new Spbhor();
		reglementaire.setLibHor("lib reglementaire");
		CadreEmploi cadreEmploiGrade = new CadreEmploi();
		cadreEmploiGrade.setLibelleCadreEmploi("libelle cadre emploi");
		Spgeng gradeGenerique = new Spgeng();
		gradeGenerique.setCdcadr("CDCADR");
		gradeGenerique.setCadreEmploiGrade(cadreEmploiGrade);
		Spgradn gradePoste = new Spgradn();
		gradePoste.setLiGrad("GRADE POSTE");
		gradePoste.setGradeInitial("GRADE INITILA");
		gradePoste.setGradeGenerique(gradeGenerique);
		Silieu lieuPoste = new Silieu();
		lieuPoste.setLibelleLieu("libelle lieu");
		NiveauEtude niveauEtude = new NiveauEtude();
		niveauEtude.setLibelleNiveauEtude("lib niveau etude");
		ActiviteFP act1 = new ActiviteFP();
		ActiviteFP act2 = new ActiviteFP();
		TypeCompetence typeCompetenceSavoir = new TypeCompetence();
		typeCompetenceSavoir.setIdTypeCompetence(1);
		TypeCompetence typeCompetenceSavoirFaire = new TypeCompetence();
		typeCompetenceSavoirFaire.setIdTypeCompetence(2);
		TypeCompetence typeCompetenceComportement = new TypeCompetence();
		typeCompetenceComportement.setIdTypeCompetence(3);
		CompetenceFP comp1 = new CompetenceFP();
		// comp1.setTypeCompetence(typeCompetenceSavoir);
		CompetenceFP comp2 = new CompetenceFP();
		// comp2.setTypeCompetence(typeCompetenceSavoirFaire);
		CompetenceFP comp3 = new CompetenceFP();
		// comp3.setTypeCompetence(typeCompetenceSavoirFaire);
		CompetenceFP comp4 = new CompetenceFP();
		// comp4.setTypeCompetence(typeCompetenceComportement);
		CompetenceFP comp5 = new CompetenceFP();
		// comp5.setTypeCompetence(typeCompetenceComportement);
		CompetenceFP comp6 = new CompetenceFP();
		// comp6.setTypeCompetence(typeCompetenceComportement);

		FichePoste fp = new FichePoste();
		// fp.setService(service);
		fp.setNumFP("2013/5");
		fp.setTitrePoste(tp);
		fp.setBudget(budget);
		fp.setBudgete(budgete);
		fp.setReglementaire(reglementaire);
		fp.setGradePoste(gradePoste);
		fp.setLieuPoste(lieuPoste);
		fp.setNiveauEtude(niveauEtude);
		fp.setMissions("mission du poste");
		fp.setAnnee(2013);
		fp.getActivites().add(act1);
		fp.getActivites().add(act2);
		fp.getCompetencesFDP().add(comp1);
		fp.getCompetencesFDP().add(comp2);
		fp.getCompetencesFDP().add(comp3);
		fp.getCompetencesFDP().add(comp4);
		fp.getCompetencesFDP().add(comp5);
		fp.getCompetencesFDP().add(comp6);

		return fp;
	}
}
