<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 

					<div style="position:relative">
					
						<img style="height: 50px;cursor:pointer;position:absolute; top:15px;left:50px" onclick="back();" src="img/back.png">


						<div class="userBoard" style="margin:0 auto;margin-top:50px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Requisition Submited</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<table>
									<tr>
										<td>${running}</td>
									</tr>
								</table>
							</div>
						</div>

					</div>
						
				</div>
								
<script>

	function back() {
		window.location.href="index";
	}
	
</script>

<%@ include file="../../footer.jsp" %>