package nc.noumea.mairie.service;

import java.util.ArrayList;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.web.dto.CarriereDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpCarrService implements ISpCarrService {

	@Autowired
	private ISpcarrRepository spcarrRepository;

	@Override
	public CarriereDto getCarriereFonctionnaireAncienne(Integer noMatr) {

		CarriereDto dto = null;
		try {
			Spcarr carr = spcarrRepository.getCarriereFonctionnaireAncienne(noMatr);

			if (null != carr)
				dto = new CarriereDto(carr);
		} catch (Exception e) {
			// le lien n'a pas été fait avec le grade
			// #11956
			SpcarrWithoutSpgradn carrWithoutGrade = spcarrRepository.getCarriereFonctionnaireAncienneGrad(noMatr);

			if (null != carrWithoutGrade)
				dto = new CarriereDto(carrWithoutGrade);
		}

		return dto;
	}

	@Override
	public CarriereDto getCarriereActive(Integer noMatr) {

		CarriereDto dto = null;
		try {
			Spcarr carr = spcarrRepository.getCarriereActive(noMatr);

			if (null != carr)
				dto = new CarriereDto(carr);
		} catch (Exception e) {
			// le lien n'a pas été fait avec le grade
			// #11956
			SpcarrWithoutSpgradn carrWithoutGrade = spcarrRepository.getCarriereActiveWithoutGrad(noMatr);

			if (null != carrWithoutGrade)
				dto = new CarriereDto(carrWithoutGrade);
		}

		return dto;
	}

	@Override
	public CarriereDto getCarriereAvecGrade(Integer noMatr, String codeGrade, Integer categorie) {
		CarriereDto dto = null;
		// redmine #13156
		// on regarde si il y a d'autre carrieres avec le meme grade
		// si oui on prend la carriere plus lointaine
		ArrayList<Spcarr> listeCarrMemeGrade = (ArrayList<Spcarr>) spcarrRepository.listerCarriereAvecGradeEtStatut(
				noMatr, codeGrade, categorie);
		if (listeCarrMemeGrade != null && listeCarrMemeGrade.size() > 0) {
			dto = new CarriereDto(listeCarrMemeGrade.get(0));
		}

		return dto;
	}

}
