<%@ page import="java.util.List" %>
<%@ page import="entities.Post" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vechituri.ro</title>
</head>
<body>
<%
    String user = (session != null) ? (String) session.getAttribute("user") : null;
    if (user != null) {
%>
<h1>Salut, <%= user %>!</h1>
<a href="logout">Logout</a>
<% } else { %>
<h1>Bine ai venit pe Vechituri.ro!</h1>
<a href="login.jsp">Login</a> | <a href="register.jsp">Inregistreaza-te</a>
<% } %>

<h1>Anunturi</h1>
<ul>
    <%
        List<Post> posts = (List<Post>) request.getAttribute("posts");
        if (posts != null) {
            for (Post post : posts) {
    %>
    <li>
        <a href="post?id=<%= post.getId() %>"><%= post.getTitle() %></a>
    </li>
    <%
        }
    } else {
    %>
    <p>Nu exista niciun anunt.</p>
    <%
        }
    %>
</ul>

<% if (user != null) { %>
<a href="createPost.jsp">Create New Post</a>
<% } %>
</body>
</html>
