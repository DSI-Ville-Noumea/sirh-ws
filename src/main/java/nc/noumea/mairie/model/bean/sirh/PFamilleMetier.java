package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "P_FAMILLE_METIER")
public class PFamilleMetier {
    private int idFamilleMetier;
    private String codeFamilleMetier;
    private String libFamilleMetier;

    @Id
    @Column(name = "ID_FAMILLE_METIER", nullable = false)
    public int getIdFamilleMetier() {
        return idFamilleMetier;
    }

    public void setIdFamilleMetier(int idFamilleMetier) {
        this.idFamilleMetier = idFamilleMetier;
    }

    @Basic
    @Column(name = "CODE_FAMILLE_METIER", nullable = false, length = 3)
    public String getCodeFamilleMetier() {
        return codeFamilleMetier;
    }

    public void setCodeFamilleMetier(String codeFamilleMetier) {
        this.codeFamilleMetier = codeFamilleMetier;
    }

    @Basic
    @Column(name = "LIB_FAMILLE_METIER", nullable = false, length = 255)
    public String getLibFamilleMetier() {
        return libFamilleMetier;
    }

    public void setLibFamilleMetier(String libFamilleMetier) {
        this.libFamilleMetier = libFamilleMetier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PFamilleMetier that = (PFamilleMetier) o;

        if (idFamilleMetier != that.idFamilleMetier) return false;
        if (codeFamilleMetier != null ? !codeFamilleMetier.equals(that.codeFamilleMetier) : that.codeFamilleMetier != null)
            return false;
        if (libFamilleMetier != null ? !libFamilleMetier.equals(that.libFamilleMetier) : that.libFamilleMetier != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFamilleMetier;
        result = 31 * result + (codeFamilleMetier != null ? codeFamilleMetier.hashCode() : 0);
        result = 31 * result + (libFamilleMetier != null ? libFamilleMetier.hashCode() : 0);
        return result;
    }
}
