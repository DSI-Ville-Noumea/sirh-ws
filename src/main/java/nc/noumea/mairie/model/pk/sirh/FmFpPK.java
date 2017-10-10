package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class FmFpPK implements Serializable {
    private int idFicheMetier;
    private int idFichePoste;
    private short fmPrimaire;

    @Column(name = "ID_FICHE_METIER", nullable = false)
    @Id
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Column(name = "ID_FICHE_POSTE", nullable = false)
    @Id
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
    }

    @Column(name = "FM_PRIMAIRE", nullable = false)
    @Id
    public short getFmPrimaire() {
        return fmPrimaire;
    }

    public void setFmPrimaire(short fmPrimaire) {
        this.fmPrimaire = fmPrimaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FmFpPK fmFpPK = (FmFpPK) o;

        if (idFicheMetier != fmFpPK.idFicheMetier) return false;
        if (idFichePoste != fmFpPK.idFichePoste) return false;
        if (fmPrimaire != fmFpPK.fmPrimaire) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idFichePoste;
        result = 31 * result + (int) fmPrimaire;
        return result;
    }
}
