package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "NIVEAU_MANAGEMENT")
public class NiveauManagement {
    private int idNiveauManagement;
    private String libNiveauManagement;
    private Integer ordre;
    private String definitionManagement;

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

    @Basic
    @Column(name = "DEF_NIVEAU_MANAGEMENT", nullable = true)
    public String getDefinitionManagement() {
        return definitionManagement;
    }

    public void setDefinitionManagement(String definitionManagement) {
        this.definitionManagement = definitionManagement;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    private Collection<CompetenceManagement> competences;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "niveauManagement")
    @OrderBy("ordre")
    public Collection<CompetenceManagement> getCompetences() {
        return competences;
    }

    public void setCompetences(Collection<CompetenceManagement> competences) {
        this.competences = competences;
    }
}
