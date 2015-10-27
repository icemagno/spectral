package cmabreu.sagitarii.spectral;


public class Test {

	public static void main(String[] args) throws Exception {
		String optimizationFunction = "\\frac{q_3}{d_4} + \\lambda_1 + \\overline{q_3} + \\mu_2 + \\overline{\\mu_2} + \\overline{\\lambda_1} + \\chi + \\overline{\\chi} + \\omega + \\overline{\\omega}";
		String tmpStr = "d_.";
		optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + 5);
		
		System.out.println( optimizationFunction );
	}

}
