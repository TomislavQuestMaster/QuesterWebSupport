<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
    <title>Add node page</title>
</head>
<body>

<h1>Add Connection</h1>

<p>Here you can add a new connection</p>
<form:form method="POST" commandName="link" action="${pageContext.request.contextPath}/connect">
    <table>
        <tbody>
        <tr>
            <td>Id:</td>
            <td><form:input path="child"/></td>
        </tr>
        <tr>
            <td>Id:</td>
            <td><form:input path="parent"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Add"/></td>
            <td></td>
        </tr>
        </tbody>
    </table>
</form:form>

</body>

</html>