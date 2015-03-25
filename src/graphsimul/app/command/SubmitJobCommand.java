package graphsimul.app.command;

import graphsimul.app.SystemCommandExecutor;
import graphsimul.dominio.SpectralWorkflowInput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitJobCommand implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		SpectralWorkflowInput input = (SpectralWorkflowInput) request
				.getSession().getAttribute("input");

		Long timestampTag = (Long) request.getSession().getAttribute(
				"timestampTag");

		System.out.println("timestampTag = " + timestampTag);

		ServletContext context = request.getServletContext();
		String workingDirectory = context.getInitParameter("WORKING_DIR");

		String inputDataseFileName = workingDirectory
				+ "SpectralWorkflowInput-" + timestampTag.toString() + ".txt";
		input.dumpToFile(inputDataseFileName);

		String chironXMLStr = getFileMask(workingDirectory + "chiron.xml");
		chironXMLStr = chironXMLStr.replaceAll("@@@exectag@@@",
				timestampTag.toString());
		chironXMLStr = chironXMLStr.replaceAll("@@@IGeng.dataset@@@",
				inputDataseFileName);
		chironXMLStr = chironXMLStr.replaceAll("@@@WORKING_DIR@@@",
				workingDirectory);
		String chironXMLFile = workingDirectory + "chiron-"
				+ timestampTag.toString() + ".xml";
		dumpToFile(chironXMLFile, chironXMLStr);

		String eigenJobStr = getFileMask(workingDirectory + "Eigen.job");
		eigenJobStr = eigenJobStr.replaceAll("@@@chiron.xml@@@", chironXMLFile);
		String eigenJobFile = workingDirectory + "Eigen-"
				+ timestampTag.toString() + ".job";
		dumpToFile(eigenJobFile, eigenJobStr);

		try {
			String result;
			String pdfFileName = workingDirectory + timestampTag.toString()
					+ ".pdf";
			result = submitJob(eigenJobFile, pdfFileName);
			if (result != null) {
				String path = request.getServletContext().getRealPath("/")
						+ "pdfs/" + timestampTag.toString() + ".pdf";
				copyFile(pdfFileName, path);
				request.setAttribute("result",
						"pdfs/" + timestampTag.toString() + ".pdf");
				return "/ListResultingGraphs.jsp";
			} else {
				request.setAttribute("msgErro",
						"Could not activate workflow activities.");
				return "/Erro.jsp";
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("msgErro",
					"Could not activate workflow activities.");
			return "/Erro.jsp";
		}
	}

	private void copyFile(String pdfFileName, String path) throws IOException {
		File srcFile = new File(pdfFileName);
		File dstFile = new File(path);
		copy(srcFile, dstFile);
	}

	public void copy(File source, File target) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel targetChannel = null;
		try {
			sourceChannel = new FileInputStream(source).getChannel();
			targetChannel = new FileOutputStream(target).getChannel();
			targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			targetChannel.close();
			sourceChannel.close();
		}
	}

	public String submitJob(String jobFile, String resultFile)
			throws IOException, InterruptedException {
		// build the system command we want to run
		List<String> commands = new ArrayList<String>();

		System.out.println("jobFile:" + resultFile);
		System.out.println("resultFile:" + resultFile);

//		commands.add("/bin/sh");
//		commands.add("-c");
//		commands.add("qsub " + jobFile);

//		 commands.add("/bin/sh");
//		 commands.add("-c");
//		 commands.add("ls -l /");

		 commands.add("cmd.exe");
		 commands.add("/C");
		 commands.add("dir");

		// execute the command
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(
				commands);
		int result = commandExecutor.executeCommand();

		if (result != 0) {
			return null;
		}

		boolean fileGenerated = false;
		int numSeconds = 0;
		while (!fileGenerated) {
			File file = new File(resultFile);
			if (file != null && file.exists()) {
				fileGenerated = true;
				return resultFile;
			} else {
				Thread.sleep(1000);
				numSeconds++;
				if (numSeconds == 30) {
					return null;
				}
			}
		}
		return null;
	}

	private void dumpToFile(String fileName, String content) {
		try {
			FileWriter outFile = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(outFile);

			// Write text to file
			out.print(content);

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileMask(String fileName) throws IOException {
		File file = new File(fileName);
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}

}
