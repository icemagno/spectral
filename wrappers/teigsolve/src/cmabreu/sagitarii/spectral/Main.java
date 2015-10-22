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
	private static String wrappersFolder; // args[1]
	
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
		
		String inputFile = lineData[fileIndex];
		String adjacency = lineData[getIndex("adjacency", header)]; 
		String laplacian = lineData[getIndex("laplacian", header)]; 
		String slaplacian = lineData[getIndex("slaplacian", header)]; 

		String adjacencyB = lineData[getIndex("adjacencyb", header)]; 
		String laplacianB = lineData[getIndex("laplacianb", header)]; 
		String slaplacianB = lineData[getIndex("slaplacianb", header)]; 
		
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
		
			String awkOutput = workFolder + "/inbox/" + inputFile;
			String eigsolveOutput = workFolder + "/sagi_output.txt";
	
			System.out.println("User Options: (A): " + adjacency + " (Q): "+ slaplacian + " (L): " + laplacian + 
					" (Ab): " + adjacencyB + " (Qb): "+ slaplacianB + " (Lb): " + laplacianB );
			
			outputCsv.add( header + ",eigsolve" );
			
			if( laplacianB.equals("on")  ) {
				System.out.println("LAPB file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".lapb";
				String eigCommand = libraryDirectory + "/tEigSolve -y -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );

				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				//moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}
			if( adjacencyB.equals("on")  ) {
				System.out.println("ADJB file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".adjb";
				String eigCommand = libraryDirectory + "/tEigSolve -x -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				// moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}
			if( slaplacianB.equals("on")  ) {
				System.out.println("SGNLAPB file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".sgnlapb";
				String eigCommand = libraryDirectory + "/tEigSolve -z -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				//moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}
			
			
			if( laplacian.equals("on")  ) {
				System.out.println("LAP file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".lap";
				String eigCommand = libraryDirectory + "/tEigSolve -l -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				// moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}
			
			if( adjacency.equals("on")  ) {
				System.out.println("ADJ file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".adj";
				String eigCommand = libraryDirectory + "/tEigSolve -a -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				// moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}
			
			if( slaplacian.equals("on")  ) {
				System.out.println("SGNLAP file requested. Will create.");
				String generatedFile = "";
				generatedFile = inputFile + ".sgnlap";
				String eigCommand = libraryDirectory + "/tEigSolve -q -f " + awkOutput + " -d " + workFolder + "/outbox/" + generatedFile;
				runSystem( eigCommand, workFolder + "/outbox/" );
				// tEigSolve produces on same folder of input file.. Lets move it to outbox!
				//moveFile( workFolder + "/inbox/" + generatedFile, workFolder + "/outbox/" + generatedFile );
				System.out.println("command: " + eigCommand );
				outputCsv.add( line + "," + generatedFile );
			}

			saveFile( eigsolveOutput );
			
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
		try (BufferedReader br = new BufferedReader( new FileReader(wrappersFolder + "spectral.config") ) ) {
		    br.readLine();        // First line. This line is for geng location. Discard.
		    line = br.readLine(); // Read again. Second line is for tEigSolve location
		} catch ( Exception e ) {
			
		}
		return line;
	}		
	
	public static void main(String[] args) throws Exception{
		workFolder = args[0];
		wrappersFolder 	= args[1];

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


