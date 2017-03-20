package com.epam.spring.core.web.view;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.spring.core.domain.ticket.Ticket;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class TicketsPdfView extends AbstractITextPdfView {
	
	private static final String PASSED_TICKETS  = "tickets";

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
        @SuppressWarnings("unchecked")
		Set<Ticket> tickets = (Set<Ticket>) model.get(PASSED_TICKETS);
        
        document.add(new Paragraph("Tickets"));
         
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {3.0f, 2.0f, 2.0f});
        table.setSpacingBefore(10);
         
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(BaseColor.WHITE);
         
        // define table header cell
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(3);
         
        // write table header
        cell.setPhrase(new Phrase("Ticket ID", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Ticket Seat", font));
        table.addCell(cell);
 
        cell.setPhrase(new Phrase("Ticket Price", font));
        table.addCell(cell);
         
        // write table row data
        for (Ticket ticket : tickets) {
            table.addCell(String.valueOf(ticket.getId()));
            table.addCell(String.valueOf(ticket.getSeat()));
            table.addCell(String.valueOf(ticket.getTicketPrice()));
        }
         
        document.add(table);
         
	}
	
}