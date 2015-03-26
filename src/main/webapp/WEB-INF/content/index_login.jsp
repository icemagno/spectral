<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 


					<div style="width:415px;float:left">

				
						<div class="userBoard" style="margin:0 auto;margin-top:50px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Workflow Submission</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<form action="doLogin" method="post" name="formLogin" id="formLogin">
									<table>
										<tr>
											<td style="width:60%">Adjacency (A)</td>
											<td><input style="width:15px" type="checkbox" name="adjacency" /></td>
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
											<td style="width:50%">&nbsp;</td><td><div style="margin-right: 7px;margin-top: 0px;" class="basicButton" onclick="doLogin()">Submit Job</div></td>
										</tr>
									</table>
									
									
								</form>
							</div>
						</div>
					
					
					</div>
					
					<div style="float:left">
					
						<div style="margin:0 auto; margin-top: 45px;" id="functionImage"></div>
						
					</div>
					
					
				</div>
								
				
<script>

	function doLogin() {
		var password = $("#password").val();
		var username = $("#username").val();
		if ( (password == '') || ( username == '' ) ) {
			showMessageBox('Please fill all required fields.');
			return;
		} 
		$("#formLogin").submit();
	}

	
	function showFunctionImage() {
		var theFunction = $("#optiFunc").val();
		
		$.ajax({
			type: "GET",
			url: "getPreview",
			data: { "function": theFunction }
		}).done(function( htmlData ) {
			$("#functionImage").html( htmlData );
		});
	}
	
	
	$(document).ready(function() {
		$("#username").focus();
		
		$("#password").keypress(function(event) {
		    if (event.which == 13) {
		        event.preventDefault();
		        doLogin();
		    }
		});

		
		$("#optiFunc").keyup(function(event) {
			showFunctionImage();
		});

	});
	
</script>				
				
				<script>
					showMessageBox( '${messageText}' );
				</script>				

				<div class="clear" />
			</div>
			
		</div>

		<div class="clear" />
		<div id="bottomBar" style="height:35px">
			<div class="footerDivCenter" style="font-style: italic;padding-bottom:10px">
				"Powered by Sagitarii"
			</div> 
		</div>
		
	</body>
	
</html>