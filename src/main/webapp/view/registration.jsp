<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Зареєструйте себе</title>
<script src="http://localhost:8080/client/registration" method="POST"></script>
</head>
<body>
    <form action="http://localhost:8080/client/registration" method="post">
        <input type="text" name="clientFirstName">
        <input type="text" name="clientLastName">
        <input type="text" name="clientAge">
        <input type="text" name="phone">
        <input type="text" name="email">
        <input style="height:30px;" type="submit" value="Submit" />
    </form>
    <a>Already registered?</a>
</body>
</html>