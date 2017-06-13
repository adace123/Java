//Aaron Feigenbaum

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class LoanServer extends Application{
    ServerSocket serverSocket;
    Socket socket;
    public void start(Stage primaryStage){
        //create GUI
        primaryStage.setTitle("Server");
        TextArea serverText = new TextArea();
        Scene scene = new Scene(new ScrollPane(serverText),450,200);
        primaryStage.setScene(scene);
        serverText.appendText("Server started at "+new Date()+"\n");
        primaryStage.show();

        //thread waits for incoming requests and sends back loan calculation
        new Thread(()->{
            int clientCount=0;
            try{
                serverSocket = new ServerSocket(3000);
                while(true){
                    socket = serverSocket.accept();
                    clientCount++;
                    InetAddress address = socket.getInetAddress();
                    serverText.appendText("Client"+clientCount+ " connected at "+address.getHostName()+" on port "+socket.getLocalPort()+"\n");
                    DataInputStream clientInput = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
                    Double interest = clientInput.readDouble();
                    Double years = clientInput.readDouble();
                    Double amount = clientInput.readDouble();
                    Double monthlyInterestRate = interest/1200;
                    Double monthlyPayment = amount * monthlyInterestRate / (1 -
                            (1 / Math.pow(1 + monthlyInterestRate, years * 12)));
                    Double totalPayment = monthlyPayment * years * 12;
                    //write fields from client to GUI
                    serverText.appendText("Message from client: "+ interest+" "+years+" "+amount+"\n");
                    //send back calculation
                    outputToClient.writeUTF("Annual interest rate: " + interest+"\n"+"Number of years: "
                            + years+"\n"+"Loan amount: " + amount+"\n"+"Monthly payment: "+monthlyPayment+"\n"+
                            "Total payment: "+totalPayment+"\n");

                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }).start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
