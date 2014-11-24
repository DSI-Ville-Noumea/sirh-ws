package nc.noumea.mairie.model.repository;

import java.util.List;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;

public interface ISpcarrRepository {

	List<Integer> getListeCarriereActiveAvecPAAffecte();

	List<Integer> getListeCarriereActiveAvecPA();

	Spcarr getCarriereFonctionnaireAncienne(Integer noMatr);

	Spcarr getCarriereActive(Integer noMatr);

	List<Spcarr> listerCarriereAvecGradeEtStatut(Integer nomatr, String cdgrad, Integer codeCategorie);

	SpcarrWithoutSpgradn getCarriereActiveWithoutGrad(Integer noMatr);

	SpcarrWithoutSpgradn getCarriereFonctionnaireAncienneGrad(Integer noMatr);
}
