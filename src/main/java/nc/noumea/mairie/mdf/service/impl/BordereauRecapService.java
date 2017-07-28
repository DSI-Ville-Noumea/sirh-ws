package nc.noumea.mairie.mdf.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;
import nc.noumea.mairie.mdf.dto.AlimenteBordereauBean;
import nc.noumea.mairie.mdf.repository.IPersonnelRepository;
import nc.noumea.mairie.mdf.service.AbstractReporting;
import nc.noumea.mairie.mdf.service.IBordereauRecapService;
import nc.noumea.mairie.mdf.service.IDataConsistencyRules;
import nc.noumea.mairie.service.CellVo;

@Service
public class BordereauRecapService extends AbstractReporting implements IBordereauRecapService {

	private AlimenteBordereauBean donnees;
	
	@Autowired
	private IPersonnelRepository personnelRepo;
	
	@Autowired
	private IDataConsistencyRules dataRules;

	private Logger logger = LoggerFactory.getLogger(BordereauRecapService.class);
	
	static final NumberFormat numberFormat = NumberFormat.getInstance(new Locale("fr"));

	@Override
	@Transactional(readOnly = true)
	public byte[] getRecapMDFAsByteArray() throws Exception {
		
		initDatas();

		Document document = new Document(PageSize.A4.rotate());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, baos);

		// on genere les metadata
		addMetaData(document, "Bordereau récapitulatif de déclaration des rémunérations", "MDF-JOBS");

		// on ouvre le document
		document.open();

		writeTitle(document);
		writeSpacing(document, 3);
		
		writeEmployerAndPeriod(document);
		writeSpacing(document, 2);
		
		writeEffectifs(document);
		writeSpacing(document, 2);
		
		writeRemunerations(document);
		writeSpacing(document, 2);
		
		writeCotisations(document);
		writeSpacing(document, 2);
		
		writeBasDePage(document);
	
		// on ferme le document
		document.close();
		logger.debug("Le bordereau récapitulatif a été généré correctement.");
	
