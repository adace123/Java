//Aaron Feigenbaum

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class LoanClient extends Application{
    public void start(Stage primaryStage){
        //create GUI
        primaryStage.setTitle("Client");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        Text sceneTitle = new Text("Loan Calculator");
        sceneTitle.setFont(Font.font("Arial", FontWeight.BOLD,20));
        grid.add(sceneTitle,0,0,2,1);
        Label label1 = new Label("Interest rate:");
        grid.add(label1,0,1);
        TextField interestRate = new TextField();
        grid.add(interestRate,1,1);
        Label label2 = new Label("Number of Years:");
        grid.add(label2,0,2);
        TextField numYears = new TextField();
        grid.add(numYears,1,2);
        grid.add(new Label("Loan amount:"),0,3);
        TextField loanAmount = new TextField();
        grid.add(loanAmount,1,3);
        Button calculate = new Button("Calculate Loan Amount");
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.getChildren().add(calculate);
        grid.add(hbox,1,4);
        ScrollPane pane = new ScrollPane();
        TextArea clientInfo = new TextArea();
        pane.setContent(clientInfo);
        grid.add(pane,0,5,2,1);

        Scene scene = new Scene(grid, 300,275);
        primaryStage.setScene(scene);
        primaryStage.show();

        //when calculate button is clicked
        calculate.setOnAction((e)->{
            //error message if any field is empty
            if(interestRate.getText().isEmpty() || numYears.getText().isEmpty() || loanAmount.getText().isEmpty()){
                clientInfo.appendText("Error. All fields must be filled in.");
            }
            else{
                clientInfo.clear();
                try{
                    //connect with server and send out form fields
                    Socket clientSocket = new Socket("localhost",3000);
                    DataInputStream  input = new DataInputStream(clientSocket.getInputStream());
                    DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
                    output.writeDouble(Double.parseDouble(interestRate.getText()));
                    output.writeDouble(Double.parseDouble(numYears.getText()));
                    output.writeDouble(Double.parseDouble(loanAmount.getText()));

                        clientInfo.appendText(input.readUTF());


                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
