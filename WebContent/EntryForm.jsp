<script type="text/javascript">
	function toLatex(str) {
		var xmlhttp;
		if (str.length == 0) {
			document.getElementById("imgLatex").innerHTML = "";
			return;
		}
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("imgLatex").innerHTML = xmlhttp.responseText;
			}
		}
		str = encodeURIComponent(str);
		xmlhttp.open("GET", "graphsimul?comando=transformarLatex&math=" + str,
				false);
		xmlhttp.send();
		// 		sleep(100);
		var caminho = document.getElementById("imgLatex").getAttribute("src");
		// 		alert("You entered: " + caminho);
		document.getElementById("imgLatex").setAttribute("src", "");
		document.getElementById("imgLatex").setAttribute("src", caminho);
	}
	function sleep(milliseconds) {
		var start = new Date().getTime();
		for ( var i = 0; i < 1e7; i++) {
			if ((new Date().getTime() - start) > milliseconds) {
				break;
			}
		}
	}
</script>

<%@include file="layout.jsp"%>

<meta http-equiv="Content-Type" content="text/html;charset=iso-8859-1"></meta>

<h2>Workflow Submission</h2>
<br>
<form action="graphsimul" method="post" name="graphsimul">
	<input type="hidden" name="comando" value="submitValues">
	<p>
		<input type="checkbox" name="adjacency" />Adjacency (A)
	</p>


	<p>
		<input type="checkbox" name="laplacian" />Laplacian (L)
	</p>

	<p>
		<input type="checkbox" name="slaplacian" />Signless Laplacian (Q)
	</p>

	<div>
		<p>
			Optimization Function: <input type="text" name="optiFunc"
				style="width: 200px; padding: 2px; border: 1px solid black" /><br>
			<!-- 			Optimization Function: <input type="text" name="optiFunc" -->
			<!-- 				style="width: 200px; padding: 2px; border: 1px solid black" -->
			<!-- 				onChange="toLatex(this.value)" onkeyup="toLatex(this.value)" /><br> -->

			<%-- 			<img id="imgLatex" src="<%=request.getAttribute("image")%>" /> <br /> --%>

			<input type="radio" name="caixa1" value="min" />Min<br /> <input
				type="radio" name="caixa1" value="max" />Max<br />
		</p>

		<p>
			Minimum order: <input type="text" name="ordermin" size="2" /> <br />
			Maximum order: <input type="text" name="ordermax" size="2" />
		</p>

		<p>
			Minimum Degree: <input type="text" name="minDegree" size="2" /> <br />
			Maximum Degree: <input type="text" name="maxDegree" size="2" />
		</p>

		<p>
			<input type="checkbox" name="triangleFree" />Only generate triangle
			free graphs? <br /> <input type="checkbox" name="allowDiscGraphs" />Only
			generate connected graphs <br /> <input type="checkbox"
				name="biptOnly" />Only generate bipartite graphs? <br />
		</p>
	</div>

	<p>
		<input type="submit" value="Submit Job" />
	</p>
</form>
</table>
</center>
</div>
</body>
</html>