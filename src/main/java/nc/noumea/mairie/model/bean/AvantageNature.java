package nc.noumea.mairie.model.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "AVANTAGE_NATURE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AvantageNature {

	@Id
	@Column(name = "ID_AVANTAGE")
	private Integer idAvantage;

	@Column(name = "NUM_RUBRIQUE", columnDefinition = "numeric")
	private Integer numRubrique;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_AVANTAGE", referencedColumnName = "ID_TYPE_AVANTAGE")
	private TypeAvantage typeAvantage;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_NATURE_AVANTAGE", referencedColumnName = "ID_NATURE_AVANTAGE")
	private NatureAvantage natureAvantage;

	@Column(name = "MONTANT", columnDefinition = "decimal", precision = 5, scale = 2)
	private BigDecimal montant;

	public Integer getIdAvantage() {
		return idAvantage;
	}

	public void setIdAvantage(Integer idAvantage) {
		this.idAvantage = idAvantage;
	}

	public Integer getNumRubrique() {
		return numRubrique;
	}

	public void setNumRubrique(Integer numRubrique) {
		this.numRubrique = numRubrique;
	}

	public TypeAvantage getTypeAvantage() {
		return typeAvantage;
	}

	public void setTypeAvantage(TypeAvantage typeAvantage) {
		this.typeAvantage = typeAvantage;
	}

	public NatureAvantage getNatureAvantage() {
		return natureAvantage;
	}

	public void setNatureAvantage(NatureAvantage natureAvantage) {
		this.natureAvantage = natureAvantage;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}
}
