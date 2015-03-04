package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.MotifAvct;

public interface IAvancementRepository {

	AvancementFonctionnaire getAvancement(Integer idAgent,
			Integer anneeAvancement, boolean isFonctionnaire);

	AvancementDetache getAvancementDetache(Integer idAgent,
			Integer anneeAvancement);

	MotifAvct getMotifAvct(Integer idMotifAvct);

	Date getDateAvancementsMinimaleAncienne(Integer idAgent);
}
