package nc.noumea.mairie.service;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.text.DocumentException;

import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.bean.sirh.VisiteMedicale;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

public interface IReportingService {

	byte[] getFichePosteReportAsByteArray(int idFichePoste,int version) throws Exception;

	byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year, boolean isDetache) throws Exception;

	byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste, int version) throws Exception;

	byte[] getNoteServiceSIRHReportAsByteArray(int idAffectation, String typeNoteService) throws Exception;

	byte[] getContratSIRHReportAsByteArray(Integer idAgent, Integer idContrat) throws Exception;

	byte[] getTableauAvancementPDF(CommissionAvancementDto dto) throws DocumentException, MalformedURLException, IOException;

	byte[] getCertificatAptitudePDF(VisiteMedicale vm, EntiteDto direction, EntiteDto service, TitrePoste titreFichePoste) throws DocumentException;
}
