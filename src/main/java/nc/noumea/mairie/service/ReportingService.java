package nc.noumea.mairie.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.Medecin;
import nc.noumea.mairie.model.bean.sirh.Recommandation;
import nc.noumea.mairie.model.bean.sirh.TitrePoste;
import nc.noumea.mairie.model.bean.sirh.VisiteMedicale;
import nc.noumea.mairie.service.ads.AdsService;
import nc.noumea.mairie.service.sirh.FichePosteService;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.avancements.AvancementItemDto;
import nc.noumea.mairie.web.dto.avancements.AvancementsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;
import nc.noumea.mairie.ws.ADSWSConsumer;

@Service
public class ReportingService extends AbstractReporting implements IReportingService {
	
	private Logger logger = LoggerFactory.getLogger(ReportingService.class);
	
	@Autowired
	@Qualifier("reportingBaseUrl")
	private String				reportingBaseUrl;

	@Autowired
	@Qualifier("reportServerPath")
	private String				reportServerPath;

	@Autowired
	private AdsService adsService;

	@Autowired
	private ADSWSConsumer adsWsConsumer;
	
	@Autowired
	private FichePosteService fichePosteService;

	private static final String	REPORT_PAGE		= "frameset";
	private static final String	PARAM_REPORT	= "__report";
	private static final String	PARAM_FORMAT	= "__format";
	private static final String	PARAM_LOCALE	= "__locale";

