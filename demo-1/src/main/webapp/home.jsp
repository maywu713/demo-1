<%@ page import="com.example.demo.HomeController"
%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
 <%
    HomeController controller = new HomeController();
  %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Table Data</title>
<link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.min.css">
</head>
<body>
<section class="section">
    <div class="container" style="display: flex; flex-direction: column; align-items: center; justify-content: center;">
      <h1 class="title">Table Data containing date, CO and NO2 levels</h1>
	
	<a class="button is-primary" href="/result" style="margin: 1em;">Calculate Mean</a>
	

 <p><%= controller.getAllData() %></p>
 
 
    </div>
  </section>
	
</body>
</html>

