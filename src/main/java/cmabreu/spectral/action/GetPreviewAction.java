
package cmabreu.spectral.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cmabreu.spectral.services.LatexFunctionGenerator;

import com.opensymphony.xwork2.ActionContext;

@Action(value="getPreview", results= {  
	    @Result(name="ok", type="httpheader", params={"status", "200"}) }
)   

@ParentPackage("default")
public class GetPreviewAction extends BasicActionClass {
	private String function;
	
	public String execute () {
		
		String resp = "";
		
		HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
		LatexFunctionGenerator sc = new LatexFunctionGenerator();
		String imagePath =  sc.execute(request, function) ;
		
		if ( imagePath != null ) {
			resp = "<img src='"+  imagePath  +"'>";
		}
		
		
		try { 
			HttpServletResponse response = (HttpServletResponse)ActionContext.getContext().get(StrutsStatics.HTTP_RESPONSE);
			response.setCharacterEncoding("UTF-8"); 
			response.getWriter().write( resp.toString() );  
		} catch (IOException ex) {
			
		}
		
		return "ok";
	}

	
	
	public void setFunction(String function) {
		this.function = function;
	}
	
}
