package nc.noumea.mairie.model.bean.eae;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.enums.EaeEtatEnum;
import nc.noumea.mairie.model.bean.Agent;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "eaePersistenceUnit", identifierColumn = "ID_EAE", identifierField = "idEae", identifierType = Integer.class, table = "EAE", sequenceName = "EAE_S_EAE")
@RooJson
public class Eae {

	@NotNull
	@Column(name = "ETAT")
	@Enumerated(EnumType.STRING)
	private EaeEtatEnum etat;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "ID_CAMPAGNE_EAE", referencedColumnName = "ID_CAMPAGNE_EAE")
	private EaeCampagne eaeCampagne;

	@Column(name = "ID_DELEGATAIRE")
	private Integer idAgentDelegataire;

	@OneToMany(mappedBy = "eae", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<EaeEvaluateur> eaeEvaluateurs = new HashSet<EaeEvaluateur>();

	@OneToOne(optional = false, mappedBy = "eae", fetch = FetchType.LAZY)
	private EaeFichePoste eaeFichePoste;

	/*
	 * Transient properties (will be populated by AS400 entity manager)
	 */
	@Transient
	private Agent agentEvalue;

	@Transient
	private Agent agentDelegataire;

	public static JSONSerializer getSerializerForEaeList() {

		JSONSerializer serializer = new JSONSerializer().include("idEae")
				.exclude("*");

		return serializer;
	}
}
