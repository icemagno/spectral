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

/**
 * Interface to Sagitarii API
 * 
 * @author Carlos Magno Oliveira de Abreu
 *
 */
public class SagitariiInterface {
	private String sagitariiHostURL;
	private HttpClient client;
	private String securityToken;
	private List<String> operationLog;


	@SuppressWarnings("deprecation")
	public SagitariiInterface( String sagitariiHostURL, String user, String password ) {
		this.sagitariiHostURL = sagitariiHostURL;
		client = new DefaultHttpClient();
		securityToken = getSecurityToken( user, password );
		operationLog = new ArrayList<String>();
	}
	
	private String getSecurityToken( String user, String password ) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiGetToken") + "," ); 
		sb.append( generateJsonPair("user", user) + "," ); 
		sb.append( generateJsonPair("password", password) ); 
		sb.append("}");
		return execute( sb.toString() );
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


	private String startExperiment( String securityToken, String experimentSerial ) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiStartExperiment") + "," ); 
		sb.append( generateJsonPair("experimentSerial", experimentSerial) + "," );
		sb.append( generateJsonPair("securityToken", securityToken) ); 
		sb.append("}");
		return execute( sb.toString() );
	}
	
	public String getData(String adjacency, String laplacian, String slaplacian, String optiFunc, String caixa1, 
			int order, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, String biptOnly, String maxResults ) {
		StringBuilder data = new StringBuilder();
		data.append("{");
		data.append( generateJsonPair("adjacency", adjacency) + "," );
		data.append( generateJsonPair("laplacian", laplacian) + "," );
		data.append( generateJsonPair("slaplacian", slaplacian) + "," );
		data.append( generateJsonPair("optifunc", optiFunc) + "," );
		data.append( generateJsonPair("caixa1", caixa1) + "," );
		data.append( generateJsonPair("gorder", String.valueOf( order ) ) + "," );
		data.append( generateJsonPair("mindegree", minDegree) + "," );
		data.append( generateJsonPair("maxdegree", maxDegree) + "," );
		data.append( generateJsonPair("trianglefree", triangleFree) + "," );
		data.append( generateJsonPair("allowdiscgraphs", allowDiscGraphs) + "," );
		data.append( generateJsonPair("biptonly", biptOnly) + "," );
		data.append( generateJsonPair("maxresults", maxResults) );
		data.append("}");
		return data.toString();
	}
	
	public void submit( String adjacency, String laplacian, String slaplacian, String optiFunc, String caixa1, String ordermin,
			String ordermax, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, String biptOnly, String maxResults ) {

		
		String experimentSerial = createNewExperiment( securityToken );
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
		sb.append( generateJsonPair("securityToken", securityToken) + "," ); // Must have
		
		StringBuilder data = new StringBuilder();
		data.append("[");
		String dataPrefix = "";
		
		for ( int x = orderMin; x <= orderMax; x++ ) {
			data.append( dataPrefix );
			data.append( getData(adjacency, laplacian, slaplacian, optiFunc, caixa1, x, minDegree, maxDegree, 
					triangleFree, allowDiscGraphs, biptOnly, maxResults ) );
			dataPrefix = ",";
		}
		
		data.append("]");
		
		sb.append( addArray("data", data.toString() ) );
		sb.append("}");

		insert = execute( sb.toString() );
		
		log( "Response to Insert Data : " + insert );
		
		String start = startExperiment( securityToken, experimentSerial );
		log( "Response to Start Experiment call : " + start );
		
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
		    ex.printStackTrace();
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
