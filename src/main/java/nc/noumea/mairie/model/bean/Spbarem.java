package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "SPBAREM")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Spbarem {

	@Id
	@Column(name = "IBAN", columnDefinition = "char")
	private String iban;

	@NotNull
	@Column(name = "INA", columnDefinition = "numeric")
	private Integer ina;

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Integer getIna() {
		return ina;
	}

	public void setIna(Integer ina) {
		this.ina = ina;
	}
}
