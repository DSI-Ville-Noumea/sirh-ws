package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.ActiviteMetierSavoirFpPK;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITE_METIER_SAVOIR_FP")
@IdClass(ActiviteMetierSavoirFpPK.class)
public class ActiviteMetierSavoirFp {
    private int idFichePoste;
    private int idActiviteMetier;
    private Integer idSavoirFaire;
    private Integer ordre;
    private ActiviteMetier activiteMetierByIdActiviteMetier;
    private SavoirFaire savoirFaireByIdSavoirFaire;

    public ActiviteMetierSavoirFp() {
    }

    public ActiviteMetierSavoirFp(int idFichePoste, int idActiviteMetier, Integer idSavoirFaire, Integer ordre) {
        this.idFichePoste = idFichePoste;
        this.idActiviteMetier = idActiviteMetier;
        this.idSavoirFaire = idSavoirFaire;
        this.ordre = ordre;
    }

    @Id
    @Column(name = "ID_FICHE_POSTE", nullable = false)
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
    }

    @Id
    @Column(name = "ID_ACTIVITE_METIER", nullable = false)
    public int getIdActiviteMetier() {
        return idActiviteMetier;
    }

    public void setIdActiviteMetier(int idActiviteMetier) {
        this.idActiviteMetier = idActiviteMetier;
    }

    @Id
    @Column(name = "ID_SAVOIR_FAIRE", nullable = true)
    public Integer getIdSavoirFaire() {
        return idSavoirFaire;
    }

    public void setIdSavoirFaire(Integer idSavoirFaire) {
        this.idSavoirFaire = idSavoirFaire;
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

        ActiviteMetierSavoirFp that = (ActiviteMetierSavoirFp) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idActiviteMetier != that.idActiviteMetier) return false;
        if (idSavoirFaire != null ? !idSavoirFaire.equals(that.idSavoirFaire) : that.idSavoirFaire != null)
            return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idActiviteMetier;
        result = 31 * result + (idSavoirFaire != null ? idSavoirFaire.hashCode() : 0);
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

    @ManyToOne
    @JoinColumn(name = "ID_SAVOIR_FAIRE", referencedColumnName = "ID_SAVOIR_FAIRE", nullable = false, insertable = false, updatable = false)
    public SavoirFaire getSavoirFaireByIdSavoirFaire() {
        return savoirFaireByIdSavoirFaire;
    }

    public void setSavoirFaireByIdSavoirFaire(SavoirFaire savoirFaire) {
        this.savoirFaireByIdSavoirFaire = savoirFaire;
    }
}
