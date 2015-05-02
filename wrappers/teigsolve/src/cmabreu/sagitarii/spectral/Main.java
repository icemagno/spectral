package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


// maxdegree,biptonly,optifunc,mindegree,trianglefree,g6splitedfile,eigsolveoption,gorder,allowdiscgraphs,caixa1,g6file
// 8,on,lambda,1,on,L,2,on,min,saida_2.g6,graph1.g6
// 0         1        2        3         4            5              6      7               8      9 	  10                

public class Main {
	private static String workFolder; // args[0]
	private static List<String> outputCsv = new ArrayList<String>();

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

	public static void processLine( String header, String line ) throws Exception {
		String[] lineData = line.split(",");
		
		int fileIndex = getIndex("g6splitedfile", header);
		int optIndex = getIndex("eigsolveoption", header);
		
		String inputFile = lineData[fileIndex];
		String eigsolveoption = lineData[optIndex]; 
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
		
			String awkOutput = workFolder + "/inbox/" + inputFile;
			String eigsolveOutput = workFolder + "/sagi_output.txt";
	
			/*
				./tEigSolve -a -f graph5 => adjacências
				./tEigSolve -l -f graph5 => laplaciana
				./tEigSolve -q -f graph5 => laplaciana sem sinal
			 */
			
			String generatedFile = "";
			if( eigsolveoption.equals("L")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -l -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
				generatedFile = inputFile + ".lap";
			}
			
			if( eigsolveoption.equals("A")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -a -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
				generatedFile = inputFile + ".adj";
			}
			
			if( eigsolveoption.equals("Q")  ) {
				String eigCommand = libraryDirectory + "/tEigSolve -q -f " + awkOutput;
				runSystem( eigCommand, workFolder + "/outbox/" );
				generatedFile = inputFile + ".sgnlap";
			}

			if ( !generatedFile.equals("") ) {
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				outputCsv.add( header + ",eigsolve" );
				outputCsv.add( line + "," + generatedFile );
				saveFile( eigsolveOutput );
			}
			
		} else {
			System.out.println("Cannot find config file spectral.config");
		}
					
			
	}	
	
	private static void moveFile(String source, String dest) throws IOException {
		File src = new File(source);
		File trgt = new File(dest);
		if ( src.exists() ) {
		    Files.copy(src.toPath(), trgt.toPath());
		    src.delete();
		}
	}
	
	private static String readLibraryDirectory() {
		String line = "";
		try (BufferedReader br = new BufferedReader( new FileReader( "spectral.config" ) ) ) {
		    br.readLine();        // First line. This line is for geng location. Discard.
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
			String line = inputData.get( 1 );   // MAP just one line
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

	    System.out.println("");
	    System.out.println( command );
	    System.out.println("");
	    
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
	
	
}


