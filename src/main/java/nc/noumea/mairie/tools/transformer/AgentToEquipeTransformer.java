package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.sirh.Agent;
import flexjson.transformer.AbstractTransformer;

public class AgentToEquipeTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Agent agent = (Agent) arg0;

		getContext().writeOpenObject();

		getContext().writeName("nom");
		getContext().transform(agent.getNomUsage());

		getContext().writeComma();
		getContext().writeName("prenom");
		getContext().transform(agent.getPrenomUsage());

		getContext().writeComma();
		getContext().writeName("position");
		getContext().transform(agent.getPosition());

		getContext().writeComma();
		getContext().writeName("titre");
		getContext().transform(agent.getTitre());

		getContext().writeComma();
		getContext().writeName("idAgent");
		getContext().transform(agent.getIdAgent());

		getContext().writeCloseObject();
	}

}
