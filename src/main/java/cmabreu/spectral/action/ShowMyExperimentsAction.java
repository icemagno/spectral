
package cmabreu.spectral.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.entity.Experiment;
import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "showMyExperiments", results = { @Result (location = "myExperiments.jsp", name = "ok") 
}, interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class ShowMyExperimentsAction extends BasicActionClass {
	private String user;
	private String password;
	private String sagitariiUrl;
	private List<Experiment> experiments;
	
	public String execute () {
		SagitariiInterface si = new SagitariiInterface(sagitariiUrl, user, password);
		experiments = si.getMyExperiments();
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

	public List<Experiment> getExperiments() {
		return experiments;
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

	public void setExperiments(List<Experiment> experiments) {
		this.experiments = experiments;
	}
	
	
}
