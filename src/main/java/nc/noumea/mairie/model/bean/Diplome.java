package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_DIPLOME_GENERIQUE", identifierField = "idDiplomeGenerique", identifierType = Integer.class, table = "P_DIPLOME_GENERIQUE", versionField = "")
@RooSerializable
public class Diplome {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "LIB_DIPLOME_GENERIQUE")
	private String libDiplomen;
}
