import java.util.*;
class Main{
  public static void main(String [] args){
    Album ghostReveries = new Album("Ghost Reveries","Opeth");
    ghostReveries.addSong("The Grand Conjuration",10.00);
    ghostReveries.addSong("Ghost of Perdition",10.30);
    ghostReveries.addSong("Reverie/Harlequin Forest",11.40);

    Album leviathan = new Album("Leviathan","Mastodon");
    leviathan.addSong("I Am Ahab",4.00);
    leviathan.addSong("Blood and Thunder",5.00);
    leviathan.addSong("Hearts Alive",7.00);
    List<Song> playlist = new LinkedList<>();
    for(Song s: ghostReveries.getSongList()){
      playlist.add(s);
    }
    ListIterator<Song> it = playlist.listIterator();
    while(it.hasNext()){
      System.out.println(it.next());
    }
  }
}

class Song{
  private String title;
  private double duration;
  private String artist;
  Song(String title,String artist,double duration){
    this.title = title;
    this.duration = duration;
    this.artist = artist;
  }
  public String getTitle(){
    return this.title;
  }
  public String getArtist(){
    return this.artist;
  }
  public double getDuration(){
    return this.duration;
  }
  @Override
  public String toString(){
    return this.getTitle()+" by "+this.getArtist()+": "+this.getDuration();
  }
}

class Album{
  private List<Song> songList = new ArrayList<>();
  private String name; private String artist;
  public Album(String name,String artist){
    this.name=name;
    this.artist=artist;
  }
  public Song getSong(String title){
    for(Song s:songList){
      if(s.getTitle().equals(title)){
        return s;
      }
    }
    System.out.println("Error. Song not found.");
    return null;
  }
  public void displaySongs(){
    System.out.println(getName() + " by " + getArtist());
    for(Song s:songList){
      System.out.printf("%s : %.2f minutes",s.getTitle(),s.getDuration());
      System.out.println();
    }
    System.out.println();
  }
  public List<Song> getSongList(){
    return this.songList;
  }
  public void addSong(String title,double duration){
    songList.add(new Song(title,this.getArtist(),duration));
  }
  public String getName(){
    return this.name;
  }
  public String getArtist(){
    return this.artist;
  }
}
