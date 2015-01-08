package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.Spadmn;

public class PositionAdmAgentDto {

	private Integer nomatr;
	private Integer datdeb;
	private Integer datfin;
	private String cdpadm;
	
	public PositionAdmAgentDto(){
	}
	
	public PositionAdmAgentDto(Spadmn spAdmn) {
		this.nomatr = spAdmn.getId().getNomatr();
		this.datdeb = spAdmn.getId().getDatdeb();
		this.datfin = spAdmn.getDatfin();
		this.cdpadm = spAdmn.getPositionAdministrative().getCdpAdm();
	}
	
	public Integer getNomatr() {
		return nomatr;
	}
	public void setNomatr(Integer nomatr) {
		this.nomatr = nomatr;
	}
	public Integer getDatdeb() {
		return datdeb;
	}
	public void setDatdeb(Integer datdeb) {
		this.datdeb = datdeb;
	}
	public Integer getDatfin() {
		return datfin;
	}
	public void setDatfin(Integer datfin) {
		this.datfin = datfin;
	}
	public String getCdpadm() {
		return cdpadm;
	}
	public void setCdpadm(String cdpadm) {
		this.cdpadm = cdpadm;
	}
	
}
