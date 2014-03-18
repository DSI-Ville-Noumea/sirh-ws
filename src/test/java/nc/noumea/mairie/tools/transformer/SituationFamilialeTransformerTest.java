package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.SituationFamiliale;

import org.junit.Test;

import flexjson.JSONSerializer;

public class SituationFamilialeTransformerTest {

	@Test
	public void testTransformNullSituationFamiliale() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		SituationFamilialeTransformer tr = new SituationFamilialeTransformer();
		SituationFamiliale tc = null;

		// When
		String json = serializer.transform(tr, SituationFamiliale.class).serialize(tc);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidSituationFamiliale() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		SituationFamilialeTransformer tr = new SituationFamilialeTransformer();
		SituationFamiliale tc = new SituationFamiliale();
		tc.setLibSituationFamiliale("libelle situation");

		// When
		String json = serializer.transform(tr, SituationFamiliale.class).serialize(tc);

		// Then
		assertEquals(json, "\"libelle situation\"");
	}
}
