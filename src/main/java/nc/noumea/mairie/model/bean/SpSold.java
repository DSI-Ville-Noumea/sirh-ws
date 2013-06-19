package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SPSOLD", versionField = "")
public class SpSold {

	@Id
	@Column(name = "NOMATR", columnDefinition = "numeric")
	private Integer nomatr;

	@NotNull
	@Column(name = "SOLDE1", columnDefinition = "decimal")
	private Double soldeAnneeEnCours;

	@NotNull
	@Column(name = "SOLDE2", columnDefinition = "decimal")
	private Double soldeAnneePrec;

	public static JSONSerializer getSerializerForAgentSoldeConge() {

		JSONSerializer serializer = new JSONSerializer()
				.include("soldeAnneeEnCours").include("soldeAnneePrec")
				.exclude("*");

		return serializer;
	}
}
