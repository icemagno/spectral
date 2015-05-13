package cmabreu.sagitarii.spectral;



public class JobUnity {
	private String function;
	private String imageFile;
	private String evalValue;

	
	public JobUnity( String function, String imageFile, String evalValue ) {
		this.function = function;
		this.imageFile = imageFile;
		this.evalValue = evalValue;

	}
	
	public String getImageFile() {
		return imageFile;
	}
	
	public String getFunction() {
		return function;
	}
	
	public String getEvalValue() {
		return evalValue;
	}
	
}
