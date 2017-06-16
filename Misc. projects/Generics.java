import java.util.*;

public class Main{

  public static void main(String [] args){
    GameLibrary<PSGame> psLibrary = new GameLibrary("My Library");
    //myLibrary.addGame(new XboxGame("Gears of War"));
    psLibrary.addGame(new PSGame("Just Cause"));
    psLibrary.showGames();
  }
}

abstract class VideoGame{
  private String title;
  VideoGame(String title){
    this.title=title;
  }
  public String getTitle(){
    return title;
  }
}

class XboxGame extends VideoGame{
  XboxGame(String title){
    super(title);
  }
}

class PSGame extends VideoGame{
  PSGame(String title){
    super(title);
  }
}

class GameLibrary<T extends VideoGame>{
  public List<T> videoGameCollection = new ArrayList<>();
  private String name;
  GameLibrary(String name){
    this.name=name;
  }
  public void addGame(T vg){
    if(vg instanceof VideoGame)
    videoGameCollection.add(vg);
  }
  public void showGames(){
    for(T t:videoGameCollection){
      System.out.println(t.getTitle());
    }
  }
}
