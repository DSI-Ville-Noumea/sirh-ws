package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "CONDITION_EXERCICE")
public class ConditionExercice {
    private int idConditionExercice;
    private String nomConditionExercice;

    @Id
    @Column(name = "ID_CONDITION_EXERCICE", nullable = false)
    public int getIdConditionExercice() {
        return idConditionExercice;
    }

    public void setIdConditionExercice(int idConditionExercice) {
        this.idConditionExercice = idConditionExercice;
    }

    @Basic
    @Column(name = "NOM_CONDITION_EXERCICE", nullable = false, length = 1000)
    public String getNomConditionExercice() {
        return nomConditionExercice;
    }

    public void setNomConditionExercice(String nomConditionExercice) {
        this.nomConditionExercice = nomConditionExercice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionExercice that = (ConditionExercice) o;

        if (idConditionExercice != that.idConditionExercice) return false;
        if (nomConditionExercice != null ? !nomConditionExercice.equals(that.nomConditionExercice) : that.nomConditionExercice != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idConditionExercice;
        result = 31 * result + (nomConditionExercice != null ? nomConditionExercice.hashCode() : 0);
        return result;
    }
}
