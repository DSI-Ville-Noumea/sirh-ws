package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITE_METIER")
public class ActiviteMetier {
    private int idActiviteMetier;
    private String nomActiviteMetier;

    @Id
    @Column(name = "ID_ACTIVITE_METIER", nullable = false)
    public int getIdActiviteMetier() {
        return idActiviteMetier;
    }

    public void setIdActiviteMetier(int idActiviteMetier) {
        this.idActiviteMetier = idActiviteMetier;
    }

    @Basic
    @Column(name = "NOM_ACTIVITE_METIER", nullable = false, length = 255)
    public String getNomActiviteMetier() {
        return nomActiviteMetier;
    }

    public void setNomActiviteMetier(String nomActiviteMetier) {
        this.nomActiviteMetier = nomActiviteMetier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActiviteMetier that = (ActiviteMetier) o;

        if (idActiviteMetier != that.idActiviteMetier) return false;
        if (nomActiviteMetier != null ? !nomActiviteMetier.equals(that.nomActiviteMetier) : that.nomActiviteMetier != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idActiviteMetier;
        result = 31 * result + (nomActiviteMetier != null ? nomActiviteMetier.hashCode() : 0);
        return result;
    }
}
