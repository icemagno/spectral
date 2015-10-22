package cmabreu.sagitarii.spectral;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.cefetrj.parser.FormulaEvaluator;

public class Main {
	private static String workFolder; // args[0]
	private static List<String> outputData = new ArrayList<String>();

	public static double evaluateOptimizationFunction(EvaluationInfo evalInfo) {

		String optimizationFunction = evalInfo.optimizationFunction;
		System.out.println("Original optimization function: " + optimizationFunction);

		String tmpStr;

		if ( evalInfo.valuesAdjs.length != 0 ) {		
			for (int i = 0; i < evalInfo.valuesAdjs.length; i++) {
	
				int index = (evalInfo.valuesAdjs.length - 1) - i;
	
				tmpStr = "d_" + Integer.toString(i + 1);
				if (evalInfo.valuesDs.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesDs[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.valuesDs[index]);
				}
	
				tmpStr = "\\\\overline{q_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesSgnlapBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlapBars[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr,	evalInfo.valuesSgnlapBars[index].toString());
				}
	
				tmpStr = "\\\\overline{\\\\mu_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesLapBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLapBars[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr, evalInfo.valuesLapBars[index].toString());
				}
	
				tmpStr = "\\\\overline{\\\\lambda_" + Integer.toString(i + 1) + "}";
				if (evalInfo.valuesAdjBars.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjBars[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr,	evalInfo.valuesAdjBars[index].toString());			
				}
				
				tmpStr = "q_" + Integer.toString(i + 1);
				if (evalInfo.valuesSgnlaps.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesSgnlaps[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.valuesSgnlaps[index]);
				}
	
				tmpStr = "\\\\mu_" + Integer.toString(i + 1);
				if (evalInfo.valuesLaps.length > 0) {
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesLaps[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.valuesLaps[index]);
				}
	
				tmpStr = "\\\\lambda_" + Integer.toString(i + 1);
				if (evalInfo.valuesAdjs.length > 0) { 
					System.out.println("replacing " + tmpStr + " by " + evalInfo.valuesAdjs[index].toString() );
					optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.valuesAdjs[index]);
				}
			}
		}
		tmpStr = "\\\\overline{\\\\chi}";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdjBar );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, evalInfo.valueChiAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\\\chi";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueChiAdj );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, evalInfo.valueChiAdj);
		} catch ( Exception ignored ) { }

		tmpStr = "\\\\overline{\\\\omega";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdjBar );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr,	evalInfo.valueOmegaAdjBar);
		} catch ( Exception ignored ) { }

		tmpStr = "\\\\omega";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.valueOmegaAdj );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, evalInfo.valueOmegaAdj);
		} catch ( Exception ignored ) { }


		tmpStr = "ORDER";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.gorder );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.gorder);
		} catch ( Exception ignored ) { }
		
		tmpStr = "d_.";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.kLargestDegree );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.kLargestDegree);
		} catch ( Exception ignored ) { }


		tmpStr = "SIZE";
		try {
			System.out.println("replacing " + tmpStr + " by " + evalInfo.numEdges );
			optimizationFunction = optimizationFunction.replaceAll(tmpStr, "" + evalInfo.numEdges);
		} catch ( Exception ignored ) { }
			
		System.out.println("optimization function: " + optimizationFunction);

		double result = 0.0;
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream( optimizationFunction.getBytes() );
			FormulaEvaluator eval = new FormulaEvaluator(inputStream);
			result = eval.evaluate();
		} catch (Throwable e) {
			System.out.println("FUNCTION ERROR: " + e.getMessage() );
		}

		System.out.println("optimization function result: "	+ result);

		return result;

	}

	public static void processJob(JobUnity job) throws Exception {
		List<String> convertedAdj = new ArrayList<String>();
		List<String> convertedLap = new ArrayList<String>();
		List<String> convertedSgnLap = new ArrayList<String>();

		List<String> convertedAdjBar = new ArrayList<String>();
		List<String> convertedLapBar = new ArrayList<String>();
		List<String> convertedSgnLapBar = new ArrayList<String>();

		List<Integer> convertedGreatestDegrees = new ArrayList<Integer>();

		if (job.isAdj()) {
			String adjFile = workFolder + "/" + "inbox"	+ "/" + job.getAdjFile();
			convertedAdj = CsvReader.readFile(adjFile);
			System.out.println( "Adj size: " + convertedAdj.size() );
		}

		if (job.isLap()) {
			String lapFile = workFolder + "/" + "inbox"	+ "/" + job.getLapFile();
			convertedLap = CsvReader.readFile(lapFile);
		}

		if (job.isSgnLap()) {
			String sgnlapFile = workFolder + "/" + "inbox" + "/" + job.getSgnLapFile();
			convertedSgnLap = CsvReader.readFile(sgnlapFile);
		}

		if (job.isAdjBar()) {
			String adjBarFile = workFolder + "/" + "inbox" + "/" + job.getAdjBarFile();
			convertedAdjBar = CsvReader.readFile(adjBarFile);
		}

		if (job.isLapBar()) {
			String lapBarFile = workFolder + "/" + "inbox" + "/" + job.getLapBarFile();
			convertedLapBar = CsvReader.readFile(lapBarFile);
		}

		if (job.isSgnLapBar()) {
			String sgnlapBarFile = workFolder + "/inbox" + "/" + job.getSgnLapBarFile();
			convertedSgnLapBar = CsvReader.readFile(sgnlapBarFile);
		}

		
		String[] valuesAdj = new String[convertedAdj.size()];
		valuesAdj = convertedAdj.toArray(valuesAdj);

		String[] valuesLap = new String[convertedLap.size()];
		valuesLap = convertedLap.toArray(valuesLap);

		String[] valuesSgnLap = new String[convertedSgnLap.size()];
		valuesSgnLap = convertedSgnLap.toArray(valuesSgnLap);

		String[] valuesAdjBar = new String[convertedAdjBar.size()];
		valuesAdjBar = convertedAdjBar.toArray(valuesAdjBar);

		String[] valuesLapBar = new String[convertedLapBar.size()];
		valuesLapBar = convertedLapBar.toArray(valuesLapBar);

		String[] valuesSgnLapBar = new String[convertedSgnLapBar.size()];
		valuesSgnLapBar = convertedSgnLapBar.toArray(valuesSgnLapBar);

		String[] valuesD = new String[convertedGreatestDegrees.size()];
		valuesD = convertedGreatestDegrees.toArray(valuesD);

		Double evaluatedValue = evaluateOptimizationFunction(
				new EvaluationInfo(
						job.getOptimizationFunction(), 
						valuesAdj, 
						valuesLap,
						valuesSgnLap, 
						job.getKLargestDegree(), 
						job.getNumEdges(), 
						valuesD, 
						valuesAdjBar,
						valuesLapBar, 
						valuesSgnLapBar, 
						job.getChi(), 
						job.getChiBar(),
						job.getOmega(), 
						job.getOmegaBar(),
						job.getGorder() ) );

		
		outputData.add("optifunc,g6fileid,evaluatedvalue,maxresults,caixa1,gorder");
		outputData.add(job.getOptimizationFunction() + "," + job.getG6fileid() + 
				"," + evaluatedValue + "," + job.getMaxResults() + "," + job.getCaixa1() + "," + job.getGorder() );
		saveOutput();

	}

	public static void saveOutput() throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(workFolder
				+ "/sagi_output.txt"));
		for (String line : outputData) {
			pw.println(line);
		}
		pw.close();
	}

	public static void saveFile(List<String> data, String fileName)
			throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new FileOutputStream(workFolder
				+ "/" + "outbox" + "/" + fileName));
		for (String line : data) {
			pw.println(line);
		}
		pw.close();
	}

	public static void main(String[] args) throws Exception {
		workFolder = args[0];

		List<String> inputData = CsvReader.readFile(workFolder + "/" + "sagi_input.txt");
		if (inputData.size() > 1) {
			String header = inputData.get(0); // Get the CSV header

			JobUnity job = new JobUnity();

			System.out.println("Input files: ");
			for (int x = 1; x < inputData.size(); x++) { // REDUCE read all
				// lines
				String line = inputData.get(x);
				String[] lineData = line.split(",");

				/*
				 * Get tEigSolve file
				 */
				String inputFile = lineData[CsvReader.getIndex("workfile",	header)];

				/*
				 * Get function expression
				 */
				String optimizationFunction = lineData[CsvReader.getIndex("optifunc", header)];

				/*
				 * Get source file reference
				 */
				String g6fileid = lineData[CsvReader.getIndex("g6fileid", header)];

				/*
				 * Get maximum results to show
				 */
				String maxResults = lineData[CsvReader.getIndex("maxresults", header)];
				String caixa1 = lineData[CsvReader.getIndex("caixa1", header)];
				String gorder = lineData[CsvReader.getIndex("gorder", header)];

				job.setOptimizationFunction(optimizationFunction);
				job.setHeader(header);
				job.setG6fileid(g6fileid);
				job.setMaxResults(maxResults);
				job.setCaixa1(caixa1);
				job.setGorder(gorder);

				System.out.println(" > " + inputFile);
				
				if (inputFile.contains(".lap")) {
					System.out.println("found lap file " + inputFile );
					job.setLapFile(inputFile);
				}
				if (inputFile.contains(".adj")) {
					System.out.println("found adj file " + inputFile );
					job.setAdjFile(inputFile);
				}
				if (inputFile.contains(".sgnlap")) {
					System.out.println("found sgnlap file " + inputFile );
					job.setSgnLapFile(inputFile);
				}

				if (inputFile.contains(".lapb")) {
					System.out.println("found lapb file " + inputFile );
					job.setLapBarFile(inputFile);
				}
				if (inputFile.contains(".adjb")) {
					System.out.println("found adjb file " + inputFile );
					job.setAdjBarFile(inputFile);
				}
				if (inputFile.contains(".sgnlapb")) {
					System.out.println("found sgnlapb file " + inputFile );
					job.setSgnLapBarFile(inputFile);
				}
				if (inputFile.contains(".csv")) {
					System.out.println("found inv file " + inputFile );
					job.setInvariantsFile(workFolder, inputFile);
				}

			}

			processJob(job);

		} else {
			System.out.println("Empty input data file.");
		}

	}
}
