package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.Siguic;

public interface ISiguicService {

	public Siguic getBanque(Integer codeBanque, Integer codeGuichet);
}
