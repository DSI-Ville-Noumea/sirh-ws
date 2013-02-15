package nc.noumea.mairie.model.bean;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierColumn = "ID_CAP", schema = "SIRH", identifierField = "idCap", identifierType = Integer.class, table = "P_CAP", versionField = "")
public class Cap {

	@Column(name = "CODE_CAP")
	private String codeCap;

	@Column(name = "REF_CAP")
	private String refCap;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "TYPE_CAP")
	private String typeCap;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(schema = "SIRH", name = "CORPS_CAP", joinColumns = { @javax.persistence.JoinColumn(name = "ID_CAP") }, inverseJoinColumns = @javax.persistence.JoinColumn(name = "CDGENG"))
	private Set<Spgeng> corps;
}
