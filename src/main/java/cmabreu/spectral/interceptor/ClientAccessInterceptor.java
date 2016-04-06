package cmabreu.spectral.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import cmabreu.spectral.entity.User;

public class ClientAccessInterceptor implements Interceptor {
	private static final long serialVersionUID = -2344136157076941239L;
	
	public String intercept(ActionInvocation invocation) throws Exception {
		User loggedUser = (User)invocation.getInvocationContext().getSession().get("loggedUser");
		if (loggedUser == null) {
			System.out.println("não logado !!");
			return "notLogged";
		}
		return invocation.invoke();
	}
 
	@Override
	public void destroy() {
		System.out.println("destroy"); 
	}

	
	@Override
	public void init() {
		System.out.println("system init");
	}	
	
}
