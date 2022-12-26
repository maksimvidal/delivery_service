<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Menu</title>
</head>
<body>
    <div>
        <h3><a href="http://localhost:8080/client/main">main menu</a></h3>
    </div>
    <c:forEach items="${couriers}" var="courier">
        <div style = "margin: 25px; border-bottom: 5px solid black;">
            <h5 class="el" style="display: none">Delivery ID:${courier.courierId}></h5>
            <h5 class="el" >Courier name: ${courier.firstName}</h5>
            <h5 class="el">Courier surname: ${courier.lastName}</h5>
            <h5 class="el">Courier age: ${courier.age}</h5>
            <h5 class="el">Courier phone : ${courier.phoneNumber}</h5>
            <h5 class="el" style="color:blue"><a href="http://localhost:8080/client/assign/${courier.courierId}">assign</a></h5>
        </div>
    </c:forEach>

    <style>
    .el{
        margin:3px 0 0 0
    }
    </style>
</body>
</html>