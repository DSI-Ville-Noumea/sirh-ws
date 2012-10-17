package nc.noumea.mairie.model.service.eae;

import nc.noumea.mairie.model.bean.eae.EaeFichePoste;

public interface IEaeFichePosteService {

	/**
	 * Sets the service of an EaeFichePoste if existing in SIRH SISERV
	 * 
	 * @param eaeFichePoste
	 * @throws EaeFichePosteServiceException
	 */
	void setService(EaeFichePoste eaeFichePoste, String codeService)
			throws EaeFichePosteServiceException;
}
