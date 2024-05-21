<!DOCTYPE html>
<html>
<head>
    <title>Autentificare</title>
</head>
<body>
<h1>Autentificare</h1>
<form action="login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Parola:</label>
    <input type="password" id="password" name="password" required><br>

    <input type="submit" value="Autentificare">
</form>

<%
    String error = request.getParameter("error");
    if (error != null) {
%>
<p style="color: red;"><%= error %></p>
<%
    }
%>
<a href="index">Inapoi</a>

</body>
</html>
