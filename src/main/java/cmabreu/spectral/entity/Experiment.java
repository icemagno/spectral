package cmabreu.spectral.entity;

public class Experiment {
	private String tagExec;
	private String status;
	private String startDate;
	private String elapsedTime;
	
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
