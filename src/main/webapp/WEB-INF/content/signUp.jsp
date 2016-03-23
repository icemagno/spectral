<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>
									
						<div class="userBoard" style="margin:0 auto;margin-top:10px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Sign up</div>
							<div class="userBoardT2 login-box" style="text-align:center;width:95%">
                                
<!--                                alterar ação do formulário -->
                                
								<form action="showMyExperiments" method="post" name="formFunction" id="formFunction">
									<table>
										<tr>
											<td>Full Name</td>
											<td><input type="text" name="user" /></td>
										</tr>
                                        
                                        <tr>
											<td>Email</td>
											<td><input type="text" value="" name="email" /></td>
										</tr>
										
										<tr>
											<td>Password</td>
											<td><input type="password" name="password" /></td>
										</tr>
										
	
										<tr>
											<td style="width:50%">&nbsp;</td><td><div style="margin-right: 7px;margin-top: 0px;" class="basicButton" onclick="doSubmit()">Sign Up</div></td>
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