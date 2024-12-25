import java.sql.*;
import java.time.LocalDateTime;
import java.util.Random;
public class MessageWriter {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5434/mydb";
        String user = "user";
        String password = "password";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Random random = new Random();
            while (true) {
                String message;
                String type;
                if (random.nextBoolean()) {
                    message = "Новое сообщение от " + LocalDateTime.now();
                    type = "INFO";
                } else {
                    message = "Произошла ошибка в " + LocalDateTime.now();
                    type = "WARN";
                }
                String query = "INSERT INTO notice (message, type, processed) VALUES (?, ?, false)";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setString(1, message);
                    stmt.setString(2, type);
                    stmt.executeUpdate();
                }
                Thread.sleep(1000);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}