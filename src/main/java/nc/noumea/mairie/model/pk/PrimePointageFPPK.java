package nc.noumea.mairie.model.pk;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nc.noumea.mairie.model.bean.FichePoste;

import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;
import org.springframework.roo.addon.tostring.RooToString;

@RooToString
@RooEquals
@RooIdentifier
public final class PrimePointageFPPK { 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "NUM_RUBRIQUE")
	private Integer numRubrique;

//	@Column(name = "ID_FICHE_POSTE")
//	private FichePoste idFichePoste;
}
