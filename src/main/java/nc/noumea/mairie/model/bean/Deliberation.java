package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_DELIBERATION", identifierField = "idDeliberation", table = "P_DELIBERATION", versionField = "")
public class Deliberation {

	@Column(name = "LIB_DELIBERATION")
	private String libDeliberation;
	
	@Column(name = "TEXTE_CAP")
	private String texteCap;
}
