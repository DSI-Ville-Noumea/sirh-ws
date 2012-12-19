package nc.noumea.mairie.tools.transformer;

import java.util.List;
import java.util.Map.Entry;

import nc.noumea.mairie.model.bean.FichePoste;
import flexjson.transformer.AbstractTransformer;

public class FichePosteTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {

		FichePoste fp = (FichePoste) arg0;

		getContext().writeOpenObject();

		getContext().writeName("activites");
		getContext().transform(fp.getActivites());

		getContext().writeComma();
		getContext().writeName("competences");
		getContext().writeOpenArray();

		boolean isFirst = true;

		for (Entry<String, List<String>> entry : fp.getCompetences().entrySet()) {
			String key = entry.getKey();
			List<String> value = entry.getValue();
			if (!isFirst)
				getContext().writeComma();
			getContext().writeOpenObject();
			getContext().writeName("typeCompetence");
			getContext().writeQuoted(key);
			getContext().writeComma();
			getContext().writeName("nomCompetence");
			getContext().writeOpenArray();
			boolean isFirstNomComp = true;
			for (String res : value) {
				if (!isFirstNomComp)
					getContext().writeComma();
				getContext().writeQuoted(res);
				isFirstNomComp = false;
			}
			getContext().writeCloseArray();
			getContext().writeCloseObject();
			isFirst = false;
		}

		getContext().writeCloseArray();
		getContext().writeComma();

		getContext().writeName("cadreEmploi");
		getContext().transform(
				fp.getGradePoste().getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi() == null ? "" : fp.getGradePoste()
						.getGradeGenerique().getCadreEmploiGrade().getLibelleCadreEmploi());
		getContext().writeComma();
		getContext().writeName("niveauEtude");
		getContext().transform(fp.getNiveauEtude() != null ? fp.getNiveauEtude().getLibelleNiveauEtude() : "");

		getContext().writeComma();
		getContext().writeName("service");
		getContext().transform(fp.getService().getDivision());
		getContext().writeComma();
		getContext().writeName("direction");
		getContext().transform(fp.getService().getDirection());
		getContext().writeComma();
		getContext().writeName("section");
		getContext().transform(fp.getService().getSection());
		getContext().writeComma();
		getContext().writeName("gradePoste");
		getContext().transform(fp.getGradePoste().getGradeInitial());
		getContext().writeComma();
		getContext().writeName("reglementaire");
		getContext().transform(fp.getReglementaire().getLibHor());
		getContext().writeComma();
		getContext().writeName("budgete");
		getContext().transform(fp.getBudgete().getLibHor());
		getContext().writeComma();
		getContext().writeName("budget");
		getContext().transform(fp.getBudget().getLibelleBudget());
		getContext().writeComma();
		getContext().writeName("lieuPoste");
		getContext().transform(fp.getLieuPoste().getLibelleLieu());
		getContext().writeComma();
		getContext().writeName("titrePoste");
		getContext().transform(fp.getTitrePoste().getLibTitrePoste());
		getContext().writeComma();
		getContext().writeName("statutFP");
		getContext().transform(fp.getStatutFP().getLibStatut());
		getContext().writeComma();
		getContext().writeName("opi");
		getContext().transform(fp.getOpi());
		getContext().writeComma();
		getContext().writeName("nfa");
		getContext().transform(fp.getNfa());
		getContext().writeComma();
		getContext().writeName("missions");
		getContext().transform(fp.getMissions());
		getContext().writeComma();
		getContext().writeName("numFP");
		getContext().transform(fp.getNumFP());
		getContext().writeComma();
		getContext().writeName("annee");
		getContext().transform(fp.getAnnee());

		getContext().writeCloseObject();
	}

}
