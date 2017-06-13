import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class ChatClient extends Application{
    Socket socket;
    public void start(Stage primaryStage){

        primaryStage.setTitle("Client");
        TextArea serverText = new TextArea();
        VBox box = new VBox();
        Label label1 = new Label("Server");
        box.getChildren().add(label1);
        box.setPadding(new Insets(5,5,5,5));
        ScrollPane sp = new ScrollPane();
        sp.setContent(serverText);
        box.getChildren().add(sp);
        Label label2 = new Label("Client");
        box.getChildren().add(label2);
        ScrollPane sp2 = new ScrollPane();
        TextArea clientText = new TextArea();
        sp2.setContent(clientText);
        box.getChildren().add(sp2);
        Scene scene = new Scene(box,450,400);
        primaryStage.setScene(scene);
        primaryStage.show();
        clientText.setEditable(false);
        ArrayList<String> lines = new ArrayList<>();

        new Thread(()->{
            try{
                socket = new Socket("localhost", 3000);
            while(true) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                clientText.appendText(input.readUTF()+"\n");
            }
            }catch (Exception ex){
                System.out.println(ex.getMessage());
            }
        }).start();

        serverText.setOnKeyPressed(e->{
            if(e.getCode().equals(KeyCode.ENTER)){
                String[] messages = serverText.getText().split("\n");
                lines.add(messages[messages.length-1]);
                try{
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    output.writeUTF(lines.get(lines.size()-1));
                }catch (Exception ex){
                    System.out.println(ex.getMessage());
                }

            }
        });



    }

    public static void main(String[] args) {
        launch(args);
    }
}
