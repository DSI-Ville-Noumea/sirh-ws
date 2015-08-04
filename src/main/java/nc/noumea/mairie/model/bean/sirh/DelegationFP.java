package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.DelegationFPPK;

@Entity
@Table(name = "DELEGATION_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class DelegationFP {

	@Id
	private DelegationFPPK delegationFPPK;

	public DelegationFPPK getDelegationFPPK() {
		return delegationFPPK;
	}

	public void setDelegationFPPK(DelegationFPPK delegationFPPK) {
		this.delegationFPPK = delegationFPPK;
	}
}
