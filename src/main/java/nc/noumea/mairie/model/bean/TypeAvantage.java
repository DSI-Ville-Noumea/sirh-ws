package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_TYPE_AVANTAGE", identifierField = "idTypeAvantage", identifierType = Integer.class, table = "P_TYPE_AVANTAGE", versionField = "")
@RooSerializable
public class TypeAvantage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "LIB_TYPE_AVANTAGE") 
	private String libTypeAvantage;
}
