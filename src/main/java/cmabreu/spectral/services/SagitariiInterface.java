package cmabreu.spectral.services;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

import cmabreu.spectral.entity.Experiment;
import cmabreu.spectral.entity.ExperimentData;
import cmabreu.spectral.entity.Running;
import cmabreu.spectral.entity.RunningData;
import cmabreu.spectral.entity.SagitariiFile;
import cmabreu.spectral.entity.SagitariiFileData;
import cmabreu.spectral.entity.User;
 
/**
 * Interface to Sagitarii API
 * 
 * @author Carlos Magno Oliveira de Abreu
 *
 */
public class SagitariiInterface {
	private String sagitariiHostURL;
	private HttpClient client;
	private List<String> operationLog;
	private User user;


	@SuppressWarnings("deprecation")
	public SagitariiInterface( String sagitariiHostURL, User tempUser ) {
		this.sagitariiHostURL = sagitariiHostURL;
		client = new DefaultHttpClient();
		if ( tempUser != null ) {
			user = getSecurityToken( tempUser.getLoginName(), tempUser.getPassword() );
			if ( user != null ) { 
				user.setPassword( tempUser.getPassword() );
			} else {
				System.out.println("Invalid Login ");
			}
		} else {
			System.out.println("NULL USER");
		}
		operationLog = new ArrayList<String>();
	}
	
	public User getUser() {
		return user;
	}
	
