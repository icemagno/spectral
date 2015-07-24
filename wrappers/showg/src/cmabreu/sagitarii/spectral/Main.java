package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//maxdegree,biptonly,optifunc,mindegree,trianglefree,eigsolveoption,gorder,allowdiscgraphs,caixa1,g6file
//8,on,lambda,1,on,L,2,on,min,saida_2.g6
//0         1        2        3         4            5              6      7               8       9 		  	                


public class Main {
	private static String workFolder; // args[0]
	private static String wrappersFolder; // args[1]
	
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

	public static void processLine(String header, String line ) throws Exception {
		String[] lineData = line.split(",");
		String inputFile = lineData[ getIndex("g6splitedfile", header) ]; 
		String gengOutput = workFolder + "/inbox/" + inputFile;
		String showgOutput = workFolder + "/outbox/" + inputFile + ".txt";

		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
		
			String showg = libraryDirectory + "/showg -A -q " + gengOutput + " " + showgOutput;

			run(showg);

			// Send back original data plus file name
			outputData.add( header + ",showgfile" );
			outputData.add( line + "," + inputFile +  ".txt" );
			saveOutput();
			
		} else {
			System.out.println("Cannot find config file spectral.config");
		}
		
		
		
	}	
	
	public static void saveOutput() throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + "/sagi_output.txt"  ) );
	    for ( String line : outputData ) {
	        pw.println( line );
	    }
	    pw.close();
	}
	
	
	public static void run( String application ) {
		Process process = null;
		
		System.out.println( application );
		
        try {
        	process = Runtime.getRuntime().exec( application );

        	BufferedReader reader = new BufferedReader( new InputStreamReader(process.getInputStream() ) );
            String line="";
            while ((line = reader.readLine()) != null) {
            	System.out.println( line );
            }
            process.waitFor();
            
        } catch ( Exception e ) {
        	e.printStackTrace();
        }
    }
	
	public static void main(String[] args) throws Exception{
		workFolder 		= args[0];	
		wrappersFolder 	= args[1];

		List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
		if( inputData.size() > 1 ) {

			String header = inputData.get( 0 ); // Get the CSV header
			String line = inputData.get( 1 ); // SPLIT MAP just one line
			processLine( header, line );
			
			
		} else {
			System.out.println("Empty input data file.");
		}
		
	}

	
	private static String readLibraryDirectory() {
		String line = "";
		try (BufferedReader br = new BufferedReader( new FileReader(  wrappersFolder + "spectral.config" ) ) ) {
		    line = br.readLine(); 
		} catch ( Exception e ) {
			
		}
		return line;
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


