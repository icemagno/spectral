
package cmabreu.spectral.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.entity.SagitariiFile;
import cmabreu.spectral.services.Downloader;
import cmabreu.spectral.services.PathFinder;
import cmabreu.spectral.services.SagitariiInterface;

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
	private String user;
	private String password;
	private String sagitariiUrl;
	private String experiment;
	private String fileName;
	private InputStream stream;
	
	public String execute () {
		try {
			SagitariiInterface si = new SagitariiInterface(sagitariiUrl, user, password); 
	
			String cachePath = PathFinder.getInstance().getPath() + File.separator + "cache"; 
			File path = new File( cachePath );
			path.mkdirs();

			System.out.println( cachePath );
			
			List<SagitariiFile> files = si.getFiles( experiment );
			if ( files.size() > 0 ) {
				SagitariiFile file = files.get(0);
				fileName = file.getFileName();
				Downloader dl = new Downloader();
				String fileUrl = sagitariiUrl + "/getFile?idFile=" + file.getFileId();
				
				String pdfName = cachePath + File.separator + file.getFileName();
				dl.download( fileUrl, pdfName, true);

				File pdf = new File( pdfName );
		        stream = new FileInputStream( pdf );
			}
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		return "ok";
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setSagitariiUrl(String sagitariiUrl) {
		this.sagitariiUrl = sagitariiUrl;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setExperiment(String experiment) {
		this.experiment = experiment;
	}
	
	public InputStream getStream() {
		return stream;
	}
	
	public String getFileName() {
		return fileName;
	}
}
