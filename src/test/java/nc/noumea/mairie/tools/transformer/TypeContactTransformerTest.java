package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.sirh.TypeContact;

import org.junit.Test;

import flexjson.JSONSerializer;

public class TypeContactTransformerTest {

	@Test
	public void testTransformNullTypeContact() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		TypeContactTransformer tr = new TypeContactTransformer();
		TypeContact tc = null;

		// When
		String json = serializer.transform(tr, TypeContact.class).serialize(tc);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidTypeContact() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		TypeContactTransformer tr = new TypeContactTransformer();
		TypeContact tc = new TypeContact();
		tc.setLibelle("libelle contact");

		// When
		String json = serializer.transform(tr, TypeContact.class).serialize(tc);

		// Then
		assertEquals(json, "\"Libelle contact\"");
	}
}
