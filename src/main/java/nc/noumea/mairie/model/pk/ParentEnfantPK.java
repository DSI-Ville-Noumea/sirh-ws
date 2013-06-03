package nc.noumea.mairie.model.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooIdentifier
@RooJson
@Embeddable
public final class ParentEnfantPK {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "ID_ENFANT")
	private Integer idEnfant;
}
