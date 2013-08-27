package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "eaePersistenceUnit", identifierColumn = "ID_EAE_EVALUATION", identifierField = "idEaeEvaluation", identifierType = Integer.class, table = "EAE_EVALUATION", sequenceName = "EAE_S_EVALUATION")
public class EaeEvaluation {

	@Column(name = "AVIS_SHD")
	private String avisShd;
	
	
	@OneToOne
	@JoinColumn(name = "ID_EAE")
	private Eae eae;
	
}
