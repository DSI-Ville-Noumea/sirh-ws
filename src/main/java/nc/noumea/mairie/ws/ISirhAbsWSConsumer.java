package nc.noumea.mairie.ws;

import java.util.List;

import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;


public interface ISirhAbsWSConsumer {

	boolean isUserApprobateur(Integer idAgent);

	boolean isUserOperateur(Integer idAgent);

	boolean isUserViseur(Integer idAgent);
	
	List<RefTypeSaisiCongeAnnuelDto> getListeTypAbsenceCongeAnnuel();
}
