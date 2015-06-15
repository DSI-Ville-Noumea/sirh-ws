package nc.noumea.mairie.model.bean.sirh;


public enum DroitsElementEnum {

	ECR_ORG_VISU(86, "ECR-ORG-VISU");

	private int idElement;
	private String libElement;

	DroitsElementEnum(int _value, String _lib) {
		idElement = _value;
		libElement = _lib;
	}

	public int getIdElement() {
		return idElement;
	}

	public void setIdElement(int idElement) {
		this.idElement = idElement;
	}

	public String getLibElement() {
		return libElement;
	}

	public void setLibElement(String libElement) {
		this.libElement = libElement;
	}

	public static DroitsElementEnum getDroitsElementEnum(Integer idElement) {

		if (idElement == null)
			return null;

		switch (idElement) {
			case 86:
				return ECR_ORG_VISU;
			default:
				return null;
		}
	}
}
