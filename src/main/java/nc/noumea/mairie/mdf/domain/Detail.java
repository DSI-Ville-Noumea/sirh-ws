package nc.noumea.mairie.mdf.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Detail {
	
	@Id
	private FdsMutDetId id;

	public FdsMutDetId getId() {
		return id;
	}

	public void setId(FdsMutDetId id) {
		this.id = id;
	}
	
	
}
