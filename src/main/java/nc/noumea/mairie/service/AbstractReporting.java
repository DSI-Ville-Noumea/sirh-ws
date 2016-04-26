package nc.noumea.mairie.service;

import java.util.ArrayList;
import java.util.List;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;

public abstract class AbstractReporting extends PdfPageEventHelper {

	protected Font fontNormal8 = FontFactory.getFont("Arial", 8, Font.NORMAL);
	protected Font fontNormal9 = FontFactory.getFont("Arial", 9, Font.NORMAL);
	protected Font fontBold8 = FontFactory.getFont("Arial", 8, Font.BOLD);
	protected Font fontBold9 = FontFactory.getFont("Arial", 9, Font.BOLD);

	protected void writeSpacing(Document document, int nbLines) throws DocumentException {
		Paragraph newLine = new Paragraph(Chunk.NEWLINE);
		newLine.setFont(fontBold9);
		for (int i = 0; i < nbLines; i++) {
			document.add(newLine);
		}
	}

	protected void writeParagraph(Document document, List<String> listParagraph, Font police, int alignement) throws DocumentException {
		for (String title : listParagraph) {
			Paragraph paragraph = null;
			paragraph = new Paragraph(title, police);
			paragraph.setAlignment(alignement);
			document.add(paragraph);
		}
	}

	protected PdfPTable writeTableau(Document document, float[] relativeWidth) throws DocumentException {
		PdfPTable table = new PdfPTable(relativeWidth);
		table.setWidthPercentage(100f);
		return table;
	}

	protected void writeLine(PdfPTable table, Integer padding, List<CellVo> values, boolean underline) {

		for (CellVo value : values) {
			table.addCell(writeCell(padding, null, value, underline, false, null, Element.ALIGN_MIDDLE));
		}
	}

	protected void writeLine(PdfPTable table, Integer padding, Integer horizontalAlign, List<CellVo> values, boolean underline) {

		for (CellVo value : values) {
			table.addCell(writeCell(padding, horizontalAlign, value, underline, false, null, Element.ALIGN_MIDDLE));
		}
	}

	protected PdfPCell writeCell(Integer padding, CellVo value, boolean underline) {
		return writeCell(padding, null, value, underline, false, null, Element.ALIGN_MIDDLE);
	}

	protected PdfPCell writeCell(Integer padding, Integer horizontalAlign, CellVo value, boolean underline, boolean fixedHeight, Float fixedHeightValue, Integer verticalAlign) {

		PdfPCell pdfWordCell = new PdfPCell();
		pdfWordCell.setPadding(padding);
		pdfWordCell.setUseAscender(true);
		pdfWordCell.setUseDescender(true);
		pdfWordCell.setBackgroundColor(value.getBackgroundColor());
		pdfWordCell.setVerticalAlignment(verticalAlign);
		if (fixedHeight && fixedHeightValue != null)
			pdfWordCell.setFixedHeight(fixedHeightValue);

		// /!\ COLSPAN
		if (value.getColspan() != null)
			pdfWordCell.setColspan(value.getColspan());

		// border
		if (!value.isBorder())
			pdfWordCell.setBorder(Rectangle.NO_BORDER);

		// on cree la phrase pour le FONT
		Chunk phrase = null;
		if (null != value.getFont()) {
			phrase = new Chunk(value.getText(), value.getFont());
			if (underline)
				phrase.setUnderline(0.1f, -1f); // 0.1 thick, -2 y-location
		} else if (value.isBold()) {
			phrase = new Chunk(value.getText(), fontBold9);
			if (underline)
				phrase.setUnderline(0.1f, -1f); // 0.1 thick, -2 y-location
		} else {
			phrase = new Chunk(value.getText(), fontNormal9);
			if (underline)
				phrase.setUnderline(0.1f, -1f); // 0.1 thick, -2 y-location
		}
		// on cree le Paragraph pour l alignement
		Paragraph paragraph = new Paragraph(phrase);
		if (null != value.getHorizontalAlign()) {
			paragraph.setAlignment(value.getHorizontalAlign());
		} else if (null != horizontalAlign) {
			paragraph.setAlignment(horizontalAlign);
		}
		pdfWordCell.addElement(paragraph);

		return pdfWordCell;
	}

	protected void addMetaData(Document document, String titre, String author) {

		document.addTitle(titre);
		if (author != null)
			document.addAuthor(author);

		document.addSubject(titre);
	}

	protected void genereSignature(Document document) throws DocumentException {
		PdfPTable table = null;
		table = new PdfPTable(1);
		table.setWidthPercentage(40f);
		table.setHorizontalAlignment(Element.ALIGN_RIGHT);

		// 1er ligne : entete
		List<CellVo> listValuesLigne1 = new ArrayList<CellVo>();
		listValuesLigne1.add(new CellVo(new String("Examiné en commission administrative paritaire le :"), true, 1, null, Element.ALIGN_LEFT, false, fontNormal8));
		listValuesLigne1.add(new CellVo(new String("Le président :"), true, 1, null, Element.ALIGN_LEFT, false, fontNormal8));

		writeLine(table, 3, listValuesLigne1, false);

		writeSpacing(document, 1);
		document.add(table);
	}

}
