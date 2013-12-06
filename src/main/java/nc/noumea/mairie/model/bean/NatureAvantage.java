package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_NATURE_AVANTAGE", identifierField = "idNatureAvantage", identifierType = Integer.class, table = "P_NATURE_AVANTAGE", versionField = "")
@RooSerializable
public class NatureAvantage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "LIB_NATURE_AVANTAGE") 
	private String libNatureAvantage;
}
