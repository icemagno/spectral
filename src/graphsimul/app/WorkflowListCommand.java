package graphsimul.app;

import graphsimul.app.command.ActionCommand;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe que contém o método responsável por listar workflows.
 * @author Hugo
 * @author Kaique
 * @author Lucas
 */

public class WorkflowListCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "/index.jsp";
	}

}
