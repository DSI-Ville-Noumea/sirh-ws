package nc.noumea.mairie.web.dto;

import java.util.Date;

public class ParcoursProDto {

	private Date dateDebut;
	private Date dateFin;
	private String service;
	private String direction;

	public ParcoursProDto() {
		super();
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

	@Override
	public boolean equals(Object obj) {
		return this.dateDebut.equals(((ParcoursProDto) obj).getDateDebut())
				&& this.dateFin.equals(((ParcoursProDto) obj).getDateFin());
	}

}
