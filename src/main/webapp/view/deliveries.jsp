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
    <c:forEach items="${deliveries}" var="delivery">
        <div style = "margin: 25px; border-bottom: 5px solid black;">
            <h5 class="el" style="display: none">Delivery ID:${delivery.deliveryId}></h5>
            <h5 class="el" >Delivery price: ${delivery.deliveryPrice}</h5>
            <h5 class="el">Delivery address: ${delivery.address}</h5>
            <h5 class="el">Delivery finsihed: ${delivery.finished}</h5>
            <h5 class="el">Item name: ${delivery.item.itemName}</h5>
            <h5 class="el">Item quantity: ${delivery.item.quantity}</h5>
            <h5 class="el">Item weight: ${delivery.item.weight}</h5>
            <h5 class="el" style="color:blue"><a href="http://localhost:8080/client/update/${delivery.deliveryId}">edit</a></h5>
            <h5 class="el" style="color:red"><a href="http://localhost:8080/client/cancel/${delivery.deliveryId}">cancel</a></h5>
        </div>
    </c:forEach> 

    <style>
    .el{
        margin:3px 0 0 0
    }
    </style>
</body>
</html>