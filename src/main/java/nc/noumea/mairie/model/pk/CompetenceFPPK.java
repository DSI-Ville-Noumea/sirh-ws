package nc.noumea.mairie.model.pk;

import javax.persistence.Column;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooIdentifier
public final class CompetenceFPPK {

	@Column(name = "ID_COMPETENCE")
	private Integer idCompetence;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;
}
