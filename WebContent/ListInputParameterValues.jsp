<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="graphsimul.dominio.SpectralWorkflowInput"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
	<%
		SpectralWorkflowInput input = (SpectralWorkflowInput) request
				.getAttribute("workflow");
	%>
	<ul>
		<li>Uses Adjacency Matrix = <%=input.usesAdjacencyMatrix%></li>

		<li>Uses Laplacian Matrix = <%=input.usesLaplacianMatrix%></li>

		<li>Uses Signless Laplacian Matrix = <%=input.usesSignlessLaplacianMatrix%></li>

		<li>Function to optimize: <br> <img id="imgLatex"
			src="<%=request.getAttribute("imageLocation")%>" />
		<li>Optimization type: <%=(input.isMaximizationProblem ? "maximization"
					: "minimization")%></li>

		<li>Graphs will be generated within the following range of
			orders: [<%=input.minimumOrder%>, <%=input.maximumOrder%>]
		</li>

		<li>Graphs will be generated within the following range vertices
			degrees: [<%=input.minimumDegree%>, <%=input.maximumDegree%>]
		</li>

		<li>Only generate triangle free graphs: <%=input.onlyTriangleFreeGraphs%></li>

		<li>Only generate connected graphs: <%=input.onlyConnectedGraphs%></li>

		<li>Only generate bipartite graphs: <%=input.onlyBipartiteGraphs%></li>
	</ul>

	<form action="graphsimul" method="post" name="graphsimul">
		<input type="hidden" name="comando" value="submitJob" /> <input
			type="submit" value="Generate Graphs" />
	</form>

</body>
</html>