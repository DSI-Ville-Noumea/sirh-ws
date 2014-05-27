package nc.noumea.mairie.service;

public interface IReportingService {

	byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception;

	byte[] getTableauAvancementsReportAsByteArray(int idCap, int idCadreEmploi, boolean avisEAE) throws Exception;

	byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year, boolean isDetache)
			throws Exception;

	byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste) throws Exception;

	byte[] getNoteServiceSIRHReportAsByteArray(int idAffectation, String typeNoteService) throws Exception;
}
