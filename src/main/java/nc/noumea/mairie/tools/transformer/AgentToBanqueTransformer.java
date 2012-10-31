package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.Agent;
import flexjson.transformer.AbstractTransformer;

public class AgentToBanqueTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Agent agent = (Agent) arg0;

		getContext().writeOpenObject();

		getContext().writeName("intituleCompte");
		getContext().transform(agent.getIntituleCompte() == null ? "" : agent.getIntituleCompte());

		getContext().writeComma();
		getContext().writeName("rib");
		getContext().transform(agent.getRib() == null ? 0 : agent.getRib());

		getContext().writeComma();
		getContext().writeName("numCompte");
		getContext().transform(agent.getNumCompte() == null ? "" : agent.getNumCompte());

		getContext().writeComma();
		getContext().writeName("banque");
		getContext().transform(agent.getBanque() == null ? "" : agent.getBanque());

		getContext().writeComma();
		getContext().writeName("codeBanque");
		getContext().transform(agent.getCodeBanque() == null ? 0 : agent.getCodeBanque());

		getContext().writeComma();
		getContext().writeName("codeGuichet");
		getContext().transform(agent.getCodeGuichet() == null ? 0 : agent.getCodeGuichet());

		getContext().writeCloseObject();

	}

}
