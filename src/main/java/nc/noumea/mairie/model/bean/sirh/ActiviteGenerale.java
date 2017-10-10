package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITE_GENERALE")
public class ActiviteGenerale {
    private int idActiviteGenerale;
    private String nomActiviteGenerale;

    @Id
    @Column(name = "ID_ACTIVITE_GENERALE", nullable = false)
    public int getIdActiviteGenerale() {
        return idActiviteGenerale;
    }

    public void setIdActiviteGenerale(int idActiviteGenerale) {
        this.idActiviteGenerale = idActiviteGenerale;
    }

    @Basic
    @Column(name = "NOM_ACTIVITE_GENERALE", nullable = false, length = 1000)
    public String getNomActiviteGenerale() {
        return nomActiviteGenerale;
    }

    public void setNomActiviteGenerale(String nomActiviteGenerale) {
        this.nomActiviteGenerale = nomActiviteGenerale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteGenerale that = (ActiviteGenerale) o;

        if (idActiviteGenerale != that.idActiviteGenerale) return false;
        if (nomActiviteGenerale != null ? !nomActiviteGenerale.equals(that.nomActiviteGenerale) : that.nomActiviteGenerale != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idActiviteGenerale;
        result = 31 * result + (nomActiviteGenerale != null ? nomActiviteGenerale.hashCode() : 0);
        return result;
    }
}
