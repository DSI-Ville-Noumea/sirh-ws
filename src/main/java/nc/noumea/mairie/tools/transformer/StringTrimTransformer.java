package nc.noumea.mairie.tools.transformer;

import flexjson.transformer.AbstractTransformer;

public class StringTrimTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		String theString = (String) arg0;
		
		if (theString == null)
			getContext().write(null);
		else {
			getContext().writeQuoted(theString.trim());
		}

	}

}