	public String requestNewUser(String fullName,String userName,String password,String email,String institution) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiRequestNewUser") + "," ); 
		sb.append( generateJsonPair("fullName", fullName) + "," ); 
		sb.append( generateJsonPair("username", userName) + "," ); 
		sb.append( generateJsonPair("password", password) + "," ); 
		sb.append( generateJsonPair("details", institution) + "," ); 
		sb.append( generateJsonPair("mailAddress", email) ); 		
		sb.append("}");
		String result = execute( sb.toString() );
		return result;
	}
	
	public User getSecurityToken( String loginName, String password ) {
		StringBuilder sb = new StringBuilder();
		User user = null;
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiGetToken") + "," ); 
		sb.append( generateJsonPair("user", loginName) + "," ); 
		sb.append( generateJsonPair("password", password) ); 
		sb.append("}");
		
		String result = execute( sb.toString() );
		
		if ( !result.equals("") ) {
			Gson gson = new Gson();
			user = gson.fromJson( result, User.class );
		}
		
		return user;
	}

	
	private String createNewExperiment( String securityToken ) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiCreateExperiment") + "," ); 
		sb.append( generateJsonPair("workflowTag", "SPECTRAL_PORTAL") + "," );
		sb.append( generateJsonPair("securityToken", securityToken) ); 
		sb.append("}");
		return execute( sb.toString() );
	}

	public List<Experiment> getMyExperiments() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiGetExperiments") + "," ); 
		sb.append( generateJsonPair("securityToken", user.getToken()) ); 
		sb.append("}");
		
		String result = execute( sb.toString() );
		
		Gson gson = new Gson();
		ExperimentData data = gson.fromJson( result, ExperimentData.class );
		List<Experiment> experiments = data.getData();
		
		return experiments;
	}


	public List<SagitariiFile> getFiles( String experiment ) {
		List<SagitariiFile> files = new ArrayList<SagitariiFile>();
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiGetFilesExperiment") + "," ); 
		sb.append( generateJsonPair("securityToken", user.getToken()) + "," ); 
		sb.append( generateJsonPair("experimentSerial", experiment) + "," ); 
		sb.append( generateJsonPair("activityTag", "FINISH") + "," ); 
		sb.append( generateJsonPair("rangeStart", "0") + "," ); 
		sb.append( generateJsonPair("rangeEnd", "1000")  ); 
		sb.append("}");

		try {
			String result = execute( sb.toString() );
			
			Gson gson = new Gson();
			SagitariiFileData data = gson.fromJson( result, SagitariiFileData.class );
			files = data.getData();
		} catch ( Exception e ) {
			//
		}
		
		return files;
	}
	
	
	private String startExperiment( String securityToken, String experimentSerial ) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiStartExperiment") + "," ); 
		sb.append( generateJsonPair("experimentSerial", experimentSerial) + "," );
		sb.append( generateJsonPair("securityToken", securityToken) ); 
		sb.append("}");
		return execute( sb.toString() );
	}

	public List<RunningData> getRunning( String experimentSerial ) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			sb.append( generateJsonPair("SagitariiApiFunction", "apiGetRunning") + ",");
			sb.append( generateJsonPair("experimentSerial", experimentSerial ) + ","); 
			sb.append( generateJsonPair("securityToken", user.getToken()) ); 
			sb.append("}");
			
			String result = execute( sb.toString() );
	
			Gson gson = new Gson();
			Running data = gson.fromJson( result, Running.class );
			List<RunningData> runningData = data.getData();
			return runningData;
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		return new ArrayList<RunningData>(); 
		
	}
	
	public String getData(String adjacency, String laplacian, String slaplacian, String adjacencyB, 
			String laplacianB, String slaplacianB, String optiFunc, String caixa1, 
			int order, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, 
			String biptOnly, String maxResults, String chromatic, String chromaticB,
			String click, String clickB, String largestDegree, String numEdges ) {
		StringBuilder data = new StringBuilder();
		data.append("{");
		data.append( generateJsonPair("adjacency", adjacency) + "," );
		data.append( generateJsonPair("laplacian", laplacian) + "," );
		data.append( generateJsonPair("slaplacian", slaplacian) + "," );
		data.append( generateJsonPair("adjacencyb", adjacencyB) + "," );
		data.append( generateJsonPair("laplacianb", laplacianB) + "," );
		data.append( generateJsonPair("slaplacianb", slaplacianB) + "," );
		data.append( generateJsonPair("optifunc", optiFunc) + "," );
		data.append( generateJsonPair("caixa1", caixa1) + "," );
		data.append( generateJsonPair("gorder", String.valueOf( order ) ) + "," );
		data.append( generateJsonPair("mindegree", minDegree) + "," );
		data.append( generateJsonPair("maxdegree", maxDegree) + "," );
		data.append( generateJsonPair("trianglefree", triangleFree) + "," );
		data.append( generateJsonPair("allowdiscgraphs", allowDiscGraphs) + "," );
		data.append( generateJsonPair("biptonly", biptOnly) + "," );

		data.append( generateJsonPair("chromatic", chromatic) + "," );
		data.append( generateJsonPair("chromaticb", chromaticB) + "," );
		data.append( generateJsonPair("click", click) + "," );
		data.append( generateJsonPair("clickb", clickB) + "," );
		data.append( generateJsonPair("largestdegree", largestDegree) + "," );
		data.append( generateJsonPair("numedges", numEdges) + "," );
		
		data.append( generateJsonPair("maxresults", maxResults) );
		data.append("}");
		return data.toString();
	}
	
	public void submit( String adjacency, String laplacian, String slaplacian, String adjacencyB, 
			String laplacianB, String slaplacianB, String optiFunc, String caixa1, String ordermin,
			String ordermax, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, 
			String biptOnly, String maxResults, String chromatic, String chromaticB,
			String click, String clickB, String largestDegree, String numEdges ) {

		
		String experimentSerial = createNewExperiment( user.getToken() );
		log("New experiment " + experimentSerial );
		
		int orderMin = Integer.valueOf( ordermin );
		int orderMax = Integer.valueOf( ordermax );
		
		String insert = "";
		StringBuilder sb = new StringBuilder();
		
		optiFunc = optiFunc.replace("\\", "\\\\");
		
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiReceiveData") + "," ); // Must have 
		sb.append( generateJsonPair("tableName", "spectral_parameters") + "," ); // Must have
		sb.append( generateJsonPair("experimentSerial", experimentSerial ) + "," ); // Must have
		sb.append( generateJsonPair("securityToken", user.getToken() ) + "," ); // Must have
		
		StringBuilder data = new StringBuilder();
		data.append("[");
		String dataPrefix = "";
		
		for ( int x = orderMin; x <= orderMax; x++ ) {
			data.append( dataPrefix );
			data.append( getData(adjacency, laplacian, slaplacian, adjacencyB, laplacianB, slaplacianB, optiFunc, caixa1, x, minDegree, maxDegree, 
					triangleFree, allowDiscGraphs, biptOnly, maxResults,chromatic, chromaticB,
					click, clickB, largestDegree, numEdges ) );
			dataPrefix = ",";
		}
		
		data.append("]");
		
		sb.append( addArray("data", data.toString() ) );
		sb.append("}");

		insert = execute( sb.toString() );
		
		log( "Response to Insert Data : " + insert );
		
		String start = startExperiment( user.getToken(), experimentSerial );
		log( "Response to Start Experiment call : " + start );
		
		log("Data sent: " + sb.toString() );
		
	}
	
	
	/** =======================================================================================
	 * 
	 * 
	 * 		AUXILIARY METHODS
	 * 
	 * 
	 * ======================================================================================= 
	 */
	
	private String execute( String json ) {
		Parameter param = new Parameter("externalForm", json );
		List<Parameter> params = new ArrayList<Parameter>();
		params.add( param );
		return doPostStrings( params, "externalApi");
	}
	
	public List<String> getLog() {
		return operationLog;
	}
	
	private String doPostStrings( List<Parameter> parameters, String action ) {
		String resposta = "SEM_RESPOSTA";
		try {
			String url = sagitariiHostURL + action;
			HttpPost post = new HttpPost(url);
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			for ( Parameter param : parameters ) {
				urlParameters.add(new BasicNameValuePair( param.name, param.value ) );
			}
			post.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			HttpResponse response = client.execute(post);
			int stCode = response.getStatusLine().getStatusCode();
			if ( stCode != 200) {
				resposta = "ERRO_" + stCode;
			} else {
				HttpEntity entity = response.getEntity();
				InputStreamReader isr = new InputStreamReader(entity.getContent(), "UTF-8");
				resposta = convertStreamToString(isr);
				Charset.forName("UTF-8").encode(resposta);
				isr.close();
			}
		} catch (Exception ex) {
		    //ex.printStackTrace();
		} 
		return resposta;
	}
	

	private void log( String log ) {
		operationLog.add( log );
	}
	
	private String convertStreamToString(java.io.InputStreamReader is) {
	    java.util.Scanner s = new java.util.Scanner(is);
		s.useDelimiter("\\A");
	    String retorno = s.hasNext() ? s.next() : "";
	    s.close();
	    return retorno;
	}	

	private String generateJsonPair(String paramName, String paramValue) {
		return "\"" + paramName + "\":\"" + paramValue + "\""; 
	}

	private String addArray(String paramName, String arrayValue) {
		return "\"" + paramName + "\":" + arrayValue ; 
	}
	
}
