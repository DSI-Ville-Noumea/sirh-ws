package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.ActiviteMetierFmPK;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITE_METIER_FM")
@IdClass(ActiviteMetierFmPK.class)
public class ActiviteMetierFm {
    private int idFicheMetier;
    private int idActiviteMetier;
    private Integer ordre;
    private ActiviteMetier activiteMetierByIdActiviteMetier;

    @Id
    @Column(name = "ID_FICHE_METIER", nullable = false)
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Id
    @Column(name = "ID_ACTIVITE_METIER", nullable = false)
    public int getIdActiviteMetier() {
        return idActiviteMetier;
    }

    public void setIdActiviteMetier(int idActiviteMetier) {
        this.idActiviteMetier = idActiviteMetier;
    }

    @Basic
    @Column(name = "ORDRE", nullable = true)
    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteMetierFm that = (ActiviteMetierFm) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idActiviteMetier != that.idActiviteMetier) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idActiviteMetier;
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ACTIVITE_METIER", referencedColumnName = "ID_ACTIVITE_METIER", nullable = false, insertable = false, updatable = false)
    public ActiviteMetier getActiviteMetierByIdActiviteMetier() {
        return activiteMetierByIdActiviteMetier;
    }

    public void setActiviteMetierByIdActiviteMetier(ActiviteMetier activiteMetierByIdActiviteMetier) {
        this.activiteMetierByIdActiviteMetier = activiteMetierByIdActiviteMetier;
    }
}
