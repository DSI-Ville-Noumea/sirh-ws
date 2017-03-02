package nc.noumea.mairie.web.dto;

public class RefTypeAbsenceDto {

	private Integer						idRefTypeAbsence;
	private String						libelle;
	private RefTypeSaisiCongeAnnuelDto	typeSaisiCongeAnnuelDto;

	public RefTypeAbsenceDto() {
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Integer getIdRefTypeAbsence() {
		return idRefTypeAbsence;
	}

	public void setIdRefTypeAbsence(Integer idRefTypeAbsence) {
		this.idRefTypeAbsence = idRefTypeAbsence;
	}

	public RefTypeSaisiCongeAnnuelDto getTypeSaisiCongeAnnuelDto() {
		return typeSaisiCongeAnnuelDto;
	}

	public void setTypeSaisiCongeAnnuelDto(RefTypeSaisiCongeAnnuelDto typeSaisiCongeAnnuelDto) {
		this.typeSaisiCongeAnnuelDto = typeSaisiCongeAnnuelDto;
	}

}
