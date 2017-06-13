//Aaron Feigenbaum

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class ShowTicTacToe extends JPanel {
    private CustomPanel1 myPanel;
    private JPanel jpButton;
    private JButton square;
    private JButton circle;
    private Color c;
    private static int gameOver=0;
    public static JFrame win;
    //create 2 arraylists to hold the circle and square buttons and 1 to hold the CustomPanels
    private static List<JButton> circlelist = new ArrayList<>();
    private static List<JButton> squarelist = new ArrayList<>();
    private static List<CustomPanel1> placements = new ArrayList<>();

    //number of squares and circles
    private static int squareCount=0;
    private static int circleCount=0;

    public void setColor(Color cc) {
        c = cc;
        myPanel.setBackground(c);
    }

    public ShowTicTacToe() {
        myPanel = new CustomPanel1();
        //each panel has no shape by default
        myPanel.draw(-1);
        placements.add(myPanel);

        circle = new JButton("Circle");
        square = new JButton("Square");
        circlelist.add(circle);
        squarelist.add(square);
        circle.addActionListener(new ActionListener() { //dis
                                     public void actionPerformed(ActionEvent e) {
                                         myPanel.setName("Circle");
                                         myPanel.setVisible(true);
                                         circle.setSelected(true);
                                         disableButtons(circle);
                                         myPanel.draw(CustomPanel1.CIRCLE);
                                         circleCount++;
                                         checkWin();
                                     }
                                 }
        );
        square.addActionListener(new ActionListener() {
                                     public void actionPerformed(ActionEvent e) {
                                         myPanel.setName("Square");
                                         myPanel.setVisible(true);
                                         square.setSelected(true);
                                         disableButtons(square);
                                         myPanel.draw(CustomPanel1.SQUARE);
                                         squareCount++;
                                         checkWin();
                                     }
                                 }
        );

        jpButton = new JPanel();
        jpButton.setSize(150, 25);
        jpButton.setLayout(new GridLayout(1, 2));
        jpButton.add(circle);
        jpButton.add(square);
        this.setLayout(new BorderLayout());
        this.add(myPanel, BorderLayout.CENTER);
        this.add(jpButton, BorderLayout.SOUTH);

    }



    public Dimension getPreferredSize() {
        return new Dimension(150, 175);

    }
    //disables both buttons for currently selected panel, enables square buttons and disables circle buttons if circle selected and vice versa
    public void disableButtons(JButton shape){
        if(shape.equals(this.circle)){
            for(int i=0;i<circlelist.size();i++){
                squarelist.get(i).setEnabled(true);
                circlelist.get(i).setEnabled(false);
                if(circlelist.get(i).equals(this.circle)){
                    squarelist.get(i).setEnabled(false);
                }
            }
            circlelist.remove(this.circle);
            squarelist.remove(this.square);
        }
        else{
            for(int i=0;i<squarelist.size();i++){
                circlelist.get(i).setEnabled(true);
                squarelist.get(i).setEnabled(false);
                if(squarelist.get(i).equals(this.square)){
                    circlelist.get(i).setEnabled(false);
                }
            }
            circlelist.remove(this.circle);
            squarelist.remove(this.square);
        }
    }
    //checks if specified panel in panel list has a name
    public static boolean isSelected(int panel){
        return placements.get(panel).shapeSelected();
    }

    public void checkWin(){
        //each win is based on panels at specified indices being selected and having the same name as each other
        String winningShape="";
        boolean rightDiagonalWin = (isSelected(0) && isSelected(4) && isSelected(8) && placements.get(0).getName().equals(placements.get(4).getName()) && placements.get(4).getName().equals(placements.get(8).getName()));

        boolean leftDiagonalWin = (isSelected(2) && isSelected(4) && isSelected(6)  && placements.get(2).getName().equals(placements.get(4).getName()) && placements.get(4).getName().equals(placements.get(6).getName()));

        boolean col1Win = (isSelected(0) && isSelected(3) && isSelected(6)  && placements.get(0).getName().equals(placements.get(3).getName())) && (placements.get(3).getName().equals(placements.get(6).getName()));

        boolean  col2Win = (isSelected(1) && isSelected(4) && isSelected(7)  && placements.get(1).getName().equals(placements.get(4).getName())) && (placements.get(4).getName().equals(placements.get(7).getName()));

        boolean col3Win = (isSelected(2) && isSelected(5) && isSelected(8) && placements.get(2).getName().equals(placements.get(5).getName())) && (placements.get(5).getName().equals(placements.get(8).getName()));

        boolean  row1Win = (isSelected(0) && isSelected(1) && isSelected(2) && placements.get(0).getName().equals(placements.get(1).getName())) && (placements.get(1).getName().equals(placements.get(2).getName()));

        boolean  row2Win = (isSelected(3) && isSelected(4) && isSelected(5) && placements.get(3).getName().equals(placements.get(4).getName())) && (placements.get(4).getName().equals(placements.get(5).getName()));

        boolean row3Win = (isSelected(6) && isSelected(7) && isSelected(8) && placements.get(6).getName().equals(placements.get(7).getName())) && (placements.get(7).getName().equals(placements.get(8).getName()));

        //determine which shape won
        if(rightDiagonalWin || col1Win || row1Win){
            winningShape = placements.get(0).getName();
        }
        else if(leftDiagonalWin || col2Win || row2Win){
            winningShape = placements.get(4).getName();
        }
        else winningShape = placements.get(8).getName();

        //display dialog based on outcome and ask user to restart game no matter the outcome
        if(rightDiagonalWin || leftDiagonalWin || col1Win || col2Win || col3Win || row1Win || row2Win || row3Win){

            JOptionPane.showMessageDialog(null,winningShape+" wins!");
            restartGameDialog();
        }
        else if((circleCount+squareCount)==placements.size()){
            JOptionPane.showMessageDialog(null,"Tie!");
            restartGameDialog();
        }


    }

    public static void startGame(){
        //reset counts and lists to 0 and empty respectively
        squareCount=0;
        circleCount=0;
        circlelist.clear();
        squarelist.clear();
        placements.clear();

        win = new JFrame();
        win.setVisible(true);
        win.setLayout(new GridLayout(3, 3, 5, 5));
        ShowTicTacToe[] tic = new ShowTicTacToe[9];
        for (int i = 0; i < 9; i++) {
            tic[i] = new ShowTicTacToe();
            tic[i].myPanel.setBackground(new Color((int) (Math.random() * 256),
                    (int) (Math.random() * 256), (int) (Math.random() * 256)));

            win.add(tic[i]);

        }

        win.setSize(550, 550);
        win.setResizable(false);
    }

    public static void quitGame(){
        JOptionPane.showMessageDialog(null,"Thanks for playing!");
        win.dispatchEvent(new WindowEvent(win,WindowEvent.WINDOW_CLOSING));
    }

    public static void restartGameDialog(){
        gameOver = JOptionPane.showConfirmDialog(null,"Would you like to continue?","Continue game",JOptionPane.YES_NO_OPTION);
        if(gameOver==0){
            //if user picks 'Yes' then close window and start new game
            win.dispatchEvent(new WindowEvent(win,WindowEvent.WINDOW_CLOSING));
            startGame();
        }
        else{
            quitGame();
        }
    }

    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }


    public static void main(String[] args) {
        startGame();
    }

}

class CustomPanel1 extends JPanel{
    public static final int CIRCLE = 0;
    public static final int SQUARE = 1 ;

    private int shape ;

    public CustomPanel1()
    {

    }
    public Dimension getPreferredSize()
    {
        return new Dimension(150,150);

    }
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        g.setColor(new Color(0x562A72));

        if(shape == 0)
        {
            g.fillOval(50 , 10 ,80, 80);
        }
        else if(shape == 1)
            g.fillRect(50,10,80,80);
    }

    public void draw(int choice)
    {
        shape = choice;
        repaint();
    }
    //checks if panel has a name
    public boolean shapeSelected(){
        return this.getName()!=null;
    }
}
