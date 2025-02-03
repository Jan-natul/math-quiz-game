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
        if (isPlayerInRanking(name, time)) {
        System.out.println("Player already has a better or equal time.");
        return; // Player has a better or equal time, so do nothing
    }

    // If player doesn't exist or new time is better, calculate rank and insert/update the entry
    int rank = calculateRank(time);

    String query = "INSERT INTO ranking (Rank, Name, Coins, Time) VALUES (?, ?, ?, ?)";

    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, rank); // Set the calculated rank
        pstmt.setString(2, name); // Player's name
        pstmt.setInt(3, coins); // Player's coins
        pstmt.setLong(4, time); // Player's time

        pstmt.executeUpdate(); // Insert the data into the ranking table
        System.out.println("Data added successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private static boolean isPlayerInRanking(String name, long newTime) {
    String query = "SELECT Time FROM ranking WHERE Name = ?";
    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        
        if (rs.next()) {
            long existingTime = rs.getLong("Time");
            // Check if the new time is better (lesser) than the existing time
            if (newTime >= existingTime) {
                return true; // Player already has a better or equal time
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false; // Player is not in the ranking table or has worse time
}
       /* int rank = calculateRank(time);

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
    }*/

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
