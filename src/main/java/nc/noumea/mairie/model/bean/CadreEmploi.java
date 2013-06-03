package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_CADRE_EMPLOI", schema = "SIRH", identifierField = "idCadreEmploi", identifierType = Integer.class, table = "P_CADRE_EMPLOI", versionField = "")
@RooSerializable
public class CadreEmploi {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "LIB_CADRE_EMPLOI")
	private String libelleCadreEmploi;
}
