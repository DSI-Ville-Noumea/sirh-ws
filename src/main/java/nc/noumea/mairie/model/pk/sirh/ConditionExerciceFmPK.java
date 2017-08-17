package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ConditionExerciceFmPK implements Serializable {
    private int idFicheMetier;
    private int idConditionExercice;

    @Column(name = "ID_FICHE_METIER", nullable = false)
    @Id
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Column(name = "ID_CONDITION_EXERCICE", nullable = false)
    @Id
    public int getIdConditionExercice() {
        return idConditionExercice;
    }

    public void setIdConditionExercice(int idConditionExercice) {
        this.idConditionExercice = idConditionExercice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConditionExerciceFmPK that = (ConditionExerciceFmPK) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (idConditionExercice != that.idConditionExercice) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + idConditionExercice;
        return result;
    }
}
