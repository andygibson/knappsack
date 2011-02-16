#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Editing Person ${symbol_dollar}{person.id} - ${symbol_dollar}{person.firstName}  ${symbol_dollar}{person.lastName} 
</h1>
<form:form commandName="person">
ID - ${symbol_dollar}{person.id}<br/>
First Name<br/>
<form:input path="firstName"/><br/>
Last Name<br/>
<form:input path="lastName"/><br/>
<input type="submit"/>

</form:form>
</body>
</html>
