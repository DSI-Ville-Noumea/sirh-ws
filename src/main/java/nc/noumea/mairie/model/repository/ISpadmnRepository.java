package nc.noumea.mairie.model.repository;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Spadmn;

public interface ISpadmnRepository {

	Spadmn chercherPositionAdmAgentAncienne(Integer noMatr);

	Spadmn chercherPositionAdmAgentEnCours(Integer noMatr);

	List<Spadmn> chercherListPositionAdmAgentSurPeriodeDonnee(Integer noMatr, Date dateDebut, Date dateFin);

	List<Spadmn> chercherListPositionAdmAgentAncienne(Integer noMatr, Integer dateLimite);
}
