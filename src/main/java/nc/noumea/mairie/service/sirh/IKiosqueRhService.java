package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.AccueilRh;
import nc.noumea.mairie.model.bean.sirh.ReferentRh;

public interface IKiosqueRhService {

	List<ReferentRh> getListeReferentRH();

	List<AccueilRh> getListeAccueilRh();

}
