package br.cefetrj.sagitarii.wrappers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cmabreu.sagitarii.sdk.IWrapperProcessor;
import cmabreu.sagitarii.sdk.LineData;
import cmabreu.sagitarii.sdk.WrapperHelper;

public class Processor implements IWrapperProcessor {
	private List<String> outputData;
	private List<LineData> csvLines;

	
	public Processor() {
		outputData = new ArrayList<String>();
		csvLines = new ArrayList<LineData>();
	}
	
	
	@Override
	public List<String> onNeedOutputData() {
		return outputData;
	}

	
	@Override
	public void processLine( LineData ld, WrapperHelper helper ) throws Exception {
		
		// How to get data from the input CSV
		String function = ld.getData("optifunc"); 
		String geniFile = helper.getWrapperFolder() + "geni.py";
		String inboxFolder = helper.getInboxFolder();
		String g6File = ld.getData("g6splitedfile");
		String parameters = "";
		
		String output = helper.getOutboxFolder();
		
		String chromatic = ld.getData("chromatic");
		String chromaticB = ld.getData("chromaticb");
		String click = ld.getData("click");
		String clickB = ld.getData("clickb");
		String largestDegree = ld.getData("largestdegree");
		String numEdges = ld.getData("numedges");
		
		if( chromatic.equals("on")  ) {
			parameters = parameters + " -a" ;
		}

		if( chromaticB.equals("on")  ) {
			parameters = parameters + " -b";
		}

		if( click.equals("on")  ) {
			parameters = parameters + " -c";
		}
		
		if( clickB.equals("on")  ) {
			parameters = parameters + " -d";
		}
		
		if( largestDegree.equals("on")  ) {
			int position = function.indexOf("d_");
			String largD =  function.substring(position+2, position+4).replace("}", "").trim();
			
			System.out.println("AAAAA " + largD);
			
			parameters = parameters + " -e " + largD;
		}
		
		if( numEdges.equals("on")  ) {
			parameters = parameters + " -g";
		}
		
		System.out.println("Function: " + function );
		System.out.println("Parameters: " + parameters);
		System.out.println("File: " + g6File);
		String inputFile = inboxFolder + g6File;
		//String runFile = "/home/sagitarii/sage-6.8-x86_64-Linux/sage -c 'load(\""+geniFile+"\");geni(\""+output+"\",\""+inputFile+"\",\""+parameters.trim()+"\")'";
		
		String runGeni = helper.getWrapperFolder() + "rungeni.sh";
		String runFile = runGeni + " "+geniFile+ " " +output + " " + inputFile + " \"" +parameters.trim() + "\"";
		
		System.out.println( runFile );
		
		// How to run external applications
		System.out.println( "set permission" );
		helper.runExternal( "chmod 0777 " + runGeni );
		
		System.out.println("run");
		helper.runExternal( runFile );
		
		ld.addValue("sagefile", "saida.csv");		
		csvLines.add( ld );
	}

	
	@Override
	public void onProcessFinish() {
		
		// Get the first data just to create the header. Can be anyone since 
		// the header is present in all items
		// In most cases this method can left untouched because it can
		// be used by MAP (1:1), REDUCE (n:1) or SPLIT (1:n) operators.
		if ( csvLines.size() > 0 ) {
			outputData.add( csvLines.get(0).getCsvHeader() );	
			for ( LineData ld : csvLines ) {
				outputData.add( ld.getCsvLine() );
			}
		} else {
			System.out.println("No data for output");
		}
	}

	
	@Override
	public void onProcessBeforeStart( LineData headerData ) {
		System.out.println("starting...");
	}


}
