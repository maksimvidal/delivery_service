<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Menu</title>
</head>
<body
    <div>
        <h3><a href="http://localhost:8080/client/main">main menu</a></h3>
    </div>
    <form action="http://localhost:8080/client/updateDelivery" method="post">
    <input style="display:hidden" type="text" placeholder="${delivery.deliveryId}" name="deliveryId" value="${delivery.deliveryId}">
        <h3>Item name</h3>
        <input type="text" name="itemName" value="${delivery.item.itemName}">

        <h3>Item weight</h3>
        <input type="text" name="weight" value="${delivery.item.weight}">

        <h3>Quantity</h3>
        <input type="text" name="quantity" value="${delivery.item.quantity}">

        <h3>Address</h3>
        <input type="text" name="address" value="${delivery.address}">
        <input style="height:30px;" type="submit" value="Submit" />
    </form>
</body>
</html>