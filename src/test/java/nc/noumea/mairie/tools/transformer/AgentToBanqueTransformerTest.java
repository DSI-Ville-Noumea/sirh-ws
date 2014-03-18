package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

import flexjson.JSONSerializer;

public class AgentToBanqueTransformerTest {

	@Test
	public void testTransformNullAgentDelegataire() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToBanqueTransformer tr = new AgentToBanqueTransformer();
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
		AgentToBanqueTransformer tr = new AgentToBanqueTransformer();
		Agent ag = new Agent();
		ag.setIntituleCompte("intitu");
		ag.setNumCompte("0101");
		ag.setCodeBanque(12);
		ag.setCodeGuichet(26);

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(
				json,
				"{\"intituleCompte\":\"intitu\",\"rib\":\"\",\"numCompte\":\"00000000101\",\"banque\":\"\",\"codeBanque\":\"00012\",\"codeGuichet\":\"00026\"}");
	}
}
