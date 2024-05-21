package servlets;

import entities.Post;
import utils.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/post")
public class SinglePostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String postId = request.getParameter("id");

        Post post = null;
        if (postId != null && !postId.isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {

                String sql = "SELECT * FROM posts WHERE id = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setLong(1, Long.parseLong(postId));

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        post = new Post();
                        post.setId(resultSet.getInt("id"));
                        post.setTitle(resultSet.getString("title"));
                        post.setDescription(resultSet.getString("description"));
                        post.setCreationDate(resultSet.getTimestamp("creation_date"));
                        post.setUsername(resultSet.getString("username"));
                        post.setPhoneNumber(resultSet.getString("phone_number"));
                        post.setPrice(resultSet.getDouble("price"));

                        System.out.println("Post found: " + post.getTitle()); // Debug statement
                    } else {
                        System.out.println("No post found with id: " + postId); // Debug statement
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid postId received"); // Debug statement
        }

        request.setAttribute("post", post);
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }
}
