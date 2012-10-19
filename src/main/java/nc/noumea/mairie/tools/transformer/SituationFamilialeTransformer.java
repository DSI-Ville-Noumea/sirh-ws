package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.SituationFamiliale;
import flexjson.transformer.AbstractTransformer;

public class SituationFamilialeTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		SituationFamiliale situation = (SituationFamiliale) object;

		getContext().transform(situation.getLibSituationFamiliale());

	}

}
