package nc.noumea.mairie.service.sirh;

import java.util.Date;

import nc.noumea.mairie.model.bean.sirh.Contrat;

public interface IContratService {

	public Contrat getContratBetweenDate(Integer idAgent, Date dateRecherche);

	public Integer getNbJoursPeriodeEssai(Date dateDebutContrat, Date dateFinPeriodeEssai);

	public Contrat getContratById(Integer idContrat);

	boolean isPeriodeEssai(Integer idAgent, Date dateRecherche);
}
