package nc.noumea.mairie.model.bean.sirh;

import nc.noumea.mairie.model.pk.sirh.ActiviteGeneraleFpPK;

import javax.persistence.*;

@Entity
@Table(name = "ACTIVITE_GENERALE_FP")
@IdClass(ActiviteGeneraleFpPK.class)
public class ActiviteGeneraleFp {
    private int idFichePoste;
    private int idActiviteGenerale;
    private Integer ordre;
    private ActiviteGenerale activiteGeneraleByIdActiviteGenerale;

    @Id
    @Column(name = "ID_FICHE_POSTE", nullable = false)
    public int getIdFichePoste() {
        return idFichePoste;
    }

    public void setIdFichePoste(int idFichePoste) {
        this.idFichePoste = idFichePoste;
    }

    @Id
    @Column(name = "ID_ACTIVITE_GENERALE", nullable = false)
    public int getIdActiviteGenerale() {
        return idActiviteGenerale;
    }

    public void setIdActiviteGenerale(int idActiviteGenerale) {
        this.idActiviteGenerale = idActiviteGenerale;
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

        ActiviteGeneraleFp that = (ActiviteGeneraleFp) o;

        if (idFichePoste != that.idFichePoste) return false;
        if (idActiviteGenerale != that.idActiviteGenerale) return false;
        if (ordre != null ? !ordre.equals(that.ordre) : that.ordre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFichePoste;
        result = 31 * result + idActiviteGenerale;
        result = 31 * result + (ordre != null ? ordre.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "ID_ACTIVITE_GENERALE", referencedColumnName = "ID_ACTIVITE_GENERALE", nullable = false, insertable = false, updatable = false)
    public ActiviteGenerale getActiviteGeneraleByIdActiviteGenerale() {
        return activiteGeneraleByIdActiviteGenerale;
    }

    public void setActiviteGeneraleByIdActiviteGenerale(ActiviteGenerale activiteGeneraleByIdActiviteGenerale) {
        this.activiteGeneraleByIdActiviteGenerale = activiteGeneraleByIdActiviteGenerale;
    }
}
