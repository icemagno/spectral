
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "submitForm", results = { @Result (location = "done.jsp", name = "ok") } ) 

@ParentPackage("default")
public class SubmitFormAction extends BasicActionClass {
	
	public String execute () {
		return "ok";
	}


}
