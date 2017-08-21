package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.SavoirFaireFpPK;

import javax.persistence.*;

@Entity
@Table(name = "SAVOIR_FAIRE_FP")
@IdClass(SavoirFaireFpPK.class)
public class SavoirFaireFp {
    private int idFichePoste;
    private int idSavoirFaire;
    private Integer ordre;
    private FichePoste fichePosteByIdFichePoste;
    private SavoirFaire savoirFaireByIdSavoirFaire;

    public SavoirFaireFp() {
    }

    public SavoirFaireFp(int idFichePoste, int idSavoirFaire, Integer ordre) {
        this.idFichePoste = idFichePoste;
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

        SavoirFaireFp that = (SavoirFaireFp) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idSavoirFaire != that.idSavoirFaire) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idSavoirFaire;
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_FICHE_POSTE", referencedColumnName = "ID_FICHE_POSTE", nullable = false, insertable = false, updatable = false)
    public FichePoste getFichePosteByIdFichePoste() {
        return fichePosteByIdFichePoste;
    }

    public void setFichePosteByIdFichePoste(FichePoste fichePosteByIdFichePoste) {
        this.fichePosteByIdFichePoste = fichePosteByIdFichePoste;
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
