package cmabreu.sagitarii.spectral;



public class JobUnity {
	private String function;
	private String imageFile;
	private String evalValue;
	private String caixa1;

	public JobUnity( String function, String imageFile, String evalValue, String caixa1 ) {
		this.function = function;
		this.imageFile = imageFile;
		this.evalValue = evalValue;
		this.caixa1 = caixa1;
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

	public String getCaixa1() {
		return caixa1;
	}
	
}
