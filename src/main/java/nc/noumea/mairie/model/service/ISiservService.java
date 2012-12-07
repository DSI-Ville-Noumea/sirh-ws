package nc.noumea.mairie.model.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.tools.ServiceTreeNode;

public interface ISiservService {

	public Siserv getDirection(String servi);

	public Siserv getSection(String servi);

	public Siserv getDivision(String servi);

	public Siserv getService(String servi);

	public List<String> getListServiceAgent(Integer idAgent);
	
	public List<String> getListServiceAgent(Integer idAgent, String sigleServiceParent);
	
	public ServiceTreeNode getAgentServiceTree(Integer idAgent);

	public List<Siserv> getListServiceActif();

	public Siserv getServiceAgent(Integer idAgent);

	public Siserv getServiceBySigle(String sigleService);
}
