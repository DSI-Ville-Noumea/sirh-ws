package nc.noumea.mairie.mdf.domain;

/**
 * This list has been created to count agents considered as 'active', for the 'Mutuelle des fonctionnaires'.
 * The values of this list is present in the file .xlsx available on this redmine : 
 * 
 * https://redmine.ville-noumea.nc/issues/39378
 * 
 * Created on the 21st Aug 2017
 * 
 * @author teo
 */
public enum InactivePAEnum {
	CESSATION_ACTIVITE("CA"), 
	DECES("DC"),
	DEMISSION("DE"),
	FIN_CONTRAT("FC"),
	FIN_CONTRAT_INTERMITANT("FI"),
	LICENCIEMENT("LI"),
	REFORME("RF"),
	RETRAITE("RT"),
	REVOCATION("RV"),
	SUSPENSION_CONTRAT("SC"),
	DISPONIBILITE_SANS_SALAIRE_CHARGES_FAMILIALES("52"),
	DISPONIBILITE_SANS_SALAIRE("53"),
	DETACHE_VILLE("54"),
	SOUS_LES_DRAPEAUX("55"),
	MISE_A_DISPO_NLLE_CALEDONIE("57"),
	AFFECTATION("58");
	
	private String code;

	private InactivePAEnum(String _code) {
		code = _code;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}
}
