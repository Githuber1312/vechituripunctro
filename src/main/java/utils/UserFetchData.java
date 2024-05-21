package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFetchData {
    public static String getUserInfo(String username, String infoType) throws SQLException {
        String fetchedData = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT " + infoType + " FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    fetchedData = resultSet.getString(infoType);
                }
            }
        }
        return fetchedData;
    }
    public static int getUserInfoInt(String username, String infoType) throws SQLException {
        int infoValue = 0;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT " + infoType + " FROM users WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    infoValue = resultSet.getInt(infoType);
                }
            }
        }
        return infoValue;
    }
}
