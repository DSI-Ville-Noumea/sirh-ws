package nc.noumea.mairie.mdf.service;

import java.util.List;

import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;
import nc.noumea.mairie.mdf.dto.AlimenteBordereauBean;

public interface IDataConsistencyRules {
	
	AlimenteBordereauBean alimenteDatas(FdsMutEnt enTete, List<FdsMutDet> details, FdsMutTot total);
	
	void verifyConsistency(AlimenteBordereauBean bean);
	
	void verifyInputDatas(FdsMutEnt entete, List<FdsMutDet> details, FdsMutTot total);
}
