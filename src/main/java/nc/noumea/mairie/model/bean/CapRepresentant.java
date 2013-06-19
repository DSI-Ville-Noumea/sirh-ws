package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "REPRESENTANT_CAP", versionField = "")
public class CapRepresentant {

	@EmbeddedId
	private CapRepresentantPK capRepresentantPk;

	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CAP", insertable = false, updatable = false)
	private Cap cap;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_REPRESENTANT", insertable = false, updatable = false)
	private Representant representant;
	
	@Column(name = "POSITION")
	private Integer position;
}
