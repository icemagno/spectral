package pdf.creator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Fun��o respons�vel por gerar arquivos PDF com imagens GIF de grafos e os
 * valores de suas respectivas fun��es de otimiza��o.
 * 
 * @author Hugo
 * @author Kaique
 * @author Lucas
 * 
 */

public class PDFCreator {

	/**
	 * Fun��o respons�vel por gerar arquivos PDF com imagens GIF de grafos e os
	 * valores de suas respectivas fun��es de otimiza��o.
	 * 
	 * @param f
	 *            Caminho da pasta aonde est�o os arquivos GIF.
	 * @param values
	 *            Lista com o valor da fun��o de otimiza��o de cada grafo.
	 * @param outputFolder           
	 *   			Pasta onde os PDF serao gravados
	 * @throws DocumentException
	 *             Quando n�o conseguir criar o arquivo PDF.
	 * @throws IOException
	 *             Quando n�o achar a pasta com os arquivos GIF.
	 */
	public static String gerarPDF( List<JobUnity> jobs, String outputFolder ) throws DocumentException, IOException {
		String pdfName = "grafos.pdf";

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(outputFolder + File.separator + pdfName ) );
		document.open();
		
		for ( JobUnity job : jobs ) {
			List<Double> values = job.getValues();
			String imageFileName = job.getImageFile();
			
			Image image = Image.getInstance( imageFileName );

			float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
		               - document.rightMargin() ) / image.getWidth()) * 100;

			image.scalePercent(scaler);			
			
			image.setAlignment(Image.MIDDLE);
			document.add(image);
			Paragraph p = new Paragraph("Value of optimization function: " + values.toString() );
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			document.newPage();
		}
		
		
		document.close();
		return pdfName;
	}

}
