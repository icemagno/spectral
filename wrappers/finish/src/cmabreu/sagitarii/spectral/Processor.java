package cmabreu.sagitarii.spectral;

import java.util.ArrayList;
import java.util.List;

import cmabreu.sagitarii.sdk.IWrapperProcessor;
import cmabreu.sagitarii.sdk.LineData;
import cmabreu.sagitarii.sdk.WrapperHelper;

public class Processor implements IWrapperProcessor {
	private List<String> outputData;
	private List<String> workFiles;
	private String gorder;
	private WrapperHelper helper;
	
	public Processor() {
		workFiles = new ArrayList<String>();
		outputData = new ArrayList<String>();
	}
	
	
	@Override
	public List<String> onNeedOutputData() {
		return outputData;
	}

	
	@Override
	public void processLine( LineData ld, WrapperHelper helper ) throws Exception {
		gorder = ld.getData("gorder"); 
		workFiles.add( helper.getInboxFolder() + ld.getData("workfile") );
		this.helper = helper;
		System.out.println("Prepare to store : " + ld.getData("workfile") );
	}

	
	@Override
	public void onProcessFinish() {
		String fileName = "order_" + gorder + ".zip";
		String zipFile = helper.getOutboxFolder() + fileName;
		helper.storeToZIP(workFiles, zipFile);
		outputData.add("workfile");
		outputData.add( fileName );
	}

	
	@Override
	public void onProcessBeforeStart( LineData headerData ) {
		//
	}


}
