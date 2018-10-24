package nc.noumea.mairie.web.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;

public class CarriereDto {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	private Date dateDebut;
	private Date dateFin;
	private Integer noMatr;
	private Integer codeCategorie;
	private String libelleCategorie;
	private GradeDto grade;
	private String INM;
	private String IBAN;
	private String typeContrat;
	private String idTiarhe;

	public CarriereDto() {
	}

	public CarriereDto(Spcarr carr) {
		try {
			this.dateDebut = sdf.parse(carr.getId().getDatdeb().toString());
		} catch (ParseException e) {
			this.dateDebut = null;
		}
		this.noMatr = carr.getId().getNomatr();
		if (null != carr.getCategorie()) {
			this.codeCategorie = carr.getCategorie().getCodeCategorie();
			this.libelleCategorie = carr.getCategorie().getLibelleCategorie();
		}

		if (null != carr.getGrade()) {
			grade = new GradeDto(carr.getGrade());
		}
	}

	public CarriereDto(SpcarrWithoutSpgradn carrWithoutGrade) {
		try {
			this.dateDebut = sdf.parse(carrWithoutGrade.getId().getDatdeb().toString());
		} catch (ParseException e) {
			this.dateDebut = null;
		}
		this.noMatr = carrWithoutGrade.getId().getNomatr();
		if (null != carrWithoutGrade.getCategorie()) {
			this.codeCategorie = carrWithoutGrade.getCategorie().getCodeCategorie();
			this.libelleCategorie = carrWithoutGrade.getCategorie().getLibelleCategorie();
		}
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Integer getNoMatr() {
		return noMatr;
	}

	public void setNoMatr(Integer noMatr) {
		this.noMatr = noMatr;
	}

	public Integer getCodeCategorie() {
		return codeCategorie;
	}

	public void setCodeCategorie(Integer codeCategorie) {
		this.codeCategorie = codeCategorie;
	}

	public String getLibelleCategorie() {
		return libelleCategorie;
	}

	public void setLibelleCategorie(String libelleCategorie) {
		this.libelleCategorie = libelleCategorie;
	}

	public GradeDto getGrade() {
		return grade;
	}

	public void setGrade(GradeDto grade) {
		this.grade = grade;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getINM() {
		return INM;
	}

	public void setINM(String iNM) {
		INM = iNM;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public String getIdTiarhe() {
		return idTiarhe;
	}

	public void setIdTiarhe(String idTiarhe) {
		this.idTiarhe = idTiarhe;
	}

}
