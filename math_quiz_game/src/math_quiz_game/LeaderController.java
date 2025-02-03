package math_quiz_game;

import java.io.IOException;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LeaderController {

    @FXML
    private TableView<Ranking> tvRanking;
    @FXML
    private TableColumn<Ranking, Integer> rank;
    @FXML
    private TableColumn<Ranking, String> topname;
    @FXML
    private TableColumn<Ranking, Integer> topcoin;
    @FXML
    private TableColumn<Ranking, Long> toptime;

    private ObservableList<Ranking> rankingData = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize columns with the properties of the Ranking class
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        topname.setCellValueFactory(new PropertyValueFactory<>("name"));
        topcoin.setCellValueFactory(new PropertyValueFactory<>("coins"));
        toptime.setCellValueFactory(new PropertyValueFactory<>("time"));

        loadRankingData();
    }

    // Method to load data from MySQL database and populate the TableView
    private void loadRankingData() {
        String url = "jdbc:mysql://localhost:3306/quizgame";
        String user = "root";
        String password = "";

        String query = "SELECT * FROM ranking ORDER BY Rank ASC";

        try (Connection conn = DriverManager.getConnection(url, user, password); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int rank = rs.getInt("Rank");
                String name = rs.getString("Name");
                int coins = rs.getInt("Coins");
                long time = rs.getLong("Time");

                // Add each ranking to the ObservableList
                rankingData.add(new Ranking(rank, name, coins, time));
            }
            // Set the table data
            tvRanking.setItems(rankingData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Back button action (you can implement this if needed)
    public void backAction(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
