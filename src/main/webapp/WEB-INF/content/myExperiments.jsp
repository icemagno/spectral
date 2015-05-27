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
								<table class="tableForm" id="example">
									<thead>
									<tr>
										<th>Experiment</th>
										<th>Start Date/Time</th>
										<th>Status</th>
									</tr>
									</thead>
									<tbody>
									<c:forEach var="experiment" items="${experiments}">
										<tr>
											<td>${experiment.tagExec}</td>
											<td>${experiment.startDate}</td>
											<td>${experiment.status}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					
					</div>
										
				</div>
								
				
<script>

	function back() {
		window.location.href="index";
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
						  { "sWidth": "20%" },
						  { "sWidth": "10%" },
						  { "sWidth": "20%" }]						
		} ).fnSort( [[0,'desc']] );
	} );	

</script>				
				
<%@ include file="../../footer.jsp" %>