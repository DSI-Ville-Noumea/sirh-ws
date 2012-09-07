package nc.noumea.mairie.enums;

public enum EnumTypeCompetence {
	SAVOIR("1", "Savoir"), SAVOIR_FAIRE("2", "Savoir-faire"), COMPORTEMENT("3", "Comportement professionnel");

	/** L'attribut qui contient le code associé à l'enum */
	private final String code;

	/** L'attribut qui contient la valeur associé à l'enum */
	private final String value;

	/** Le constructeur qui associe un code et une valeur à l'enum */
	private EnumTypeCompetence(String code, String value) {
		this.code = code;
		this.value = value;
	}

	/** La méthode accesseur qui renvoit le code de l'enum */
	public String getCode() {
		return this.code;
	}

	/** La méthode accesseur qui renvoit la valeur de l'enum */
	public String getValue() {
		return this.value;
	}

	/** La méthode accesseur qui renvoit la liste des valeurs de l'enum */
	public static String[] getValues() {
		String competences[] = new String[EnumTypeCompetence.values().length];
		int i = 0;
		for (EnumTypeCompetence elt : EnumTypeCompetence.values()) {
			competences[i++] = elt.getValue();
		}
		return competences;
	}
}
