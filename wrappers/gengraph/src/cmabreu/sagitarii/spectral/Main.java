package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Main {
	private static String workFolder; // args[0]
	private static List<String> outputData = new ArrayList<String>();
	
	
	/**
	 * Process the lines
	 * @param columns : Array of string
	 * 
	 * Each index of this array will match the index of columns array
	 * 
	 */
	public static void processLine( String line ) throws Exception {
		String[] lineData = line.split(","); 
		

		// maxdegree,biptonly,optifunc,mindegree,trianglefree,eigsolveoption,gorder,allowdiscgraphs,caixa1
		// 8,on,lambda,1,on,L,1,on,min
		
		// 0         1        2        3         4            5              6      7               8                       

		
		String maxDegree = lineData[0];
		String biptOnly = lineData[1];
		String minDegree = lineData[3];
		String triangleFree = lineData[4];
		String order = lineData[6];
		String allowDiscGraphs = lineData[7];
		
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
			
			String gengOutput = workFolder + "/outbox/saida_" + order +  ".g6";

			String geng = libraryDirectory + "/geng " + degreeOptions + " -g -q " + gengOptions + " " + order + " " + gengOutput;

			run(geng);

			// Send back original data plus file name
			outputData.add( line + ",saida_" + order +  ".g6" );
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
			
			String line = inputData.get(1);  // This is a MAP activity. Just one line.
			outputData.add( inputData.get(0) + ",g6file" );
			processLine( line );
			
			
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


