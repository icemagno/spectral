
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "createAccount", results = { @Result (location = "createAccount.jsp", name = "ok") } )  

@ParentPackage("default")
public class CreateAccountAction extends BasicActionClass  {
	
	public String execute () {
		return "ok";
	}


}
