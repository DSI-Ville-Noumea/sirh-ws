package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

import flexjson.JSONSerializer;

public class AgentDelegataireTransformerTest {

	@Test
	public void testTransformNullAgentDelegataire() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentDelegataireTransformer tr = new AgentDelegataireTransformer();
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
		AgentDelegataireTransformer tr = new AgentDelegataireTransformer();
		Agent ag = new Agent();
		ag.setNomUsage(null);
		ag.setNomMarital("marital");
		ag.setPrenom("prenom1");
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json, "{\"nom\":\"marital\",\"prenom\":\"prenom1\",\"idAgent\":9005138,\"nomatr\":5138}");
	}
}
