<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

						<div class="function">
							<img id="functionImage" style="margin: 0 auto;display:block" src="">
						</div>

<form action="doSubmitFunction" method="post" name="formFunction" id="formFunction">
<div class="metade" style="height: 546px;">				
        <div class="titulo">Workflow Submission</div>
    
                <table>
                    <tr>
                        <td >Optimization Function</td>
                        <td><input type="text" value="" name="optiFunc" id="optiFunc"></td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>
                            <input checked="checked" style="width:15px" type="radio" name="caixa1" value="min" />Minimize
                            <input style="width:15px" type="radio" name="caixa1" value="max" />Maximize
                        </td>
                    </tr>
                    <tr>
                        <td>Minimum order <input style="width:25px; float: right;" type="text" name="ordermin" id="ordermin" size="2" /></td>
                        <td>Maximum order <input style="width:25px; float: right;" type="text" name="ordermax" id="ordermax" size="2" /></td>
                    </tr>

                    <tr>
                        <td>Minimum degree <input style="width:25px; float: right;" type="text" name="minDegree" id="minDegree" size="2" /></td>
                        <td>Maximum degree <input style="width:25px; float: right;" type="text" name="maxDegree" id="maxDegree" size="2" /></td>
                    </tr>

                    <tr>
                        <td>Number of results to show</td>
                        <td><input style="float: right; width:25px;" type="text" value="2" name="maxResults" id="maxResults" size="2" /></td>
                    </tr>


                    <tr>
                        <td style="width:50%">Only generate triangle free graphs?</td>
                        <td><input style="float: right; width:15px" type="checkbox"  name="triangleFree" /> </td>
                    </tr>
                    <tr>
                        <td style="width:50%">Only generate connected graphs?</td>
                        <td><input style="float: right; width:15px" type="checkbox"  name="allowDiscGraphs" /></td>
                    </tr>
                    <tr>
                        <td style="width:50%">Only generate bipartite graphs?</td>
                        <td ><input style="float: right; width:15px" type="checkbox"  name="biptOnly" /></td>
                    </tr>

                </table>
<div class="clear"></div>
</div>

<div class="metade" style="float: right; height: 547px;">
        <div class="titulo">Function Parameters</div>
        	<div id="checks">
            <table>
                <tr  >
                    <td style="width:80%">Adjacency (A)</td>
                    <td><input style="width:15px" type="checkbox"   name="adjacency"  id="adjacency" /></td>
                </tr>
                <tr  >
                    <td >Laplacian (L)</td>
                    <td><input style="width:15px" type="checkbox"   name="laplacian" id="laplacian" /></td>
                </tr>
                <tr  >
                    <td>Signless Laplacian (Q)</td>
                    <td><input style="width:15px" type="checkbox"   name="slaplacian" id="slaplacian" /></td>
                </tr>

                <tr  >
                    <td >AdjacencyB (Ab)</td>
                    <td><input style="width:15px" type="checkbox"   name="adjacencyB"  id="adjacencyB" /></td>
                </tr>
                <tr  >
                    <td >LaplacianB (Lb)</td>
                    <td><input style="width:15px" type="checkbox"   name="laplacianB" id="laplacianB" /></td>
                </tr>
                <tr  >
                    <td>Signless LaplacianB (Qb)</td>
                    <td><input style="width:15px" type="checkbox"   name="slaplacianB" id="slaplacianB" /></td>
                </tr>

                <tr  >
                    <td >Chromatic (Chi)</td>
                    <td><input style="width:15px" type="checkbox"   name="chromatic"  id="chromatic" /></td>
                </tr>
                <tr  >
                    <td >Chromatic Compl. (OverChi)</td>
                    <td><input style="width:15px" type="checkbox"   name="chromaticB" id="chromaticB" /></td>
                </tr>
                <tr  >
                    <td>Largest Click (Omega)</td>
                    <td><input style="width:15px" type="checkbox"   name="click" id="click" /></td>
                </tr>
                <tr  >
                    <td>Largest Click Compl.(OverOmega)</td>
                    <td><input style="width:15px" type="checkbox"   name="clickB" id="clickB" /></td>
                </tr>
                <tr  >
                    <td>Largest Degree.(Dk)</td>
                    <td><input style="width:15px" type="checkbox"   name="largestDegree" id="largestDegree" /></td>
                </tr>
                <tr  >
                    <td>Num of Edges.(M)</td>
                    <td><input style="width:15px" type="checkbox"   name="numEdges" id="numEdges" /></td>
                </tr>
        </table>
        </div>
<div class="clear"></div>
</div>	
<div class="clear"></div>
    <div class="submitJob" onclick="doSubmit()">Submit Job</div>
