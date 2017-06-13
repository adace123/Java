import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


public class Client extends Application{
    Socket socket;
    public void start(Stage primaryStage){
        GridPane pane = new GridPane();
        pane.setVgap(10);
        Label l1 = new Label("Name: ");
        pane.add(l1,0,0);
        TextField name = new TextField();
        pane.add(name,1,0);
        Label l2 = new Label("Enter text: ");
        pane.add(l2,0,1);
        TextField message = new TextField();
        pane.add(message,1,1);
        ScrollPane scroll = new ScrollPane();
        TextArea clientText = new TextArea();
        scroll.setContent(clientText);
        pane.add(scroll,0,2,2,1);
        Scene scene = new Scene(pane,480,280);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Welcome to the Chat App!");
        dialog.setHeaderText("Hey there!");
        dialog.setContentText("Please enter your username:");
        String username = dialog.showAndWait().get();
        name.setText(username);
        name.setEditable(false);
        message.requestFocus();
        primaryStage.show();

        new Thread(()->{
            try{
                socket = new Socket("localhost",3000);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                DataInputStream input = new DataInputStream(socket.getInputStream());
                if(!socket.isClosed()) {
                    output.writeUTF(name.getText() + " joined the chat at " + new Date() + "\n");
                    clientText.appendText(input.readUTF()); //append new user message

                }
                while(true){
                  clientText.appendText(input.readUTF());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }).start();


        message.setOnKeyPressed(e->{

            if(e.getCode().equals(KeyCode.ENTER)) {
                if(name.getText().isEmpty() || message.getText().isEmpty()){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText("Neither field can be blank.");
                    alert.showAndWait();
                }
                else{
//                   clientText.deleteText(clientText.getText().indexOf("(")-1,clientText.getText().indexOf("...")+3);
                    try{
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        output.writeUTF(name.getText()+":   "+message.getText());
                    }catch (Exception ex){
                        System.out.println(ex.getMessage());
                    }
                    message.clear();
                }
            }
            else{
                if(message.getText().length()==1) {
                    try {
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                        output.writeUTF(name.getText()+" is typing a message...");
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                else{

                }
            }

        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
