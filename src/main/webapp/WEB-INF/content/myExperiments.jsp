<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>


						<div class="oitenta" style="padding: 0px 0px 20px 0px;">
							<div class="titulo" style="margin-bottom: 20px;">Request Result Data</div>
                            
								<table class="tableForm" id="example">
									<thead>
									<tr>
										<th>Experiment</th>
										<th>Start Date/Time</th>
										<th>Status</th>
										<th>Elapsed Time</th>
										<th>Files</th>
									</tr>
									</thead>
                                    
									<tbody>
									<c:forEach var="experiment" items="${experiments}">
										<tr>
											<td>${experiment.tagExec}</td>
											<td>${experiment.startDate}</td>
											<c:if test="${experiment.status == 'RUNNING'}">
												<td><a href="getRunning?experiment=${experiment.tagExec}">${experiment.status}</a></td>
											</c:if>
											<c:if test="${experiment.status != 'RUNNING'}">
												<td>${experiment.status}</td>
											</c:if>
											<td>${experiment.elapsedTime}</td>
											<td>
												<c:if test="${experiment.status == 'FINISHED'}">
													<table style="border:0px;padding:0px;margin:0px;">
														<c:forEach var="file" items="${experiment.files}">
															<tr>
																<td>
																	<a href="getFiles?idFile=${file.fileName}&shortName=${file.shortName}" style="margin-top: 2px;">${file.shortName}</a>
																</td>
															</tr>
														</c:forEach>
													</table>
												</c:if>&nbsp;
											</td>
										</tr>
									</c:forEach>
									</tbody>
                                    
                                    <div class="clear"></div>
								</table>
                            <div class="clear"></div>
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