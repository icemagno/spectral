package cmabreu.sagitarii.spectral;


public class JobUnity {
	private String values;
	private String imageFile;
	private String evalValue;
	
	public JobUnity( String values, String imageFile, String evalValue ) {
		this.values = values;
		this.imageFile = imageFile;
		this.evalValue = evalValue;
	}

	public String getImageFile() {
		return imageFile;
	}
	
	public String getValues() {
		return values;
	}
	
	public String getEvalValue() {
		return evalValue;
	}
	
}
