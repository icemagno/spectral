
package cmabreu.spectral.action;

import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.services.LatexFunctionGenerator;

@Action(value="getPreview", results= {  
	    @Result(name="ok", type="stream", params = {
                "contentType", "image/png",
                "inputName", "fileInputStream",
                "contentDisposition", "filename=\"image.png\"",
                "bufferSize", "1024"
        }) }
)   

@ParentPackage("default")
public class GetPreviewAction extends BasicActionClass {
	private InputStream fileInputStream;
	private String function;
	
	public String execute () {
		
		LatexFunctionGenerator sc = new LatexFunctionGenerator();
		fileInputStream = sc.getImage( function ) ;
		if( fileInputStream == null ) {
			fileInputStream = sc.getImage( "Function Error" ) ;
		}
		
		return "ok";
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	
	public void setFunction(String function) {
		this.function = function;
	}

}
