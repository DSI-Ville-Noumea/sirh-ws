package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

import flexjson.JSONSerializer;

public class AgentToHierarchiqueTransformerTest {

	@Test
	public void testTransformNullAgentToHierarchique() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToHierarchiqueTransformer tr = new AgentToHierarchiqueTransformer();
		Agent ag = null;

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidAgentToHierarchique() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToHierarchiqueTransformer tr = new AgentToHierarchiqueTransformer();
		Agent ag = new Agent();
		ag.setTitre("1");
		ag.setPosition("a");
		ag.setPrenomUsage("usage");
		ag.setNomUsage("nomuasag");

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json,
				"{\"nom\":\"nomuasag\",\"prenom\":\"usage\",\"position\":\"a\",\"titre\":\"Madame\"}");
	}
}
