#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world! - 
</h1>
${symbol_dollar}{controllerMessage}<br/>
<br/>
<br/>
<a href="person/list">Go to the person list</a>
</body>
</html>
