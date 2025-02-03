package math_quiz_game;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/quizgame";
    private static final String USERNAME = "root"; // Default XAMPP username
    private static final String PASSWORD = ""; // Default XAMPP password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static void addRanking(String name, int coins, long time) {
        int rank = calculateRank(time);

        String query = "INSERT INTO ranking (Rank, Name, Coins, Time) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, rank);
            pstmt.setString(2, name);
            pstmt.setInt(3, coins);
            pstmt.setLong(4, time);

            pstmt.executeUpdate();
            System.out.println("Data added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int calculateRank(long time) {
        String query = "SELECT MAX(Rank) AS max_rank FROM ranking";
        int rank = 1; // Default rank if no rows exist

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                rank = rs.getInt("max_rank") + 1; // Increment the max rank by 1
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rank;
    }
}
