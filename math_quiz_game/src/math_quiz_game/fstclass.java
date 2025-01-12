/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package math_quiz_game;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class fstclass {
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void switchToRules(ActionEvent event) throws IOException{
         Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    }
    
    public void switchToBack(ActionEvent event) throws IOException{
       Parent root = FXMLLoader.load(getClass().getResource("rule.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show(); 
    }

    @FXML
    private void switchToBack(MouseEvent event) {
    }


}
