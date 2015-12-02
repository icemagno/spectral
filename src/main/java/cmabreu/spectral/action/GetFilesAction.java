
package cmabreu.spectral.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.services.Downloader;
import cmabreu.spectral.services.PathFinder;

@Action (value = "getFiles", results = {@Result(
	    name = "ok", 
	    type = "stream", 
	    params = { 
	        "contentType", "application/pdf", 
	        "inputName", "stream", 
	        "bufferSize", "1024", 
	        "contentDisposition", "attachment;filename=\"${fileName}\"" 
	    }
	) 
}, interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class GetFilesAction extends BasicActionClass {
	private String sagitariiUrl;
	private String fileName;
	private InputStream stream;
	private String idFile;
	
	public String execute () {
		try {
			fileName = fileName + ".gz";
			
			String cachePath = PathFinder.getInstance().getPath() + "/cache"; 
			File path = new File( cachePath );
			path.mkdirs();

			Downloader dl = new Downloader();
			String fileUrl = sagitariiUrl + "/getFile?idFile=" + idFile;
			
			String pdfName = cachePath + File.separator + fileName;
			dl.download( fileUrl, pdfName, false);

			File pdf = new File( pdfName );
	        stream = new FileInputStream( pdf );
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return "ok";
	}

	public void setSagitariiUrl(String sagitariiUrl) {
		this.sagitariiUrl = sagitariiUrl;
	}
	
	public void setIdFile(String idFile) {
		this.idFile = idFile;
	}
	
	public InputStream getStream() {
		return stream;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
