package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.eae.EaeEvaluateur;
import flexjson.transformer.AbstractTransformer;

public class EaeEvaluateurToAgentTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		EaeEvaluateur evaluateur = (EaeEvaluateur) object;
		
		getContext().transform(evaluateur.getAgent());

	}

}
