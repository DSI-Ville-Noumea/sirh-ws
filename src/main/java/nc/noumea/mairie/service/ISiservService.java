package nc.noumea.mairie.service;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.tools.ServiceTreeNode;

public interface ISiservService {

	public Siserv getDirection(String servi);
	
	Siserv getDirectionPourEAE(String codeService);

	public Siserv getSection(String servi);

	public Siserv getDivision(String servi);

	public Siserv getService(String servi);

	public List<String> getListServiceAgent(Integer idAgent);
	
	public List<String> getListServiceAgent(Integer idAgent, String sigleServiceParent);
	
	public List<String> getListSubServicesSigles(String servi);
	
	public List<ServiceTreeNode> getListSubServices(String servi);
	
	public ServiceTreeNode getAgentServiceTree(Integer idAgent, Date date);

	public List<Siserv> getListServiceActif();

	public Siserv getServiceAgent(Integer idAgent, Date date);

	public Siserv getServiceBySigle(String sigleService);
	
	public ServiceTreeNode getAgentDirection(Integer idAgent, Date date);

	public void construitArbreServices();
	
}
