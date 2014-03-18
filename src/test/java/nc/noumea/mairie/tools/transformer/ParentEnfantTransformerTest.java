package nc.noumea.mairie.tools.transformer;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import nc.noumea.mairie.model.bean.Enfant;
import nc.noumea.mairie.model.bean.ParentEnfant;
import nc.noumea.mairie.model.bean.Sicomm;

import org.joda.time.DateTime;
import org.junit.Test;

import flexjson.JSONSerializer;

public class ParentEnfantTransformerTest {

	@Test
	public void testTransformNullAgentToHierarchique() {

		// Given
		JSONSerializer serializer = new JSONSerializer();
		ParentEnfantTransformer tr = new ParentEnfantTransformer();
		ParentEnfant ag = null;

		// When
		String json = serializer.transform(tr, ParentEnfant.class).serialize(ag);

		// Then
		assertEquals("null", json);
	}

	@Test
	public void testTransformValidAgentToHierarchique() {

		// Given
		Date datenaiss = new DateTime(2013, 02, 25, 0, 0, 0).toDate();
		JSONSerializer serializer = new JSONSerializer();
		ParentEnfantTransformer tr = new ParentEnfantTransformer();
		Sicomm codeCommuneNaissFr = new Sicomm();
		codeCommuneNaissFr.setLibVil("ANGERS");
		Enfant enfant = new Enfant();
		enfant.setDateNaissance(datenaiss);
		enfant.setNom("nom enf");
		enfant.setPrenom("prenom enf");
		enfant.setSexe("M");
		enfant.setCodeCommuneNaissFr(codeCommuneNaissFr);
		enfant.setLieuNaissance("ANGERS");
		ParentEnfant ag = new ParentEnfant();
		ag.setEnfant(enfant);
		ag.setEnfantACharge(false);

		// When
		String json = serializer.transform(tr, ParentEnfant.class).serialize(ag);

		// Then
		assertEquals(
				json,
				"{\"dateNaissance\":"
						+ datenaiss.getTime()
						+ ",\"aCharge\":\"non\",\"nom\":\"nom enf\",\"prenom\":\"prenom enf\",\"sexe\":\"M\",\"lieuNaissance\":\"ANGERS\"}");
	}
}
