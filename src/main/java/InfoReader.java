import java.sql.*;
public class InfoReader {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/mydb";
        String user = "user";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            while (true) {
                String selectQuery = "SELECT id, message, type, processed FROM notice WHERE type = 'INFO' AND processed = false";
                try (Statement stmt = connection.createStatement();
                     ResultSet resultSet = stmt.executeQuery(selectQuery)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String message = resultSet.getString("message");
                        System.out.println(message);
                        String deleteQuery = "DELETE FROM notice WHERE id = ?";
                        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                            deleteStmt.setInt(1, id);
                            deleteStmt.executeUpdate();
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