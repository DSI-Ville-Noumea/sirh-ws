package nc.noumea.mairie.service.sirh;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Contrat;
import nc.noumea.mairie.web.dto.ContratDto;

public interface IContratService {

	public Contrat getContratBetweenDate(Integer idAgent, Date dateRecherche);

	public Integer getNbJoursPeriodeEssai(Date dateDebutContrat, Date dateFinPeriodeEssai);

	public Contrat getContratById(Integer idContrat);

	boolean isPeriodeEssai(Integer idAgent, Date dateRecherche);

	byte[] getHistoContratForTiarhe(boolean isFonctionnaire) throws IOException, ParseException;
	
	List<ContratDto> getAllContratsByAgentForTiarhe(Integer idAgent);
}
