package nc.noumea.mairie.model.bean.eae;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import nc.noumea.mairie.model.bean.Agent;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_EAE_EVALUATEUR", identifierField = "idEaeEvaluateur", identifierType = Integer.class, table = "EAE_EVALUATEUR", persistenceUnit = "eaePersistenceUnit")
@RooJson
public class EaeEvaluateur {

	@Column(name = "ID_AGENT")
	private int idAgent;

	@Column(name = "FONCTION")
	private String fonction;

	@Column(name = "DATE_ENTREE_SERVICE")
	private Date dateEntreeService;
	
	@Column(name = "DATE_ENTREE_COLLECTIVITE")
	private Date dateEntreeCollectivite;
	
	@Column(name = "DATE_ENTREE_FONCTION")
	private Date dateEntreeFonction;
	
	@ManyToOne
	@JoinColumn(name = "ID_EAE", referencedColumnName = "ID_EAE")
	private Eae eae;
		
	/*
     * Transient properties (will be populated by AS400 entity manager)
     */
    @Transient
    private Agent agent;
}