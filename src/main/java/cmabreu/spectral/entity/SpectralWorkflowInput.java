package cmabreu.spectral.entity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Classe que contém todos os dados de especificação do workflow.
 * 
 */
public class SpectralWorkflowInput {
	public boolean usesAdjacencyMatrix;
	public boolean usesLaplacianMatrix;
	public boolean usesSignlessLaplacianMatrix;
	public boolean isMaximizationProblem;
	public boolean onlyTriangleFreeGraphs;
	public boolean onlyConnectedGraphs;
	public boolean onlyBipartiteGraphs;

	public String optimizationFunctionExpression;

	public int minimumOrder;
	public int maximumOrder;
	public int minimumDegree;
	public int maximumDegree;

	public SpectralWorkflowInput(boolean adjacency, boolean laplacian,
			boolean signless_laplacian, boolean max_function, boolean triangle,
			boolean disconected_graphs, boolean bipartite_graphs,
			String optimization_function, int min_order, int max_order,
			int min_degree, int max_degree) {

		if (min_order > max_order) {
			throw new IllegalArgumentException(
					"Incompatible values for range of orders.");
		}

		if (min_degree > max_degree) {
			throw new IllegalArgumentException(
					"Incompatible values for range of degrees.");
		}

		this.usesAdjacencyMatrix = adjacency;
		this.usesLaplacianMatrix = laplacian;
		this.usesSignlessLaplacianMatrix = signless_laplacian;
		this.isMaximizationProblem = max_function;
		this.onlyTriangleFreeGraphs = triangle;
		this.onlyConnectedGraphs = disconected_graphs;
		this.onlyBipartiteGraphs = bipartite_graphs;
		this.optimizationFunctionExpression = optimization_function;
		this.minimumOrder = min_order;
		this.maximumOrder = max_order;
		this.minimumDegree = min_degree;
		this.maximumDegree = max_degree;
	}

	@Override
	public String toString() {
		String temp;

		temp = "adjacency = " + this.usesAdjacencyMatrix;
		temp += ", laplacian = " + this.usesLaplacianMatrix;
		temp += ", signless_laplacian = " + this.usesSignlessLaplacianMatrix;
		temp += "\n";
		temp += "Function: " + optimizationFunctionExpression;
		temp += "optimization type: "
				+ (isMaximizationProblem ? "maximization" : "minimization");
		temp += "\n";
		temp += "Range for graph orders: [" + this.minimumOrder + ","
				+ this.maximumOrder + "]\n";
		temp += "Range for vertice degrees: [" + this.minimumDegree + ","
				+ this.maximumDegree + "]\n";
		temp += " only generate triangle free graphs = "
				+ this.onlyTriangleFreeGraphs;
		temp += ", only generate connected graphs = "
				+ this.onlyConnectedGraphs;
		temp += ", only generate bipartite graphs = "
				+ this.onlyBipartiteGraphs;

		return temp;
	}

	private static final String SEPARATOR_SYMBOL = ";";

	public void dumpToFile(String fileName) {
		try {
			FileWriter outFile = new FileWriter(fileName);
			PrintWriter out = new PrintWriter(outFile);

			String optimizationType = (isMaximizationProblem ? "maximization"
					: "minimization");

			// Write text to file
			for (int order = this.minimumOrder; order < this.maximumOrder; order++) {
				// for (int degree = this.minimumDegree; degree <
				// this.maximumDegree; degree++) {

				String graphOptions = "";
				if (this.onlyBipartiteGraphs) {
					graphOptions += "-b ";
				}
				if (this.onlyTriangleFreeGraphs) {
					graphOptions += "-t ";
				}
				if (this.onlyConnectedGraphs) {
					graphOptions += "-c ";
				}

				String degreeOptions = "";
				degreeOptions += " -d" + this.minimumDegree;
				degreeOptions += " -D" + this.maximumDegree;

				out.println(order + SEPARATOR_SYMBOL
						+ optimizationFunctionExpression + SEPARATOR_SYMBOL
						+ optimizationType + SEPARATOR_SYMBOL + graphOptions
						+ SEPARATOR_SYMBOL + degreeOptions);
				// }
			}

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
