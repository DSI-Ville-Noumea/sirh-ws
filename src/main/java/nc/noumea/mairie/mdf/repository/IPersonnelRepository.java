package nc.noumea.mairie.mdf.repository;

import java.util.List;

import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutDetAdm;
import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutEntAdm;
import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutTotAdm;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutDetPers;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutEntPers;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutTotPers;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;

public interface IPersonnelRepository {
	
	FdsMutEnt getEnteteVdn();
	FdsMutTot getTotalVdn();
	List<FdsMutDet> getAllDetailsVdn();
	
	FdsMutEntPers getEntetePers();
	FdsMutTotPers getTotalPers();
	List<FdsMutDetPers> getAllDetailsPers();
	
	FdsMutEntAdm getEnteteAdm();
	FdsMutTotAdm getTotalAdm();
	List<FdsMutDetAdm> getAllDetailsAdm();
}
