<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>JSP com JDBC</title>
</head>
<body>
	<h1>Curso JSP</h1>

	<br>
	
	<form action="ServletLogin" method="post">
	
		<input type="hidden" name="url" value="<%= request.getAttribute("url")%>"/>
		<table>
			<tr>
				<th>Login: </th>
				<th><input type="text" name="login"/>
			</tr>
			
			<tr>
				<th>Senha: </th>
				<th><input type="password" name="senha"/></th>
			</tr>
		</table>
		<br>
		<input type="submit" value="Enviar" />
	</form>
	
	<br>
	<p>${msg}</p>
</body>
</html>