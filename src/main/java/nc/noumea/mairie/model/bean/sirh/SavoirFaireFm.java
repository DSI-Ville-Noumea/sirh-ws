package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.SavoirFaireFmPK;

import javax.persistence.*;

@Entity
@Table(name = "SAVOIR_FAIRE_FM")
@IdClass(SavoirFaireFmPK.class)
public class SavoirFaireFm {
    private int idFicheMetier;
    private int idSavoirFaire;
    private Integer ordre;
    private FicheMetier ficheMetierByIdFicheMetier;
    private SavoirFaire savoirFaireByIdSavoirFaire;

    @Id
    @Column(name = "ID_FICHE_METIER", nullable = false)
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Id
    @Column(name = "ID_SAVOIR_FAIRE", nullable = false)
    public int getIdSavoirFaire() {
        return idSavoirFaire;
    }

    public void setIdSavoirFaire(int idSavoirFaire) {
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

        SavoirFaireFm that = (SavoirFaireFm) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idSavoirFaire != that.idSavoirFaire) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idSavoirFaire;
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_FICHE_METIER", referencedColumnName = "ID_FICHE_METIER", nullable = false, insertable = false, updatable = false)
    public FicheMetier getFicheMetierByIdFicheMetier() {
        return ficheMetierByIdFicheMetier;
    }

    public void setFicheMetierByIdFicheMetier(FicheMetier ficheMetierByIdFicheMetier) {
        this.ficheMetierByIdFicheMetier = ficheMetierByIdFicheMetier;
    }

    @ManyToOne
    @JoinColumn(name = "ID_SAVOIR_FAIRE", referencedColumnName = "ID_SAVOIR_FAIRE", nullable = false, insertable = false, updatable = false)
    public SavoirFaire getSavoirFaireByIdSavoirFaire() {
        return savoirFaireByIdSavoirFaire;
    }

    public void setSavoirFaireByIdSavoirFaire(SavoirFaire savoirFaireByIdSavoirFaire) {
        this.savoirFaireByIdSavoirFaire = savoirFaireByIdSavoirFaire;
    }
}
