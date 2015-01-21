package nc.noumea.mairie.service.sirh;

import java.io.IOException;

import nc.noumea.mairie.ws.dto.ReturnMessageDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.CharacterDataArea;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.IllegalObjectTypeException;
import com.ibm.as400.access.ObjectDoesNotExistException;
import com.ibm.as400.access.QSYSObjectPathName;

@Service
public class PaieService implements IPaieService {

	@Autowired
	@Qualifier("dtaaraSchema")
	private String dtaaraSchema;

	@Autowired
	@Qualifier("dtaaraName")
	private String dtaaraName;

	@Autowired
	@Qualifier("hostSgbdPaie")
	private String hostSgbdPaie;

	@Autowired
	@Qualifier("hostSgbdLogin")
	private String hostSgbdLogin;

	@Autowired
	@Qualifier("hostSgbdPwd")
	private String hostSgbdPwd;

	@Override
	public ReturnMessageDto isPaieEnCours() throws AS400SecurityException, ErrorCompletingRequestException,
			IllegalObjectTypeException, InterruptedException, IOException, ObjectDoesNotExistException {
		ReturnMessageDto result = new ReturnMessageDto();

		QSYSObjectPathName CALC_PATH = new QSYSObjectPathName(dtaaraSchema, dtaaraName, "DTAARA");
		CharacterDataArea DTAARA_CALC = new CharacterDataArea(new AS400(hostSgbdPaie, hostSgbdLogin, hostSgbdPwd),
				CALC_PATH.getPath());

		String percou = DTAARA_CALC.read().toString();
		if (!percou.trim().equals("")) {
			result.getErrors()
					.add("Vous ne pouvez annuler cette demande car un calcul de salaire est en cours. Merci de réessayer ultérieurement.");
		}
		return result;
	}
}