</form>

				
<script>

	function doSubmit() {
		var uncoded = $("#optiFunc").val();
		checkOptions( uncoded );
		
		var ordermin = $("#ordermin").val();
		var ordermax = $("#ordermax").val();
		var minDegree = $("#minDegree").val();
		var maxDegree = $("#maxDegree").val();
		var maxResults = $("#maxResults").val();
		
		var selA = $("#adjacency").prop('checked');
		var selL = $("#laplacian").prop('checked');
		var selQ = $("#slaplacian").prop('checked');
		

		if ( maxResults == "" ) {
			showMessageBox("You must provide a maximun results do show");
			return;
		}

		if ( uncoded == "" ) {
			showMessageBox("You must provide an Optimization Function");
			return;
		}

		if ( ordermin == "" ) {
			showMessageBox("You must provide the Minimun Order value");
			return;
		}

		if ( ordermax == "" ) {
			showMessageBox("You must provide the Maximun Order value");
			return;
		}

		if ( minDegree == "" ) {
			showMessageBox("You must provide the Minimun Degree value");
			return;
		}
		
		if ( maxDegree == "" ) {
			showMessageBox("You must provide the Maximun Degree value");
			return;
		}
		
		if ( +ordermin > +ordermax ) {
			showMessageBox("The Minimun Order value must be less than Maximun Order");
			return;
		}

		if ( +minDegree > +maxDegree ) {
			showMessageBox("The Minimun Degree value must be less than Maximun Degree");
			return;
		}
		
		$("#formFunction").submit();
		
		return false;
	}

	
	function checkOptions( funct ) {
		
		$("#adjacency").prop('checked', false);
		$("#laplacian").prop('checked', false);
		$("#slaplacian").prop('checked', false);

		$("#adjacencyB").prop('checked', false);
		$("#laplacianB").prop('checked', false);
		$("#slaplacianB").prop('checked', false);
		
		$("#chromatic").prop('checked', false);
		$("#chromaticB").prop('checked', false);
		$("#click").prop('checked', false);
		$("#clickB").prop('checked', false);
		$("#largestDegree").prop('checked', false);
		$("#numEdges").prop('checked', false);

		
		if ( funct.indexOf("overline{\\omega") > -1 ) {
			$("#clickB").prop('checked', true);
			funct = funct.replace('overline{\\omega','');
		}

		if ( funct.indexOf("overline{\\lambda") > -1 ) {
			$("#adjacencyB").prop('checked', true);
			funct = funct.replace('overline{\\lambda','');
		}
		
		if ( funct.indexOf("overline{\\mu") > -1 ) {
			$("#laplacianB").prop('checked', true);
			funct = funct.replace('overline{\\mu','');
		}

		if ( funct.indexOf("overline{q_") > -1 ) {
			$("#slaplacianB").prop('checked', true);
			funct = funct.replace('overline{q_','');
		}

		if ( funct.indexOf("\\omega") > -1 ) {
			$("#click").prop('checked', true);
			funct = funct.replace('omega','');
		}
		
		if ( funct.indexOf("\\chi") > -1 ) {
			$("#chromatic").prop('checked', true);
			funct = funct.replace('chi','');
		}
		
		if ( funct.indexOf("overline{\\chi") > -1 ) {
			$("#chromaticB").prop('checked', true);
			funct = funct.replace('overline{\\chi','');
		}
		
		if ( funct.indexOf("\\lambda") > -1 ) {
			$("#adjacency").prop('checked', true);
			funct = funct.replace('lambda','');
		}
		
		if ( funct.indexOf("\\mu") > -1 ) {
			$("#laplacian").prop('checked', true);
			funct = funct.replace('mu','');
		}

		
		if ( funct.indexOf("q_") > -1 ) {
			funct = funct + " ";
			$("#slaplacian").prop('checked', true);
			var indI = funct.indexOf("q_");
			if ( indI > -1 ) {
				var indF = funct.indexOf(" ", indI);
				var sgnlap = funct.substring( indI, indF );
				//funct = funct.replace( sgnlap,'');
			}
		}
		
		if ( funct.indexOf("d_") > -1 ) {
			funct = funct + " ";
			$("#largestDegree").prop('checked', true);
			var indI = funct.indexOf("d_");
			if ( indI > -1 ) {
				var indF = funct.indexOf(" ", indI);
				var largdeg = funct.substring( indI, indF );
				//funct = funct.replace( largdeg,'');
			}
		}
		
		
		if ( funct.indexOf("SIZE") > -1 ) {
			$("#numEdges").prop('checked', true);
			funct = funct.replace( "SIZE","");
		}
		
	}
	
	function showFunctionImage() {
		var uncoded = $("#optiFunc").val();
		checkOptions( uncoded );
		var theFunction = encodeURIComponent( uncoded );
		
		
		
		if ( theFunction.length > 0 ) {
			$("#functionImage")
		    .on('load', function() { 
		    	$("#functionImage").css("display","block"); 
		    })   
	        .on('error', function() { 
	        	$("#functionImage").css("display","none");
	        })
			.attr( "src", "getPreview?function=" + theFunction );
		} else {
			$("#functionImage").css("display","none");
		}
	}
	
	
	$(document).ready(function() {
		$("#adjacency").focus();
		
		
		$("#checks").children().bind('click', function(){ return false; });
		
		$("#optiFunc").keyup(function(event) {
			showFunctionImage();
		});
		
		showFunctionImage();
	});
	
	
	function back() {
		window.location.href="index";
	}

</script>				
				
<%@ include file="../../footer.jsp" %>
