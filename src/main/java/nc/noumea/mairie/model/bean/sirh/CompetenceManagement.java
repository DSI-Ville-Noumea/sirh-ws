package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "COMPETENCE_MANAGEMENT")
public class CompetenceManagement {
    private int idCompetenceManagement;
    private String libCompetenceManagement;
    private Integer ordre;

    @Id
    @Column(name = "ID_COMPETENCE_MANAGEMENT", nullable = false)
    public int getIdCompetenceManagement() {
        return idCompetenceManagement;
    }

    public void setIdCompetenceManagement(int idCompetenceManagement) {
        this.idCompetenceManagement = idCompetenceManagement;
    }

    @Basic
    @Column(name = "LIB_COMPETENCE_MANAGEMENT", nullable = false, length = 255)
    public String getLibCompetenceManagement() {
        return libCompetenceManagement;
    }

    public void setLibCompetenceManagement(String libCompetenceManagement) {
        this.libCompetenceManagement = libCompetenceManagement;
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

        CompetenceManagement that = (CompetenceManagement) o;

        if (idCompetenceManagement != that.idCompetenceManagement) return false;
        if (libCompetenceManagement != null ? !libCompetenceManagement.equals(that.libCompetenceManagement) : that.libCompetenceManagement != null)
            return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idCompetenceManagement;
        result = 31 * result + (libCompetenceManagement != null ? libCompetenceManagement.hashCode() : 0);
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    private NiveauManagement niveauManagement;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_NIVEAU_MANAGEMENT", referencedColumnName = "ID_NIVEAU_MANAGEMENT")
    public NiveauManagement getNiveauManagement() {
        return niveauManagement;
    }

    public void setNiveauManagement(NiveauManagement niveauManagement) {
        this.niveauManagement = niveauManagement;
    }
}
