package nc.noumea.mairie.model.repository.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Droits;

public interface IDroitsRepository {

	List<Droits> getDroitsByElementAndAgent(Integer idElement, String login);
}
