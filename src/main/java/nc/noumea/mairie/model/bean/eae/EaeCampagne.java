package nc.noumea.mairie.model.bean.eae;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "eaePersistenceUnit", identifierColumn = "ID_CAMPAGNE_EAE", identifierField = "idCampagneEae", identifierType = Integer.class, table = "EAE_CAMPAGNE_EAE", versionField = "",schema="EAE")
public class EaeCampagne {

	@NotNull
	@Column(name = "ANNEE")
	private Integer annee;
	

	@Column(name = "DATE_DEBUT")
	private Date dateDebut;

	@Column(name = "DATE_FIN")
	private Date dateFin;

	@Column(name = "DATE_OUVERTURE_KIOSQUE")
	private Date dateOuvertureKiosque;

	@Column(name = "DATE_FERMETURE_KIOSQUE")
	private Date dateFermetureKiosque;
}
