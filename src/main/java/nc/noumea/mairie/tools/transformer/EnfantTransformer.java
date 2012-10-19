package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.Enfant;
import flexjson.transformer.AbstractTransformer;

public class EnfantTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Enfant enfant = (Enfant) arg0;

		getContext().writeOpenObject();

		getContext().writeName("dateNaissance");
		getContext().transform(enfant.getDateNaissance());

		getContext().writeComma();
		getContext().writeName("aCharge");
		getContext().transform(enfant.getACharge());

		getContext().writeComma();
		getContext().writeName("nom");
		getContext().transform(enfant.getNom());

		getContext().writeComma();
		getContext().writeName("prenom");
		getContext().transform(enfant.getPrenom());

		getContext().writeComma();
		getContext().writeName("sexe");
		getContext().transform(enfant.getSexe());

		getContext().writeComma();
		getContext().writeName("lieuNaissance");
		getContext().transform(enfant.getLieuNaissance());

		getContext().writeCloseObject();

	}

}
