package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.tools.transformer.StringTrimTransformer;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_ACTIVITE", schema = "SIRH", identifierField = "idActivite", identifierType = Integer.class, table = "ACTIVITE", versionField = "")
@RooSerializable
public class Activite {

	@NotNull
	@Column(name = "NOM_ACTIVITE")
	private String nomActivite;

	public static JSONSerializer getSerializerForActivite() {

		JSONSerializer serializer = new JSONSerializer().include("nomActivite").transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}
}
