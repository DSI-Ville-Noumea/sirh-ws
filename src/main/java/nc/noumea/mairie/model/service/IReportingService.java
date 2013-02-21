package nc.noumea.mairie.model.service;

public interface IReportingService {

	byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception;
	byte[] getTableauAvancementsReportAsByteArray(int idCap, int idCadreEmploi) throws Exception;
}
