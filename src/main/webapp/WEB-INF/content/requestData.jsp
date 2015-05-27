<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 


					<div style="position:relative">
					
						<img style="height: 50px;cursor:pointer;position:absolute; top:15px;left:50px" onclick="back();" src="img/back.png">
					
					
						<div style="text-align: center; margin-top: 5px;;height:100px" >
							<img id="functionImage" style="margin: 0 auto;display:none" src="">
						</div>

				
						<div class="userBoard" style="margin:0 auto;margin-top:10px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Request Result Data</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<form action="showMyExperiments" method="post" name="formFunction" id="formFunction">
									<table>
										<tr>
											<td>User</td>
											<td><input type="text" name="user" /></td>
										</tr>
										
										<tr>
											<td>Password</td>
											<td><input type="password" name="password" /></td>
										</tr>
										
										<tr>
											<td>Sagitarii URL Host</td>
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

	
	function back() {
		window.location.href="index";
	}

</script>				
				
<%@ include file="../../footer.jsp" %>