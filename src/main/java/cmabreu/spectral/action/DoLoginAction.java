
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;

import cmabreu.spectral.entity.User;
import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "doLogin", results = { @Result (type="redirect", location = "${dest}", name = "ok") } ) 

@ParentPackage("default")
public class DoLoginAction  {
	private String userName;
	private String password;
	private String dest;
	
	public String getDest() {
		return dest;
	}
	
	public String execute () {
		String sagitariiUrl = "http://localhost:8080/sagitarii/";
		dest = "index";
		if ( ( userName != null ) && ( password != null ) ) {
			User user = new User();
			user.setLoginName( userName );
			user.setPassword( password );
			SagitariiInterface si = new SagitariiInterface(sagitariiUrl, user );
			user = si.getUser();
			
			if( user != null ) {
				ActionContext.getContext().getSession().put("loggedUser", user);
				ActionContext.getContext().getSession().put("sagitariiUrl", sagitariiUrl);
				dest = "showMyExperiments";
			} else {
				// message to user
			}
			
		}
		
		return "ok";
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
