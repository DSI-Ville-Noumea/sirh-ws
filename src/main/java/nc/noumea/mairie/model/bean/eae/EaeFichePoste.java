package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Siserv;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "eaePersistenceUnit", identifierColumn = "ID_EAE_FICHE_POSTE", identifierField = "idEaeFichePoste", identifierType = Integer.class, table = "EAE_FICHE_POSTE",sequenceName="EAE_S_FICHE_POSTE")
public class EaeFichePoste {

	@Column(name = "ID_SHD")
	private Integer idAgentShd;

	@OneToOne(optional = false)
	@JoinColumn(name = "ID_EAE", unique = true, nullable = false)
	private Eae eae;

	/*@OneToOne
	@JoinColumn(name = "CODE_SERVICE", referencedColumnName = "SERVI")
	private Siserv service;*/

	@Transient
	private Agent agentShd;
}
