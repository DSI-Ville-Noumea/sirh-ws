package nc.noumea.mairie.model.repository;

import nc.noumea.mairie.model.bean.Spadmn;

public interface ISpadmnRepository {

	Spadmn chercherPositionAdmAgentAncienne(Integer noMatr);

	Spadmn chercherPositionAdmAgentEnCours(Integer noMatr);
}
