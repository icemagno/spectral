
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.entity.User;
import cmabreu.spectral.services.SagitariiInterface;

import com.opensymphony.xwork2.ActionContext;

@Action (value = "doLogin", results = { @Result (type="redirect", location = "index", name = "ok") } ) 

@ParentPackage("default")
public class DoLoginAction  {
	private String userName;
	private String password;
	
	public String execute () {
		String sagitariiUrl = "http://eic.cefet-rj.br:8134/sagitarii/";
		
		if ( ( userName != null ) && ( password != null ) ) {
			//SagitariiInterface si = new SagitariiInterface(sagitariiUrl, "" );
			//User user = si.getSecurityToken(userName, password);
			
			User user = new User();
			user.setDetails("asdasds");
			user.setFullName("Carlos Magno Abreu");
			user.setLoginName("loginname");
			user.setUserMail("adsdasds");
			
			if( user != null ) {
				ActionContext.getContext().getSession().put("loggedUser", user);
				ActionContext.getContext().getSession().put("sagitariiUrl", sagitariiUrl);
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
