package nc.noumea.mairie.tools;

import java.util.ArrayList;
import java.util.List;

public class ServiceTreeNode {

	private String service;
	private String sigle;
	private String sigleParent;
	private List<ServiceTreeNode> servicesEnfant;
	private ServiceTreeNode serviceParent;
	
	
	public ServiceTreeNode() {
		servicesEnfant = new ArrayList<ServiceTreeNode>();
	}


	public String getSigleParent() {
		return sigleParent;
	}

	public void setSigleParent(String sigleParent) {
		this.sigleParent = sigleParent;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getSigle() {
		return sigle;
	}

	public void setSigle(String sigle) {
		this.sigle = sigle;
	}

	public List<ServiceTreeNode> getServicesEnfant() {
		return servicesEnfant;
	}

	public void setServicesEnfant(List<ServiceTreeNode> servicesEnfant) {
		this.servicesEnfant = servicesEnfant;
	}

	public ServiceTreeNode getServiceParent() {
		return serviceParent;
	}

	public void setServiceParent(ServiceTreeNode serviceParent) {
		this.serviceParent = serviceParent;
	}

}
