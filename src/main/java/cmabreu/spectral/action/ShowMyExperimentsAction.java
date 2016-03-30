
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
	private List<Experiment> experiments;
	
	public String execute () {
		SagitariiInterface si = new SagitariiInterface( getSagitariiUrl() , user.getToken() );
		experiments = si.getMyExperiments();
		
		for ( Experiment exp : experiments ) {
			exp.setFiles( si.getFiles( exp.getTagExec() ) );
		}
		
		return "ok";
	}

	public List<Experiment> getExperiments() {
		return experiments;
	}

	public void setExperiments(List<Experiment> experiments) {
		this.experiments = experiments;
	}
	
	
}
