package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Spcong;

public interface ISpcongService {

	public List<Spcong> getHistoriqueCongeAnnee(Long nomatr);

	public List<Spcong> getToutHistoriqueConge(Long nomatr);
}
