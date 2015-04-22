package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//maxdegree,biptonly,optifunc,mindegree,trianglefree,laplacian,gorder,allowdiscgraphs,caixa1,slaplacian,adjacency,g6file,     g6filesplited
//8,        on,      \lambda, 1,        on,          on,       8,     on,             min,   on,        on,       saida_8.g6, graph_78.g6
//0         1        2        3         4            5         6      7               8      9          10        11          12


public class Main {
	private static String workFolder; // args[0]
	private static List<String> eigResult = new ArrayList<String>();
	private static List<String> outputCsv = new ArrayList<String>();

	/**
	 * Process the lines
	 * @param columns : Array of string
	 * 
	 * Each index of this array will match the index of columns array
	 * 
	 */
	public static void processLine( String header, String line ) throws Exception {
		String[] lineData = line.split(",");

		String inputFile = lineData[12]; // Index of file name (splited by awk)
		String laplacian = lineData[5]; 
		String slaplacian = lineData[9]; 
		String adjacency = lineData[10]; 
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
		
			String awkOutput = workFolder + "/inbox/" + inputFile;
			String eigsolveOutput = workFolder + "/sagi_output.txt";
	
			/*
				./tEigSolve -a -f graph5 => adjacências
				./tEigSolve -l -f graph5 => laplaciana
				./tEigSolve -q -f graph5 => laplaciana sem sinal
			 */
			
			if( laplacian.equals("on")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -l -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
			}
			
			if( adjacency.equals("on")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -a -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
			}
			
			if( slaplacian.equals("on")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -q -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
			}
	
			getEigResult( workFolder + "/outbox/" );
			
			outputCsv.add( header + ",eigsolve" );
			for ( String eigFile : eigResult ) {
				outputCsv.add( line + "," + eigFile );
			}
			saveFile( eigsolveOutput );

		} else {
			System.out.println("Cannot find config file spectral.config");
		}
					
			
	}	
	

	private static String readLibraryDirectory() {
		String line = "";
		try (BufferedReader br = new BufferedReader( new FileReader( "spectral.config" ) ) ) {
		    br.readLine(); // This line is for geng location. Discard.
		    line = br.readLine(); // Read again. Second line is for tEigSolve location
		} catch ( Exception e ) {
			
		}
		return line;
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


	private static int runSystem( String command, String directoryContext ) {
		List<String> commands = new ArrayList<String>();
		int result = 0;
		File folder = null;
		
		folder = new File( directoryContext );
		
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
	
	
	public static void saveFile(String fileName) throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
	    for ( String outLine : outputCsv ) {
	        pw.println( outLine );
	    }
	    pw.close();
	}
	
	public static void getEigResult( String outbox ) {
		File folder = new File( outbox );
	    for (final File fileEntry : folder.listFiles()) {
	        if ( !fileEntry.isDirectory()) {
	            eigResult.add( fileEntry.getName() );
	        }
	    }
	}
	
}


