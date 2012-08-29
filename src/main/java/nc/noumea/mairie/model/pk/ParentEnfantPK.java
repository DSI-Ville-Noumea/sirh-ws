package nc.noumea.mairie.model.pk;

import javax.persistence.Column;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooJson
@RooIdentifier
public final class ParentEnfantPK {

	@Column(name = "ID_AGENT")
	private Integer idAgent;

	@Column(name = "ID_ENFANT")
	private Integer idEnfant;
}
