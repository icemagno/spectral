
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "login", results = { @Result (location = "login.jsp", name = "ok") } ) 

@ParentPackage("default")
public class LoginAction  {
	
	public String execute () {
		return "ok";
	}

}
