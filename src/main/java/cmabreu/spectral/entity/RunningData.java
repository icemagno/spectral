package cmabreu.spectral.entity;

import java.util.List;

public class RunningData {
	private String tagExec;
	private String importer;
	private String importerStatus;
	private String owner;
	private List<Fragment> fragments;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	public String getTagExec() {
		return tagExec;
	}
	public void setTagExec(String tagExec) {
		this.tagExec = tagExec;
	}
	public String getImporter() {
		return importer;
	}
	public void setImporter(String importer) {
		this.importer = importer;
	}
	public String getImporterStatus() {
		return importerStatus;
	}
	public void setImporterStatus(String importerStatus) {
		this.importerStatus = importerStatus;
	}
	public List<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}
}
