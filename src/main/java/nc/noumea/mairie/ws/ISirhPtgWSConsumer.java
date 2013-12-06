package nc.noumea.mairie.ws;

import nc.noumea.mairie.ws.dto.RefPrimeDto;

public interface ISirhPtgWSConsumer {

	RefPrimeDto getPrime(Integer noRubr);
}
