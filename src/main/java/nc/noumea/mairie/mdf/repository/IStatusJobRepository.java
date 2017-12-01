package nc.noumea.mairie.mdf.repository;

import java.util.List;

import nc.noumea.mairie.mdf.domain.StatusJob;

public interface IStatusJobRepository {
	
	List<StatusJob> getAllStatusByDateAndEntite(String date, String entite);
}
