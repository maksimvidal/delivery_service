<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Menu</title>
</head>
<body>
    <div>
        <h3><a href="http://localhost:8080/client/main">main menu</a></h3>
    </div>
    <form action="http://localhost:8080/client/createDelivery" method="post">
        <h3>Item name</h3>
        <input type="text" name="itemName">

        <h3>Item weight</h3>
        <input type="text" name="weight">

        <h3>Quantity</h3>
        <input type="text" name="quantity">

        <h3>Address</h3>
        <input type="text" name="address">
        <input style="height:30px;" type="submit" value="Submit" />
    </form>
</body>
</html>

