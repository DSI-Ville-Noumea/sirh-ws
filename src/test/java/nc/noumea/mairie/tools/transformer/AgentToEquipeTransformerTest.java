package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Agent;

import org.junit.Test;

import flexjson.JSONSerializer;

public class AgentToEquipeTransformerTest {

	@Test
	public void testTransformNullAgentToEquipe() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToEquipeTransformer tr = new AgentToEquipeTransformer();
		Agent ag = null;

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidAgentToEquipe() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentToEquipeTransformer tr = new AgentToEquipeTransformer();
		Agent ag = new Agent();
		ag.setIdAgent(9005138);
		ag.setTitre("1");
		ag.setPosition("a");
		ag.setPrenomUsage("usage");
		ag.setNomUsage("nomuasag");

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json,
				"{\"nom\":\"nomuasag\",\"prenom\":\"usage\",\"position\":\"a\",\"titre\":\"Madame\",\"idAgent\":9005138}");
	}
}
