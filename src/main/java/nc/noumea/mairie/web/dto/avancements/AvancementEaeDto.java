package nc.noumea.mairie.web.dto.avancements;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.AvancementDetache;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.web.dto.GradeDto;

public class AvancementEaeDto {

	private Integer idAvct;
	private String etat;

	private Date dateAvctMoy;
	private GradeDto grade;
	
	public AvancementEaeDto(AvancementFonctionnaire avct){
		this.idAvct = avct.getIdAvct();
		this.etat = avct.getEtat();
		this.dateAvctMoy = avct.getDateAvctMoy();
		if(null != avct.getGradeNouveau()) {
			this.grade = new GradeDto(avct.getGradeNouveau());
		}
	}
	
	public AvancementEaeDto(AvancementDetache avct){
		this.idAvct = avct.getIdAvct();
		this.etat = avct.getEtat();
		this.dateAvctMoy = avct.getDateAvctMoy();
		this.grade = new GradeDto(avct.getGradeNouveau());
	}
	
	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
	public Integer getIdAvct() {
		return idAvct;
	}

	public void setIdAvct(Integer idAvct) {
		this.idAvct = idAvct;
	}

	public Date getDateAvctMoy() {
		return dateAvctMoy;
	}

	public void setDateAvctMoy(Date dateAvctMoy) {
		this.dateAvctMoy = dateAvctMoy;
	}

	public GradeDto getGrade() {
		return grade;
	}

	public void setGrade(GradeDto grade) {
		this.grade = grade;
	}
	
}
