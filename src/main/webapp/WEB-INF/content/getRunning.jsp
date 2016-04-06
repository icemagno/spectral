<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 

					<div style="position:relative">
					
						<div class="userBoard" style="margin:0 auto;margin-top:50px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Running Experiments</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<table style="width:100%">
									<tr>
										<th>Experiment</th>
										<th>Importer</th>
									</tr>
									<c:forEach var="runningData" items="${running}">
										<c:if test="${runningData.owner == user.loginName}">
											<tr>
												<td style="width:80%">${runningData.tagExec}</td>
												<td style="width:20%">${runningData.importer} ( ${runningData.importerStatus} )&nbsp;</td>
											</tr>
											<tr>
												<td colspan="2">
													<table>
														<tr>
															<th>Activities</th>
															<th>Status</th>
															<th>Total</th>
															<th>Remaining</th>
														<tr>
														<c:forEach var="fragment" items="${runningData.fragments}">
															<tr>
																<td>${fragment.activities}</td>
																<td>${fragment.status}</td>
																<td>${fragment.totalInstances}</td>
																<td>${fragment.remainingInstances}</td>
															<tr>  
														</c:forEach>
													</table>
												</td>
											</tr>
										</c:if>
									</c:forEach>	
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