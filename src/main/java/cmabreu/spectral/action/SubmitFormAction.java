
package cmabreu.spectral.action;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import br.cefetrj.parser.FormulaEvaluator;
import cmabreu.spectral.services.SagitariiInterface;

@Action (value = "doSubmitFunction", results = { @Result (location = "done.jsp", name = "ok"),
		@Result(location="userForm", type="redirect", name="error") },
		interceptorRefs= { @InterceptorRef("seguranca")} ) 

@ParentPackage("default")
public class SubmitFormAction extends BasicActionClass {
	private String adjacency = "off";
	private String laplacian = "off";
	private String slaplacian= "off";
	private String adjacencyB = "off";
	private String laplacianB = "off";
	private String slaplacianB = "off";
	private String optiFunc;
	private String caixa1;
	private String ordermin;
	private String ordermax;
	private String minDegree;
	private String maxDegree;
	private String triangleFree = "off";
	private String allowDiscGraphs = "off";
	private String biptOnly = "off";
	private String maxResults;
	
	private String chromatic = "off";
	private String chromaticB = "off";
	private String click = "off";
	private String clickB = "off";
	private String largestDegree = "off";
	private String numEdges = "off";
	
	private List<String> log;
	
	public String execute () {
		
		System.out.println( adjacency+ " " + laplacian+ " " + slaplacian+ " " + adjacencyB+ " " + laplacianB+ " " + slaplacianB+ " " + optiFunc+ " " + caixa1+ " " + ordermin+ " " + ordermax+ " " + minDegree+ " " + maxDegree+ " " + 
				triangleFree+ " " + allowDiscGraphs+ " " + biptOnly+ " " + maxResults+ " " + chromatic+ " " + chromaticB+ " " +
				click+ " " + clickB+ " " + largestDegree+ " " + numEdges );
		
		
		if ( (optiFunc != null) && (!optiFunc.equals("")) ) {
			try {
				ByteArrayInputStream inputStream = new ByteArrayInputStream( optiFunc.getBytes() );
				FormulaEvaluator eval = new FormulaEvaluator(inputStream);
				eval.parse();
			} catch ( Throwable e ) {
				setMessageText( "Error in Optimization Function validation: " + e.getMessage() );
				return "error";
			}
		} else {
			setMessageText( "You must provide an Optimization Function" );
			return "error";
		}

		if( caixa1 != null ) {
			try {
				SagitariiInterface si = new SagitariiInterface(getSagitariiUrl(), user );
				si.submit(adjacency, laplacian, slaplacian, adjacencyB, laplacianB, slaplacianB, optiFunc, caixa1, ordermin, ordermax, minDegree, maxDegree, 
						triangleFree, allowDiscGraphs, biptOnly, maxResults, chromatic, chromaticB,
						click, clickB, largestDegree, numEdges );
				log = si.getLog();
			} catch ( Exception e ) {
				setMessageText( e.getMessage() );
				return "error";
			}
		} else {
			setMessageText("Invalid arguments");
			return "error";
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

	public void setMaxResults(String maxResults) {
		this.maxResults = maxResults;
	}

	public void setAdjacencyB(String adjacencyB) {
		this.adjacencyB = adjacencyB;
	}

	public void setLaplacianB(String laplacianB) {
		this.laplacianB = laplacianB;
	}

	public void setSlaplacianB(String slaplacianB) {
		this.slaplacianB = slaplacianB;
	}
	
	public void setChromatic(String chromatic) {
		this.chromatic = chromatic;
	}
	
	public void setChromaticB(String chromaticB) {
		this.chromaticB = chromaticB;
	}
	
	public void setClick(String click) {
		this.click = click;
	}
	
	public void setClickB(String clickB) {
		this.clickB = clickB;
	}
	
	public void setLargestDegree(String largestDegree) {
		this.largestDegree = largestDegree;
	}
	
	public void setNumEdges(String numEdges) {
		this.numEdges = numEdges;
	}
	
}
