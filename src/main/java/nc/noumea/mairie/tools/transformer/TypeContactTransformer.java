package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.TypeContact;
import flexjson.transformer.AbstractTransformer;

public class TypeContactTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		TypeContact typeContact = (TypeContact) object;
		String libelleTypeContact = typeContact.getLibelle().substring(0, 1).toUpperCase();
		libelleTypeContact += typeContact.getLibelle().substring(1, typeContact.getLibelle().length()).toLowerCase();
		getContext().transform(libelleTypeContact);

	}

}
