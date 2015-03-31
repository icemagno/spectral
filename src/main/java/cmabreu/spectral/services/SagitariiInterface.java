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


public class SagitariiInterface {
	private String sagitariiHostURL;
	private HttpClient client;
	private String securityToken;
	private List<String> operationLog;

	
	//=========================== API TEST =====================================
	
	private String createTable( String securityToken ) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiCreateTable") + "," ); 
		sb.append( generateJsonPair("tableName", "test00010") + "," );
		sb.append( generateJsonPair("tableDescription", "This is a test") + "," );
		sb.append( generateJsonPair("my_field1", "STRING") + "," );
		sb.append( generateJsonPair("my_field2", "FILE") + "," );
		sb.append( generateJsonPair("my_field3", "INTEGER") + "," );
		sb.append( generateJsonPair("securityToken", securityToken) ); 
		sb.append("}");
		return execute( sb.toString() );
	}
	
	
	//==========================================================================
	
	public SagitariiInterface( String sagitariiHostURL, String user, String password ) {
		this.sagitariiHostURL = sagitariiHostURL;
		client = new DefaultHttpClient();
		// We must login first!
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
	
	
	public void submit( String adjacency, String laplacian,	 String slaplacian,	 String optiFunc, String caixa1, String ordermin,
			String ordermax, String minDegree, String maxDegree, String triangleFree, String allowDiscGraphs, String biptOnly ) {

		
		String experimentSerial = createNewExperiment( securityToken );
		log("New experiment " + experimentSerial );
		
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append( generateJsonPair("SagitariiApiFunction", "apiReceiveData") + "," ); // Must have 
		sb.append( generateJsonPair("tableName", "spectral_parameters") + "," ); // Must have
		sb.append( generateJsonPair("experimentSerial", experimentSerial ) + "," ); // Must have
		sb.append( generateJsonPair("securityToken", securityToken) + "," ); // Must have

		sb.append( generateJsonPair("adjacency", adjacency) + "," );
		sb.append( generateJsonPair("laplacian", laplacian) + "," );
		sb.append( generateJsonPair("slaplacian", slaplacian) + "," );
		sb.append( generateJsonPair("optiFunc", optiFunc) + "," );
		sb.append( generateJsonPair("caixa1", caixa1) + "," );
		sb.append( generateJsonPair("ordermin", ordermin) + "," );
		sb.append( generateJsonPair("ordermax", ordermax) + "," );
		sb.append( generateJsonPair("minDegree", minDegree) + "," );
		sb.append( generateJsonPair("maxDegree", maxDegree) + "," );
		sb.append( generateJsonPair("triangleFree", triangleFree) + "," );
		sb.append( generateJsonPair("allowDiscGraphs", allowDiscGraphs) + "," );
		sb.append( generateJsonPair("biptOnly", biptOnly) );
		sb.append("}");

		String insert = execute( sb.toString() );
		log( "Response to Insert Data : " + insert );
		
		String start = startExperiment( securityToken, experimentSerial );
		log( "Response to Start Experiment call : " + start );
		
		String test = createTable( securityToken );
		log( "Response to Create Table : " + test );
		
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

	
}
