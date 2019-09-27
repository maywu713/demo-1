<%@ page import="com.example.demo.ResultController"
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
 <%
 ResultController controller = new ResultController();
  %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Means</title>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.min.css">
</head>
<body>

	
	
	<section class="section">
    <div class="container" style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
      <h1 class="title">CO and NO2 Means throughout Year 2014</h1>
	<p>
	<b>Query For Calculating CO means: </b><i>select MEAN(co) from test where time < 1104537600000000000 AND time > 1072915200000000000</i>
	</p>
	<p>
	<b>Query For Calculating NO2 means: </b><i>select MEAN(no2) from test where time < 1104537600000000000 AND time > 1072915200000000000</i>
	</p>
	<br/>
	<br/>
	<br/>
	<div>
	<%= controller.getMeans() %>
	</div>

 
 
    </div>
  </section>
  
  
    
</body>
</html>

