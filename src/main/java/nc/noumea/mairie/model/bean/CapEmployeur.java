package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "EMPLOYEUR_CAP", versionField="")
public class CapEmployeur {
	
	@EmbeddedId
	private CapEmployeurPK capEmployeurPk;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_CAP", insertable = false, updatable = false)
	private Cap cap;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "ID_EMPLOYEUR", insertable = false, updatable = false)
	private Employeur employeur;
	
	@Column(name = "POSITION")
	private Integer position;
}
