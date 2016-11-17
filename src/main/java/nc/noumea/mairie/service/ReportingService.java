package nc.noumea.mairie.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.avancements.AvancementItemDto;
import nc.noumea.mairie.web.dto.avancements.AvancementsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

@Service
public class ReportingService extends AbstractReporting implements IReportingService {

	@Autowired
	@Qualifier("reportingBaseUrl")
	private String				reportingBaseUrl;

	@Autowired
	@Qualifier("reportServerPath")
	private String				reportServerPath;

	private static final String	REPORT_PAGE		= "frameset";
	private static final String	PARAM_REPORT	= "__report";
	private static final String	PARAM_FORMAT	= "__format";
	private static final String	PARAM_LOCALE	= "__locale";

	@Override
	public byte[] getFichePosteReportAsByteArray(int idFichePoste) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));

		ClientResponse response = createAndFireRequest(map, "fichePoste.rptdesign", "PDF");

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
	public byte[] getFichePosteSIRHReportAsByteArray(int idFichePoste) throws Exception {

		Map<String, String> map = new HashMap<String, String>();
		map.put("idFichePoste", String.valueOf(idFichePoste));

		ClientResponse response = createAndFireRequest(map, "fichePosteSIRH.rptdesign", "ODT");

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
			genereEnteteDocument(document, "images/logo_gouv.jpg", true, "Aucune correspondance");
		}

		// on ferme le document
		document.close();

		return baos.toByteArray();

	}

	private void writeTabDiff(Document document, CommissionAvancementCorpsDto dto) throws DocumentException, MalformedURLException, IOException {
		// les données en entête du document
		String titre = "Direction des ressources humaines de la fonction publique de Nouvelle-Calédonie";
		titre += "\nAVANCEMENT DIFFERENCIE - ANNEE " + dto.getAvancementsDifferencies().getAnnee();
		genereEnteteDocument(document, "images/logo_gouv.jpg", true, titre);

		// le titre
		genereTitreJaune(document, dto.getAvancementsDifferencies());

		// les infos CAP
		genereInfoCAP(document, dto.getAvancementsDifferencies(), true);

		// genere entete tableau
		genereTableauDiff(document, dto.getAvancementsDifferencies());

		// genere bas de page
		genereBasPage(document, dto.getAvancementsDifferencies());

	}

	private void genereBasPage(Document document, AvancementsDto dto) throws DocumentException {
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

	private void genereTableauDiff(Document document, AvancementsDto dto) throws DocumentException {

		PdfPTable table = writeTableau(document, new float[] { 6, 5, 5, 4, 4, 2, 2, 2, 3, 3, 3, 5 });

		// 1er ligne : entete
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo("", true, 3, null, Element.ALIGN_LEFT, true, fontBold8));
		listValuesLigne1.add(new CellVo("Date prévisionelle\nd'avancement\nmoyen", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne1.add(new CellVo("Historique\n(à remplir par\nl'employeur)", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne1.add(new CellVo("Propositions de l'employeur", true, 4, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne1.add(new CellVo("Avis de la CAP et observations", true, 3, null, Element.ALIGN_CENTER, true, fontBold8));
		writeLine(table, 3, listValuesLigne1, false);

		// 2e ligne : entete
		List<CellVo> listValuesLigne2 = new ArrayList<CellVo>();
		listValuesLigne2.add(new CellVo("Nom", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Prénom", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Grade", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Date du dernier\navancement à la\ndurée minimale", true, 1, null, Element.ALIGN_CENTER, false, fontBold8));
		listValuesLigne2.add(new CellVo("Durée\nmin", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Durée\nmoy", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Durée\nmax", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Classement", true, 1, new BaseColor(176, 216, 255), Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Favorable", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("Défavorable", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		listValuesLigne2.add(new CellVo("", true, 1, null, Element.ALIGN_CENTER, true, fontBold8));
		writeLine(table, 3, listValuesLigne2, false);

		// on boucle sur les agents
		for (AvancementItemDto agentDto : dto.getAvancementsItems()) {
			writeLineDiffByAgent(table, agentDto);
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

	private void writeLineDiffByAgent(PdfPTable table, AvancementItemDto agentDto) {

		List<CellVo> listValuesByAgent = new ArrayList<CellVo>();

		// on ecrit les donnees de l agent
		listValuesByAgent.add(new CellVo(agentDto.getNom(), 1, Element.ALIGN_LEFT));
		listValuesByAgent.add(new CellVo(agentDto.getPrenom(), 1, Element.ALIGN_LEFT));
		listValuesByAgent.add(new CellVo(agentDto.getGrade(), 1, Element.ALIGN_LEFT));

		// on ecrit les date previsionnelles
		listValuesByAgent
				.add(new CellVo(new SimpleDateFormat("dd/MM/yyyy").format(agentDto.getDatePrevisionnelleAvancement()), 1, Element.ALIGN_CENTER));

		// on ecrit les date avancement min
		listValuesByAgent.add(new CellVo(agentDto.getDateAncienAvancementMinimale() == null ? ""
				: new SimpleDateFormat("dd/MM/yyyy").format(agentDto.getDateAncienAvancementMinimale()), 1, Element.ALIGN_CENTER));

		// on ecrit les durées
		listValuesByAgent.add(new CellVo(agentDto.isDureeMin() ? "X" : "", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo(agentDto.isDureeMoy() ? "X" : "", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo(agentDto.isDureeMax() ? "X" : "", 1, Element.ALIGN_CENTER));

		// classement
		listValuesByAgent.add(new CellVo(agentDto.getClassement(), 1, Element.ALIGN_CENTER));

		// avis CAP
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));
		listValuesByAgent.add(new CellVo("", 1, Element.ALIGN_CENTER));

		writeLine(table, 3, listValuesByAgent, false);
	}

	private void genereInfoCAP(Document document, AvancementsDto dto, boolean withQuota) throws DocumentException {
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

	private void genereEnteteDocument(Document document, String urlImage, boolean isBold, String title) throws DocumentException {
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
		genereEnteteDocument(document, "images/logo_gouv.jpg", true, titre);

		// le titre
		genereTitreJaune(document, dto.getChangementClasses());

		// les infos CAP
		genereInfoCAP(document, dto.getChangementClasses(), false);

		// genere entete tableau
		genereTableauChgtClasse(document, dto.getChangementClasses());

		// genere bas de page
		genereBasPage(document, dto.getChangementClasses());
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
			genereEnteteDocument(document, "images/logo_DRH.png", true, "");

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
}
