package nc.noumea.mairie.mdf.repository;

import java.util.List;

import nc.noumea.mairie.mdf.domain.StatusJob;

public interface IStatusJobRepository {
	
	List<StatusJob> getAllStatusByDateForVDN(String date);
}
