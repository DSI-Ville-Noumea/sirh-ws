package nc.noumea.mairie.service;

import java.util.Date;

import nc.noumea.mairie.model.bean.Spphre;

public interface ISpphreService {
	
	public Spphre getSpphreForDayAndAgent(Integer idAgent, Date day);
}
