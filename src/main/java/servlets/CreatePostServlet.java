package servlets;
import utils.UserFetchData;

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
        String userId = (session != null) ? (String) session.getAttribute("userid") : null;

        // if user is NOT logged in, redirect to login page
        if (userId == null) {
            response.sendRedirect("login");
            return;
        }

        // FETCH DETAILS
        String userName = null;
        String phoneNumber = null;
        int fkUserId = 0;

        try {
            // FETCH USERNAME
            userName = UserFetchData.getUserInfo(userId, "username");
            System.out.println("Username:" + userName);
            // FETCH PHONE NUMBER
            phoneNumber = UserFetchData.getUserInfo(userId, "phone_number");
            System.out.println("PhoneNumber:" + phoneNumber);
            // FETCH FKID NUMBER
            fkUserId = UserFetchData.getUserInfoInt(userId, "id");
            System.out.println("fkUserId:" + fkUserId);
            if (userName == null || phoneNumber == null || fkUserId == 0) {
                response.sendRedirect("createPost?error=Datele utilizatorului sunt invalide.");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("createPost?error=A fost o eroare la crearea anuntului, te rugam incearca din nou.");
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO posts (title, description, username, phone_number, price, fk_userid) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, title);
                statement.setString(2, description);
                statement.setString(3, userName);
                statement.setString(4, phoneNumber);
                statement.setDouble(5, price);
                statement.setInt(6, fkUserId);
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