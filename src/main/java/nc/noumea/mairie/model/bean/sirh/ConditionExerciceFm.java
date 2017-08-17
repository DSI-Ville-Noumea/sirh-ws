package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.ConditionExerciceFmPK;

import javax.persistence.*;

@Entity
@Table(name = "CONDITION_EXERCICE_FM")
@IdClass(ConditionExerciceFmPK.class)
public class ConditionExerciceFm {
    private int idFicheMetier;
    private int idConditionExercice;
    private Integer ordre;
    private FicheMetier ficheMetierByIdFicheMetier;
    private ConditionExercice conditionExerciceByIdConditionExercice;

    @Id
    @Column(name = "ID_FICHE_METIER", nullable = false)
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Id
    @Column(name = "ID_CONDITION_EXERCICE", nullable = false)
    public int getIdConditionExercice() {
        return idConditionExercice;
    }

    public void setIdConditionExercice(int idConditionExercice) {
        this.idConditionExercice = idConditionExercice;
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

        ConditionExerciceFm that = (ConditionExerciceFm) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idConditionExercice != that.idConditionExercice) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idConditionExercice;
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
    @JoinColumn(name = "ID_CONDITION_EXERCICE", referencedColumnName = "ID_CONDITION_EXERCICE", nullable = false, insertable = false, updatable = false)
    public ConditionExercice getConditionExerciceByIdConditionExercice() {
        return conditionExerciceByIdConditionExercice;
    }

    public void setConditionExerciceByIdConditionExercice(ConditionExercice conditionExerciceByIdConditionExercice) {
        this.conditionExerciceByIdConditionExercice = conditionExerciceByIdConditionExercice;
    }
}
