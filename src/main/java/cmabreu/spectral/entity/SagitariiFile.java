package cmabreu.spectral.entity;

public class SagitariiFile {
	private String fileName;
	private String fileId;
	private String tableName;
	private String experimentId;
	private String shortName;
	
	public String getShortName() {
		shortName = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());		
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		getShortName();
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getExperimentId() {
		return experimentId;
	}

	public void setExperimentId(String experimentId) {
		this.experimentId = experimentId;
	}
	
	
}
