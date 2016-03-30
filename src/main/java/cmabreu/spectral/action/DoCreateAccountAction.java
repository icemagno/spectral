
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "doCreateAccount", results = { @Result (type="redirect", location = "index", name = "ok") } ) 

@ParentPackage("default")
public class DoCreateAccountAction extends BasicActionClass {
	private String fullName;
	private String userName;
	private String password;
	private String email;
	private String rePassword;
	private String institution;
	
	public String execute () {
		String sagitariiUrl = "http://eic.cefet-rj.br:8134/sagitarii/";
		
		if( !password.equals( rePassword) ) {
			setMessageText("Password does not match. Try Again.");
			return "ok";
		}
		
		if ( ( userName != null ) && ( password != null ) && ( institution != null )  ) {
			// Other tests...
			SagitariiInterface si = new SagitariiInterface(sagitariiUrl, "" );
			String result = si.requestNewUser(fullName,userName,password,email,institution);
			if ( result.equals("RETURN_REQUEST_ERROR")) {
				setMessageText("Error while processing your request.");
			} else if( result.equals("SEM_RESPOSTA") ) {
				setMessageText("No response from Sagitarii server.");
			} else {
				setMessageText("Your request was sent. You must wait for an Administrator aproval.");
			}
		} else {
			setMessageText("Invalid information. Fill all fields.");
		}
		
		return "ok";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}
	
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
