package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.SIVIET;

public interface ISivietService {
	
	public SIVIET getLieuNaissEtr(Integer codePays, Integer codeCommune);
}
