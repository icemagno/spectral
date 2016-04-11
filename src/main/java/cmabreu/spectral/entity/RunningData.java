package cmabreu.spectral.entity;

import java.util.List;

public class RunningData {
	private String tagExec;
	private String owner;
	private List<Fragment> fragments;
	private List<Importer> importers;
	
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

	public void setImporters(List<Importer> importers) {
		this.importers = importers;
	}
	public List<Importer> getImporters() {
		return importers;
	}
	public List<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(List<Fragment> fragments) {
		this.fragments = fragments;
	}
}
