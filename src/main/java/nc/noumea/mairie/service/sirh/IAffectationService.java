package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.ws.dto.EasyVistaDto;

public interface IAffectationService {

	public Affectation getAffectationById(Integer idAffectation);

	public Affectation getAffectationActiveByIdAgent(Integer idAgent);

	public List<Integer> getListChefsService();

	public EasyVistaDto getChefServiceAgent(Affectation aff, EasyVistaDto result);
}
