package pdf.creator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Main {

	private static String workFolder; // args[0]
	private static List<String> outputData = new ArrayList<String>();
	

	public static void processLine(String header, String line ) throws Exception {

		String inputFolder = workFolder + File.separator + "inbox";
		String outputFolder = workFolder + File.separator + "outbox";
		
		File f = new File( inputFolder );
		ArrayList<Double> values = new ArrayList<Double>();
		values.add(3.4);
		values.add(4.3);
		values.add(5.5);
		values.add(7.8);
		values.add(8.7);
		List<String> generatedPdfs = PDFCreator.gerarPDF(f, values, outputFolder);
		
		// Send back original data plus file name
		outputData.add( header + ",pdfFile" );
		for ( String pdfFile : generatedPdfs ) {
			outputData.add( line + "," + pdfFile );
		}
		saveOutput();
	}


	
	public static void main(String[] args) throws Exception{
		workFolder = args[0];	

		List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
		if( inputData.size() > 1 ) {

			String header = inputData.get( 0 ); // Get the CSV header
			String line = inputData.get( 1 ); // SPLIT MAP just one line
			processLine( header, line );
			
			
		} else {
			System.out.println("Empty input data file.");
		}
		
	}

	
	public static void saveOutput() throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + "/sagi_output.txt"  ) );
	    for ( String line : outputData ) {
	        pw.println( line );
	    }
	    pw.close();
	}
	
	
	/**
	 * This is a method to read the CSV data 
	 * @param file
	 * @return StringBuilder : The file data as a list of lines.
	 * @throws Exception
	 */
	public static List<String> readFile(String file) throws Exception {
		String line = "";
		ArrayList<String> list = new ArrayList<String>();
		BufferedReader br = new BufferedReader( new FileReader( file ) );
		while ( (line = br.readLine() ) != null ) {
		    list.add( line );
		}
		if (br != null) {
			br.close();
		}		
		return list;
	}
	
	
}
