package nc.noumea.mairie.service;

import java.io.IOException;
import java.net.MalformedURLException;

import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import com.lowagie.text.DocumentException;

public interface IReportingService {

	byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception;

	byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year, boolean isDetache) throws Exception;

	byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste) throws Exception;

	byte[] getNoteServiceSIRHReportAsByteArray(int idAffectation, String typeNoteService) throws Exception;

	byte[] getConvocationSIRHReportAsByteArray(String csvIdSuiviMedical, String typePopulation) throws Exception;

	byte[] getAccompagnementSIRHReportAsByteArray(String csvIdSuiviMedical, String typePopulation) throws Exception;

	byte[] getContratSIRHReportAsByteArray(Integer idAgent, Integer idContrat) throws Exception;

	byte[] getTableauAvancementPDF(CommissionAvancementDto dto) throws DocumentException, MalformedURLException, IOException;
}
