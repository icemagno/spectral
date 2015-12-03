
package cmabreu.spectral.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "getRunning", results = { @Result (location = "getRunning.jsp", name = "ok") 
}, interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class GetRunningAction extends BasicActionClass {
	private String user;
	private String password;
	private String sagitariiUrl;
	private String running;
	
	public String execute () {
		SagitariiInterface si = new SagitariiInterface(sagitariiUrl, user, password);
		running = si.getRunning();
		return "ok";
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setSagitariiUrl(String sagitariiUrl) {
		this.sagitariiUrl = sagitariiUrl;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getSagitariiUrl() {
		return sagitariiUrl;
	}

	public String getRunning() {
		return running;
	}
}
