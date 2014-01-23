package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import flexjson.JSONSerializer;

@Entity
@Table(name = "SPSOLD")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class SpSold {

	@Id
	@Column(name = "NOMATR", columnDefinition = "numeric")
	private Integer nomatr;

	@NotNull
	@Column(name = "SOLDE1", columnDefinition = "decimal")
	private Double soldeAnneeEnCours;

	@NotNull
	@Column(name = "SOLDE2", columnDefinition = "decimal")
	private Double soldeAnneePrec;

	public static JSONSerializer getSerializerForAgentSoldeConge() {

		JSONSerializer serializer = new JSONSerializer().include("soldeAnneeEnCours").include("soldeAnneePrec")
				.exclude("*");

		return serializer;
	}

	public Integer getNomatr() {
		return nomatr;
	}

	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}

	public Double getSoldeAnneeEnCours() {
		return soldeAnneeEnCours;
	}

	public void setSoldeAnneeEnCours(Double soldeAnneeEnCours) {
		this.soldeAnneeEnCours = soldeAnneeEnCours;
	}

	public Double getSoldeAnneePrec() {
		return soldeAnneePrec;
	}

	public void setSoldeAnneePrec(Double soldeAnneePrec) {
		this.soldeAnneePrec = soldeAnneePrec;
	}
}
