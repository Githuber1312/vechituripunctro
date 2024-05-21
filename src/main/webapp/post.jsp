<%@ page import="entities.Post" %>
<!DOCTYPE html>
<%
    Post post = (Post) request.getAttribute("post");
%>
<html>
<head>
    <title><%= (post != null) ? post.getTitle() : "Nu am gasit acest anunt" %></title>
</head>
<body>
<h1>Anunt</h1>
<%
    if (post == null) {
        System.out.println("Post is NULL"); // Debug statement
%>
<p>Nu am gasit acest anunt.</p>
<%
} else {
%>
<ul>
    <li>
        <p><strong>Descriere:</strong> <%= post.getDescription() %></p>
        <p><strong>User:</strong> <%= post.getUser() %></p>
        <p><strong>Data postarii:</strong> <%= post.getCreationDate() %></p>
        <p><strong>Numar telefon:</strong> <%= post.getPhoneNumber() %></p>
        <p><strong>Pret:</strong> <%= post.getPrice() %></p>
    </li>
</ul>
<%
    }
%>
<a href="index">Inapoi</a>
</body>
</html>
