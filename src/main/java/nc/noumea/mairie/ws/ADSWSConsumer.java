package nc.noumea.mairie.ws;

import java.util.ArrayList;
import java.util.List;

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

	@Override
	public List<Integer> getListIdsServiceWithEnfantsOfService(Integer idService) {

		List<Integer> result = new ArrayList<Integer>();

//		NoeudDto noeudDto = adsConsumer.getNoeudWithChildrenByIdService(idService);
//		result.add(noeudDto.getIdService());
//		result.addAll(getListIdsServiceEnfants(noeudDto));

		return result;
	}

	private List<Integer> getListIdsServiceEnfants(NoeudDto noeud) {

		List<Integer> result = new ArrayList<Integer>();

		if (null != noeud && null != noeud.getEnfants()) {
			for (NoeudDto enfant : noeud.getEnfants()) {
				result.add(enfant.getIdService());
				result.addAll(getListIdsServiceEnfants(enfant));
			}
		}
		return result;
	}

	@Override
	public NoeudDto getParentOfNoeudByIdService(int idService) {
		// TODO Auto-generated method stub
		return null;
	}

}
