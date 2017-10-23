package nc.noumea.mairie.mdf.domain;

/**
 * This list has been created to count agents considered as 'active', for the 'Mutuelle des fonctionnaires'.
 * The values of this list is present in the file .xlsx available on this redmine : 
 * 
 * https://redmine.ville-noumea.nc/issues/39378
 * Updated by this redmine : https://redmine.ville-noumea.nc/issues/42537
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
	CONGE_PARENTAL_EDUCATION("46"),
	CONGE_CREATION_ENTREPRISE("48"),
	CONGE_SABBATIQUE("49"),
	CONGE_AFFAIRE_PERSONNEL("50"),
	DISPONIBILITE_SANS_SALAIRE_CHARGES_FAMILIALES("52"),
	DISPONIBILITE_SANS_SALAIRE("53"),
	DETACHE_VILLE("54"),
	SOUS_LES_DRAPEAUX("55"),
	MISE_A_DISPO_NLLE_CALEDONIE("57"),
	AFFECTATION("58"),
	MISE_A_DISPOSITION_NON_REMUNEREE("67"),
	CONGE_SANS_SOLDE_LONGUE_DUREE("81"),
	CONGE_SANS_SOLDE_COURTE_DUREE("82");
	
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
