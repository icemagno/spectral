package graphsimul.app.command;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import graphsimul.dominio.SpectralWorkflowInput;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

/**
 * Classe que contém o método responsável por submeter workflows.
 * 
 * @author Hugo
 * @author Kaique
 * @author Lucas
 */
public class SubmitCommand implements ActionCommand {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		boolean adjacency = toBoolean(request.getParameter("adjacency"));
		boolean laplacian = toBoolean(request.getParameter("laplacian"));
		boolean signless_laplacian = toBoolean(request
				.getParameter("slaplacian"));
		boolean max_function;
		if (request.getParameter("caixa1") == null) {
			String m = "Error: Min/Max must be chosen.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		} else if (request.getParameter("caixa1").equals("max"))
			max_function = true;
		else {
			max_function = false;
		}
		boolean triangle = toBoolean(request.getParameter("triangleFree"));
		boolean disconected_graphs = toBoolean(request
				.getParameter("allowDiscGraphs"));
		boolean bipartite_graphs = toBoolean(request.getParameter("biptOnly"));

		String optimizationFunction = request.getParameter("optiFunc");

		String orderMin = request.getParameter("ordermin");
		int min_order;
		if (orderMin != null && !orderMin.isEmpty()) {
			try {
				min_order = Integer.parseInt(request.getParameter("ordermin"));
			} catch (NumberFormatException ex) {
				request.setAttribute("msgErro", ex);
				return "/Erro.jsp";
			}
		} else {
			String m = "Error: minimum order is mandatory.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}
		int maxOrder;
		if (!request.getParameter("ordermax").isEmpty()) {
			try {
				maxOrder = Integer.parseInt(request.getParameter("ordermax"));
			} catch (NumberFormatException ex) {
				request.setAttribute("msgErro", ex);
				return "/Erro.jsp";
			}
		} else {
			String m = "Error: maximum order is mandatory.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}
		int minDegree;
		if (!request.getParameter("minDegree").isEmpty()) {
			try {
				minDegree = Integer.parseInt(request.getParameter("minDegree"));
			} catch (NumberFormatException ex) {
				request.setAttribute("msgErro", ex);
				return "/Erro.jsp";
			}
		} else {
			String m = "Error: minimum degree is mandatory.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}
		int maxDegree;
		if (!request.getParameter("maxDegree").isEmpty()) {
			try {
				maxDegree = Integer.parseInt(request.getParameter("maxDegree"));
			} catch (NumberFormatException ex) {
				request.setAttribute("msgErro", ex);
				return "/Erro.jsp";
			}
		} else {
			String m = "Error: Maximum degree is mandatory.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}

		SpectralWorkflowInput input = new SpectralWorkflowInput(adjacency,
				laplacian, signless_laplacian, max_function, triangle,
				disconected_graphs, bipartite_graphs, optimizationFunction,
				min_order, maxOrder, minDegree, maxDegree);

		if (!adjacency && !laplacian && !signless_laplacian) {
			String m = "Error: At least one matrix must be chosen.";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}

		if (!testTypes(optimizationFunction, signless_laplacian, laplacian,
				adjacency)) {
			String m = "Error: The variables used in the optmization function expression do not match the chosen matrix(es).";
			request.setAttribute("msgErro", m);
			return "/Erro.jsp";
		}

		long timestampTag = System.currentTimeMillis();

		request.setAttribute("workflow", input);
		String imageLocation = getImageLocation(timestampTag,
				input.optimizationFunctionExpression, request);
		if (imageLocation != null) {
			request.setAttribute("imageLocation", imageLocation);

			request.getSession().setAttribute("input", input);
			request.getSession().setAttribute("timestampTag", timestampTag);

			return "/ListInputParameterValues.jsp";
		} else {
			request.setAttribute("msgErro", "Unable to find out location of image.");
			return "/Erro.jsp";
		}
	}

	private String getImageLocation(long timestampTag, String expression,
			HttpServletRequest request) {

		try {
			TeXFormula fomule = new TeXFormula(expression);
			TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

			BufferedImage image = new BufferedImage(ti.getIconWidth(),
					ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			ti.paintIcon(new JLabel(), image.getGraphics(), 0, 0);

			String path = request.getServletContext().getRealPath("/");

			String localName = "eqimages/latex-" + timestampTag + ".png";

			File file = new File(path + localName);

			file.createNewFile();
			ImageIO.write(image, "png", file);

			System.err.println("Localização da imagem gerada: "
					+ file.getAbsolutePath());

			return localName;

		} catch (ParseException ex) {
			System.err
					.println("Erro durante a criação da imagem da expressão em LateX");
			request.setAttribute("msgErro",
					"Há uma erro de sintaxe na expressão em LaTex.");
			return null;
		} catch (IOException e) {
			request.setAttribute("msgErro",
					"Can not create renderized LaTex image.");
			return null;
		}
	}

	private boolean toBoolean(String valor) {
		// Precisa receber "", pois se fosse false a string seria nula, causando
		// erro na função equals
		valor += "";
		return valor.equals("on");
	}

	private boolean testTypes(String optimizationFunction,
			boolean signless_laplacian, boolean laplacian, boolean adjacency) {
		optimizationFunction = optimizationFunction.replace("\\lambda", "?");
		for (int i = 0; i < optimizationFunction.length(); i++) {
			if (optimizationFunction.charAt(i) == 'q' && !signless_laplacian)
				return false;
			if (optimizationFunction.charAt(i) == 'M' && !laplacian)
				return false;
			if (optimizationFunction.charAt(i) == '?' && !adjacency)
				return false;
		}
		return true;
	}
}
