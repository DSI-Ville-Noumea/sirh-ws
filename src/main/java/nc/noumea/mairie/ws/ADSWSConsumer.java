package nc.noumea.mairie.ws;

import nc.noumea.mairie.web.dto.NoeudDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ADSWSConsumer extends BaseWsConsumer implements IADSWSConsumer {

	private Logger logger = LoggerFactory.getLogger(ADSWSConsumer.class);

	@Override
	public NoeudDto getCurrentWholeTreeFromRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoeudDto getNoeudByIdService(Integer idService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoeudDto getDirectionPourEAE(Integer idServiceADS) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoeudDto getSection(Integer idServiceADS) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoeudDto getNoeudFromCodeServiceAS400(String servi) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NoeudDto getDirectionByIdService(Integer idServiceADS) {
		// TODO Auto-generated method stub
		return null;
	}

}
