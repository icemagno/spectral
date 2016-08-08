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
        <link href='https://fonts.googleapis.com/css?family=Lato:400' rel='stylesheet' type='text/css'>
        <link href='https://fonts.googleapis.com/css?family=Open+Sans+Condensed:700' rel='stylesheet' type='text/css'>


		<script src="js/jquery-1.11.0.min.js"></script>
		<script src="js/jquery.tipTip.minified.js"></script>	
		<script src="js/script.js"></script>
		<script src="js/jquery.dataTables.js"></script>
		
	</head>
	
	<body>
	
		<div id="msgBar">
			<div id="msgBarBody" >
				<span id="msgText"></span> 
			</div>
		</div>
	
	
		<div id="centerContainer" > 
		<div id="blockAllPanel"></div>
				
        <div id="page">
        <img src="img/logo.png">
        
        
        <c:if test="${empty user}">    
	        <div class="log_reg">
	            <a href="login">Login</a> or <a href="createAccount">Create an account</a>    
	        </div>
        </c:if>
        
        <c:if test="${not empty user}">    
	        <div class="log_reg">
	            ${user.fullName} | <a href="logout">Logout</a>
	            <br />${user.details}    
	        </div>
        </c:if>
        
        
            
        <div class="clear"></div>
        
        <div id="menu">
            <ul>
                <li><a href="index">Home</a></li>
                <li><a href="description">Description</a></li>
		        <c:if test="${not empty user}">    
	                <li class="sae"><a href="userForm">Submit an experiment</a></li>
	                <li><a href="showMyExperiments">My experiments</a></li>
	            </c:if>
		        <c:if test="${empty user}">    
	                <li class="sae"><a href="login">Submit an experiment</a></li>
	                <li><a href="login">My experiments</a></li>
	            </c:if>
            </ul>    
        </div>
        
        <div class="clear"></div>
