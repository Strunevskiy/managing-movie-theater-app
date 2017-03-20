package com.epam.spring.core.web.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.epam.spring.core.domain.ticket.Ticket;
import com.epam.spring.core.domain.user.User;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfHttpMessageConverter extends AbstractHttpMessageConverter<Set<Ticket>> {
	
	private static final int OUTPUT_BYTE_ARRAY_INITIAL_SIZE = 4096;

	public PdfHttpMessageConverter() {
		super(new MediaType("application", "pdf"));
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
	}
	
	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		return false;
	}

	@Override
	protected void writeInternal(Set<Ticket> t, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(OUTPUT_BYTE_ARRAY_INITIAL_SIZE);
		try {
			Document document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, baos);
			writer.setViewerPreferences(PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage);
			
	        document.open();
	        
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
	        for (Ticket ticket : t) {
	            table.addCell(String.valueOf(ticket.getId()));
	            table.addCell(String.valueOf(ticket.getSeat()));
	            table.addCell(String.valueOf(ticket.getTicketPrice()));
	        }
	        document.add(table);
	        document.close();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		outputMessage.getBody().write(baos.toByteArray());		
	}

	@Override
	protected Set<Ticket> readInternal(Class<? extends Set<Ticket>> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		return null;
	}

}
