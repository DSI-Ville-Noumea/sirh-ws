package nc.noumea.mairie.model.bean.sirh;

public enum DroitsGroupeEnum {

	GROUPE_SIRH(1);

	private int idGroupe;

	DroitsGroupeEnum(int _value) {
		idGroupe = _value;
	}

	public static DroitsGroupeEnum getDroitsGroupeEnum(Integer idGroupe) {

		if (idGroupe == null)
			return null;

		switch (idGroupe) {
			case 1:
				return GROUPE_SIRH;
			default:
				return null;
		}
	}

	public int getIdGroupe() {
		return idGroupe;
	}

	public void setIdGroupe(int idGroupe) {
		this.idGroupe = idGroupe;
	}
}
