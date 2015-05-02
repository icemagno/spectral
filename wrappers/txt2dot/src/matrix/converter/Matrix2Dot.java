package matrix.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Matrix2Dot {

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

	
	
	public static void processLine(String header, String line ) throws Exception {
		String[] lineData = line.split(",");
		String inputFile = lineData[ getIndex("showgfile", header) ]; 

		String showgOutput = workFolder + "/inbox/" + inputFile;
		String dotFile = workFolder + "/outbox/" + inputFile + ".dot";


		converterMatriz( showgOutput, dotFile );
		
		
		// Send back original data plus file name
		outputData.add( header + ",dotfile" );
		outputData.add( line + "," + inputFile +  ".dot" );
		saveOutput();
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

	
	public static void saveOutput() throws FileNotFoundException {
	    PrintWriter pw = new PrintWriter( new FileOutputStream( workFolder + "/sagi_output.txt"  ) );
	    for ( String line : outputData ) {
	        pw.println( line );
	    }
	    pw.close();
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
	
	
	/**
	 * Função que converte um arquivo com matriz de adjacência e converte para
	 * um arquivo DOT no formato de entrada do graphViz.
	 * 
	 * @param inputFile
	 *            Nome do arquivo com a matriz a ser convertida.
	 * @throws IOException
	 *             Quando não achar o arquivo especificado no parâmetro.
	 */
	public static void converterMatriz(String inputFile, String outputFile) throws IOException {
		File f = new File(inputFile);

		// Inicialização das variáveis
		BufferedReader in = new BufferedReader(new FileReader(f));
		int vertice_1 = 1;
		int vertice_2 = 1;
		boolean primeiro_vertice = true;

		// Criação do arquivo de saída
		File g = new File( outputFile );
		BufferedWriter out = new BufferedWriter(new FileWriter(g));
		out.write("graph Graph_1 {");
		out.newLine();
		String linha = in.readLine();

		// Leitura do arquivo de entrada e escrita do de saída
		while (in.ready()) {
			linha = in.readLine();
			for (int i = 0; i < linha.length(); i++) {
				if (linha.charAt(i) == '0') {
					vertice_2++;
				}
				if (linha.charAt(i) == '1') {
					if (primeiro_vertice) {
						primeiro_vertice = false;
					} else {
						if (vertice_1 < vertice_2) {
							out.write("   V" + vertice_1 + " -- V" + vertice_2);
							out.newLine();
						}
						vertice_2++;
					}
				}
			}
			vertice_1++;
			vertice_2 = 1;
		}

		// Fechamento dos dois arquivos
		in.close();
		out.write('}');
		out.close();
	}
}
