package nc.noumea.mairie.service;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.web.dto.CarriereDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpCarrService implements ISpCarrService {

	@Autowired
	private IMairieRepository mairieRepository;
	
	@Override
	public CarriereDto getCarriereFonctionnaireAncienne(Integer noMatr) {
		
		CarriereDto dto = null;
		Spcarr carr = mairieRepository.getCarriereFonctionnaireAncienne(noMatr);
		
		if(null != carr)
				dto = new CarriereDto(carr);
			
		return dto;
	}
	
	@Override
	public CarriereDto getCarriereActive(Integer noMatr) {
		
		CarriereDto dto = null;
		Spcarr carr = mairieRepository.getCarriereActive(noMatr);
		
		if(null != carr)
				dto = new CarriereDto(carr);
			
		return dto;
	}

}
