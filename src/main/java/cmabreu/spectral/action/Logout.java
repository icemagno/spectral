
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;

import cmabreu.spectral.entity.User;

@Action (value = "index", results = { @Result (location = "welcomepage.jsp", name = "ok") } ) 

@ParentPackage("default")
public class Logout  {
	
	public String execute () {
		ActionContext.getContext().getSession().put("loggedUser", null);		
		
		return "ok";
	}


}
