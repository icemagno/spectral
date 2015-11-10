package cmabreu.sagitarii.spectral;



public class JobUnity {
	private String function;
	private String imageFile;
	private String evalValue;
	private String caixa1;
	private String maxresults;
	private String gorder;
	private String g6fileid;

	public JobUnity( String function, String imageFile, String evalValue, String caixa1, String maxresults, String gorder, String g6fileid ) {
		this.function = function;
		this.imageFile = imageFile;
		this.evalValue = evalValue;
		this.caixa1 = caixa1;
		this.maxresults = maxresults;
		this.gorder = gorder;
		this.g6fileid = g6fileid;
	}
	
	public String getMaxresults() {
		return maxresults;
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
	
	public String getGorder() {
		return gorder;
	}
	
	public String getG6fileid() {
		return g6fileid;
	}
	
}
