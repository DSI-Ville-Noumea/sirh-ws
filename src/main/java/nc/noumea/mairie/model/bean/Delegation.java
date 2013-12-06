package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_DELEGATION", identifierField = "idDelegation", identifierType = Integer.class, table = "DELEGATION", versionField = "")
@RooSerializable
public class Delegation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_DELEGATION", referencedColumnName = "ID_TYPE_DELEGATION")
	private TypeDelegation typeDelegation;
	
	@Column(name = "LIB_DELEGATION")
	private String libDelegation;
}
