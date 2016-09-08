package br.cefetrj.sagitarii.wrappers;

import cmabreu.sagitarii.sdk.Wrapper;


public class Main {
	
	public static void main(String[] args) {
		try {
			Processor myProcessor = new Processor();
			
			Wrapper wrapper = new Wrapper("GENI-v3", args[0], args[1], myProcessor );
			wrapper.process();
			wrapper.save();
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}

}


