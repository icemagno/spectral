/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cmabreu.spectral;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import org.nfunk.jep.JEP;

public class TesteAvaliacao {
	public static void main(String args[]) {

		// Arquivo: graph10356.g6.adj
		Double teste[] = {-2.92144,	-1.92231, -1.47701, -0.941143, -0.462193, 0.355915, 0.820754, 1.50535, 5.04208};

		String math = " q_1 + q_2 ";

		evaluateOptimizationFunction(math, teste);
	}

	/**
	 * Função responsável por substituir os valores nas variáveis da função,
	 * calculando assim o seu resultado.
	 * 
	 * @param optimizationFunction
	 *            Função de otimização.
	 * @param values
	 *            Valores de cada variável da função.
	 * @return Resultado da função.
	 */
	public static double evaluateOptimizationFunction( String optimizationFunction, Double[] values ) {
		for (int i = 0; i < values.length; i++) {
			String old = "q_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
			
			old = "M_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
			
			old = "\\lambda_" + Integer.toString(i + 1);
			optimizationFunction = optimizationFunction.replace( old, "" + values[i] );
		}
		
		
		optimizationFunction = optimizationFunction.replace( "\\frac", "" );
		optimizationFunction = optimizationFunction.replaceAll(	"[}]{1,1}+[\\s]*+[{]{1,1}", ")/(" );
		optimizationFunction = optimizationFunction.replace( "}", ")" );
		optimizationFunction = optimizationFunction.replace( "{", "(" );
		
		JEP myParser = new JEP();
		myParser.parseExpression(optimizationFunction);
		System.out.println(optimizationFunction + " = " + myParser.getValue());
		return myParser.getValue();
	}

	/**
	 * Função responsável por verificar se a função digitada pelo usuário é
	 * valida.
	 * 
	 * @param optimizationFunction
	 *            Função de otimização.
	 * @return Verdadeiro se for válida e falso se possuir algum caractere
	 *         inválido.
	 * @throws IOException
	 *             Quando não achar o arquivo conf.txt.
	 */
	public static boolean verifyVariables(String optimizationFunction)
			throws IOException {
		ArrayList<String> subString = new ArrayList<String>();
		subString.add(" ");
		File f = new File("d:/conf.txt");
		BufferedReader in = new BufferedReader(new FileReader(f));

		while (in.ready()) {
			String linha = in.readLine();
			// Com esta verificação, comentários no arquivo conf.txt não são
			// adicionados na lista.
			if (!linha.startsWith(Pattern.quote("//"))) {
				subString.add(linha);
			}
		}

		for (int i = 0; i < subString.size(); i++) {
			optimizationFunction = optimizationFunction.replaceAll(
					Pattern.quote(subString.get(i)), "");
		}
		if (optimizationFunction.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Função responsável por verificar se a quantidade de variáveis na função
	 * de otimização é igual a de valores.
	 * 
	 * @param optimizationFunction
	 *            Função de otimização.
	 * @param values
	 *            Valores de cada variável da função.
	 * @return Verdadeiro se a quantidade for a mesma e falso se não for.
	 */
	public static boolean testValues(String optimizationFunction,
			Double[] values) {
		String var = null;
		int indice = 0;
		int auxiliar = 0;
		char charposition;
		int position = 0;
		optimizationFunction = optimizationFunction.replaceAll(
				Pattern.quote("\\bar{\\lambda_"), "lambdabar_");
		optimizationFunction = optimizationFunction.replaceAll(
				Pattern.quote("\\bar{q_"), "qbar_");
		optimizationFunction = optimizationFunction.replaceAll(
				Pattern.quote("\\bar{M_"), "Mbar_");
		for (int i = 0; i < 6; i++) {
			auxiliar = 0;
			if (i == 0) {
				var = "\\lambda_";
			}
			if (i == 1) {
				var = "q_";
			}
			if (i == 2) {
				var = "M_";
			}
			if (i == 3) {
				var = "lambdabar_";
			}
			if (i == 4) {
				var = "qbar_";
			}
			if (i == 5) {
				var = "Mbar_";
			}
			int index = optimizationFunction.indexOf(var);
			int j = index + (var).length();
			while (index > -1) {
				charposition = optimizationFunction.charAt(j);
				position = Integer.parseInt("" + charposition);
				if (auxiliar < position) {
					auxiliar = position;
				}
				index = optimizationFunction.indexOf(var, j);
				j = index + var.length();
			}
			indice += auxiliar;
		}
		if (indice == values.length) {
			return true;
		}
		return false;
	}
}