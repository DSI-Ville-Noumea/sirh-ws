package nc.noumea.mairie.model.bean.sirh;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.pk.sirh.RegIndemFPPK;

@Entity
@Table(name = "REG_INDEMN_FP")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class RegIndemFP {

	@Id
	private RegIndemFPPK regIndemFPPK;
}
