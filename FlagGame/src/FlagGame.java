/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class FlagGame extends Application 
{
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("startscreen.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("gamescreen.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("flaggame.css");
        scene.getStylesheets().add("http://fonts.googleapis.com/css?family=Lobster");
        Scene scene2 = new Scene(root2);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        
        
    }

    public static void main(String[] args) 
    {
        launch(args);
        
    }
    
}
