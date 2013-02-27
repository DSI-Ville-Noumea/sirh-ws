package nc.noumea.mairie.model.bean.eae;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(table = "EAE_FINALISATION", sequenceName = "EAE_S_FINALISATION", identifierColumn = "ID_EAE_FINALISATION", identifierField = "idEaeFinalisation", identifierType = Integer.class, persistenceUnit = "eaePersistenceUnit")
public class EaeFinalisation {
	
	@NotNull
	@Column(name = "DATE_FINALISATION")
    @Temporal(TemporalType.TIMESTAMP)
	private Date dateFinalisation;
	
	@NotNull
	@Column(name = "ID_AGENT")
	private int idAgent;
	
	@NotNull
	@Column(name = "ID_GED_DOCUMENT")
	private String idGedDocument;
	
	@NotNull
	@Column(name = "VERSION_GED_DOCUMENT")
	private String versionGedDocument;
	
	@Lob
	@Column(name = "COMMENTAIRE")
	private String commentaire;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_EAE")
	private Eae eae;
}
