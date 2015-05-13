package cmabreu.sagitarii.spectral;

import org.nfunk.jep.JEP;

public class Test {

	public static double evaluateOptimizationFunction( String optimizationFunction, 
			Double[] valuesAdj, Double[] valuesLap, Double[] valuesSgnlap ) {
		
		for (int i = 0; i < valuesAdj.length; i++) {
			String old = "q_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + valuesSgnlap[i] );
			
			old = "\\mu_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + valuesLap[i] );
			
			old = "\\lambda_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + valuesAdj[i] );
		}
		
		optimizationFunction = optimizationFunction.replace( "\\frac", "" );
		optimizationFunction = optimizationFunction.replaceAll(	"[}]{1,1}+[\\s]*+[{]{1,1}", ")/(" );
		optimizationFunction = optimizationFunction.replace( "}", ")" );
		optimizationFunction = optimizationFunction.replace( "{", "(" );
		
		System.out.println( optimizationFunction );
		
		JEP myParser = new JEP();
		myParser.parseExpression(optimizationFunction);
		
		return myParser.getValue();
		
	}

	public static void main(String[] args) {
		Double[] valuesAdj = {2.3,4.1,3.7,1.0};
		Double[] valuesLap = {2.8,5.7,2.0,0.3};
		Double[] valuesSgnlap = {8.0,2.0,5.2,1.1};
		
		System.out.println( evaluateOptimizationFunction("\\frac{q_3}{q_4}+\\lambda_1",valuesAdj,valuesLap,valuesSgnlap) );
	}

}
