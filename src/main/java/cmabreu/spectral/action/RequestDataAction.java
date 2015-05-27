
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@Action (value = "requestData", results = { @Result (location = "requestData.jsp", name = "ok") 
}, interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class RequestDataAction extends BasicActionClass {
	
	public String execute () {
		
		return "ok";
	}


}
