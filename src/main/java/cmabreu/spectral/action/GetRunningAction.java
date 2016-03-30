
package cmabreu.spectral.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.entity.Experiment;
import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "getRunning", results = { @Result (location = "getRunning.jsp", name = "ok") 
}, interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class GetRunningAction extends BasicActionClass {
	private List<Experiment> running;
	
	public String execute () {
		SagitariiInterface si = new SagitariiInterface( getSagitariiUrl(), user.getToken() );
		running = si.getRunning();
		return "ok";
	}

	public List<Experiment> getRunning() {
		return running;
	}
}
