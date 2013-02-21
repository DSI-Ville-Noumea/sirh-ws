package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "SIRH", table = "P_EMPLOYEUR", identifierColumn = "ID_EMPLOYEUR", identifierField = "idEmployeur", identifierType = Integer.class, versionField="")
//@Embeddable
public class Employeur {

	@Column(name = "LIB_EMPLOYEUR")
	private String libelle;
	
	@Column(name = "TITRE_EMPLOYEUR")
	private String titre;
}
