package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "P_ACCUEIL_KIOSQUE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AccueilRh {

	@Id
	@Column(name = "ID_ACCUEIL_KIOSQUE")
	private Integer idAccueilKiosque;

	@Column(name = "TEXTE_ACCUEIL_KIOSQUE")
	private String texteAccueilKiosque;

	public Integer getIdAccueilKiosque() {
		return idAccueilKiosque;
	}

	public void setIdAccueilKiosque(Integer idAccueilKiosque) {
		this.idAccueilKiosque = idAccueilKiosque;
	}

	public String getTexteAccueilKiosque() {
		return texteAccueilKiosque;
	}

	public void setTexteAccueilKiosque(String texteAccueilKiosque) {
		this.texteAccueilKiosque = texteAccueilKiosque;
	}
}
