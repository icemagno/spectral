package cmabreu.sagitarii.spectral;


public class Test {

	public static void main(String[] args) throws Exception {

		String kLargestDegree = " 3|3|2|2|1|1";
		
		
		String tmpStr = "";
		String optimizationFunction = "q_1 - 2 * d_1 + d_2 - d_4";
		
		try {
			System.out.println("Largest Degree Vector: " + kLargestDegree );
			String[] degrees = kLargestDegree.trim().split("[|]");
			int x = 1;
			for ( String degree : degrees ) {
				tmpStr = "d_" + x;
				System.out.println("replacing " + tmpStr + " by " + degree );
				optimizationFunction = optimizationFunction.replaceAll(tmpStr, degree);
				x++;
			}
		} catch ( Exception ignored ) { }		
		
		System.out.println( optimizationFunction );
		
	}

}
