package servlets;

import utils.DatabaseConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

@WebServlet("/createPost")
public class CreatePostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String user = (session != null) ? (String) session.getAttribute("user") : null;

        // if user is NOT logged in, redirect to login page
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String phoneNumber = request.getParameter("phone_number");
        double price = Double.parseDouble(request.getParameter("price"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO posts (title, description, user_email, phone_number, price) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, user);
                statement.setString(4, phoneNumber);
                statement.setDouble(5, price);
                statement.executeUpdate();

                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()){
                    long postId = generatedKeys.getLong(1);
                    response.sendRedirect("post?id=" + postId);
                }   else {
                    response.sendRedirect("createPost?error=Ceva nu a functionat, incearca din nou.");
            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("createPost.jsp?error=An error occurred while creating the post. Please try again.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("createPost.jsp").forward(request, response);
    }
}