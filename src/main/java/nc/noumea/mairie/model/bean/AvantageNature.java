package nc.noumea.mairie.model.bean;

import java.math.BigDecimal;

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
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_AVANTAGE", identifierField = "idAvantage", identifierType = Integer.class, table = "AVANTAGE_NATURE", versionField = "")
@RooSerializable
public class AvantageNature {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "NUM_RUBRIQUE", columnDefinition = "numeric")
	private Integer numRubrique;
	
	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_AVANTAGE", referencedColumnName = "ID_TYPE_AVANTAGE")
	private TypeAvantage typeAvantage;
	
	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NATURE_AVANTAGE", referencedColumnName = "ID_NATURE_AVANTAGE")
	private NatureAvantage natureAvantage;
	
	@Column (name="MONTANT", columnDefinition="decimal", precision=5, scale=2) 
	private BigDecimal montant; 
}
