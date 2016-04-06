package cmabreu.spectral.entity;

import java.util.ArrayList;
import java.util.List;

public class Experiment {
	private String tagExec;
	private String status;
	private String startDate;
	private String elapsedTime;
	private String savers;
	private String importer;
	private List<SagitariiFile> files;
	
	public Experiment() {
		files = new ArrayList<SagitariiFile>();
	}
	
	public void setSavers(String savers) {
		this.savers = savers;
	}
	
	public void setImporter(String importer) {
		this.importer = importer;
	}
	
	public String getSavers() {
		return savers;
	}
	
	public String getImporter() {
		return importer;
	}
	
	public void setFiles(List<SagitariiFile> files) {
		this.files = files;
	}
	
	public List<SagitariiFile> getFiles() {
		return files;
	}
	
	public String getElapsedTime() {
		return elapsedTime;
	}
	
	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getTagExec() {
		return tagExec;
	}
	
	public void setTagExec(String tagExec) {
		this.tagExec = tagExec;
	}

}
