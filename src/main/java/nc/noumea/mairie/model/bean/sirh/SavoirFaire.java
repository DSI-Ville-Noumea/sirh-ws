package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.*;

@Entity
@Table(name = "SAVOIR_FAIRE")
public class SavoirFaire {
    private int idSavoirFaire;
    private String nomSavoirFaire;
    private Integer ordre;
    private ActiviteMetier activiteMetierByIdActiviteMetier;

    @Id
    @Column(name = "ID_SAVOIR_FAIRE", nullable = false)
    public int getIdSavoirFaire() {
        return idSavoirFaire;
    }

    public void setIdSavoirFaire(int idSavoirFaire) {
        this.idSavoirFaire = idSavoirFaire;
    }

    @Basic
    @Column(name = "NOM_SAVOIR_FAIRE", nullable = false, length = 1000)
    public String getNomSavoirFaire() {
        return nomSavoirFaire;
    }

    public void setNomSavoirFaire(String nomSavoirFaire) {
        this.nomSavoirFaire = nomSavoirFaire;
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

        SavoirFaire that = (SavoirFaire) o;

        if (idSavoirFaire != that.idSavoirFaire) return false;
        if (nomSavoirFaire != null ? !nomSavoirFaire.equals(that.nomSavoirFaire) : that.nomSavoirFaire != null)
            return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idSavoirFaire;
        result = 31 * result + (nomSavoirFaire != null ? nomSavoirFaire.hashCode() : 0);
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ACTIVITE_METIER", referencedColumnName = "ID_ACTIVITE_METIER")
    public ActiviteMetier getActiviteMetierByIdActiviteMetier() {
        return activiteMetierByIdActiviteMetier;
    }

    public void setActiviteMetierByIdActiviteMetier(ActiviteMetier activiteMetierByIdActiviteMetier) {
        this.activiteMetierByIdActiviteMetier = activiteMetierByIdActiviteMetier;
    }
}
