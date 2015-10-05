package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


// maxdegree,biptonly,optifunc,mindegree,trianglefree,eigsolveoption,gorder,allowdiscgraphs,caixa1,g6file
// 8,on,lambda,1,on,L,2,on,min,saida_2.g6
// 0         1        2        3         4            5              6      7               8       9 		  	                


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
		String inputFile = lineData[ getIndex("g6file", header) ]; 
		
		System.out.println("will process file " + inputFile);
		
		String gengOutput = workFolder + "/inbox/" + inputFile;
		String awkOutput = workFolder + "/sagi_output.txt";

		String fileId = UUID.randomUUID().toString().toUpperCase().substring(0,8);
		
		String awk = "awk '{x=\""+fileId+"\"++i\".g6\";}{print>x}' " + gengOutput;

		System.out.println("command line: " + awk );
		runSystem( awk, workFolder + "/outbox/" );

		List<String> awkResult = getAwkResult( workFolder + "/outbox/" );
		
		outputCsv.add( header + ",g6splitedfile,g6fileid" );
		for ( String awkFile : awkResult ) {
	        String fileIdIndexed = UUID.randomUUID().toString().toUpperCase().substring(0,8);
			outputCsv.add( line + "," + awkFile + "," + fileIdIndexed );
		}

		for ( String s : outputCsv ) {
			System.out.println( s );
		}
		
		saveFile( awkOutput );
	}	
	
	
	public static void main(String[] args) {
		workFolder = args[0];	
			
		try {
			List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
			if( inputData.size() > 1 ) {
	
				String header = inputData.get( 0 ); // Get the CSV header
				String line = inputData.get( 1 ); // SPLIT MAP just one line
				processLine( header, line );
				
				
			} else {
				System.out.println("Empty input data file.");
			}
		} catch ( Exception e ) {
			System.out.println("Error: " + e.getMessage() );
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
	
	public static List<String> getAwkResult( String outbox ) {
		List<String> awkResult = new ArrayList<String>();
		File folder = new File( outbox );
	    for (final File fileEntry : folder.listFiles()) {
	        if ( !fileEntry.isDirectory()) {
	            awkResult.add( fileEntry.getName() );
	        }
	    }
	    return awkResult;
	}
	
}


