package nc.noumea.mairie.model.repository;

import java.util.List;

import nc.noumea.mairie.model.bean.Spcarr;

public interface ISpcarrRepository {

	List<Integer> getListeCarriereActiveAvecPAAffecte();

	List<Integer> getListeCarriereActiveAvecPA();

	Spcarr getCarriereFonctionnaireAncienne(Integer noMatr);

	Spcarr getCarriereActive(Integer noMatr);
}
