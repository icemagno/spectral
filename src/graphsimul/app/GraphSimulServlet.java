package graphsimul.app;

import graphsimul.app.command.ActionCommand;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GraphSimulServlet extends HttpServlet {

	private static final long serialVersionUID = 5183514451158712018L;
	private RequestHelper requestHelper = new RequestHelper();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doResponse(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doResponse(request, response);
	}

	private void doResponse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String operacaoSolicitada = request.getParameter("comando");

		ActionCommand comando = requestHelper.command(operacaoSolicitada);

		if (comando != null) {
			String pagina = comando.execute(request, response);
			System.err.println("Página a ser chamada: " + pagina);
			dispatch(request, response, pagina);
		}
	}

	/**
	 * Despacha o controle para a view (página JSP) adequada.
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @throws javax.servlet.ServletException
	 * @throws java.io.IOException
	 */
	protected void dispatch(HttpServletRequest request,
			HttpServletResponse response, String page)
			throws javax.servlet.ServletException, java.io.IOException {
		RequestDispatcher dispatcher = getServletConfig().getServletContext()
				.getRequestDispatcher(page);
		dispatcher.forward(request, response);
	}

	/** Default constructor */
	public GraphSimulServlet() {
	}
}
