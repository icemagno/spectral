package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Wrapper {

	private static String workFolder; // args[0]
	private static List<String> outputData = new ArrayList<String>();
	private static List<JobUnity> jobs = new ArrayList<JobUnity>();
	
	private static int getIndex( String key, String header) {
		int index = -1;
		String[] headers = header.split(",");
		for ( int x = 0; x < headers.length; x++  ) {
			if ( headers[x].equals( key )  ) {
				index = x;
			}
		}
		return index;
	}	
	
	public static void processLine(String header, String line ) throws Exception {
		String[] lineData = line.split(",");
		String inputFolder = workFolder + File.separator + "inbox" + File.separator;

		String imageFile = inputFolder + File.separator + lineData[ getIndex("gvfile", header) ];
		String valuesFile = inputFolder + File.separator + lineData[ getIndex("eigsolve", header) ];
		String evalValue = lineData[ getIndex("evaluatedvalue", header) ];
		
		List<String> valuesS = readFile( valuesFile );
		jobs.add( new JobUnity(valuesS.toString(), imageFile, evalValue) );

	}


	public static void main(String[] args) throws Exception{
		workFolder = args[0];	

		List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
		if( inputData.size() > 1 ) {

			String header = inputData.get( 0 ); // Get the CSV header
			String line = "";
			for ( int x = 1; x < inputData.size(); x++  ) {
				line = inputData.get( x );      // REDUCE process every line
				processLine( header, line );
			}
			
			
			String outputFolder = workFolder + File.separator + "outbox" + File.separator;

			String generatedPdf = PDFCreator.gerarPDF( jobs, outputFolder );
			outputData.add( "pdfFile" );
			outputData.add( generatedPdf );
			saveOutput();
			

			
			
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
