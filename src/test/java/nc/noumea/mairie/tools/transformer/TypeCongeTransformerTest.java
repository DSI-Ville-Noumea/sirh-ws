package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Sptyco;

import org.junit.Test;

import flexjson.JSONSerializer;

public class TypeCongeTransformerTest {

	@Test
	public void testTransformNullTypeConge() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		TypeCongeTransformer tr = new TypeCongeTransformer();
		Sptyco tc = null;

		// When
		String json = serializer.transform(tr, Sptyco.class).serialize(tc);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidTypeConge() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		TypeCongeTransformer tr = new TypeCongeTransformer();
		Sptyco tc = new Sptyco();
		tc.setLibTypeConge("conge pour maladie");

		// When
		String json = serializer.transform(tr, Sptyco.class).serialize(tc);

		// Then
		assertEquals(json, "\"conge pour maladie\"");
	}
}
