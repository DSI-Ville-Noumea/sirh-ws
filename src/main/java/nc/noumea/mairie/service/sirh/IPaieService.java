package nc.noumea.mairie.service.sirh;

import java.io.IOException;

import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.IllegalObjectTypeException;
import com.ibm.as400.access.ObjectDoesNotExistException;

import nc.noumea.mairie.ws.dto.ReturnMessageDto;

public interface IPaieService {

	ReturnMessageDto isPaieEnCours() throws AS400SecurityException, ErrorCompletingRequestException,
			IllegalObjectTypeException, InterruptedException, IOException, ObjectDoesNotExistException;
}