		return baos.toByteArray();
	}
	
	private void writeTitle(Document document) throws DocumentException {
		PdfPTable table =  writeTableau(document, new float[] { 1 });
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Bordereau récapitulatif de déclaration des rémunérations", false, 1, null, Element.ALIGN_CENTER, false, fontBoldUnderline12));
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
	}
	
	private void writeEmployerAndPeriod(Document document) throws DocumentException {
		// Employer
		PdfPTable table =  writeTableau(document, new float[] { 2, 3 });
        
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Dénomination employeur", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo(donnees.getCodeCollectivité(), false, 1, null, Element.ALIGN_RIGHT, true, fontNormal10));
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
		

		writeSpacing(document, 1);
		
		// Period
		PdfPTable table2 =  writeTableau(document, new float[] { 2, 3 });
        
		List<CellVo> listValuesReliquat2 = new ArrayList<CellVo>();
		listValuesReliquat2.add(new CellVo("MOIS DE PAYE", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat2.add(new CellVo(donnees.getMoisPaye(), false, 1, null, Element.ALIGN_RIGHT, true, fontNormal10));
		writeLine(table2, 3, listValuesReliquat2, false);
		document.add(table2);
	}
	
	private void writeEffectifs(Document document) throws DocumentException, ParseException {
		PdfPTable table =  writeTableau(document, new float[] { 5, 2 });
        
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Effectif", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo("Total", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));
		
		listValuesReliquat.add(new CellVo("Effectif de l'employeur au 1er jour du mois + entrants au cours du mois m", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getEffectif().getEffectif()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Effectif pour lequel une rémunération est déclarée pour le mois m", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getEffectif().getEffectifRemunere()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
	}
	
	private void writeRemunerations(Document document) throws DocumentException, ParseException {
		PdfPTable table =  writeTableau(document, new float[] { 5, 2 });
        
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Rémunération", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo("Total", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));
		
		listValuesReliquat.add(new CellVo("Montant total des rémunérations brutes soumises à cotisation", false, 1, null, Element.ALIGN_LEFT, true, fontNormal10));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getRemuneration().getTotalBrut()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Dont montant des rémunérations brutes versées au titre du mois m", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getRemuneration().getMontantRemuneration()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Dont montant des régularisations sur rémunérations brutes des mois antérieurs versé au cours du mois", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getRemuneration().getMontantRegularisation()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
	}
	
	private void writeCotisations(Document document) throws DocumentException, ParseException {
		PdfPTable table =  writeTableau(document, new float[] { 5, 2, 2, 2 });
        
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Cotisations", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo("Part patronale", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));
		listValuesReliquat.add(new CellVo("Part salariale", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));
		listValuesReliquat.add(new CellVo("Total", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));

		listValuesReliquat.add(new CellVo("Montant de cotisation calculé par l’employeur pour le mois m", false, 1, null, Element.ALIGN_LEFT, true, fontNormal10));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationPatronale().getCotisationCalculee()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationSalariale().getCotisationCalculee()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		Integer totalCalculee = donnees.getCotisationPatronale().getCotisationCalculee() + donnees.getCotisationSalariale().getCotisationCalculee();
		listValuesReliquat.add(new CellVo(numberFormat.format(totalCalculee), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Dont montant de cotisation sur les rémunérations brutes versées au titre du mois m", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationPatronale().getSurRemuneration()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationSalariale().getSurRemuneration()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		Integer totalRemuneration = donnees.getCotisationPatronale().getSurRemuneration() + donnees.getCotisationSalariale().getSurRemuneration();
		listValuesReliquat.add(new CellVo(numberFormat.format(totalRemuneration), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Dont montant total de cotisation au titre de régularisations sur des périodes antérieures", false, 1, null, Element.ALIGN_LEFT, true, fontNormal08));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationPatronale().getSurRegularisation()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		listValuesReliquat.add(new CellVo(numberFormat.format(donnees.getCotisationSalariale().getSurRegularisation()), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		Integer totalRegul = donnees.getCotisationPatronale().getSurRegularisation() + donnees.getCotisationSalariale().getSurRegularisation();
		listValuesReliquat.add(new CellVo(numberFormat.format(totalRegul), false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
	}
	
	private void writeBasDePage(Document document) throws DocumentException {
		PdfPTable table =  writeTableau(document, new float[] { 5, 1, 1, 3, 7 });
        
		List<CellVo> listValuesReliquat = new ArrayList<CellVo>();
		listValuesReliquat.add(new CellVo("Cadre réservé à la MDF", false, 3, null, Element.ALIGN_CENTER, true, fontBold10));
		listValuesReliquat.add(new CellVo("", false, 1, null, Element.ALIGN_CENTER, false, fontBold10));
		listValuesReliquat.add(new CellVo("certifié sincère, conforme et véritable", false, 1, null, Element.ALIGN_CENTER, true, fontNormal10));
		
		listValuesReliquat.add(new CellVo("Date de récéption", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo("", false, 2, null, Element.ALIGN_LEFT, true, fontBold10));
		listValuesReliquat.add(new CellVo("", false, 1, null, Element.ALIGN_LEFT, false, fontBold10));
		listValuesReliquat.add(new CellVo("Cachet et signature de l’employeur", false, 1, null, Element.ALIGN_CENTER, true, fontBold10));
		
		listValuesReliquat.add(new CellVo("Conformité au flux numérique", false, 1, null, Element.ALIGN_LEFT, true, fontBold10, 50F));
		listValuesReliquat.add(new CellVo("OK", false, 1, null, Element.ALIGN_CENTER, true, fontBold10, 50F));
		listValuesReliquat.add(new CellVo("KO", false, 1, null, Element.ALIGN_CENTER, true, fontBold10, 50F));
		listValuesReliquat.add(new CellVo("", false, 1, null, Element.ALIGN_CENTER, false, fontBold10, 50F));
		listValuesReliquat.add(new CellVo("", false, 1, null, Element.ALIGN_CENTER, true, fontBold10, 50F));
		
		listValuesReliquat.add(new CellVo("Visa du contrôleur", false, 1, null, Element.ALIGN_LEFT, true, fontNormal10));
		listValuesReliquat.add(new CellVo("", false, 2, null, Element.ALIGN_CENTER, true, fontNormal10));
		listValuesReliquat.add(new CellVo("", false, 1, null, Element.ALIGN_LEFT, false, fontBold10));
		listValuesReliquat.add(new CellVo("Nom et titre du signataire : ", false, 1, null, Element.ALIGN_LEFT, true, fontBold10));
		
		writeLine(table, 3, listValuesReliquat, false);
		document.add(table);
	}
	
	private void initDatas() {
		logger.debug("Entrée dans l'initialisation des données du bordereau récapitulatif.");
		
		FdsMutEnt enTete = personnelRepo.getEntete();
		FdsMutTot total = personnelRepo.getTotal();
		List<FdsMutDet> details = personnelRepo.getAllDetails();
		
		// On vérifie les données en entrée
		dataRules.verifyInputDatas(enTete, details, total);
		logger.debug("Les données récupérées sont bien présentes.");
		
		AlimenteBordereauBean datas = dataRules.alimenteDatas(enTete, details, total);

		// On vérifie les données en sortie
		dataRules.verifyConsistency(datas);
		logger.debug("Les données du bordereau sont complètes.");
		
		setDonnees(datas);
	}
	
	public AlimenteBordereauBean getDonnees() {
		return donnees;
	}

	public void setDonnees(AlimenteBordereauBean donnees) {
		this.donnees = donnees;
	}
}
