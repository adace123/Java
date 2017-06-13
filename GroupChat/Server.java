import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class Server extends Application{
    ServerSocket serverSocket;

    ArrayList<String> userNames = new ArrayList<>();
    ArrayList<Socket> sockets = new ArrayList<>();

    public void start(Stage primaryStage){
        primaryStage.setTitle("MultiThread Chat Server");
        ScrollPane scrollPane = new ScrollPane();
        TextArea serverText = new TextArea();
        scrollPane.setContent(serverText);
        Scene scene = new Scene(scrollPane,480,185);
        primaryStage.setScene(scene);
        primaryStage.show();
        try{
            serverSocket = new ServerSocket(3000);
        }
    catch (Exception ex){
        System.out.println(ex.getMessage());
    }

    new Thread(()->{
        serverText.appendText("Server started: "+"listening on port "+serverSocket.getLocalPort()+"\n");
        while(true){

            try{
              Socket socket = serverSocket.accept();
                sockets.add(socket);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                String newUser = input.readUTF(); //new user message

                serverText.appendText("Client connected: "+socket+"\n");

                for(Socket s:sockets){
                    DataOutputStream stream = new DataOutputStream(s.getOutputStream());

                    stream.writeUTF(newUser);
                }

                new Thread(()->{
                    try{
                        while(true){
                            String line = input.readUTF();
                            socket.setKeepAlive(true);
                            for(Socket s:sockets){
                                DataOutputStream stream = new DataOutputStream(s.getOutputStream());
                                if(s.equals(socket)){
                                    stream.writeUTF("(Me) " + line+"\n");
                                }else {
                                    stream.writeUTF(line+"\n");
                                }
                            }
                        }
                    }catch (Exception e){
                        try{
                            socket.close();
                            sockets.remove(socket);
                            serverText.appendText("Client disconnected: "+socket+"\n");
                            String username = newUser.split(" ")[0];
                            for(Socket s:sockets){
                                DataOutputStream stream = new DataOutputStream(s.getOutputStream());
                                stream.writeUTF(username+ " left the chat room at " + new Date());
                            }

                        }catch (Exception ex){
                            System.out.println(e.getMessage());
                        }

                    }
                }).start();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }).start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
