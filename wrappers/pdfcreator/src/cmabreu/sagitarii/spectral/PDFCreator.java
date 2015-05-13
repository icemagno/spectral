package cmabreu.sagitarii.spectral;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Função responsável por gerar arquivos PDF com imagens GIF de grafos e os
 * valores de suas respectivas funções de otimização.
 * 
 * @author Hugo
 * @author Kaique
 * @author Lucas
 * 
 */

public class PDFCreator {

	public static String gerarPDF( List<JobUnity> jobs, String outputFolder ) throws DocumentException, IOException {
		String pdfName = "grafos.pdf";

		Document document = new Document( PageSize.A4 );
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFolder + File.separator + pdfName ) );
		document.open();
		
		document.addCreator("Sagitarii");
		document.addAuthor("Carlos Magno Abreu");
		document.addTitle("Spectral Portal");
		
		writer.setPageEvent( new HeaderAndFooter() );
		
		for ( JobUnity job : jobs ) {
			String imageFileName = job.getImageFile();
			String evalValue = job.getEvalValue();
			
			// Graph image
			Image image = Image.getInstance( imageFileName );
			float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
		               - document.rightMargin() ) / image.getWidth()) * 100;
			image.scalePercent(scaler);			
			image.setAlignment(Image.MIDDLE);
			document.add(image);
			
			document.add( new Paragraph("") );
			
			// Function image
			LatexFunctionGenerator sc = new LatexFunctionGenerator();
			ByteArrayOutputStream stream = sc.getImageAsBaos( job.getFunction() + " = " + evalValue ) ;			
			Image imgFunc = Image.getInstance( stream.toByteArray() );
			imgFunc.setAlignment(Image.MIDDLE);
			document.add(imgFunc);			
			
			document.newPage();
		}
		
		
		document.close();
		return pdfName;
	}

}
