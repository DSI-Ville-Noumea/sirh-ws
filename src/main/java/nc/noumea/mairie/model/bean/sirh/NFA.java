package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "SERVICE_NFA")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class NFA {

	@Id
	@Column(name = "ID_SERVICE_ADS")
	private Integer idServiceAds;

	@Column(name = "NFA")
	private String nfa;

	public String getNfa() {
		return nfa;
	}

	public void setNfa(String nfa) {
		this.nfa = nfa;
	}

	public Integer getIdServiceAds() {
		return idServiceAds;
	}

	public void setIdServiceAds(Integer idServiceAds) {
		this.idServiceAds = idServiceAds;
	}
}
