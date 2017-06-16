
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class FXMLDocumentController implements Initializable 
{
    
    @FXML
    private Label scoreLabel;
    
    @FXML
    private Label timer;
    
    @FXML
    private Label countryNameHolder;
    
    @FXML
    private GridPane gamegrid;
    
    @FXML
    private HBox lives;
    
    @FXML
    protected Button start;
    
    @FXML
    private Button howtoplay;
    
    @FXML
    private Button scores;
    
    @FXML
    static Stage gameStage;
    @FXML
    static Scene gamescreen;
    
    static List<String> filenames = new ArrayList<>();
    static List<Node> gamepanels = new ArrayList<>();
    static List<String> randomSix = new ArrayList<>();
    static List<Node> lifepanes = new ArrayList<>();
    static Map<Integer,String> map = new TreeMap<>(Collections.reverseOrder());
    
    String dir = System.getProperty("user.dir");
    static int scorenum = 0;
    static int wrongguess = -1;
   

    

    Alert gameover = new Alert(AlertType.INFORMATION);
    TextInputDialog dialog = new TextInputDialog();
    Alert showInstructions = new Alert(AlertType.INFORMATION);
   
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException 
    {
        
        if(event.getSource().equals(start))
        {      
            scorenum=0;

                try{
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gamescreen.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        
                        gameStage = new Stage();
                        gamescreen = new Scene(root1);
                        gameStage.setScene(gamescreen);  
                        gameStage.setResizable(false);
                        gameStage.show();
                        gameStage.setAlwaysOnTop(true);

                    }
                        catch(Exception e){}
            
        }
        
        if(event.getSource().equals(howtoplay))
        {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("How To Play");
                alert.setHeaderText("Instructions");

                TextArea textArea = new TextArea();

                textArea.setEditable(false);
                textArea.setWrapText(true);
                alert.getDialogPane().setPrefSize(500,575);
                textArea.setMaxWidth(Double.MAX_VALUE);
                textArea.setMaxHeight(Double.MAX_VALUE);
                GridPane.setVgrow(textArea, Priority.ALWAYS);
                GridPane.setHgrow(textArea, Priority.ALWAYS);
                GridPane expContent = new GridPane();
                expContent.setMaxWidth(Double.MAX_VALUE);
                expContent.add(textArea, 0, 1);

                alert.getDialogPane().setExpandableContent(expContent);
                alert.getDialogPane().setExpanded(true);

                Path path = Paths.get(FXMLDocumentController.class.getClassLoader().getResource("instructions.txt").getPath());


                    try (BufferedReader reader = Files.newBufferedReader(path))
                        {
                            String line;
                            while ((line = reader.readLine()) != null) 
                            {
                                textArea.appendText(line+"\n");
                            }

                        }catch(Exception e){}

                alert.showAndWait();

        }

        if(event.getSource().equals(scores))
        {
            map.clear();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("High Scores");
            alert.setHeaderText("Scoreboard\n(Name: Score)");
            alert.setContentText(null);
            TextArea textArea = new TextArea();
            alert.getDialogPane().setPrefSize(300,400);
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setStyle("-fx-font-size: 24px;-fx-text-alignment: center;");
            textArea.setText("");
    
            GridPane.setVgrow(textArea, Priority.ALWAYS);
            GridPane.setHgrow(textArea, Priority.ALWAYS);
            GridPane expContent = new GridPane();
            expContent.setMaxWidth(Double.MAX_VALUE);
            expContent.add(textArea, 0, 1);

            alert.getDialogPane().setExpandableContent(expContent);
            alert.getDialogPane().setExpanded(true);
            Path path = Paths.get(dir+"\\src\\highscores.txt");
            BufferedReader reader = Files.newBufferedReader(path);
            
    
                try {
                    String line;
                    while ((line = reader.readLine()) != null) 
                    {
			map.put(Integer.parseInt(line.split(" ")[1]),line.split(" ")[0]);
                    }
                    for(Map.Entry<Integer,String> entry:map.entrySet())
                    {
                        textArea.appendText(entry.getValue()+" "+entry.getKey()+"\n");
                    } 
                    }catch(Exception e){}
            

            alert.showAndWait();

        }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {   
        resetFlags();
    }    
    

    public void resetFlags()
    {
        wrongguess = -1;


        Task task = new Task<Void>() 
        {
            @Override
            public Void call() throws Exception 
            {
                int i = 8;
                while (i>-1) 
                {
                    final int finalI = i;
                    
                    
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public synchronized void run() 
                        {
                            try{
                                timer.setText("Time left: " + finalI); 
                                if(timer.getText().equals("Time left: 0") && wrongguess<2)
                                gameOver(); 
                                }catch(Exception e){}
                        }
                    });
            
                    i--;
                    Thread.sleep(1000);
                }
                   return null;
            }
        };
   
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        
        for(int i=0;i<3;i++)
        {
    
            try
                {
                    lifepanes.add(lives.getChildren().get(i)); 
                }catch(Exception e){}
      
        }

        for(int i=0;i<lifepanes.size();i++)
        {
            Pane pane = (Pane) lifepanes.get(i);
                try{
                    Image image = new Image("heart.png",47,37,false,true);
                    BackgroundImage myBI= new BackgroundImage(image,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
                    pane.setBackground(new Background(myBI));
                    }catch(Exception e){}
        }
      
        filenames.clear();
        gamepanels.clear();
        randomSix.clear();


        Path path = Paths.get(FXMLDocumentController.class.getClassLoader().getResource("flagnames.txt").getPath());
        
        try (BufferedReader reader = Files.newBufferedReader(path)) 
        {
            String line;
    
            while ((line = reader.readLine()) != null) 
            {
                filenames.add(line);
            }
        }catch (IOException ex) 
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

        for(int i=0;i<6;i++)
        {      
             try
             {
                 gamepanels.add(gamegrid.getChildren().get(i));
             }catch(Exception e){}
             
        }
         

        Collections.shuffle(filenames);


        for(int i=0;i<gamepanels.size();i++)
        {
            randomSix.add(filenames.get(i));
            scoreLabel.setText("Score: "+scorenum);
            Pane pane = (Pane) gamepanels.get(i);
            pane.setVisible(true);
                try
                {
                    Image image = new Image(filenames.get(i),200,150,false,true);
                    BackgroundImage myBI= new BackgroundImage(image,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);
                    pane.setBackground(new Background(myBI));
                    pane.setId(filenames.get(i).replace("/flagfiles/","").replace(".png","").replace("_"," "));
                    pane.setOnMouseClicked(new EventHandler<MouseEvent>()
                        {   
                            @Override
                            public void handle(MouseEvent t) 
                            {
                                pane.setVisible(false);
               
                                if(randomSix.get(0).replace("/flagfiles/","").replace(".png","").replace("_"," ").equals(pane.getId()))
                                {
                                    scorenum++;
                                    scoreLabel.setText("Score: "+scorenum);
                                    th.stop();
                                    resetFlags();
                                }
                                else
                                {
                                    wrongguess++;
                                    if(wrongguess<lives.getChildren().size()-1)
                                    {
                                        Pane pane = (Pane) lives.getChildren().get(wrongguess);
                                        pane.setBackground(Background.EMPTY);
                                    }
                                    else
                                    {
                                        Pane pane = (Pane) lives.getChildren().get(wrongguess);
                                        pane.setBackground(Background.EMPTY);
                                        gameOver();
                                    }
                                }       
                        }
                    });
           
            }catch(Exception e){System.out.println(e.getMessage());}    
        }


        Collections.shuffle(randomSix);   
        try
        { 
            countryNameHolder.setText(randomSix.get(0).replace("/flagfiles/","").replace(".png","").replace("_"," "));
            countryNameHolder.setWrapText(true);
        }catch(Exception e){}       
      
    }
    
    public void gameOver()
    {
        dialog.setTitle("Game over!");
        dialog.setHeaderText("Thanks for playing!");
        dialog.setContentText("Please enter your name:");
        gameStage.setAlwaysOnTop(false);
        Optional<String> result = dialog.showAndWait(); 
        gameStage.close();
        if(result.isPresent())
        {
            User user = new User(result.get(),scorenum);
            User.openFile();
            User.writeUser(user);
            User.closeFile(user);
            gameStage.close();
        }
    }
}
    
    
  // end of resetFlags
    
    
    //----------------------------------------------------------------------------------------

