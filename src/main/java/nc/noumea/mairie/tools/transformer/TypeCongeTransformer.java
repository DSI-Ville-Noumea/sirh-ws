package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.Sptyco;
import nc.noumea.mairie.model.bean.TypeContact;
import flexjson.transformer.AbstractTransformer;

public class TypeCongeTransformer extends AbstractTransformer {

	@Override
	public void transform(Object object) {
		Sptyco typeConge = (Sptyco) object;

		getContext().transform(typeConge.getLibTypeConge());

	}

}
