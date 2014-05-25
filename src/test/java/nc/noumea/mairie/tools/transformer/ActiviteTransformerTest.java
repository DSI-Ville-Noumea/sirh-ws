package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.Activite;

import org.junit.Test;

import flexjson.JSONSerializer;

public class ActiviteTransformerTest {

	@Test
	public void testTransformNullActivite() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		ActiviteTransformer tr = new ActiviteTransformer();
		Activite acti = null;

		// When
		String json = serializer.transform(tr, Activite.class).serialize(acti);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidActivite() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		ActiviteTransformer tr = new ActiviteTransformer();
		Activite acti = new Activite();
		acti.setNomActivite("acti");

		// When
		String json = serializer.transform(tr, Activite.class).serialize(acti);

		// Then
		assertEquals(json, "{\"nomActivite\":\"acti\"}");
	}
}
