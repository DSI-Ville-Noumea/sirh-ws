package nc.noumea.mairie.tools.transformer;

import nc.noumea.mairie.model.bean.sirh.Agent;
import flexjson.transformer.AbstractTransformer;

public class AgentDelegataireTransformer extends AbstractTransformer {

	@Override
	public void transform(Object arg0) {
		Agent agent = (Agent) arg0;

		getContext().writeOpenObject();
		
		//on traite le nom
		String nom = "";
		if(agent.getNomUsage()!= null && !agent.getNomUsage().equals("")){
			nom = agent.getNomUsage();
		}else if(agent.getNomMarital()!=null&& !agent.getNomMarital().equals("") ){
			nom=agent.getNomMarital();
		}else{
			nom = agent.getNomPatronymique();
		}
		//on traite le prénom
		String prenom = "";
		if(agent.getPrenomUsage()!= null && !agent.getPrenomUsage().equals("")){
			prenom = agent.getPrenomUsage();
		}else{
			prenom = agent.getPrenom();
		}
		
		getContext().writeName("nom");
		getContext().transform(nom);

		getContext().writeComma();
		getContext().writeName("prenom");
		getContext().transform(prenom);

		getContext().writeComma();
		getContext().writeName("idAgent");
		getContext().transform(agent.getIdAgent());

		getContext().writeComma();
		getContext().writeName("nomatr");
		getContext().transform(agent.getNomatr());

		getContext().writeCloseObject();

	}

}
