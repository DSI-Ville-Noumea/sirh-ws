package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.sirh.Agent;
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
		String rib = "";
		if (agent.getRib() != null) {
			String vide = "00";
			String res = agent.getRib().toString();
			rib = vide.substring(0, vide.length() - res.length()) + res;
		}
		getContext().transform(rib);

		getContext().writeComma();
		getContext().writeName("numCompte");
		String numcompte = "";
		if (agent.getNumCompte() != null) {
			String vide = "00000000000";
			String res = agent.getNumCompte();
			numcompte = vide.substring(0, vide.length() - res.length()) + res;
		}
		getContext().transform(numcompte);

		getContext().writeComma();
		getContext().writeName("banque");
		getContext().transform(agent.getBanque() == null ? "" : agent.getBanque());

		getContext().writeComma();
		getContext().writeName("codeBanque");
		String codeBanque = "";
		if (agent.getCodeBanque() != null) {
			String vide = "00000";
			String res = agent.getCodeBanque().toString();
			codeBanque = vide.substring(0, vide.length() - res.length()) + res;
		}
		getContext().transform(codeBanque);

		getContext().writeComma();
		getContext().writeName("codeGuichet");
		String codeGuichet = "";
		if (agent.getCodeGuichet() != null) {
			String vide = "00000";
			String res = agent.getCodeGuichet().toString();
			codeGuichet = vide.substring(0, vide.length() - res.length()) + res;
		}
		getContext().transform(codeGuichet);

		getContext().writeCloseObject();

	}

}
