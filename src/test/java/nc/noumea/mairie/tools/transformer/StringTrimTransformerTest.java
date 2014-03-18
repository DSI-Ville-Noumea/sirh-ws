package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import flexjson.JSONSerializer;

public class StringTrimTransformerTest {

	@Test
	public void testTransformNullStringTrim() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		StringTrimTransformer tr = new StringTrimTransformer();
		String tc = null;

		// When
		String json = serializer.transform(tr, String.class).serialize(tc);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidStringTrim() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		StringTrimTransformer tr = new StringTrimTransformer();
		String tc = " text avec espace ";

		// When
		String json = serializer.transform(tr, String.class).serialize(tc);

		// Then
		assertEquals(json, "\"text avec espace\"");
	}
}
