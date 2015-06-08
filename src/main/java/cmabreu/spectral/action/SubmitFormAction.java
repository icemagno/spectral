
package cmabreu.spectral.action;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import br.cefetrj.parser.FormulaEvaluator;
import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "doSubmitFunction", results = { @Result (location = "done.jsp", name = "ok") } ) 

@ParentPackage("default")
public class SubmitFormAction extends BasicActionClass {
	private String adjacency;
	private String laplacian;
	private String slaplacian;
	private String optiFunc;
	private String caixa1;
	private String ordermin;
	private String ordermax;
	private String minDegree;
	private String maxDegree;
	private String triangleFree;
	private String allowDiscGraphs;
	private String biptOnly;
	private String maxResults;
	
	private String user;
	private String password;
	private String sagitariiUrl;
	
	private List<String> log;
	
	public String execute () {
		
		if ( (optiFunc != null) && (!optiFunc.equals("")) ) {
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream( optiFunc.getBytes() );
				FormulaEvaluator eval = new FormulaEvaluator(inputStream);
				eval.parse();
			} catch ( Exception e ) {
				setMessageText( "Error in Optimization Function validation" );
				return "ok";
			}
		} else {
			setMessageText( "You must provide an Optimization Function" );
			return "ok";
		}

		if( caixa1 != null ) {
			try {
				SagitariiInterface si = new SagitariiInterface(sagitariiUrl, user, password);
				si.submit(adjacency, laplacian, slaplacian, optiFunc, caixa1, ordermin, ordermax, minDegree, maxDegree, 
						triangleFree, allowDiscGraphs, biptOnly, maxResults);
				log = si.getLog();
			} catch ( Exception e ) {
				e.printStackTrace();
				setMessageText( e.getMessage() );
			}
		} else {
			setMessageText("Invalid arguments");
		}
		
		return "ok";
	}

	public List<String> getLog() {
		return log;
	}

	public void setAdjacency(String adjacency) {
		this.adjacency = adjacency;
	}

	public void setLaplacian(String laplacian) {
		this.laplacian = laplacian;
	}

	public void setSlaplacian(String slaplacian) {
		this.slaplacian = slaplacian;
	}

	public void setOptiFunc(String optiFunc) {
		this.optiFunc = optiFunc;
	}

	public void setCaixa1(String caixa1) {
		this.caixa1 = caixa1;
	}

	public void setOrdermin(String ordermin) {
		this.ordermin = ordermin;
	}

	public void setOrdermax(String ordermax) {
		this.ordermax = ordermax;
	}

	public void setMinDegree(String minDegree) {
		this.minDegree = minDegree;
	}

	public void setMaxDegree(String maxDegree) {
		this.maxDegree = maxDegree;
	}

	public void setTriangleFree(String triangleFree) {
		this.triangleFree = triangleFree;
	}

	public void setAllowDiscGraphs(String allowDiscGraphs) {
		this.allowDiscGraphs = allowDiscGraphs;
	}

	public void setBiptOnly(String biptOnly) {
		this.biptOnly = biptOnly;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setSagitariiUrl(String sagitariiUrl) {
		this.sagitariiUrl = sagitariiUrl;
	}
	
	public void setMaxResults(String maxResults) {
		this.maxResults = maxResults;
	}
}
