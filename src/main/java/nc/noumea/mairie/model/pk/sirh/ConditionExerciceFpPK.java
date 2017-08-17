package nc.noumea.mairie.model.pk.sirh;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class ConditionExerciceFpPK implements Serializable {
    private int idFichePoste;
    private int idConditionExercice;

    @Column(name = "ID_FICHE_POSTE", nullable = false)
    @Id
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
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

        ConditionExerciceFpPK that = (ConditionExerciceFpPK) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idConditionExercice != that.idConditionExercice) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idConditionExercice;
        return result;
    }
}
