package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_TYPE_DELEGATION", identifierField = "idTypeDelegation", identifierType = Integer.class, table = "P_TYPE_DELEGATION", versionField = "")
@RooSerializable
public class TypeDelegation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "LIB_TYPE_DELEGATION") 
	private String libTypeDelegation;
}
