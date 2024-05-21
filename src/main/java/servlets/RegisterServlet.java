package servlets;

import utils.DatabaseConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        String phoneNumber = request.getParameter("phone_number");

        // Log received data
        System.out.println("Received registration request:");
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("Name: " + username);
        System.out.println("Phone Number: " + phoneNumber);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO users (email, password, username, phone_number) VALUES (?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setString(3, username);
                statement.setString(4, phoneNumber);
                statement.executeUpdate();
                System.out.println("Te-ai inregistrat cu succes!");
                response.sendRedirect("register-success.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Eroare la inregistrare, va rugam incerati din nou.");
            response.sendRedirect("register-fail.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
