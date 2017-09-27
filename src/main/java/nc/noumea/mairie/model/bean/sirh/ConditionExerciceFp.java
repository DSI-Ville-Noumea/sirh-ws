package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.ConditionExerciceFpPK;

import javax.persistence.*;

@Entity
@Table(name = "CONDITION_EXERCICE_FP")
@IdClass(ConditionExerciceFpPK.class)
public class ConditionExerciceFp {
    private int idFichePoste;
    private int idConditionExercice;
    private Integer ordre;
    private ConditionExercice conditionExerciceByIdConditionExercice;

    public ConditionExerciceFp() {
    }

    public ConditionExerciceFp(int idFichePoste, int idConditionExercice, Integer ordre) {
        this.idFichePoste = idFichePoste;
        this.idConditionExercice = idConditionExercice;
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

        ConditionExerciceFp that = (ConditionExerciceFp) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idConditionExercice != that.idConditionExercice) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idConditionExercice;
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_CONDITION_EXERCICE", referencedColumnName = "ID_CONDITION_EXERCICE", nullable = false, updatable = false, insertable = false)
    public ConditionExercice getConditionExerciceByIdConditionExercice() {
        return conditionExerciceByIdConditionExercice;
    }

    public void setConditionExerciceByIdConditionExercice(ConditionExercice conditionExerciceByIdConditionExercice) {
        this.conditionExerciceByIdConditionExercice = conditionExerciceByIdConditionExercice;
    }
}
