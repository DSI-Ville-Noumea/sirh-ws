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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_REG_INDEMN", identifierField = "idRegimeIndemnitaire", identifierType = Integer.class, table = "REGIME_INDEMNITAIRE", versionField = "")
@RooSerializable
public class RegimeIndemnitaire {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_REG_INDEMN", referencedColumnName = "ID_TYPE_REG_INDEMN")
	private TypeRegimeIndemnitaire typeRegimeIndemnitaire;
	
	@Column(name = "NUM_RUBRIQUE", columnDefinition = "numeric")
	private Integer numRubrique;
	
	@Column(name = "FORFAIT", columnDefinition = "decimal")
	private Float forfait;
	
	@Column(name = "NOMBRE_POINTS", columnDefinition = "integer")
	private Integer nombrePoint;
}
