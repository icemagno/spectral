package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.nfunk.jep.JEP;


public class Main {
	private static String workFolder; // args[0]
	private static List<String> outputData = new ArrayList<String>();
	
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
	
	private static List<Double> convertToDouble( List<String> values ) throws Exception {
		List<Double> result = new ArrayList<Double>();
		for ( String value : values ) {
			result.add(  Double.valueOf( value ) );
		}
		return result;
	}
	
	public static double evaluateOptimizationFunction( String optimizationFunction, Double[] values ) {
		for (int i = 0; i < values.length; i++) {
			String old = "q_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
			
			old = "M_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
			
			old = "\\lambda_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
		}
		
		optimizationFunction = optimizationFunction.replace( "\\frac", "" );
		optimizationFunction = optimizationFunction.replaceAll(	"[}]{1,1}+[\\s]*+[{]{1,1}", ")/(" );
		optimizationFunction = optimizationFunction.replace( "}", ")" );
		optimizationFunction = optimizationFunction.replace( "{", "(" );
		
		JEP myParser = new JEP();
		myParser.parseExpression(optimizationFunction);
		
		System.out.println(optimizationFunction + " = " + myParser.getValue());
		
		return myParser.getValue();
		
	}


	/**
	 * Process the lines
	 * @param columns : Array of string
	 * 
	 * Each index of this array will match the index of columns array
	 * 
	 */
	public static void processLine(String header, String line ) throws Exception {
		String[] lineData = line.split(",");
		String inputFile = lineData[ getIndex("eigsolve", header) ];             // Get the eigsolve file
		String optimizationFunction = lineData[ getIndex("optifunc", header) ];  // Get the function
		
		String eigsolveOutput = workFolder + "/inbox/" + inputFile;
		
		List<String> inputData = readFile( eigsolveOutput );
		
		List<Double> convertedValues = convertToDouble( inputData );
		Double[] values = new Double[ convertedValues.size() ];
		values = convertedValues.toArray(values);

		
		Double evaluatedValue = evaluateOptimizationFunction( optimizationFunction, values );
		
		// Send back original data plus file name
		outputData.add( header + ",evaluatedvalue" );
		outputData.add( line + "," + evaluatedValue );
		saveOutput();

	}	
	
	
	public static void saveOutput() throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + "/sagi_output.txt"  ) );
	    for ( String line : outputData ) {
	        pw.println( line );
	    }
	    pw.close();
	}
	
	public static void saveFile( List<String> data,  String fileName  ) throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + File.separator + "outbox" + File.separator + fileName  ) );
	    for ( String line : data ) {
	        pw.println( line );
	    }
	    pw.close();
	}
	
	public static void main(String[] args) throws Exception{
		workFolder = args[0];		 

		List<String> inputData = readFile( workFolder + File.separator + "sagi_input.txt" );
		if( inputData.size() > 1 ) {
			
			String header = inputData.get( 0 ); // Get the CSV header
			String line = inputData.get( 1 );   // MAP just one line
			processLine( header, line );
			
		} else {
			System.out.println("Empty input data file.");
		}
		
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


