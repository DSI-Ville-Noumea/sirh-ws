package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.TypeContact;
import flexjson.transformer.AbstractTransformer;

public class TypeContactTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		TypeContact typeContact = (TypeContact) object;

		getContext().transform(typeContact.getLibelle());

	}

}
