package nc.noumea.mairie.model.service;

public interface IReportingService {

	byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception;
	byte[] getTableauAvancementsReportAsByteArray(int idCap, int idCadreEmploi,boolean avisEAE) throws Exception;
	byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year, boolean isDetache) throws Exception;
}
