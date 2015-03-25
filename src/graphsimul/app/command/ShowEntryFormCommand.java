package graphsimul.app.command;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Classe que contém o método responsável por renderizar a equação LateX em uma
 * imagem.
 * 
 * @author Hugo
 * @author Kaique
 * @author Lucas
 */
public class ShowEntryFormCommand implements ActionCommand {

	@Override
	public synchronized String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return "/EntryForm.jsp";
	}
}
