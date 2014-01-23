package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.Agent;
import flexjson.transformer.AbstractTransformer;

public class AgentToAdresseTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Agent agent = (Agent) arg0;

		getContext().writeOpenObject();

		getContext().writeName("BP");
		getContext().transform(agent.getbP());

		getContext().writeComma();
		getContext().writeName("adresseComplementaire");
		getContext().transform(agent.getAdresseComplementaire());

		getContext().writeComma();
		getContext().writeName("numRue");
		getContext().transform(agent.getNumRue());

		getContext().writeComma();
		getContext().writeName("bisTer");
		getContext().transform(agent.getBisTer());

		getContext().writeComma();
		getContext().writeName("rue");
		getContext().transform(agent.getRue() == null ? "" : agent.getRue());

		getContext().writeComma();
		getContext().writeName("codeCommuneVilleDom");
		getContext().transform(
				agent.getCodeCommuneVilleDom() == null ? "" : agent.getCodeCommuneVilleDom().getLibVil().trim());

		getContext().writeComma();
		getContext().writeName("codeCommuneVilleBP");
		getContext().transform(
				agent.getCodeCommuneVilleBP() == null ? "" : agent.getCodeCommuneVilleBP().getLibVil().trim());

		getContext().writeComma();
		getContext().writeName("codePostalVilleDom");
		getContext().transform(
				agent.getCodePostalVilleDom() == null || agent.getCodePostalVilleDom().toString().equals("0")
						|| agent.getCodePostalVilleDom().toString().equals("") ? "" : agent.getCodePostalVilleDom()
						.toString());

		getContext().writeComma();
		getContext().writeName("codePostalVilleBP");
		getContext().transform(
				agent.getCodePostalVilleBP() == null || agent.getCodePostalVilleBP().toString().equals("0")
						|| agent.getCodePostalVilleBP().toString().equals("") ? "" : agent.getCodePostalVilleBP());

		getContext().writeCloseObject();

	}

}
