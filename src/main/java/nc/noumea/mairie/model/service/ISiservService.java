package nc.noumea.mairie.model.service;

import nc.noumea.mairie.model.bean.Siserv;

public interface ISiservService {

	public Siserv getDirection(String servi);

	public Siserv getSection(String servi);

	public Siserv getDivision(String servi);

	public Siserv getService(String servi);
}
