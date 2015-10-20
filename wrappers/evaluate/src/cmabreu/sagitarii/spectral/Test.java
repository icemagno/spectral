package cmabreu.sagitarii.spectral;

import java.io.ByteArrayInputStream;

import br.cefetrj.parser.FormulaEvaluator;

public class Test {

	public static double evaluateOptimizationFunction( String optimizationFunction, 
			Double[] valuesAdj, Double[] valuesLap, Double[] valuesSgnlap ) throws Exception {
		
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
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream( optimizationFunction.getBytes() );
		FormulaEvaluator eval = new FormulaEvaluator(inputStream);
		
		return eval.evaluate();
		
	}

	public static void main(String[] args) throws Exception {
		Double[] valuesAdj = {2.3,4.1,3.7,1.0};
		Double[] valuesLap = {2.8,5.7,2.0,0.3};
		Double[] valuesSgnlap = {8.0,2.0,5.2,1.1};
		
		String tt = "teste d_6 bla bla";
		String t2 = tt.replaceAll("d_.", "34");
		
		System.out.println( t2 );
		
		/*
		String function = "\\lambda_1 + fsdfdsfds";
		
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream( function.getBytes() );
			FormulaEvaluator eval = new FormulaEvaluator(inputStream);
			eval.parse();
		} catch ( Throwable e ) {
			e.printStackTrace();
		}
		
		System.out.println( evaluateOptimizationFunction( function, valuesAdj,valuesLap,valuesSgnlap ) );
		*/
	}

}
