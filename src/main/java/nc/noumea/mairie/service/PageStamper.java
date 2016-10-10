package nc.noumea.mairie.service;

import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;


public class PageStamper extends PdfPageEventHelper {
	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		final int currentPageNumber = writer.getCurrentPageNumber();

		try {
			final Rectangle pageSize = document.getPageSize();
			final PdfContentByte directContent = writer.getDirectContent();

			directContent.setColorFill(BaseColor.BLACK);
			directContent.setFontAndSize(BaseFont.createFont(), 10);

			directContent.setTextMatrix(pageSize.getRight(60), pageSize.getBottom(20));
			directContent.showText("Page " + currentPageNumber);

		} catch (DocumentException | IOException e) {

		}
	}
}
