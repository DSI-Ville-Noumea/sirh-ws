package nc.noumea.mairie.mdf.service;

import java.text.ParseException;
import java.util.List;

import nc.noumea.mairie.mdf.dto.AlimenteBordereauBean;
import nc.noumea.mairie.mdf.dto.DetailDto;
import nc.noumea.mairie.mdf.dto.EnTeteDto;
import nc.noumea.mairie.mdf.dto.TotalDto;

public interface IDataConsistencyRules {
	
	AlimenteBordereauBean alimenteDatas(EnTeteDto entete, List<DetailDto> details, TotalDto total, String entite) throws ParseException;
	
	void verifyConsistency(AlimenteBordereauBean bean);
	
	void verifyInputDatas(EnTeteDto entete, List<DetailDto> details, TotalDto total);
}
