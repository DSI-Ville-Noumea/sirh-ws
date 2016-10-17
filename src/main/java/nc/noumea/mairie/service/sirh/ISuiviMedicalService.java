package nc.noumea.mairie.service.sirh;

import nc.noumea.mairie.model.bean.sirh.SuiviMedical;
import nc.noumea.mairie.model.bean.sirh.VisiteMedicale;

public interface ISuiviMedicalService {

	public SuiviMedical getSuiviMedicalById(Integer idSuiviMedical);

	public VisiteMedicale getVisiteMedicale(int idVisite);
}
