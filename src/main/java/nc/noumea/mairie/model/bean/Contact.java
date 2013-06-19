package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import nc.noumea.mairie.tools.transformer.NullableIntegerTransformer;
import nc.noumea.mairie.tools.transformer.StringTrimTransformer;
import nc.noumea.mairie.tools.transformer.TypeContactTransformer;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_CONTACT", identifierField = "idContact", table = "CONTACT", versionField = "")
public class Contact {

	@OneToOne
	@JoinColumn(name = "ID_TYPE_CONTACT", referencedColumnName = "ID_TYPE_CONTACT")
	private TypeContact typeContact;

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DIFFUSABLE")
	private String diffusable;

	public String getDiffusable() {
		if (this.diffusable.equals("1")) {
			return "oui";
		} else {
			return "non";
		}
	}

	@Column(name = "PRIORITAIRE", columnDefinition = "smallint")
	private Integer contactPrioritaire;

	@Transient
	private String prioritaire;
	public String getPrioritaire() {
		if (this.contactPrioritaire.toString().equals("1")) {
			return "oui";
		} else {
			return "non";
		}
	}

	public static JSONSerializer getSerializerForAgentContacts() {

		JSONSerializer serializer = new JSONSerializer().include("diffusable").include("prioritaire").include("typeContact").include("description")
				.transform(new NullableIntegerTransformer(), Integer.class).transform(new TypeContactTransformer(), TypeContact.class)
				.transform(new StringTrimTransformer(), String.class).exclude("*");

		return serializer;
	}
}
