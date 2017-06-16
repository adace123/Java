import java.util.*;

class Main extends Thread{
  public void run(){
    try{
    ArrayList<String> galaxies = new ArrayList<>();
    for(Planets p:Planets.values()){
    System.out.println(Thread.currentThread().getName() +": " + p + " is a planet and is located in " + SolarSystem.solarSystem.getSolarSystem());
    Thread.sleep(1000);
    }
    System.out.println("\nSome of the most famous galaxies are:");

  for(Galaxies g:Galaxies.values())
  galaxies.add(g.toString());

  for(Iterator<String> it = galaxies.iterator();it.hasNext();){
  System.out.println(Thread.currentThread().getName() +": " + it.next());
  Thread.sleep(2000);
  }
    }
    catch(Exception e){
      System.out.println(e.toString());
    }
  }

  public static void main(String[] args) {
    Thread t1 = new Main();
    Thread t2 = new Main();
    t1.setName("Thread 1");
    t2.setName("Thread 2");
    t1.start();
    t2.start();

  }

}

enum Planets{
  Mercury, Venus, Earth, Mars, Jupiter, Saturn, Uranus, Neptune;
}

enum Galaxies{
  Milky_Way, Andromeda, Triangulum, Sombrero, M81, Whirlpool;
}

enum SolarSystem{
  solarSystem("Sol");
  String system;

  SolarSystem(String s){
    system = s;
  }

  String getSolarSystem(){
    return system;
  }
}
