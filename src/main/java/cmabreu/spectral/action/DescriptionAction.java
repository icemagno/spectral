
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "description", results = { @Result (location = "description.jsp", name = "ok") } ) 

@ParentPackage("default")
public class DescriptionAction extends BasicActionClass  {
	
	public String execute () {
		return "ok";
	}


}
