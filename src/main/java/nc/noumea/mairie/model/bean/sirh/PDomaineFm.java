package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "P_DOMAINE_FM")
public class PDomaineFm {
    private int idDomaineFm;
    private String codeDomaineFm;
    private String libDomaineFm;

    @Id
    @Column(name = "ID_DOMAINE_FM", nullable = false)
    public int getIdDomaineFm() {
        return idDomaineFm;
    }

    public void setIdDomaineFm(int idDomaineFm) {
        this.idDomaineFm = idDomaineFm;
    }

    @Basic
    @Column(name = "CODE_DOMAINE_FM", nullable = false, length = 10)
    public String getCodeDomaineFm() {
        return codeDomaineFm;
    }

    public void setCodeDomaineFm(String codeDomaineFm) {
        this.codeDomaineFm = codeDomaineFm;
    }

    @Basic
    @Column(name = "LIB_DOMAINE_FM", nullable = false, length = 1000)
    public String getLibDomaineFm() {
        return libDomaineFm;
    }

    public void setLibDomaineFm(String libDomaineFm) {
        this.libDomaineFm = libDomaineFm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PDomaineFm that = (PDomaineFm) o;

        if (idDomaineFm != that.idDomaineFm) return false;
        if (codeDomaineFm != null ? !codeDomaineFm.equals(that.codeDomaineFm) : that.codeDomaineFm != null)
            return false;
        if (libDomaineFm != null ? !libDomaineFm.equals(that.libDomaineFm) : that.libDomaineFm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idDomaineFm;
        result = 31 * result + (codeDomaineFm != null ? codeDomaineFm.hashCode() : 0);
        result = 31 * result + (libDomaineFm != null ? libDomaineFm.hashCode() : 0);
        return result;
    }
}
