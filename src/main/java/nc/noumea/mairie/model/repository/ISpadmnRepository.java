package nc.noumea.mairie.model.repository;

import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Spadmn;

public interface ISpadmnRepository {

	Spadmn chercherPositionAdmAgentAncienne(Integer noMatr);

	Spadmn chercherPositionAdmAgentEnCours(Integer noMatr);

	List<Spadmn> chercherListPositionAdmAgentSurPeriodeDonnee(Integer noMatr, Date dateDebut, Date dateFin);

	List<Spadmn> chercherListPositionAdmAgentAncienne(Integer noMatr, Integer dateLimite);

	List<Integer> listAgentActiviteAnnuaire();

	PositDesc chercherPositDescByPosit(String position);

	/**
	 * Retourne la liste des nomatr agents en activité sur une periode donnée
	 * Utile à PTG pour la génération du fichier prestataire des titres repas Il
	 * faut une PA et une affectation active sur la periode
	 * 
	 * @return List<Integer> La liste des nomatr en activité sur la période
	 */
	List<Integer> listNomatrEnActiviteSurPeriode(Date dateDebutPeriode, Date dateFinPeriode);
}
