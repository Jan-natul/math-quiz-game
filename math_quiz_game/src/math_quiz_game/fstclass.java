package math_quiz_game;

import java.io.IOException;
import java.sql.*;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;


public class fstclass {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField tf1;
    @FXML
    private Label lbl1;
    @FXML
    private TableView<player> tvplayer;
    @FXML
    private TableColumn<player, String> colname;
    @FXML
    private TableColumn<player, String> colgender;
    @FXML
    private TableColumn<player, Integer> colage;
    @FXML
    private TextField name;
    @FXML
    private Button btnsbmt;
    @FXML
    private TextField gndr;
    @FXML
    private TextField age;
    @FXML
    private TextField tf2;
    @FXML
    private Label lbl3;
    @FXML
    private Label lbl4;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;
    @FXML
    private Label lbl5;
    @FXML
    private TextField tf5;
    @FXML
    private Label lblTime;

    private long startTime;
    private long endTime;
    private TableColumn<leaderBoard,Integer> rank;
    @FXML
    private TableColumn<leaderBoard, String> topname;
    @FXML
    private TableColumn<leaderBoard, Integer> topcoin;
    @FXML
    private TableColumn<leaderBoard, Long> toptime;
    @FXML
    private TableView<leaderBoard> tvRanking;
    @FXML
    private Button ply;
    @FXML
    private Button btnrank;
    @FXML
    private Button crs;
    public String S_name;
    
    public Preferences prefs = Preferences.userRoot().node("Math_quiz_game");
    
    
    @FXML
    public void switchToRules(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void switchToBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("rule.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToSubmit(ActionEvent event) throws IOException {
        System.out.println("my name: " + S_name);
        
        if (event.getSource() == btnsbmt) {
            insert();
        }

        
        startTime = System.currentTimeMillis(); 

        Parent root = FXMLLoader.load(getClass().getResource("lvl1.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void playAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void DoneAction(ActionEvent event) throws IOException {
        String ans = tf1.getText();

        if (ans.equals("3")) {
            Parent root = FXMLLoader.load(getClass().getResource("lvl2.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            lbl1.setText("Your answer is incorrect.");
        }
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizGame", "root", "");
            System.out.println("db connected");
            return conn;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public ObservableList<player> getPlayers() {
        ObservableList<player> playerList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM data";

        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while (rs.next()) {
                player data = new player(rs.getString("name"), rs.getString("gender"), rs.getInt("age"));
                playerList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;
    }

    public void showPlayers() {
        if (tvplayer != null && colname != null && colgender != null && colage != null) {
            ObservableList<player> list = getPlayers();
            colname.setCellValueFactory(new PropertyValueFactory<player, String>("name"));
            colgender.setCellValueFactory(new PropertyValueFactory<player, String>("gender"));
            colage.setCellValueFactory(new PropertyValueFactory<player, Integer>("age"));
            tvplayer.setItems(list);
        } else {
            System.out.println("TableView or columns not initialized.");
        }
    }

    void executeQuery(String query) {
        Connection conn = getConnection();
        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private boolean playerExists(String name, String gender, String age) {
        String query = "SELECT COUNT(*) FROM data WHERE name = ? AND gender = ? AND age = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, gender);
            pstmt.setInt(3, Integer.parseInt(age));

            ResultSet rs = pstmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    void insert() {
        prefs.put("name", name.getText());
        if (name.getText().isEmpty() || gndr.getText().isEmpty() || age.getText().isEmpty()) {
            System.out.println("All fields must be filled.");
            return;
        }

        if (playerExists(name.getText(), gndr.getText(), age.getText())) {
            System.out.println("Player already exists in the database.");
            return;
        }

        String query = "INSERT INTO data (name, gender, age) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            S_name = name.getText();
            pstmt.setString(1, name.getText());
            pstmt.setString(2, gndr.getText());
            pstmt.setInt(3, Integer.parseInt(age.getText()));
            pstmt.executeUpdate();
            System.out.println("Player added successfully.");
            showPlayers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void participantAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("participant.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void initialize() {
        showPlayers();
        updateLeaderboardTable();
    }
    @FXML
    private void pbackAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void DoneAction2(ActionEvent event) throws IOException {
        String ans = tf2.getText();

        if (ans.equals("446")) {
            Parent root = FXMLLoader.load(getClass().getResource("lvl3.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            lbl1.setText("Your answer is incorrect.");
        }
    }
    @FXML
    private void DoneAction3(ActionEvent event) throws IOException {
        String ans = tf3.getText();

        if (ans.equals("11")) {
            Parent root = FXMLLoader.load(getClass().getResource("lvl4.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            lbl3.setText("Your answer is incorrect.");
        }
    }
    @FXML
    private void DoneAction4(ActionEvent event) throws IOException {
        String ans = tf4.getText();

        if (ans.equals("410")) {
            Parent root = FXMLLoader.load(getClass().getResource("lvl5.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            lbl4.setText("Your answer is incorrect.");
        }
    }
    @FXML
    private void DoneAction5(ActionEvent event) throws IOException {
        String ans = tf5.getText();

        if (ans.equals("87")) {
            endTime = System.currentTimeMillis();

            long timeTaken = endTime - startTime;

            long timeInSeconds = timeTaken / 1000;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("congress.fxml"));
            Parent root = loader.load();

            fstclass congressController = loader.getController();
            congressController.setElapsedTime(timeInSeconds);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
             updateLeaderboardTable();
             
        } else {
            lbl5.setText("Your answer is incorrect.");
        }
    }
    @FXML
    private void homaAction(ActionEvent event) throws IOException {
        String storedName = prefs.get("name", "Default Name"); 
        System.out.println("my name: " + prefs.get("name", "Default"));
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setElapsedTime(long elapsedTimeInSeconds) {
        lblTime.setText( + elapsedTimeInSeconds + " sec ");
        String storedName = prefs.get("name", "Default Name");
        DatabaseHandler.addRanking(storedName, 25, elapsedTimeInSeconds);
    }
    @FXML
    private void backAction(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
       public ObservableList<leaderBoard> getLeaderboardEntries() {
        ObservableList<leaderBoard> leaderboardList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM ranking ORDER BY time ASC"; 

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                leaderboardList.add(new leaderBoard(
                        rs.getInt("rank"), 
                        rs.getString("name"), 
                        rs.getInt("coins"), 
                        rs.getLong("time")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leaderboardList;
    }

    public void insertLeaderboardEntry(String name, long time) {
        Connection conn = getConnection();
        String query = "INSERT INTO ranking (name, coins, time) VALUES (?, 25, ?)"; 
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setLong(2, time);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateLeaderboardTable() {
    if (tvRanking == null || rank == null || topname == null || topcoin == null || toptime == null) {
        System.out.println("TableView or columns are not initialized.");
        return;
    }

    ObservableList<leaderBoard> leaderboard = getLeaderboardEntries();
    rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
    topname.setCellValueFactory(new PropertyValueFactory<>("name"));
    topcoin.setCellValueFactory(new PropertyValueFactory<>("coins"));
    toptime.setCellValueFactory(new PropertyValueFactory<>("time"));
    tvRanking.setItems(leaderboard);

    int rankCounter = 1;
    for (leaderBoard entry : leaderboard) {
        entry.setRank(rankCounter++);
    }
}
    @FXML
    private void rankAction(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("leader.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void crossAction(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close();
    System.exit(0);
    }

}
    

