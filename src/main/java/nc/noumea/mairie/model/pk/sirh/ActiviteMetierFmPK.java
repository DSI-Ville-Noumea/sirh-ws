package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ActiviteMetierFmPK implements Serializable {
    private int idFicheMetier;
    private int idActiviteMetier;

    @Column(name = "ID_FICHE_METIER", nullable = false)
    @Id
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Column(name = "ID_ACTIVITE_METIER", nullable = false)
    @Id
    public int getIdActiviteMetier() {
        return idActiviteMetier;
    }

    public void setIdActiviteMetier(int idActiviteMetier) {
        this.idActiviteMetier = idActiviteMetier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteMetierFmPK that = (ActiviteMetierFmPK) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idActiviteMetier != that.idActiviteMetier) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idActiviteMetier;
        return result;
    }
}
