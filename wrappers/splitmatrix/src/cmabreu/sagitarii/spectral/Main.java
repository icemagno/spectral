package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	private static String workFolder; // args[0]

	/**
	 * Process the lines
	 * @param columns : Array of string
	 * 
	 * Each index of this array will match the index of columns array
	 * 
	 */
	public static void processLine( String line ) {
		String[] lineData = line.split(",");

		String inputFile = lineData[11]; // Index of file name
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
			
		
			String gengOutput = workFolder + "/inbox/" + inputFile;
			String showgOutput = workFolder + "/outbox/" + inputFile +  ".txt";

			String showg = libraryDirectory + "/showg -a " + gengOutput + " " + showgOutput;
			run(showg);

			String awk = "awk '/^Graph/{close(\"graph_\"f);f++}{print $0 > \"graph_\"f}' " + showgOutput;
			runSystem( awk, workFolder );

			
		} else {
			System.out.println("Cannot find config file spectral.config");
		}
		
		
		
	}	
	
	
	
	public static void main(String[] args) throws Exception{
		workFolder = args[0];		 

		List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
		if( inputData.size() > 1 ) {

			String line = inputData.get( 1 ); // MAP just one line
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
	
	

	private static int runSystem( String command, String directoryContext ) {
		List<String> commands = new ArrayList<String>();
		int result = 0;
		File folder = null;
		
		if ( (directoryContext != null) && (!directoryContext.equals("")) ) {
			folder = new File( directoryContext );
		}
		
	    commands.add("/bin/sh");
	    commands.add("-c");
	    commands.add(command);
		
	    try {
			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
			result = commandExecutor.executeCommand( folder );
	    } catch ( Exception e ) {
	    	result = 1;
	    }
		return result;
	}
	
	
	public static void run( String application ) {
		
		System.out.println(" run(): " + application );
		
		
		Process process = null;
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


