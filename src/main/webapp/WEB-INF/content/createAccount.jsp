<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>
									
						<div class="userBoard" style="margin:0 auto;margin-top:10px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Create Account</div>
							<div class="userBoardT2 login-box" style="text-align:center;width:95%">
                                
<!--                                alterar ação do formulário -->
                                
								<form action="doCreateAccount" method="post" name="formFunction" id="formFunction">
									<table>
										<tr>
											<td>Full Name</td>
											<td><input type="text" name="fullName" /></td>
										</tr>

										<tr>
											<td>Login Name</td>
											<td><input type="text" name="userName" /></td>
										</tr>
                                        
										<tr>
											<td>Password</td>
											<td><input type="password" name="password" /></td>
										</tr>
										
										<tr>
											<td>Retype Password</td>
											<td><input type="password" name="rePassword" /></td>
										</tr>

										<tr>
											<td>E-Mail</td>
											<td><input type="text" name="email" /></td>
										</tr>

										<tr>
											<td>Institution</td>
											<td><input type="text" name="institution" /></td>
										</tr>

										<tr>
											<td style="width:50%">&nbsp;</td><td><div style="margin-right: 7px;margin-top: 0px;" class="basicButton" onclick="doSubmit()">Submit</div></td>
										</tr>
									</table>
								</form>
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