package nc.noumea.mairie.service.sirh;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.web.dto.AffectationDto;

public interface IAffectationService {

	public Affectation getAffectationById(Integer idAffectation);

	public List<Affectation> getListAffectationByIdFichePoste(Integer idFichePoste);

	public Affectation getAffectationByIdFichePoste(Integer idFichePoste);

	public List<Affectation> getListAffectationActiveByIdFichePoste(Integer idFichePoste);

	List<AffectationDto> getListeAffectationDtosForListAgentByPeriode(
			List<Integer> listIdsAgent, Date dateDebut, Date dateFin);
}
