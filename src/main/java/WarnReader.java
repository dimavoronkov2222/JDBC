import java.sql.*;
public class WarnReader {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/mydb";
        String user = "user";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            while (true) {
                String selectQuery = "SELECT id, message, type, processed FROM notice WHERE type = 'WARN' AND processed = false";
                try (Statement stmt = connection.createStatement();
                     ResultSet resultSet = stmt.executeQuery(selectQuery)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String message = resultSet.getString("message");
                        System.out.println(message);
                        String updateQuery = "UPDATE notice SET processed = true WHERE id = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, id);
                            updateStmt.executeUpdate();
                        }
                    }
                }
                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}