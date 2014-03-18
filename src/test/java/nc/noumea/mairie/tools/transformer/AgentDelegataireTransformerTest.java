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
	public void testTransformValidAgentDelegataire_NomMarital() {

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

	@Test
	public void testTransformValidAgentDelegataire_NomUsage() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentDelegataireTransformer tr = new AgentDelegataireTransformer();
		Agent ag = new Agent();
		ag.setNomUsage("usage");
		ag.setNomMarital("marital");
		ag.setPrenom("prenom1");
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json, "{\"nom\":\"usage\",\"prenom\":\"prenom1\",\"idAgent\":9005138,\"nomatr\":5138}");
	}

	@Test
	public void testTransformValidAgentDelegataire_NomPatro() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentDelegataireTransformer tr = new AgentDelegataireTransformer();
		Agent ag = new Agent();
		ag.setNomUsage(null);
		ag.setNomMarital(null);
		ag.setNomPatronymique("patro");
		ag.setPrenom("prenom1");
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json, "{\"nom\":\"patro\",\"prenom\":\"prenom1\",\"idAgent\":9005138,\"nomatr\":5138}");
	}

	@Test
	public void testTransformValidAgentDelegataire_PrenomUsage() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		AgentDelegataireTransformer tr = new AgentDelegataireTransformer();
		Agent ag = new Agent();
		ag.setNomUsage(null);
		ag.setNomMarital(null);
		ag.setNomPatronymique("patro");
		ag.setPrenomUsage("preusage");
		ag.setPrenom("prenom1");
		ag.setIdAgent(9005138);
		ag.setNomatr(5138);

		// When
		String json = serializer.transform(tr, Agent.class).serialize(ag);

		// Then
		assertEquals(json, "{\"nom\":\"patro\",\"prenom\":\"preusage\",\"idAgent\":9005138,\"nomatr\":5138}");
	}
}
