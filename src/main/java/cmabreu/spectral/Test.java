package cmabreu.spectral;

import java.io.ByteArrayInputStream;

import br.cefetrj.parser.FormulaEvaluator;

public class Test {

	public static void main(String[] args) {
		try {
			
			String optiFunc = "\\frac{q_3}{q_4} + lambda_1";
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream( optiFunc.getBytes() );
			FormulaEvaluator eval = new FormulaEvaluator(inputStream);
			eval.parse();
		} catch ( Throwable e ) {
			e.printStackTrace();
		}
	}

}
