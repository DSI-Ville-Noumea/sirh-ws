package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ActiviteGeneraleFpPK implements Serializable {
    private int idFichePoste;
    private int idActiviteGenerale;

    @Column(name = "ID_FICHE_POSTE", nullable = false)
    @Id
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
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

        ActiviteGeneraleFpPK that = (ActiviteGeneraleFpPK) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idActiviteGenerale != that.idActiviteGenerale) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idActiviteGenerale;
        return result;
    }
}
