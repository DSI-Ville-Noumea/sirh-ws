package nc.noumea.mairie.model.repository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;
import nc.noumea.mairie.web.dto.CarriereDto;

public interface ISpcarrRepository {

	List<Integer> getListeCarriereActiveAvecPAAffecte();

	List<Integer> getListeCarriereActiveAvecPA();

	Spcarr getCarriereFonctionnaireAncienne(Integer noMatr);

	Spcarr getCarriereActive(Integer noMatr);

	List<Spcarr> listerCarriereAvecGradeEtStatut(Integer nomatr, String cdgrad, Integer codeCategorie);

	SpcarrWithoutSpgradn getCarriereActiveWithoutGrad(Integer noMatr);

	SpcarrWithoutSpgradn getCarriereFonctionnaireAncienneGrad(Integer noMatr);

	List<Integer> getListeAgentsPourAlimAutoCongesAnnuels(Date datdeb, Date datfin);
	
	Integer getListeAgentsActifsPourGenerationBordereauMDF(Date dateDebut, Date dateFin);
	
	List<CarriereDto> getAllCarrieresByAgentForTiarhe(Integer idAgent) throws ParseException;
}
