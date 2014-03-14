package nc.noumea.mairie.web.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.noumea.mairie.model.bean.Spmtsr;

public class ParcoursProDto {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	
	private Date dateDebut;
	private Date dateFin;
	private String service;
	private String direction;
	
	public ParcoursProDto(Spmtsr spMtsr) {
		try {
			this.dateDebut = sdf.parse(spMtsr.getDatdeb().toString());
		} catch (ParseException e) {
			this.dateDebut = null;
		}
		try {
			this.dateFin = sdf.parse(spMtsr.getDatfin().toString());
		} catch (ParseException e) {
			this.dateDebut = null;
		}
	}
	
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	
}
