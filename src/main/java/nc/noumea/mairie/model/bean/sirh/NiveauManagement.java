package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "NIVEAU_MANAGEMENT")
public class NiveauManagement {
    private int idNiveauManagement;
    private String libNiveauManagement;
    private Integer ordre;

    @Id
    @Column(name = "ID_NIVEAU_MANAGEMENT", nullable = false)
    public int getIdNiveauManagement() {
        return idNiveauManagement;
    }

    public void setIdNiveauManagement(int idNiveauManagement) {
        this.idNiveauManagement = idNiveauManagement;
    }

    @Basic
    @Column(name = "LIB_NIVEAU_MANAGEMENT", nullable = false, length = 255)
    public String getLibNiveauManagement() {
        return libNiveauManagement;
    }

    public void setLibNiveauManagement(String libNiveauManagement) {
        this.libNiveauManagement = libNiveauManagement;
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

        NiveauManagement that = (NiveauManagement) o;

        if (idNiveauManagement != that.idNiveauManagement) return false;
        if (libNiveauManagement != null ? !libNiveauManagement.equals(that.libNiveauManagement) : that.libNiveauManagement != null)
            return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idNiveauManagement;
        result = 31 * result + (libNiveauManagement != null ? libNiveauManagement.hashCode() : 0);
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }
}
