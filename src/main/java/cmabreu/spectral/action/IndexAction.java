
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "index", results = { @Result (location = "welcomepage.jsp", name = "ok") } ) 

@ParentPackage("default")
public class IndexAction extends BasicActionClass {
	
	public String execute () {
		return "ok";
	}


}
