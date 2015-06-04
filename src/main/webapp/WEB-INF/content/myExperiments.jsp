<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 


					<div style="position:relative">
					
						<img style="height: 50px;cursor:pointer;position:absolute; top:15px;left:50px" onclick="back();" src="img/back.png">
					
					
						<div style="text-align: center; margin-top: 5px;;height:100px" >
							<img id="functionImage" style="margin: 0 auto;display:none" src="">
						</div>
				
						<div class="userBoard" style="margin:0 auto;margin-top:10px;width: 500px;">
							<div class="userBoardT1" style="text-align:center;width:95%">Request Result Data</div>
							<div class="userBoardT2" style="text-align:center;width:95%">
								<table class="tableForm" id="example">
									<thead>
									<tr>
										<th>Experiment</th>
										<th>Start Date/Time</th>
										<th>Status</th>
										<th>Elapsed Time</th>
										<th>&nbsp;</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="experiment" items="${experiments}">
										<tr>
											<td>${experiment.tagExec}</td>
											<td>${experiment.startDate}</td>
											<td>${experiment.status}</td>
											<td>${experiment.elapsedTime}</td>
											<td>
												<c:if test="${experiment.status == 'FINISHED'}">
													<img class="dicas" title="Download result" onclick="getFiles('${experiment.tagExec}')" src="img/save.png" style="margin:0px;cursor:pointer;height:24px;width:24px;">
												</c:if>&nbsp;
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<form action="getFiles" method="post" name="formFunction" id="formFunction">
							<input type="hidden" id="experiment" name="experiment" value="">
							<input type="hidden" id="sagitariiUrl" name="sagitariiUrl" value="${sagitariiUrl}">
							<input type="hidden" id="password" name="password" value="${password}">
							<input type="hidden" id="user" name="user" value="${user}">
						</form>
					
					</div>
										
				</div>
								
				
<script>

	function back() {
		window.location.href="index";
	}
	
	function getFiles( exp ) {
		$("#experiment").val( exp );
		$("#formFunction").submit();
	}
	
	$(document).ready(function() {
		$('#example').dataTable({
	        "oLanguage": {
	            "sUrl": "js/pt_BR.txt"
	        },	
	        "iDisplayLength" : 10,
			"bLengthChange": false,
			"fnInitComplete": function(oSettings, json) {
				doTableComplete();
			},
			"bAutoWidth": false,
			"sPaginationType": "full_numbers",
			"aoColumns": [ 
						  { "sWidth": "25%" },
						  { "sWidth": "30%" },
						  { "sWidth": "20%" },
						  { "sWidth": "30%" },
						  { "sWidth": "10%" }]						
		} ).fnSort( [[0,'desc']] );
	} );	

</script>				
				
<%@ include file="../../footer.jsp" %>