package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SavoirFaireFmPK implements Serializable {
    private int idFicheMetier;
    private int idSavoirFaire;

    @Column(name = "ID_FICHE_METIER", nullable = false)
    @Id
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Column(name = "ID_SAVOIR_FAIRE", nullable = false)
    @Id
    public int getIdSavoirFaire() {
        return idSavoirFaire;
    }

    public void setIdSavoirFaire(int idSavoirFaire) {
        this.idSavoirFaire = idSavoirFaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SavoirFaireFmPK that = (SavoirFaireFmPK) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idSavoirFaire != that.idSavoirFaire) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idSavoirFaire;
        return result;
    }
}
