package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "REGIME_INDEMNITAIRE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class RegimeIndemnitaire {

	@Id
	@Column(name = "ID_REG_INDEMN")
	private Integer idRegimeIndemnitaire;

	@OneToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_TYPE_REG_INDEMN", referencedColumnName = "ID_TYPE_REG_INDEMN")
	private TypeRegimeIndemnitaire typeRegimeIndemnitaire;

	@Column(name = "NUM_RUBRIQUE", columnDefinition = "numeric")
	private Integer numRubrique;

	@Column(name = "FORFAIT", columnDefinition = "decimal")
	private Float forfait;

	@Column(name = "NOMBRE_POINTS", columnDefinition = "integer")
	private Integer nombrePoint;

	public Integer getIdRegimeIndemnitaire() {
		return idRegimeIndemnitaire;
	}

	public void setIdRegimeIndemnitaire(Integer idRegimeIndemnitaire) {
		this.idRegimeIndemnitaire = idRegimeIndemnitaire;
	}

	public TypeRegimeIndemnitaire getTypeRegimeIndemnitaire() {
		return typeRegimeIndemnitaire;
	}

	public void setTypeRegimeIndemnitaire(TypeRegimeIndemnitaire typeRegimeIndemnitaire) {
		this.typeRegimeIndemnitaire = typeRegimeIndemnitaire;
	}

	public Integer getNumRubrique() {
		return numRubrique;
	}

	public void setNumRubrique(Integer numRubrique) {
		this.numRubrique = numRubrique;
	}

	public Float getForfait() {
		return forfait;
	}

	public void setForfait(Float forfait) {
		this.forfait = forfait;
	}

	public Integer getNombrePoint() {
		return nombrePoint;
	}

	public void setNombrePoint(Integer nombrePoint) {
		this.nombrePoint = nombrePoint;
	}
}
