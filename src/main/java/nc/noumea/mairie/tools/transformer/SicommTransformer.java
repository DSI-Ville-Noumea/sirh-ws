package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.Sicomm;
import flexjson.transformer.AbstractTransformer;

public class SicommTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		Sicomm sicomm = (Sicomm) object;

		getContext().transform(sicomm.getLibVil().trim());

	}

}
