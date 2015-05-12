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


public class Main {
	private static String workFolder; // args[0]
	private static List<String> outputCsv = new ArrayList<String>();
	private static List<String> folderContent = new ArrayList<String>();

	
	
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

		String inputFile = lineData[ getIndex("dotfile", header) ]; 
		
		String dotOutput = workFolder + File.separator + "inbox" + File.separator + inputFile;
		String outbox = workFolder + File.separator + "outbox" + File.separator ;	
		String inbox = workFolder + File.separator + "inbox" + File.separator ;	
		String gvOutput = workFolder + File.separator + "sagi_output.txt";

		// 0         1        2        3         4            5              6      7               8      9      10        11         
		// maxdegree,biptonly,optifunc,mindegree,trianglefree,eigsolveoption,gorder,allowdiscgraphs,caixa1,g6file,showgfile,dotFile
		// 16,off,lambda,4,off,L,9,on,min,saida_9.g6,saida_9.g6.txt,saida_9.g6.txt.dot
		
		String graphviz = "dot -Tgif -O " + dotOutput;

		runSystem( graphviz, outbox );
		getFolderContent( inbox );
		
		outputCsv.add( header + ",gvfile" );
		for ( String gvFile : folderContent ) {
			moveFile( inbox + gvFile, outbox + gvFile);
			outputCsv.add( line + "," + gvFile );
		}

		saveFile( gvOutput );
			
		
	}	
	
	private static void moveFile(String source, String dest) throws IOException {
		File src = new File(source);
		File trgt = new File(dest);
		if ( src.exists() ) {
		    Files.copy(src.toPath(), trgt.toPath());
		    src.delete();
		}
	}
	
	public static void getFolderContent( String outbox ) {
		File folder = new File( outbox );
	    for (final File fileEntry : folder.listFiles()) {
	        if ( (fileEntry.getName().contains(".gif") ) && !fileEntry.isDirectory()) {
	            folderContent.add( fileEntry.getName() );
	        }
	    }
	}
	
	public static void saveOutput() throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + "/sagi_output.txt"  ) );
	    for ( String line : outputCsv ) {
	        pw.println( line );
	    }
	    pw.close();
	}
	
	public static void saveFile(String fileName) throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
	    for ( String outLine : outputCsv ) {
	        pw.println( outLine );
	    }
	    pw.close();
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


