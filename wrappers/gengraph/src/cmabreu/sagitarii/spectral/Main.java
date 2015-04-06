package cmabreu.sagitarii.spectral;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Main {
	private static String inputFile;		// args[0]
	private static String workFolder;		// args[1]

	public static void main(String[] args) {
		inputFile = args[0];			
		workFolder = args[1];		 
		
		String libraryDirectory = readLibraryDirectory();
		
		if( !libraryDirectory.equals("")  ) {
			
			String geng = libraryDirectory + "/geng -g -q 4 saida.g6";
			String showg = libraryDirectory + "/showg -q -A saida.g6 saida.txt";
			
			System.out.println("Running geng...");
			run(geng);
			System.out.println("Running showg...");
			run(showg);
			System.out.println("end.");
			
			
		} else {
			System.out.println("Cannot find config file spectral.config");
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

        	BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream() ) );
            String line="";
            while ((line = reader.readLine()) != null) {
            	System.out.println( line );
            }
            process.waitFor();
            
        } catch ( Exception e ) {
        	e.printStackTrace();
        }
    }
	
	
	public void generateParameter(String adjacency, String laplacian, String slaplacian, String optiFunc, String caixa1, String ordermin,
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

	
	
}


