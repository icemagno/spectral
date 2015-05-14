package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
		
	
	/**
	 * Process the lines
	 * @param columns : Array of string
	 * 
	 * Each index of this array will match the index of columns array
	 * 
	 */
	public static void processLine( String header, String line ) throws Exception {
		String[] lineData = line.split(","); 

		// 0         1        2        3         4            5              6      7               8                       
		// maxdegree,biptonly,optifunc,mindegree,trianglefree,eigsolveoption,gorder,allowdiscgraphs,caixa1
		// 8,on,lambda,1,on,L,1,on,min
		
		String maxDegree = lineData[ getIndex("maxdegree", header) ]; 
		String biptOnly = lineData[ getIndex("biptonly", header) ]; 
		String minDegree = lineData[ getIndex("mindegree", header) ];
		String triangleFree = lineData[ getIndex("trianglefree", header) ];
		String order = lineData[ getIndex("gorder", header) ];
		String allowDiscGraphs = lineData[ getIndex("allowdiscgraphs", header) ];
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
			
			String degreeOptions = " -d" + minDegree + " -D" + maxDegree;
			String gengOptions = "";
			if ( biptOnly.equals("on") ) {
				gengOptions += "-b ";
			}
			if ( triangleFree.equals("on") ) {
				gengOptions += "-t ";
			}			
			if ( !allowDiscGraphs.equals("on") ) {
				gengOptions += "-c ";
			}			
			
			String fileName = UUID.randomUUID().toString().toUpperCase().substring(0,8) + "_" + order + ".g6";
			String gengOutput = workFolder + "/outbox/" + fileName;

			String geng = libraryDirectory + "/geng " + degreeOptions + " -g -q " + gengOptions + " " + order + " " + gengOutput;

			run(geng);

			// Send back original data plus file name
			outputData.add( header + ",g6file" );
			outputData.add( line + "," + fileName );
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

	
	private static String readLibraryDirectory() {
		String line = "";
		try (BufferedReader br = new BufferedReader( new FileReader( "spectral.config" ) ) ) {
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


