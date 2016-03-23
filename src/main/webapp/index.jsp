<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../../header.jsp" %>
    
<!--- conteudo pagina inicial ----->
<img src="img/grafos1.png" class="alignright" style="margin-right: -60px; margin-top: -20px;">
<div class="content">

<p>Computers have been, definitely, used for many graph theorists as a tool for describe classes of graphs that satisfies some constraints and/or optimizes a given function.</p>

<p>RioGraphX is a portal for supporting experiments involving spectral parameters of a graph and some other invariants. This platform is based on <a target="_BLANK" href="http://eic-cefet-rj.github.io/sagitarii/">Sagitarii Data Science Workflow System</a> that integrates Nauty, Lapack++, Sage and GraphViz. </p>

<p>The aim is to run experiments in order to find extremal graphs for a specified objective function given as an input. The idea is to generate all graphs satisfying some constraints and from that get the top graphs corresponding the optimization of a given objective function. Those graphs are displayed in a PDF file. Also, the .g6 files of  the top graphs are available at the end of the experiment.</p>

</div>
<!--- // conteudo pagina inicial ----->
    
<%@ include file="../../footer.jsp" %>