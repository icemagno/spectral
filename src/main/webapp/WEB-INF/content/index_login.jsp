<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 
				
					<div class="userBoard" style="margin:0 auto;margin-top:100px">
						<div class="userBoardT1" style="text-align:center;width:95%">Workflow Submission</div>
						<div class="userBoardT2" style="text-align:center;width:95%">
							<form action="doLogin" method="post" name="formLogin" id="formLogin">
							
								<table>
									<tr>
										<td style="width:50%">Adjacency (A)</td>
										<td><input type="checkbox" name="adjacency" /></td>
									</tr>
									<tr>
										<td style="width:50%">Laplacian (L)</td>
										<td><input type="checkbox" name="laplacian" /></td>
									</tr>
									<tr>
										<td style="width:50%">&nbsp;</td><td><div style="margin-right: 7px;margin-top: 0px;" class="basicButton" onclick="doLogin()">Submit Job</div></td>
									</tr>
								</table>
								
								
							</form>
						</div>
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

	$(document).ready(function() {
		$("#username").focus();
		
		$("#password").keypress(function(event) {
		    if (event.which == 13) {
		        event.preventDefault();
		        doLogin();
		    }
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