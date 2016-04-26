package nc.noumea.mairie.service;

import java.awt.Color;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

public class PageStamper extends PdfPageEventHelper {
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		final int currentPageNumber = writer.getCurrentPageNumber();

		try {
			final Rectangle pageSize = document.getPageSize();
			final PdfContentByte directContent = writer.getDirectContent();

			directContent.setColorFill(Color.BLACK);
			directContent.setFontAndSize(BaseFont.createFont(), 10);

			directContent.setTextMatrix(pageSize.getRight(60), pageSize.getBottom(20));
			directContent.showText("Page " + currentPageNumber);

		} catch (DocumentException | IOException e) {

		}
	}
}
