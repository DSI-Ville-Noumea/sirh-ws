package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "AUTRE_APPELLATION_METIER")
public class AutreAppellationMetier {
    private int idAutreAppellationMetier;
    private String libAutreAppellationMetier;

    @Id
    @Column(name = "ID_AUTRE_APPELLATION_METIER", nullable = false)
    public int getIdAutreAppellationMetier() {
        return idAutreAppellationMetier;
    }

    public void setIdAutreAppellationMetier(int idAutreAppellationMetier) {
        this.idAutreAppellationMetier = idAutreAppellationMetier;
    }

    @Basic
    @Column(name = "LIB_AUTRE_APPELLATION_METIER", nullable = false, length = 255)
    public String getLibAutreAppellationMetier() {
        return libAutreAppellationMetier;
    }

    public void setLibAutreAppellationMetier(String libAutreAppellationMetier) {
        this.libAutreAppellationMetier = libAutreAppellationMetier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AutreAppellationMetier that = (AutreAppellationMetier) o;

        if (idAutreAppellationMetier != that.idAutreAppellationMetier) return false;
        if (libAutreAppellationMetier != null ? !libAutreAppellationMetier.equals(that.libAutreAppellationMetier) : that.libAutreAppellationMetier != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAutreAppellationMetier;
        result = 31 * result + (libAutreAppellationMetier != null ? libAutreAppellationMetier.hashCode() : 0);
        return result;
    }
}
