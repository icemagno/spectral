package pdf.creator;

import java.util.List;

public class JobUnity {
	private List<Double> values;
	private String imageFile;
	
	public JobUnity( List<Double> values, String imageFile ) {
		this.values = values;
		this.imageFile = imageFile;
	}

	public String getImageFile() {
		return imageFile;
	}
	
	public List<Double> getValues() {
		return values;
	}
	
}
