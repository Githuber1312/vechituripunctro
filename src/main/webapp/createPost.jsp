<!DOCTYPE html>
<html>
<head>
    <title>Create Post</title>
</head>
<body>
<h1>Create Post</h1>
<form action="createPost" method="post">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description" required></textarea><br>

    <label for="price">Price:</label>
    <input type="text" id="price" name="price" required><br>

    <input type="submit" value="Create Post">
</form>

<%
    String error = request.getParameter("error");
    if (error != null) {
%>
<p style="color: red;"><%= error %></p>
<%
    }
%>

<a href="index">Back to Posts</a>
</body>
</html>
