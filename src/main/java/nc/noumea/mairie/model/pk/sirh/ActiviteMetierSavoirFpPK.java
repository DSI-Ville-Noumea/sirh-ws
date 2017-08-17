package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ActiviteMetierSavoirFpPK implements Serializable {
    private int idFichePoste;
    private int idActiviteMetier;
    private Integer idSavoirFaire;

    @Column(name = "ID_FICHE_POSTE", nullable = false)
    @Id
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
    }

    @Column(name = "ID_ACTIVITE_METIER", nullable = false)
    @Id
    public int getIdActiviteMetier() {
        return idActiviteMetier;
    }

    public void setIdActiviteMetier(int idActiviteMetier) {
        this.idActiviteMetier = idActiviteMetier;
    }

    @Column(name = "ID_SAVOIR_FAIRE", nullable = true)
    @Id
    public Integer getIdSavoirFaire() {
        return idSavoirFaire;
    }

    public void setIdSavoirFaire(Integer idSavoirFaire) {
        this.idSavoirFaire = idSavoirFaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteMetierSavoirFpPK that = (ActiviteMetierSavoirFpPK) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idActiviteMetier != that.idActiviteMetier) return false;
        if (idSavoirFaire != null ? !idSavoirFaire.equals(that.idSavoirFaire) : that.idSavoirFaire != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idActiviteMetier;
        result = 31 * result + (idSavoirFaire != null ? idSavoirFaire.hashCode() : 0);
        return result;
    }
}
