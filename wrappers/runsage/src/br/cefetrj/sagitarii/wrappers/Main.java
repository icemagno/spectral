package br.cefetrj.sagitarii.wrappers;

import cmabreu.sagitarii.sdk.Wrapper;


public class Main {
	
	public static void main(String[] args) throws Exception{
		Processor myProcessor = new Processor();
		try {
			// Echo the input data
			Wrapper wrapper = new Wrapper("GENI", args[0], args[1], myProcessor );
			wrapper.process();
			wrapper.save();
			
		} catch ( Exception e ) {
			System.out.println("Wrapper execution error: " );
			for ( StackTraceElement ste : e.getStackTrace()  ) {
				System.out.println( ste.getClassName() );
			}
		}
	}

}


