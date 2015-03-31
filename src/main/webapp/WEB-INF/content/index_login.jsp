<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 


					<div >
					
						<div style="text-align: center; margin-top: 5px;" >
							<img id="functionImage" src="getPreview?function=Empty Function">
						</div>

				
						<div class="userBoard" style="margin:0 auto;margin-top:10px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Workflow Submission</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<form action="doSubmitFunction" method="post" name="formFunction" id="formFunction">
									<table>
										<tr>
											<td style="width:60%">Adjacency (A)</td>
											<td><input style="width:15px" type="checkbox" name="adjacency"  id="adjacency" /></td>
										</tr>
										<tr>
											<td >Laplacian (L)</td>
											<td><input style="width:15px" type="checkbox" name="laplacian" /></td>
										</tr>
										<tr>
											<td>Signless Laplacian (Q)</td>
											<td><input style="width:15px" type="checkbox" name="slaplacian" /></td>
										</tr>
										<tr>
											<td >Optimization Function</td>
											<td><input type="text" name="optiFunc" id="optiFunc"></td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td>
												<input checked="checked" style="width:15px" type="radio" name="caixa1" value="min" />Min
												<input style="width:15px" type="radio" name="caixa1" value="max" />Max
											</td>
										</tr>
										<tr>
											<td>Minimum order</td>
											<td><input style="width:15px" type="text" name="ordermin" size="2" /></td>
										</tr>
										<tr>
											<td>Maximun order</td>
											<td><input style="width:15px" type="text" name="ordermax" size="2" /></td>
										</tr>
	
	
										<tr>
											<td>Minimum degree</td>
											<td><input style="width:15px" type="text" name="minDegree" size="2" /></td>
										</tr>
										<tr>
											<td>Maximun degree</td>
											<td><input style="width:15px" type="text" name="maxDegree" size="2" /></td>
										</tr>
	
	
										<tr>
											<td style="width:50%">Only generate triangle free graphs?</td>
											<td><input style="width:15px" type="checkbox" name="triangleFree" /> </td>
										</tr>
										<tr>
											<td style="width:50%">Only generate connected graphs?</td>
											<td><input style="width:15px" type="checkbox" name="allowDiscGraphs" /></td>
										</tr>
										<tr>
											<td style="width:50%">Only generate bipartite graphs?</td>
											<td ><input style="width:15px" type="checkbox" name="biptOnly" /></td>
										</tr>
										
										<tr>
											<td>User</td>
											<td><input type="text" name="user" /></td>
										</tr>
										
										<tr>
											<td>Password</td>
											<td><input type="password" name="password" /></td>
										</tr>
										
										<tr>
											<td>Sagitarii URL Host (Temporário)</td>
											<td><input type="text" value="http://localhost:8080/sagitarii/" name="sagitariiUrl" /></td>
										</tr>
	
										<tr>
											<td style="width:50%">&nbsp;</td><td><div style="margin-right: 7px;margin-top: 0px;" class="basicButton" onclick="doSubmit()">Submit Job</div></td>
										</tr>
									</table>
									
									
								</form>
							</div>
						</div>
					
					
					</div>
										
					
				</div>
								
				
<script>

	function doSubmit() {
		$("#formFunction").submit();
		return false;
	}

	
	function showFunctionImage() {
		var theFunction = $("#optiFunc").val();
		$("#functionImage").attr( "src", "getPreview?function=" + theFunction );
	}
	
	
	$(document).ready(function() {
		$("#adjacency").focus();
		
	
		$("#optiFunc").keyup(function(event) {
			showFunctionImage();
		});

	});
	
</script>				
				
<%@ include file="../../footer.jsp" %>