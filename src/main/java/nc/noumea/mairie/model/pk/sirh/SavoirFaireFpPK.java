package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SavoirFaireFpPK implements Serializable {
    private int idFichePoste;
    private int idSavoirFaire;

    @Column(name = "ID_FICHE_POSTE", nullable = false)
    @Id
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
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

        SavoirFaireFpPK that = (SavoirFaireFpPK) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idSavoirFaire != that.idSavoirFaire) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idSavoirFaire;
        return result;
    }
}
