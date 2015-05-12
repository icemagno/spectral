package pdf.creator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
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

	/**
	 * Função responsável por gerar arquivos PDF com imagens GIF de grafos e os
	 * valores de suas respectivas funções de otimização.
	 * 
	 * @param f
	 *            Caminho da pasta aonde estão os arquivos GIF.
	 * @param values
	 *            Lista com o valor da função de otimização de cada grafo.
	 * @param outputFolder           
	 *   			Pasta onde os PDF serao gravados
	 * @throws DocumentException
	 *             Quando não conseguir criar o arquivo PDF.
	 * @throws IOException
	 *             Quando não achar a pasta com os arquivos GIF.
	 */
	public static List<String> gerarPDF(String imageFileName, List<Double> values, String outputFolder ) throws DocumentException, IOException {
		Document document;
		
		List<String> generatedPdfs = new ArrayList<String>();
		int contador = 1;
		for (Double i : values) {
			document = new Document();
			String pdfName = "grafo" + contador + ".pdf";
			generatedPdfs.add( pdfName );
			PdfWriter.getInstance(document, new FileOutputStream(outputFolder + File.separator + pdfName ) );
			document.open();
			Image img = Image.getInstance( imageFileName );
			img.setAlignment(Image.MIDDLE);
			document.add(img);
			Paragraph p = new Paragraph("Value of optimization function: " + i);
			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);
			document.close();
			contador++;
		}
		return generatedPdfs;
	}

}
