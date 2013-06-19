package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "P_REPRESENTANT", identifierColumn = "ID_REPRESENTANT", identifierField = "idRepresentant", identifierType = Integer.class, versionField="")
//@Embeddable
public class Representant {

	@Column(name = "NOM_REPRESENTANT")
	private String nom;
	
	@Column(name = "PRENOM_REPRESENTANT")
	private String prenom;
}
