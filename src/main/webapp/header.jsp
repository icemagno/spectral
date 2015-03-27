<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
	<head>
		<title>Spectral Simulation Workflow Portal</title>
		<link rel="stylesheet" href="css/style.css?id=400" type="text/css"/>
		<link rel="stylesheet" href="css/jquery.dataTables.css?id=400" type="text/css"/>
		<link rel="stylesheet" href="css/tipTip.css" type="text/css"/>	


		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/jquery.tipTip.minified.js"></script>	
		<script src="js/script.js"></script>
		<script src="js/jquery.dataTables.js"></script>
		
		
	</head>
	
	<body>
		<div id="mainContainer" >
		
			<div id="topBar"> 
			
				<div style="margin-left:10px;float:left;margin-top:5px;height:70px;width:170px;" id="systemLogo">
					<img src="img/logos/logo.png" style="background-color:white;padding-right:10px;padding-left:10px;border: 1px solid black; height:67;width:210;border-radius:5px;" > 
				</div>
			
				<div style="margin-left: 70px;" id="orgName">Spectral Simulation Workflow Portal</div>
			</div>

			<div id="msgBar">
				<div id="msgBarBody" >
					<span id="msgText"></span> 
				</div>
			</div>

			<div id="centerContainer" > 
				<div id="blockAllPanel"></div>
			
			
