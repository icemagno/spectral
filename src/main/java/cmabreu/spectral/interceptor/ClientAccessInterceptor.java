package cmabreu.spectral.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ClientAccessInterceptor implements Interceptor {
	private static final long serialVersionUID = -2344136157076941239L;
	
	public String intercept(ActionInvocation invocation) throws Exception {
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
