package cmabreu.sagitarii.spectral;


public class Test {

	public static void main(String[] args) throws Exception {
		String optimizationFunction = "\\frac{q_3}{4} + \\lambda_1 + \\overline{q_3} + \\mu_2 + \\overline{\\mu_2} + \\overline{\\lambda_1} + \\chi + \\overline{\\chi} + \\omega + \\overline{\\omega}";
		String tmpStr = "\\\\mu_" + Integer.toString(2);
		optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + 3);
		
		System.out.println( optimizationFunction );
	}

}
