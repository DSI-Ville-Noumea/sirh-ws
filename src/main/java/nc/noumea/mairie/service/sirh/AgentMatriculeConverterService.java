package nc.noumea.mairie.service.sirh;

import org.springframework.stereotype.Service;

@Service
public class AgentMatriculeConverterService implements IAgentMatriculeConverterService {

	@Override
	public int tryConvertFromADIdAgentToIdAgent(Integer adIdAgent) {

		if (adIdAgent.toString().length() != 6)
			return adIdAgent;

		return addMissingDigit(adIdAgent);
	}

	private int addMissingDigit(Integer adIdAgent) {

		StringBuilder newIdSb = new StringBuilder();
		newIdSb.append(adIdAgent.toString().substring(0, 2));
		newIdSb.append("0");
		newIdSb.append(adIdAgent.toString().substring(2, 6));

		return Integer.parseInt(newIdSb.toString());
	}

	@Override
	public Integer tryConvertFromADIdAgentToSIRHIdAgent(Integer nomatr) {

		if (nomatr == null)
			return nomatr;

		if (nomatr.toString().length() == 4)
			return convertNoMatrToIdAgent(nomatr);

		if (nomatr.toString().length() == 6)
			return addMissingDigit(nomatr);

		return nomatr;
	}

	private Integer convertNoMatrToIdAgent(Integer noMatr) {
		if (noMatr.toString().length() == 4) {
			return 9000000 + noMatr;
		}
		return noMatr;
	}

}
