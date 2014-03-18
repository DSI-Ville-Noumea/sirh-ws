package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

import flexjson.JSONSerializer;

public class AgentToAdresseTransformerTest {

	@Test
	public void testTransformNullAgentDelegataire() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToAdresseTransformer tr = new AgentToAdresseTransformer();
		Agent ag = null;

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidAgentDelegataire() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToAdresseTransformer tr = new AgentToAdresseTransformer();
		Agent ag = new Agent();
		ag.setNomUsage(null);
		ag.setNomMarital("marital");
		ag.setPrenom("prenom1");
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);
		ag.setbP("bp98");
		ag.setNumRue("2");

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(
				json,
				"{\"BP\":\"bp98\",\"adresseComplementaire\":null,\"numRue\":\"2\",\"bisTer\":null,\"rue\":\"\",\"codeCommuneVilleDom\":\"\",\"codeCommuneVilleBP\":\"\",\"codePostalVilleDom\":\"\",\"codePostalVilleBP\":\"\"}");
	}
}
