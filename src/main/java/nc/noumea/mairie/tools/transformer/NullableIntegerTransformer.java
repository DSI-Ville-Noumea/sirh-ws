package nc.noumea.mairie.tools.transformer;

import flexjson.transformer.AbstractTransformer;

public class NullableIntegerTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Integer theInteger = (Integer) arg0;
		
		if (theInteger == null)
			getContext().write("0");
		else {
			getContext().write(theInteger.toString());
		}

	}

}
