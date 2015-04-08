package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
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
	public static void processLine( String[] lineData ) {
		// maxdegree,biptonly,optifunc,mindegree,trianglefree,laplacian,allowdiscgraphs,ordermin,caixa1,slaplacian,adjacency,ordermax
		// 0         1        2        3         4            5         6               7        8      9          10        11       
		
		String maxDegree = lineData[0];
		String biptOnly = lineData[1];
		String optiFunc = lineData[2];
		String minDegree = lineData[3];
		String triangleFree = lineData[4];
		String laplacian = lineData[5];
		String allowDiscGraphs = lineData[6];
		String ordermin = lineData[7];
		String caixa1 = lineData[8];
		String slaplacian = lineData[9];
		String adjacency = lineData[10];
		String ordermax = lineData[11];
		
		generateParameter(adjacency, laplacian, slaplacian, optiFunc, caixa1, ordermin, ordermax, minDegree, maxDegree, triangleFree, allowDiscGraphs, biptOnly);
		
		String libraryDirectory = readLibraryDirectory();
		if( !libraryDirectory.equals("")  ) {
			
			String gengOutput = workFolder + "/saida.g6";
			String showgOutput = workFolder + "/saida.txt";
			
			String geng = libraryDirectory + "/geng -g -q 4 " + gengOutput;
			String showg = libraryDirectory + "/showg -q -A " + gengOutput + " " + showgOutput;
			
			run(geng);
			run(showg);
			
			// awk '/^Graph/{close("graph_"f);f++}{print $0 > "graph_"f}' x.converted
			
		} else {
			System.out.println("Cannot find config file spectral.config");
		}
		
		
		
	}	
	
	
	
	public static void main(String[] args) throws Exception{
		workFolder = args[0];		 

		List<String> inputData = readFile( workFolder + "/sagi_input.txt" );
		if( inputData.size() > 1 ) {

			for( int x=1; x<inputData.size(); x++ ) {
				String[] lineData = inputData.get(x).split(",");
				processLine( lineData );
			}
			
			
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
	
	
	
	public static void run( String application ) {
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
	
	
	public static void generateParameter(String adjacency, String laplacian, String slaplacian, String optiFunc, String caixa1, String ordermin,
			String ordermax, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, String biptOnly) {

			String SEPARATOR_SYMBOL = ";";
		
			int minimumOrder = Integer.valueOf(ordermin);
			int maximumOrder = Integer.valueOf(ordermax);
			
			for (int order = minimumOrder; order < maximumOrder; order++) {

				String graphOptions = "";
				if ( biptOnly.equals("on") ) {
					graphOptions += "-b ";
				}
				if ( triangleFree.equals("on") ) {
					graphOptions += "-t ";
				}
				if ( !allowDiscGraphs.equals("on") ) {
					graphOptions += "-c ";
				}

				String degreeOptions = "";
				degreeOptions += " -d" + minDegree;
				degreeOptions += " -D" + maxDegree;

				String optimizationType = ( caixa1.equals("max") ? "maximization" : "minimization");				
				
				System.out.println( order + SEPARATOR_SYMBOL + optiFunc + SEPARATOR_SYMBOL
						+ optimizationType + SEPARATOR_SYMBOL + graphOptions
						+ SEPARATOR_SYMBOL + degreeOptions);
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


