package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.sirh.Activite;
import flexjson.transformer.AbstractTransformer;

public class ActiviteTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Activite activite = (Activite) arg0;

		getContext().writeOpenObject();

		getContext().writeName("nomActivite");
		getContext().transform(activite.getNomActivite());

		getContext().writeCloseObject();

	}

}
