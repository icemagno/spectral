<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 

						<div class="userBoard" style="margin:0 auto;margin-top:50px;width: 400px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Requisition Submited</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<table>
									<c:forEach var="entry" items="${log}">
										<tr>
											<td>${entry}</td>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
						
				</div>
								
				
<%@ include file="../../footer.jsp" %>