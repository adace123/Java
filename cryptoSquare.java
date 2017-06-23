public class Main{
  public static void cryptoSquare(String message){
    message = normalize(message);
    int length = 0;
    int rows =(int) Math.ceil(Math.sqrt(message.length()));
    int cols = rows-1;
    String[][] rectangle = new String[rows][cols+1];
    String[][] encoded = new String[rows][cols+1];
    for(int i=0;i<rows;i++){
      for(int j=0;j<cols+1;j++){
        if(length<message.length()){
        rectangle[i][j] = message.split("")[length];
        System.out.print(rectangle[i][j]);
        length++;
        }
      }
      System.out.println();
      
    }
    for(int k=0;k<rectangle.length;k++){
      for(int l=0;l<rectangle.length;l++){
        if(rectangle[l][k]!=null)
        encoded[k][l] = rectangle[l][k];
        else encoded[k][l]=" ";
      }
    }
    for(int m=0;m<encoded.length;m++){
      for(int n=0;n<encoded.length;n++){
        System.out.print(encoded[m][n]);
      }
      
    }
  }
  public static String normalize(String message){
    return message.trim().replaceAll(" ","").replaceAll("[^A-Za-z]","").toLowerCase();
  }
  
  public static void main(String [] args){
    String normalized = normalize("Madness, and then illumination.");
    cryptoSquare(normalized);
  }
}