	@Override
	public byte[] getFichePosteReportAsByteArray(int idFichePoste, int version) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));

		ClientResponse response;
		if (version == 2) {
			response = createAndFireRequest(map, "fichePostev2.rptdesign", "PDF");
		} else {
			response = createAndFireRequest(map, "fichePoste.rptdesign", "PDF");
		}

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getArretesReportAsByteArray(String csvIdAgents, boolean isChangementClasse, int year, boolean isDetache) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("csvIdAgents", csvIdAgents);
		map.put("annee", String.valueOf(year));
		map.put("isDetache", isDetache ? "true" : "false");

		String reportName = isChangementClasse ? "avctChgtClasseArrete.rptdesign" : "avctDiffArrete.rptdesign";

		ClientResponse response = createAndFireRequest(map, reportName, "ODT");

		return readResponseAsByteArray(response, map);
	}

	public ClientResponse createAndFireRequest(Map<String, String> reportParameters, String reportName, String format) {

		Client client = Client.create();

		WebResource webResource = client.resource(reportingBaseUrl + REPORT_PAGE).queryParam(PARAM_REPORT, reportServerPath + reportName)
				.queryParam(PARAM_FORMAT, format).queryParam(PARAM_LOCALE, "FR");

		for (String key : reportParameters.keySet()) {
			webResource = webResource.queryParam(key, reportParameters.get(key));
		}

		ClientResponse response = webResource.get(ClientResponse.class);

		return response;
	}

	public byte[] readResponseAsByteArray(ClientResponse response, Map<String, String> reportParameters) throws Exception {

		if (response.getStatus() != HttpStatus.OK.value()) {
			throw new Exception(String.format("An error occured while querying the reporting server '%s' with ids '%s'. HTTP Status code is : %s.",
					reportingBaseUrl, getListOfParamsFromMap(reportParameters), response.getStatus()));
		}

		byte[] reponseData = null;
		File reportFile = null;

		try {
			reportFile = response.getEntity(File.class);
			reponseData = IOUtils.toByteArray(new FileInputStream(reportFile));
		} catch (Exception e) {
			throw new Exception("An error occured while reading the downloaded report.", e);
		} finally {
			if (reportFile != null && reportFile.exists())
				reportFile.delete();
		}

		return reponseData;
	}

	private String getListOfParamsFromMap(Map<String, String> reportParameters) {

		StringBuilder sb = new StringBuilder();

		for (String key : reportParameters.keySet()) {
			sb.append(String.format("[%s: %s] ", key, reportParameters.get(key)));
		}

		return sb.toString();
	}

	@Override
	public byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste, int version) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));

		ClientResponse response;
		if (version == 2) {
			response = createAndFireRequest(map, "fichePosteSIRHv2.rptdesign", "PDF");
		} else {
			response = createAndFireRequest(map, "fichePosteSIRH.rptdesign", "PDF");
		}

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getNoteServiceSIRHReportAsByteArray(int idAffectation, String typeNoteService) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idAffectation", String.valueOf(idAffectation));
		ClientResponse response = null;
		if (typeNoteService != null) {
			map.put("typeNoteService", typeNoteService);
			response = createAndFireRequest(map, "noteService.rptdesign", "DOCX");
		} else {
			response = createAndFireRequest(map, "noteServiceInterne.rptdesign", "DOCX");
		}

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getContratSIRHReportAsByteArray(Integer idAgent, Integer idContrat) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idAgent", idAgent.toString());
		map.put("idContrat", idContrat.toString());
		ClientResponse response = createAndFireRequest(map, "contrat.rptdesign", "DOCX");

		return readResponseAsByteArray(response, map);
	}

	@Override
	public byte[] getTableauAvancementPDF(CommissionAvancementDto dto) throws DocumentException, MalformedURLException, IOException {
		Document document = new Document(PageSize.A4.rotate());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, baos);
		writer.setPageEvent(new PageStamper());

		// on genere les metadata
		addMetaData(document, "Tableau CAP", "SIRH");

		// on ouvre le document
		document.open();

		if (dto.getCommissionsParCorps().size() > 0) {
			// on ecrit dans le document les avancements diff
			for (CommissionAvancementCorpsDto dtoCorps : dto.getCommissionsParCorps()) {
				writeTabDiff(document, dtoCorps);

				// on fait un saut de page
				document.newPage();
				document.add(new Paragraph(""));
				document.newPage();

			}

			// on fait un saut de page
			document.newPage();
			document.add(new Paragraph(""));
			document.newPage();

			// on ecrit dans le document les changements de classe
			for (CommissionAvancementCorpsDto dtoCorps : dto.getCommissionsParCorps()) {
				writeTabChgtClasse(document, dtoCorps);

				// on fait un saut de page
				document.newPage();
				document.add(new Paragraph(""));
				document.newPage();

			}
		} else {
			genereEnteteDocumentChgtClasse(document, "images/logo_gouv.jpg", true, "Aucune correspondance");
		}

		// on ferme le document
		document.close();

		return baos.toByteArray();

	}

	private void writeTabDiff(Document document, CommissionAvancementCorpsDto dto) throws DocumentException, MalformedURLException, IOException {
		// les données en entête du document
		String titre = "Direction des ressources humaines de la fonction publique de Nouvelle-Calédonie";
		titre += "\nAVANCEMENT DIFFERENCIE - ANNEE " + dto.getAvancementsDifferencies().getAnnee();
		// #35759 : on change le titre
		titre += "\nCadre d'emploi des " + dto.getAvancementsDifferencies().getCadreEmploiLibelle();
		genereEnteteDocumentTabAD(document, "images/logo_gouv.jpg", true, titre);

		// les infos CAP
		genereInfoCAPDiff(document, dto.getAvancementsDifferencies(), true);

		// genere entete tableau
		genereTableauDiff(document, dto.getAvancementsDifferencies());

		// genere texte sous tableau
		genereTexteSousTableauDiff(document);

		// genere bas de page
		genereBasPageTabAD(document, dto.getAvancementsDifferencies());

	}

	private void genereTexteSousTableauDiff(Document document) throws DocumentException {

		PdfPTable table = new PdfPTable(new float[] { 1 });
		table.setWidthPercentage(100f);

		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(
				new CellVo("NB : le tableau doit être renseigné de la manière suivante :", true, 0, null, Element.ALIGN_LEFT, false, fontItalic5));
		listValuesLigne1.add(new CellVo("- en début de tableau les propositions AD minimal dans l'ordre alphabétique", true, 0, null,
				Element.ALIGN_LEFT, false, fontItalic5));
		listValuesLigne1
				.add(new CellVo("- puis les durées moyennes dans l'ordre alphabétique", true, 0, null, Element.ALIGN_LEFT, false, fontItalic5));
		listValuesLigne1
				.add(new CellVo("- enfin les durées maximales dans l'ordre alphabétique.", true, 0, null, Element.ALIGN_LEFT, false, fontItalic5));
		writeLine(table, 3, Element.ALIGN_LEFT, listValuesLigne1, false);

		document.add(table);

	}

	private void genereBasPageTabChgtClasse(Document document, AvancementsDto dto) throws DocumentException {
		// Lignes sous le tableau
		genereSignature(document);

		writeSpacing(document, 3);

		// table globale
		PdfPTable table = writeTableau(document, new float[] { 13, 10, 10 });
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setKeepTogether(true);

		// les petites lignes au dessus des tableaux
		List<CellVo> listValuesPetitesLignesHaut = new ArrayList<CellVo>();
		listValuesPetitesLignesHaut.add(new CellVo("Les employeurs", false, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		listValuesPetitesLignesHaut.add(new CellVo("Les représentants du personnel", false, 2, null, Element.ALIGN_LEFT, false, fontBold8));
		writeLine(table, 3, listValuesPetitesLignesHaut, true);
		List<CellVo> listValuesPetitesLignes = new ArrayList<CellVo>();
		listValuesPetitesLignes.add(new CellVo("", false, 1, null, Element.ALIGN_LEFT, false, fontNormal8));
		listValuesPetitesLignes.add(new CellVo("Titulaires :", false, 1, null, Element.ALIGN_LEFT, false, fontNormal8));
		listValuesPetitesLignes.add(new CellVo("Suppléants :", false, 1, null, Element.ALIGN_LEFT, false, fontNormal8));
		writeLine(table, 3, listValuesPetitesLignes, false);

		// table employeurs
		PdfPTable tableEmployeur = writeTableau(document, new float[] { 10, 7 });
		for (String employeur : dto.getEmployeurs()) {
			List<CellVo> listValuesEmployeur = new ArrayList<CellVo>();
			listValuesEmployeur.add(new CellVo(employeur, false, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
			listValuesEmployeur.add(new CellVo(""));
			writeLine(tableEmployeur, 3, listValuesEmployeur, false);
		}
		PdfPCell cellEmployeur = new PdfPCell();
		cellEmployeur.addElement(tableEmployeur);
		cellEmployeur.setFixedHeight(tableEmployeur.getTotalHeight());
		cellEmployeur.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellEmployeur);

		// table titulaires
		PdfPTable tableTitulaire = writeTableau(document, new float[] { 10, 7 });
		for (String titu : dto.getRepresentantsTitulaires()) {
			List<CellVo> listValuesTitulaire = new ArrayList<CellVo>();
			listValuesTitulaire.add(new CellVo(titu, false, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
			listValuesTitulaire.add(new CellVo(""));
			writeLine(tableTitulaire, 10, listValuesTitulaire, false);
		}
		PdfPCell cellTitulaire = new PdfPCell();
		cellTitulaire.addElement(tableTitulaire);
		cellTitulaire.setFixedHeight(tableTitulaire.getTotalHeight());
		cellTitulaire.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellTitulaire);

		// table suppleants
		PdfPTable tableSuppleant = writeTableau(document, new float[] { 10, 7 });
		for (String supp : dto.getRepresentantsSuppleants()) {
			List<CellVo> listValuesSupp = new ArrayList<CellVo>();
			listValuesSupp.add(new CellVo(supp, false, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
			listValuesSupp.add(new CellVo(""));
			writeLine(tableSuppleant, 10, listValuesSupp, false);

		}
		PdfPCell cellSuppleant = new PdfPCell();
		cellSuppleant.addElement(tableSuppleant);
		cellSuppleant.setFixedHeight(tableSuppleant.getTotalHeight());
		cellSuppleant.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellSuppleant);

		// Cachet et Visa
		PdfPTable tableCachetVisa = writeTableau(document, new float[] { 17 });
		tableCachetVisa.setWidthPercentage(40f);
		tableCachetVisa.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableCachetVisa.addCell(writeCell(3, null, new CellVo("CACHET + VISA EMPLOYEUR : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8), true,
				true, 80f, Element.ALIGN_TOP));
		table.addCell(tableCachetVisa);
		writeLine(table, 0, Arrays.asList(new CellVo("", false, 3, null, Element.ALIGN_LEFT, false, fontNormal8)), false);

		document.add(table);
	}

	private void genereBasPageTabAD(Document document, AvancementsDto dto) throws DocumentException {

		writeSpacing(document, 1);

		// table globale
		PdfPTable table = writeTableau(document, new float[] { 13, 10, 10 });
		table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table.setKeepTogether(true);

		// les petites lignes au dessus des tableaux
		List<CellVo> listValuesPetitesLignesHaut = new ArrayList<CellVo>();
		listValuesPetitesLignesHaut.add(new CellVo("Les employeurs", false, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesPetitesLignesHaut.add(new CellVo("Les représentants du personnel", false, 2, null, Element.ALIGN_LEFT, false, fontBold5));
		writeLine(table, 3, listValuesPetitesLignesHaut, false);
		List<CellVo> listValuesPetitesLignes = new ArrayList<CellVo>();
		listValuesPetitesLignes.add(new CellVo("", false, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		listValuesPetitesLignes.add(new CellVo("Titulaires :", false, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		listValuesPetitesLignes.add(new CellVo("Suppléants :", false, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 3, listValuesPetitesLignes, false);

		// table employeurs
		PdfPTable tableEmployeur = writeTableau(document, new float[] { 5, 5, 7 });
		List<CellVo> employeur1 = new ArrayList<CellVo>();
		employeur1.add(new CellVo("Province des Iles Loyauté", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur1.add(new CellVo("Province des Iles Loyauté", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur1.add(new CellVo(""));
		writeLine(tableEmployeur, 10, employeur1, false);
		List<CellVo> employeur2 = new ArrayList<CellVo>();
		employeur2.add(new CellVo("Province Nord", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur2.add(new CellVo("Province Nord", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur2.add(new CellVo(""));
		writeLine(tableEmployeur, 10, employeur2, false);
		List<CellVo> employeur3 = new ArrayList<CellVo>();
		employeur3.add(new CellVo("Province Sud", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur3.add(new CellVo("Province Sud", false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		employeur3.add(new CellVo(""));
		writeLine(tableEmployeur, 10, employeur3, false);

		PdfPCell cellEmployeur = new PdfPCell();
		cellEmployeur.addElement(tableEmployeur);
		cellEmployeur.setFixedHeight(tableEmployeur.getTotalHeight());
		cellEmployeur.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellEmployeur);

		// table titulaires
		PdfPTable tableTitulaire = writeTableau(document, new float[] { 10, 7 });
		for (String titu : dto.getRepresentantsTitulaires()) {
			List<CellVo> listValuesTitulaire = new ArrayList<CellVo>();
			listValuesTitulaire.add(new CellVo(titu, false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
			listValuesTitulaire.add(new CellVo(""));
			writeLine(tableTitulaire, 10, listValuesTitulaire, false);
		}
		PdfPCell cellTitulaire = new PdfPCell();
		cellTitulaire.addElement(tableTitulaire);
		cellTitulaire.setFixedHeight(tableTitulaire.getTotalHeight());
		cellTitulaire.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellTitulaire);

		// table suppleants
		PdfPTable tableSuppleant = writeTableau(document, new float[] { 10, 7 });
		for (String supp : dto.getRepresentantsSuppleants()) {
			List<CellVo> listValuesSupp = new ArrayList<CellVo>();
			listValuesSupp.add(new CellVo(supp, false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
			listValuesSupp.add(new CellVo(""));
			writeLine(tableSuppleant, 10, listValuesSupp, false);

		}
		PdfPCell cellSuppleant = new PdfPCell();
		cellSuppleant.addElement(tableSuppleant);
		cellSuppleant.setFixedHeight(tableSuppleant.getTotalHeight());
		cellSuppleant.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellSuppleant);

		// president
		PdfPTable tablePresident = writeTableau(document, new float[] { 17 });
		tablePresident.addCell(writeCell(3, null, new CellVo("Le président ", true, 1, null, Element.ALIGN_LEFT, false, fontBold5), false, true, 80f,
				Element.ALIGN_TOP));
		PdfPCell cellPresident = new PdfPCell();
		cellPresident.addElement(tablePresident);
		cellPresident.setBorder(Rectangle.NO_BORDER);

		// Cachet et Visa
		PdfPTable tableCachetVisa = writeTableau(document, new float[] { 17 });
		tableCachetVisa.setWidthPercentage(40f);
		tableCachetVisa.setHorizontalAlignment(Element.ALIGN_LEFT);
		tableCachetVisa.addCell(writeCell(3, null, new CellVo("CACHET + VISA EMPLOYEUR : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold5), true,
				true, 80f, Element.ALIGN_TOP));
		PdfPCell cellCachetVisa = new PdfPCell();
		cellCachetVisa.addElement(tableCachetVisa);
		cellCachetVisa.setBorder(Rectangle.NO_BORDER);

		// table bas de page
		PdfPTable tableBasPage = writeTableau(document, new float[] { 13, 13, 13 });
		tableBasPage.setSpacingBefore(5);
		tableBasPage.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		tableBasPage.setKeepTogether(true);
		tableBasPage.addCell(tableCachetVisa);
		tableBasPage.addCell("");
		tableBasPage.addCell(tablePresident);

		writeLine(table, 0, Arrays.asList(new CellVo("", false, 3, null, Element.ALIGN_LEFT, false, fontNormal5)), false);
		document.add(table);
		document.add(tableBasPage);

	}

	private void genereTableauDiff(Document document, AvancementsDto dto) throws DocumentException {

		PdfPTable table = writeTableau(document, new float[] { 2, 5, 5, 5, 3, 3, 3, 2, 3, 3, 4, 2, 2, 2, 2, 2, 2 });

		// 1er ligne : entete
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("", true, 5, null, Element.ALIGN_LEFT, false, fontNormal5));
		listValuesLigne1.add(new CellVo("Historique\n(à remplir par\nl'employeur)", true, 1, null, Element.ALIGN_CENTER, true, fontBold5));
		listValuesLigne1.add(new CellVo("EAE réalisé\n(OUI/NON à remplir par l'employeur)", true, 1, null, Element.ALIGN_CENTER, true, fontBold5));
		listValuesLigne1.add(new CellVo(
				"Propositions de l'employeur (cocher la durée proposée et faire un classement par ordre de mérite des agents proposés à la durée minimale)",
				true, 5, null, Element.ALIGN_CENTER, true, fontBold5));
		listValuesLigne1.add(new CellVo("Propositions de la CAP", true, 4, null, Element.ALIGN_CENTER, true, fontBold5));
		listValuesLigne1.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 3, listValuesLigne1, false);

		// 2e ligne : entete
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Nom", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Prénom", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Grade", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Date prévisionelle\nd'avancement\nmoyen", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Date du dernier\navancement à la\ndurée minimale", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("", true, 2, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne2.add(new CellVo("Durée moyenne", true, 2, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("", true, 4, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 3, listValuesLigne2, false);

		// 3e ligne : entete
		List<CellVo> listValuesLigne3 = new ArrayList<CellVo>();
		listValuesLigne3.add(new CellVo("Nb", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée\nminimale", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Classement\n(si HQ)", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Proposition employeur", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée moyenne par\ndéfaut(si absence\nd'EAE", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée\nmaximale", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée\nminimale", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée\nmoyenne", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Durée\nmaximale", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Avis réputé\nrendu", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		listValuesLigne3.add(new CellVo("Report de\nquota", true, 1, null, Element.ALIGN_CENTER, true, fontNormal5));
		writeLine(table, 3, listValuesLigne3, false);

		// on tri la liste des agents par durée puis par ordre alpha

		Comparator<AvancementItemDto> comp = new Comparator<AvancementItemDto>() {
			@Override
			public int compare(AvancementItemDto o1, AvancementItemDto o2) {
				return o1.getNom().compareTo(o2.getNom());
			}

		};

		List<AvancementItemDto> listMini = new ArrayList<AvancementItemDto>();
		List<AvancementItemDto> listMoyen = new ArrayList<AvancementItemDto>();
		List<AvancementItemDto> listMaxi = new ArrayList<AvancementItemDto>();
		for (AvancementItemDto agentDto : dto.getAvancementsItems()) {
			if (agentDto.isDureeMax()) {
				listMaxi.add(agentDto);
			} else if (agentDto.isDureeMin()) {
				listMini.add(agentDto);
			} else {
				listMoyen.add(agentDto);
			}
		}

		// on tri par ordre alpha chaque liste
		Collections.sort(listMini, comp);
		Collections.sort(listMoyen, comp);
		Collections.sort(listMaxi, comp);
		// on crée une liste de tout
		List<AvancementItemDto> res = new ArrayList<AvancementItemDto>();
		res.addAll(listMini);
		res.addAll(listMoyen);
		res.addAll(listMaxi);

		// on boucle sur les agents
		Integer nbLigne = 1;
		for (AvancementItemDto agentDto : res) {
			writeLineDiffByAgent(table, agentDto, nbLigne);
			nbLigne++;
		}

		document.add(table);
	}

	private void genereTableauChgtClasse(Document document, AvancementsDto dto) throws DocumentException {

		PdfPTable table = writeTableau(document, new float[] { 6, 5, 5, 4, 4, 4, 3, 3, 5 });

		// 1er ligne : entete
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("", true, 3, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne1.add(new CellVo("Date prévisionelle\nd'avancement\nmoyen", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne1.add(new CellVo("Propositions de l'employeur", true, 2, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne1.add(new CellVo("Avis de la CAP et observations", true, 3, null, Element.ALIGN_CENTER, true, fontBold8));
		writeLine(table, 3, listValuesLigne1, false);

		// 2e ligne : entete
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("Nom", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Prénom", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Grade", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Favorable", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Défavorable", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Favorable", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Défavorable", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		writeLine(table, 3, listValuesLigne2, false);

		// on boucle sur les agents
		for (AvancementItemDto agentDto : dto.getAvancementsItems()) {
			writeLineChgtClasseByAgent(table, agentDto);
		}

		document.add(table);
	}

	private void writeLineChgtClasseByAgent(PdfPTable table, AvancementItemDto agentDto) {

		List<CellVo> listValuesByAgent = new ArrayList<CellVo>();

		// on ecrit les donnees de l agent
		listValuesByAgent.add(new CellVo(agentDto.getNom(), 1, Element.ALIGN_LEFT));
		listValuesByAgent.add(new CellVo(agentDto.getPrenom(), 1, Element.ALIGN_LEFT));
		listValuesByAgent.add(new CellVo(agentDto.getGrade(), 1, Element.ALIGN_LEFT));

		// on ecrit les date previsionnelles
		listValuesByAgent
				.add(new CellVo(new SimpleDateFormat("dd/MM/yyyy").format(agentDto.getDatePrevisionnelleAvancement()), 1, Element.ALIGN_CENTER));

		// on ecrit les durées
		listValuesByAgent.add(new CellVo(agentDto.isFavorable() ? "X" : "", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo(!agentDto.isFavorable() ? "X" : "", 1, Element.ALIGN_CENTER));

		// avis CAP
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));

		writeLine(table, 3, listValuesByAgent, false);
	}

	private void writeLineDiffByAgent(PdfPTable table, AvancementItemDto agentDto, Integer nb) {

		List<CellVo> listValuesByAgent = new ArrayList<CellVo>();
		// on ecrit le numero de la ligne
		listValuesByAgent.add(new CellVo(nb.toString(), false, 1, null, Element.ALIGN_RIGHT, true, fontNormal5));

		// on ecrit les donnees de l agent
		listValuesByAgent.add(new CellVo(agentDto.getNom(), false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesByAgent.add(new CellVo(agentDto.getPrenom(), false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));
		listValuesByAgent.add(new CellVo(agentDto.getGrade(), false, 1, null, Element.ALIGN_LEFT, true, fontNormal5));

		// on ecrit les date previsionnelles
		listValuesByAgent.add(new CellVo(new SimpleDateFormat("dd/MM/yyyy").format(agentDto.getDatePrevisionnelleAvancement()), false, 1, null,
				Element.ALIGN_CENTER, true, fontNormal5));

		// on ecrit les date avancement min
		listValuesByAgent.add(new CellVo(
				agentDto.getDateAncienAvancementMinimale() == null ? ""
						: new SimpleDateFormat("dd/MM/yyyy").format(agentDto.getDateAncienAvancementMinimale()),
				false, 1, null, Element.ALIGN_CENTER, true, fontNormal5));

		// EAE réalisé
		listValuesByAgent.add(new CellVo(agentDto.isEaeRealise() ? "OUI" : "NON", false, 1, null, Element.ALIGN_CENTER, true, fontNormal5));

		// on ecrit les durées
		listValuesByAgent.add(new CellVo(agentDto.isDureeMin() ? "X" : "", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo(agentDto.getClassement(), 1, Element.ALIGN_CENTER));
		if (agentDto.isEaeRealise()) {
			boolean cocheDureeMoy = false;
			if (agentDto.isDureeMoy()) {
				cocheDureeMoy = true;
			} else if (!agentDto.isDureeMin() && !agentDto.isDureeMax()) {
				cocheDureeMoy = true;
			}

			listValuesByAgent.add(new CellVo(cocheDureeMoy ? "X" : "", 1, Element.ALIGN_CENTER));
			listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		} else {
			listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
			listValuesByAgent.add(new CellVo("X", 1, Element.ALIGN_CENTER));
		}
		listValuesByAgent.add(new CellVo(agentDto.isDureeMax() ? "X" : "", 1, Element.ALIGN_CENTER));

		// avis CAP
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));

		writeLine(table, 3, listValuesByAgent, false);
	}

	private void genereInfoCAPDiff(Document document, AvancementsDto dto, boolean withQuota) throws DocumentException {
		PdfPTable table = writeTableau(document, new float[] { 50, 10 });
		table.setSpacingAfter(5);
		table.setSpacingBefore(5);
		// on calcul le nombre de min et max
		int nbMini = 0;
		int nbMaxi = 0;
		for (AvancementItemDto agentDto : dto.getAvancementsItems()) {
			if (agentDto.isDureeMin()) {
				nbMini++;
			}
			if (agentDto.isDureeMax()) {
				nbMaxi++;
			}
		}

		// 1er ligne
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("Employeur : " + dto.getEmployeur(), true, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesLigne1.add(new CellVo("CAP N° " + dto.getCap() + " du ", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne1, false);
		// 2eme ligne
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("Nombre d'agents : " + dto.getNbAgents(), true, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesLigne2.add(new CellVo("Filière : " + dto.getFiliere(), true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne2, false);
		// 3eme ligne
		List<CellVo> listValuesLigne3 = new ArrayList<CellVo>();
		listValuesLigne3.add(new CellVo(withQuota ? "Quota d'avancement à la durée minimale (ratio = 30%) : " : "", true, 1, null, Element.ALIGN_LEFT,
				false, fontBold5));
		listValuesLigne3.add(new CellVo("Catégorie : " + dto.getCategorie(), true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne3, false);
		// 4eme ligne
		List<CellVo> listValuesLigne4 = new ArrayList<CellVo>();
		listValuesLigne4.add(new CellVo("Nb d'agents proposés à la durée minimale : " + nbMini, true, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesLigne4.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne4, false);
		// 5eme ligne
		List<CellVo> listValuesLigne5 = new ArrayList<CellVo>();
		listValuesLigne5.add(new CellVo("Nb d'agents proposés hors quota : ", true, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesLigne5.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne5, false);
		// 6eme ligne
		List<CellVo> listValuesLigne6 = new ArrayList<CellVo>();
		listValuesLigne6.add(new CellVo("Nb d'agents proposés à la durée maximale : " + nbMaxi, true, 1, null, Element.ALIGN_LEFT, false, fontBold5));
		listValuesLigne6.add(new CellVo("", true, 1, null, Element.ALIGN_LEFT, false, fontNormal5));
		writeLine(table, 2, listValuesLigne6, false);

		document.add(table);
	}

	private void genereInfoCAPChgtClasse(Document document, AvancementsDto dto, boolean withQuota) throws DocumentException {
		PdfPTable table = writeTableau(document, new float[] { 15, 10 });
		table.setSpacingAfter(5);
		table.setSpacingBefore(5);

		// 1er ligne
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("Employeur : " + dto.getEmployeur(), true, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		listValuesLigne1.add(new CellVo("CAP N° " + dto.getCap(), true, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		writeLine(table, 2, listValuesLigne1, false);
		// 2eme ligne
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("Nombre d'agents : " + dto.getNbAgents(), true, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		listValuesLigne2.add(new CellVo("Filière : " + dto.getFiliere(), true, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		writeLine(table, 2, listValuesLigne2, false);
		// 3eme ligne
		List<CellVo> listValuesLigne3 = new ArrayList<CellVo>();
		listValuesLigne3.add(new CellVo(withQuota ? "Quota d'avancement à la durée minimale (ratio = 30%) : " : "", true, 1, null, Element.ALIGN_LEFT,
				false, fontBold8));
		listValuesLigne3.add(new CellVo("Catégorie : " + dto.getCategorie(), true, 1, null, Element.ALIGN_LEFT, false, fontBold8));
		writeLine(table, 2, listValuesLigne3, false);

		document.add(table);
	}

	private void genereTitreJaune(Document document, AvancementsDto dto) throws DocumentException {
		String titre = dto.getDeliberationLibelle();
		titre += "\nCadre d'emploi des " + dto.getCadreEmploiLibelle();

		PdfPTable table = new PdfPTable(new float[] { 1 });
		table.setWidthPercentage(100f);

		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo(titre, true, 3, new BaseColor(255, 255, 174), Element.ALIGN_CENTER, false, fontBold8));
		writeLine(table, 5, Element.ALIGN_CENTER, listValuesLigne1, false);

		document.add(table);
	}

	private void genereEnteteDocumentTabAD(Document document, String urlImage, boolean isBold, String title) throws DocumentException {
		PdfPCell cellLogo = null;
		if (null != urlImage) {
			Image logo = null;
			try {
				logo = Image.getInstance(this.getClass().getClassLoader().getResource(urlImage));
				logo.scaleToFit(60, 60);
			} catch (Exception e) {
			}
			cellLogo = new PdfPCell();
			cellLogo.addElement(logo);
			cellLogo.setBorder(Rectangle.NO_BORDER);
		}

		PdfPTable table = null;
		if (null != urlImage) {
			table = new PdfPTable(new float[] { 1, 7 });
			table.addCell(cellLogo);
		} else {
			table = new PdfPTable(new float[] { 1 });
		}
		table.setWidthPercentage(100f);

		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo(title, true, 0, null, Element.ALIGN_CENTER, false, fontBold5));
		writeLine(table, 3, Element.ALIGN_LEFT, listValuesLigne1, false);

		document.add(table);
	}

	private void genereEnteteDocumentChgtClasse(Document document, String urlImage, boolean isBold, String title) throws DocumentException {
		PdfPCell cellLogo = null;
		if (null != urlImage) {
			Image logo = null;
			try {
				logo = Image.getInstance(this.getClass().getClassLoader().getResource(urlImage));
				logo.scaleToFit(60, 60);
			} catch (Exception e) {
			}
			cellLogo = new PdfPCell();
			cellLogo.addElement(logo);
			cellLogo.setBorder(Rectangle.NO_BORDER);
		}

		PdfPTable table = null;
		if (null != urlImage) {
			table = new PdfPTable(new float[] { 1, 7 });
			table.addCell(cellLogo);
		} else {
			table = new PdfPTable(new float[] { 1 });
		}
		table.setWidthPercentage(100f);

		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo(title, true, 0, null, Element.ALIGN_CENTER, false, fontBold8));
		writeLine(table, 3, Element.ALIGN_LEFT, listValuesLigne1, false);

		document.add(table);
	}

	private void writeTabChgtClasse(Document document, CommissionAvancementCorpsDto dto) throws DocumentException {
		// les données en entête du document
		String titre = "GOUVERNEMENT DE LA NOUVELLE-CALEDONIE";
		titre += "\nDRHFPNC - SERVICE DE LA GESTION STATUTAIRE ET DES ORGANISMES PARITAIRES - SECTION EMPLOYEURS";
		genereEnteteDocumentChgtClasse(document, "images/logo_gouv.jpg", true, titre);

		// le titre
		genereTitreJaune(document, dto.getChangementClasses());

		// les infos CAP
		genereInfoCAPChgtClasse(document, dto.getChangementClasses(), false);

		// genere entete tableau
		genereTableauChgtClasse(document, dto.getChangementClasses());

		// genere bas de page
		genereBasPageTabChgtClasse(document, dto.getChangementClasses());
	}

	@Override
	public byte[] getCertificatAptitudePDF(VisiteMedicale vm, EntiteDto direction, EntiteDto service, TitrePoste titreFichePoste)
			throws DocumentException {
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);

		// on genere les metadata
		addMetaData(document, "Certificat d'aptitude", "SIRH");

		// on ouvre le document
		document.open();

		if (null != vm) {

			// on concat les donnnées
			String poste = (direction == null ? "" : direction.getSigle().trim() + " / ") + (service == null ? "" : service.getSigle().trim() + " / ")
					+ (titreFichePoste == null ? "" : titreFichePoste.getLibTitrePoste().trim());

			// medecin
			Medecin medecin = vm.getMedecin() == null ? null : vm.getMedecin();
			String nomMedecin = "";
			if (medecin != null) {
				nomMedecin = medecin.getPrenomMedecin() + " " + medecin.getNomMedecin();
			}
			// recommandation
			Recommandation recom = vm.getRecommandation() == null ? null : vm.getRecommandation();
			String recommandation = "";
			if (recom != null) {
				recommandation = recom.getDescription();
			}
			// date prochaine visite
			String dateARevoir = "";
			if (vm.getDureeValidite() != null) {
				dateARevoir = vm.getDureeValidite() + " mois";
			}

			// on commence le document
			List<String> listTitre = new ArrayList<>();
			listTitre.add(getTitreCertificatAptitude(vm.getAgent()));
			listTitre.add(vm.getMotifVM() == null ? "" : vm.getMotifVM().getLibMotifVisiteMedicale());

			// on ajoute le titre, le logo sur le document
			genereEnteteDocumentChgtClasse(document, "images/logo_DRH.png", true, "");

			// 34317 : on ajoute le type de visite dans le titre
			for (String title : listTitre) {
				Paragraph paragraph = null;
				paragraph = new Paragraph(title, fontBold8);
				paragraph.setAlignment(Element.ALIGN_CENTER);
				document.add(paragraph);
			}

			genereTableauCertificatAptitude(document, vm.getDateDerniereVisite(), nomMedecin.toUpperCase(), poste.toUpperCase(), recommandation,
					vm.getCommentaire() == null ? "" : vm.getCommentaire(), dateARevoir,
					vm.getMotifVM() == null ? "" : vm.getMotifVM().getLibMotifVisiteMedicale());

			genereSignatureCertificatAptitude(document);

		}

		// on ferme le document
		document.close();

		return baos.toByteArray();
	}

	private void genereTableauCertificatAptitude(Document document, Date dateVisite, String nomMedecin, String poste, String avis, String restriction,
			String aRevoir, String motif) throws DocumentException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		PdfPTable table = writeTableau(document, new float[] { 8, 20 });
		table.setSpacingBefore(10);
		table.setSpacingAfter(10);

		// #34317 : ligne avec le motif
		// 0 ligne : motif
		List<CellVo> listValuesLigne0 = new ArrayList<CellVo>();
		listValuesLigne0.add(new CellVo("Motif : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne0.add(new CellVo(motif, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne0);

		// 1ere ligne : medecin
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("Medecin : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne1.add(new CellVo(nomMedecin, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne1);

		// 2er ligne : date visite
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("Date de la visite : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne2.add(new CellVo(sdf.format(dateVisite), true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne2);

		// 3eme ligne : poste
		List<CellVo> listValuesLigne3 = new ArrayList<CellVo>();
		listValuesLigne3.add(new CellVo("Poste : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne3.add(new CellVo(poste, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne3);

		// 4eme ligne : avis
		List<CellVo> listValuesLigne4 = new ArrayList<CellVo>();
		listValuesLigne4.add(new CellVo("Avis médical : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne4.add(new CellVo(avis, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne4);

		// 5eme ligne : restrictions
		List<CellVo> listValuesLigne5 = new ArrayList<CellVo>();
		listValuesLigne5.add(new CellVo("Recommandations éventuelles : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne5.add(new CellVo(restriction, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne5);

		// 6eme ligne : restrictions
		List<CellVo> listValuesLigne6 = new ArrayList<CellVo>();
		listValuesLigne6.add(new CellVo("A revoir dans : ", true, 1, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne6.add(new CellVo(aRevoir, true, 1, null, Element.ALIGN_LEFT, true, fontNormal8));
		writeLine(table, 7, listValuesLigne6);

		document.add(table);
	}

	protected void genereSignatureCertificatAptitude(Document document) throws DocumentException {
		PdfPTable table = null;
		table = new PdfPTable(1);
		table.setWidthPercentage(80f);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		// 1er ligne : entete
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		String dateJour = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		listValuesLigne1.add(new CellVo(new String("nouméa le " + dateJour).toUpperCase(), true, 1, null, Element.ALIGN_RIGHT, false, fontNormal8));
		writeLine(table, 3, listValuesLigne1);

		writeSpacing(document, 1);
		document.add(table);
	}

	private String getTitreCertificatAptitude(Agent agent) {
		String nomPrenom = agent.getDisplayNom() + " " + agent.getDisplayPrenom();
		return "CERTIFICAT D'APTITUDE DE " + agent.getTitre().toUpperCase() + " " + nomPrenom.toUpperCase();
	}

	@Override
	public Map<String, byte[]> getFichePosteParServicePourReorg(Integer idService) throws Exception {
		Map<String, byte[]> returnList = Maps.newHashMap();
		ClientResponse response = null;
		Map<String, String> map = new HashMap<String, String>();

		// Récupération des services et sous-services
		Map<String, Integer> idsServices = adsService.getListIdsOfEntiteTree(idService);
		logger.debug("Ce service contient {} sous services.", idsServices.size());
		
		for (Entry<String, Integer> idServ : idsServices.entrySet()) {
			// Récupération des fiches de poste actives pour ce service.
			Map<String, Integer> idFichePostes = fichePosteService.getListeIdFPActivesParService(idServ.getValue());
			logger.debug("Traitement du service {}, {} fiches de poste", idServ.getKey(), idFichePostes.size());
			
			for (Entry<String, Integer> idFP : idFichePostes.entrySet()) {
				logger.debug("Traitement de la fiche de poste {}", idFP.getKey());
				map = new HashMap<String, String>();
				map.put("idFichePoste", String.valueOf(idFP.getValue()));

				response = createAndFireRequest(map, "fichePosteSIRHPourReferentiel.rptdesign", "PDF");
				returnList.put(idServ.getKey()+"_"+StringUtils.replace(idFP.getKey(), "/", "-")+".pdf", readResponseAsByteArray(response, map));
			}
		}
		
		return returnList;
	}
}
