
package math_quiz_game;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class fstclass {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private TextField tf1;
    private Label lbl1;
    private TableView<player> tvplayer;
    private TableColumn<player, String> colname;
    private TableColumn<player, String> colgender;
    private TableColumn<player, Integer> colage;
    
    private TextField name;
    private Button btnsbmt;
    private TextField gndr;
    private TextField age;
    @FXML
    private Button ply;

    public void switchToRules(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
    @FXML
    public void switchToBack(ActionEvent event) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("rule.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show(); 
    }
    public void switchToMakeaccount(ActionEvent event) throws IOException{
     Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();   
    }
    @FXML
    private void switchToSubmit(ActionEvent event) throws IOException {
        if(event.getSource()==btnsbmt){
            insert();
        
        }
    Parent root = FXMLLoader.load(getClass().getResource("lvl1.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
        
        
    }
    @FXML
    private void playAction(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("account.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();             
}
     @FXML
    private void DoneAction(ActionEvent event) throws IOException {
         String ans = tf1.getText();
    
    // Check if the answer is correct
    if (ans.equals("3")) {
        // Load the lvl2.fxml scene if the answer is correct
        Parent root = FXMLLoader.load(getClass().getResource("lvl2.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    else {
        // Display an error message if the answer is incorrect
        lbl1.setText("Your answer is incorrect.");
    } 
     }
    public Connection getConnection(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizGame", "root", "");
            System.out.println("db connected");
            return conn;
        }
        catch(Exception e){
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
                player data = new player (rs.getString("name"),rs.getString("gender"),rs.getInt("age"));
                playerList.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playerList;
    }
    
      public void showPlayers() {
        ObservableList<player> list = getPlayers();

        colname.setCellValueFactory(new PropertyValueFactory<player, String>("name"));
        colgender.setCellValueFactory(new PropertyValueFactory<player, String>("name"));
        colage.setCellValueFactory(new PropertyValueFactory<player, Integer>("age"));
        
        tvplayer.setItems(list);
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
    
    void insert() {
        if ( name.getText().isEmpty() || gndr.getText().isEmpty() || age.getText().isEmpty() ) {
            System.out.println("All fields must be filled.");
            return;
        }
        String query = "INSERT INTO data VALUES('" + name.getText() + "','" + gndr.getText() + "'," + age.getText() + ")";
        executeQuery(query);
        showPlayers();
    }

    @FXML
    private void participantAction(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("participant.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show(); 
    }
      }
   
     

