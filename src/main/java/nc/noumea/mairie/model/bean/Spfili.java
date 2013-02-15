package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SPFILI", versionField = "")
public class Spfili {

	@Id
	@Column(name = "CDFILI", columnDefinition = "char")
	private String cdfili;
	
	@Column(name = "LIFILI", columnDefinition = "char")
	private String libelleFili;
}
