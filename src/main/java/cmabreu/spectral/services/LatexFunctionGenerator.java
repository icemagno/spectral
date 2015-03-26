package cmabreu.spectral.services;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JLabel;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;


public class LatexFunctionGenerator  {

	public String execute(HttpServletRequest request, String function) {
		long timestampTag = System.currentTimeMillis();
		String imageLocation = getImageLocation( timestampTag, function, request);
		return imageLocation;
	}

	
	private String getImageLocation(long timestampTag, String expression,
			HttpServletRequest request) {

		try {
			TeXFormula fomule = new TeXFormula(expression);
			TeXIcon ti = fomule.createTeXIcon(TeXConstants.STYLE_DISPLAY, 40);

			BufferedImage image = new BufferedImage(ti.getIconWidth(),	ti.getIconHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			ti.paintIcon(new JLabel(), image.getGraphics(), 0, 0);

			String path = request.getServletContext().getRealPath("/");

			String localName = "eqimages/latex-" + timestampTag + ".png";

			File file = new File(path + localName);

			file.createNewFile();
			ImageIO.write(image, "png", file);

			return localName;

		} catch (Exception ex) {
			//ex.printStackTrace();
			return null;
		}
		
	}

}
