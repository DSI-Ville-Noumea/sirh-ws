package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.FmFpPK;

import javax.persistence.*;

@Entity
@Table(name = "FM_FP")
@IdClass(FmFpPK.class)
public class FmFp {
    private int idFicheMetier;
    private int idFichePoste;
    private short fmPrimaire;
    private FicheMetier ficheMetierByIdFicheMetier;

    @Id
    @Column(name = "ID_FICHE_METIER", nullable = false)
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
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
    @Column(name = "FM_PRIMAIRE", nullable = false)
    public short getFmPrimaire() {
        return fmPrimaire;
    }

    public void setFmPrimaire(short fmPrimaire) {
        this.fmPrimaire = fmPrimaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FmFp fmFp = (FmFp) o;

        if (idFicheMetier != fmFp.idFicheMetier) return false;
        if (idFichePoste != fmFp.idFichePoste) return false;
        if (fmPrimaire != fmFp.fmPrimaire) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idFichePoste;
        result = 31 * result + (int) fmPrimaire;
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
}
