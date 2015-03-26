
package cmabreu.spectral.action;

import java.io.ByteArrayInputStream;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action(value="getFile", results= {  
	    @Result(name="ok", type="stream", params = {
                "contentType", "${contentType}",
                "inputName", "fileInputStream",
                "contentDisposition", "filename=\"${fileName}\"",
                "bufferSize", "1024"
        }) }
)   

@ParentPackage("default")
public class GetFileAction extends BasicActionClass {
	private String fileName;
	private String contentType;
	private Integer idFile;
	private ByteArrayInputStream fileInputStream;
	
	public String execute () {
		
		/*
		cmabreu.sagitarii.persistence.entity.File file = null;
		
		try {
			FileService fs = new FileService();
			if ( (idFile != null) && ( idFile > -1 ) ) {
				file = fs.getFile( idFile );
			}
			
			if ( file != null ) {
		        byte[] theFile = file.getFile();
		        contentType = file.getContentType();
		        fileInputStream = new ByteArrayInputStream( theFile );
		        fileName = file.getFileName();
			} else {
				System.out.println("FILE IS NULL");
			}
			
		} catch ( Exception e ) {
            //
		}
		*/
		
		return "ok";
	}

	
	public void setIdFile(Integer idFile) {
		this.idFile = idFile;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getContentType() {
		return contentType;
	}
	
	public ByteArrayInputStream getFileInputStream() {
		return fileInputStream;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	

}
