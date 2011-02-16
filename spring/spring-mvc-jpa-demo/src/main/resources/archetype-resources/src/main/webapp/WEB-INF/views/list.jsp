#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
<h1>Listing People</h1>
<c:forEach items="${symbol_dollar}{people}" var="v_person">
	<a href="edit?id=${symbol_dollar}{v_person.id}">${symbol_dollar}{v_person.id} -
	${symbol_dollar}{v_person.firstName} ${symbol_dollar}{v_person.lastName}</a>
	<br />
</c:forEach>
<a href="edit"> Add Person</a>
</body>
</html>
