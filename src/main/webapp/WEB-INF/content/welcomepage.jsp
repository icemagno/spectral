<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>

				<div id="leftBoxAlter" style="width:100%; border-right:0px" > 

					<div>
					
						<div style="position:relative;border-bottom:1px dotted #4D7A93; font-size: 17px;background:url('img/panelbar.png') no-repeat;margin:0 auto;margin-top:10px;width:820px; height:250px;">

							<div style="position:relative;padding-top:45px;float:left;width:293px;height:250px;padding-left:5px;">Request an account to the system administrator.
								<img style="height: 50px;cursor:pointer;position:absolute; top:175px;right:25px" onclick="" src="img/go.png">
							</div>
							<div style="position:relative;padding-top:45px;float:left;width:265px;height:250px;padding-left:5px;">Submit your job.
								<img style="height: 50px;cursor:pointer;position:absolute; top:175px;right:25px" onclick="form()" src="img/go.png">
							</div>
							<div style="position:relative;padding-top:45px;float:left;width:245px;height:250px;padding-left:5px;">Take the resuls.
								<img style="height: 50px;cursor:pointer;position:absolute; top:175px;right:25px" onclick="requestData();" src="img/go.png">
							</div>
							
						</div>
					
					</div>
					
				</div>

<script>

function form() {
	window.location.href="userForm";
}

function requestData() {
	window.location.href="requestData";
}

</script>

								
<%@ include file="../../footer.jsp" %>