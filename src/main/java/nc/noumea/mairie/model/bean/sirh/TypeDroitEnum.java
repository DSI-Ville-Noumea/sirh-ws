package nc.noumea.mairie.model.bean.sirh;

public enum TypeDroitEnum {

	CONSULTATION(1),
	EDITION(2);
	
	private Integer idTypeDroit;
	
	private TypeDroitEnum(Integer idTypeDroit) {
		this.idTypeDroit = idTypeDroit;
	}
	
	public Integer getIdTypeDroit() {
		return this.idTypeDroit;
	}
	
	public String toString() {
		return String.format("%s : %s", idTypeDroit, this.name());
	}
}
