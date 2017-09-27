package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "FICHE_METIER")
public class FicheMetier {
    private int idFicheMetier;
    private String refMairie;
    private String nomMetier;
    private String definitionMetier;
    private String cadreStatutaire;

    @Id
    @Column(name = "ID_FICHE_METIER", nullable = false)
    public int getIdFicheMetier() {
        return idFicheMetier;
    }

    public void setIdFicheMetier(int idFicheMetier) {
        this.idFicheMetier = idFicheMetier;
    }

    @Basic
    @Column(name = "REF_MAIRIE", nullable = false, length = 10)
    public String getRefMairie() {
        return refMairie;
    }

    public void setRefMairie(String refMairie) {
        this.refMairie = refMairie;
    }

    @Basic
    @Column(name = "NOM_METIER", nullable = false, length = 255)
    public String getNomMetier() {
        return nomMetier;
    }

    public void setNomMetier(String nomMetier) {
        this.nomMetier = nomMetier;
    }

    @Basic
    @Column(name = "DEFINITION_METIER", nullable = false, columnDefinition = "clob")
    public String getDefinitionMetier() {
        return definitionMetier;
    }

    public void setDefinitionMetier(String definitionMetier) {
        this.definitionMetier = definitionMetier;
    }

    @Column(name = "CADRE_STATUTAIRE", nullable = true, columnDefinition = "clob")
    public String getCadreStatutaire() {
        return cadreStatutaire;
    }

    public void setCadreStatutaire(String cadreStatutaire) {
        this.cadreStatutaire = cadreStatutaire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FicheMetier that = (FicheMetier) o;

        if (idFicheMetier != that.idFicheMetier) return false;
        if (refMairie != null ? !refMairie.equals(that.refMairie) : that.refMairie != null) return false;
        if (nomMetier != null ? !nomMetier.equals(that.nomMetier) : that.nomMetier != null) return false;
        if (definitionMetier != null ? !definitionMetier.equals(that.definitionMetier) : that.definitionMetier != null)
            return false;
        if (cadreStatutaire != null ? !cadreStatutaire.equals(that.cadreStatutaire) : that.cadreStatutaire != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFicheMetier;
        result = 31 * result + (refMairie != null ? refMairie.hashCode() : 0);
        result = 31 * result + (nomMetier != null ? nomMetier.hashCode() : 0);
        result = 31 * result + (definitionMetier != null ? definitionMetier.hashCode() : 0);
        result = 31 * result + (cadreStatutaire != null ? cadreStatutaire.hashCode() : 0);
        return result;
    }
}
