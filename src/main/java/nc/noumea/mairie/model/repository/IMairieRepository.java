package nc.noumea.mairie.model.repository;

import java.util.List;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spmtsr;

public interface IMairieRepository {

	List<Integer> getListeCarriereActiveAvecPAAffecte();

	List<Integer> getListeCarriereActiveAvecPA();

	Spadmn chercherPositionAdmAgentAncienne(Integer noMatr);

	Spadmn chercherPositionAdmAgentEnCours(Integer noMatr);

	List<Spmtsr> getListSpmtsr(Integer noMatricule);

	Spcarr getCarriereFonctionnaireAncienne(Integer noMatr);

	Spcarr getCarriereActive(Integer noMatr);
}
