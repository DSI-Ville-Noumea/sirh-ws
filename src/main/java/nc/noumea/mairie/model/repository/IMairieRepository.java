package nc.noumea.mairie.model.repository;

import java.util.List;

import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spmtsr;

public interface IMairieRepository {

	List<Spmtsr> getListSpmtsr(Integer noMatricule);

	List<Spbhor> getListSpbhor();

	Spbhor getSpbhorById(Integer idSpbhor);
}
