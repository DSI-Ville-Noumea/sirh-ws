package nc.noumea.mairie.mdf.repository;

import java.util.List;

import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;

public interface IPersonnelRepository {
	
	FdsMutEnt getEnteteVdn();
	FdsMutTot getTotalVdn();
	List<FdsMutDet> getAllDetailsVdn();
}
