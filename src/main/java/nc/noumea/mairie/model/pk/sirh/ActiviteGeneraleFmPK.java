package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ActiviteGeneraleFmPK implements Serializable {
    private int idFicheMetier;
    private int idActiviteGenerale;

    @Column(name = "ID_FICHE_METIER", nullable = false)
    @Id
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Column(name = "ID_ACTIVITE_GENERALE", nullable = false)
    @Id
    public int getIdActiviteGenerale() {
        return idActiviteGenerale;
    }

    public void setIdActiviteGenerale(int idActiviteGenerale) {
        this.idActiviteGenerale = idActiviteGenerale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteGeneraleFmPK that = (ActiviteGeneraleFmPK) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idActiviteGenerale != that.idActiviteGenerale) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idActiviteGenerale;
        return result;
    }
}
