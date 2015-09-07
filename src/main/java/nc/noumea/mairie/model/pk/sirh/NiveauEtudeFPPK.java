package nc.noumea.mairie.model.pk.sirh;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NiveauEtudeFPPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ID_NIVEAU_ETUDE")
	private Integer idNiveauEtude;

	@Column(name = "ID_FICHE_POSTE")
	private Integer idFichePoste;

	public Integer getIdNiveauEtude() {
		return idNiveauEtude;
	}

	public void setIdNiveauEtude(Integer idNiveauEtude) {
		this.idNiveauEtude = idNiveauEtude;
	}

	public Integer getIdFichePoste() {
		return idFichePoste;
	}

	public void setIdFichePoste(Integer idFichePoste) {
		this.idFichePoste = idFichePoste;
	}
}