class User implements Serializable{
    static String dir = System.getProperty("user.dir");
    static ObjectOutputStream output;
    static ObjectInputStream input;
    static ArrayList<User> userList = new ArrayList<>();
    String name;
    int score;
    
    public User(String name,int score)
    {
        this.name = name;
        this.score=score;
        userList.add(this);
    }
    public int getScore()
    {
        return this.score;
    }
    public String getName()
    {
        return this.name;
    }
    public void setScore(int newScore)
    {
        this.score = newScore;
    }
    public void setName(String newName)
    {
        this.name = newName;
    }
    public static void openFile()
	{
		try{
			 output = new ObjectOutputStream(new FileOutputStream(dir+"\\src\\user.ser"));
			 
                    }catch (IOException io)
                        {
                            System.out.println("Error found ...");
                        }
		
	}
    public static void writeUser(User user)
    {   
          try
          {
            output.writeObject(user);
          }catch(Exception e){}
      
    }
  
  public static void closeFile(User user)
    {
        File file = new File(dir+"\\src\\highscores.txt");
        try
        {
            FileWriter filewriter = new FileWriter(file,true);
            BufferedWriter writer = new BufferedWriter(filewriter);
            writer.write(user.getName()+": "+user.getScore()+"\n");
            writer.close();
            filewriter.close();
        }catch (IOException ex) 
            {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
      try
        {
            if(output != null)
            output.close();
        }catch (IOException io)
            {
                System.err.println("Error in writing to File");
                System.exit(1);
            }
    }
  
}