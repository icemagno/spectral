package br.cefetrj.sagitarii.wrappers;

import cmabreu.sagitarii.sdk.Wrapper;


public class Main {
	
	public static void main(String[] args) {
		try {
			Processor myProcessor = new Processor();
			// Echo the input data
			Wrapper wrapper = new Wrapper("GENI", args[0], args[1], myProcessor );
			wrapper.process();
			wrapper.save();
			
		} catch ( Exception e ) {
			
			e.printStackTrace();
			/*
			System.out.println("Wrapper execution error: " );
			for ( StackTraceElement ste : e.getStackTrace()  ) {
				System.out.println( ste.getClassName() );
			}
			*/
		}
	}

}


