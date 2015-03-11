package nc.noumea.mairie.ws.dto;

import java.util.ArrayList;
import java.util.List;

public class EasyVistaDto {

	private Integer nomatrChef;
	private List<String> errors;
	private List<String> infos;

	public EasyVistaDto() {
		errors = new ArrayList<String>();
		infos = new ArrayList<String>();
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getInfos() {
		return infos;
	}

	public void setInfos(List<String> infos) {
		this.infos = infos;
	}

	public Integer getNomatrChef() {
		return nomatrChef;
	}

	public void setNomatrChef(Integer nomatrChef) {
		this.nomatrChef = nomatrChef;
	}
}
