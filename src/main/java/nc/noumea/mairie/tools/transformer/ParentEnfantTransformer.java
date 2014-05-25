package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.sirh.ParentEnfant;
import flexjson.transformer.AbstractTransformer;

public class ParentEnfantTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		ParentEnfant enfant = (ParentEnfant) arg0;

		getContext().writeOpenObject();

		getContext().writeName("dateNaissance");
		getContext().transform(enfant.getEnfant().getDateNaissance());

		getContext().writeComma();
		getContext().writeName("aCharge");
		getContext().transform(enfant.getEnfantACharge());

		getContext().writeComma();
		getContext().writeName("nom");
		getContext().transform(enfant.getEnfant().getNom());

		getContext().writeComma();
		getContext().writeName("prenom");
		getContext().transform(enfant.getEnfant().getPrenom());

		getContext().writeComma();
		getContext().writeName("sexe");
		getContext().transform(enfant.getEnfant().getSexe());

		getContext().writeComma();
		getContext().writeName("lieuNaissance");
		getContext().transform(enfant.getEnfant().getLieuNaissance()==null ? "" : enfant.getEnfant().getLieuNaissance());

		getContext().writeCloseObject();

	}

}
