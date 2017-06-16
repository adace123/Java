interface Vehicle{
  int getSpeed();
}

class Main {
  public String type;
  public Main(String vehicleType){
    type = vehicleType;
  }
  public Main(){
    this("default type");
  }
  public String getType(){
    return this.type;
  }

  public void setType(String newType){
    this.type = newType;
  }
  public static void main(String[] args) {
    String [] astronauts = {"Neil Armstrong", "Buzz Aldrin", "Michael Collins"};
    for(int i=1;i<=3;i++)
      System.out.println(Astronaut.makeAstronaut(astronauts[i-1],"Apollo 11").toString()+""+SpaceShuttle.makeSpaceShuttle("space shuttle",32000000,24791).toString());
    SpaceShuttle s = new Astronaut();
    Astronaut a = (Astronaut) s;

    System.out.println(a.sayHello() +" My name is ["+a.name+"] and one day I will ["+a.mission+"]!!");

    System.out.println("I am a vehicle of " + s.getType());
  }
}

class SpaceShuttle extends Main implements Vehicle{
  public int hp;
  public int speed;
  public SpaceShuttle(String type,int hp, int spaceSpeed){
    super(type);
    this.hp = hp;
    speed = spaceSpeed;
  }

  public SpaceShuttle(){
    super();
  }

  public int getHp(){
    return this.hp;
  }

  @Override
  public String toString(){
    return "a " + this.getType() + " that has a horsepower of " + this.getHp()+"\nand travels at an incredible\n"+this.getSpeed()+" miles per hour!\n";
  }

  public int getSpeed(){
    return this.speed;
  }


  public static SpaceShuttle makeSpaceShuttle(String type,int hp,int spaceSpeed){
    return new SpaceShuttle(type,hp,spaceSpeed);
  }

}

class Astronaut extends SpaceShuttle{
  public String name; public String mission;

  public Astronaut(String name,String mission){
    this.name = name;
    this.mission = mission;
  }
  public Astronaut(){
    this("random name", "random mission");
  }

  @Override
  public String toString(){
    return "My name is " + this.name + " and I was a member of the " + this.mission + " mission. I flew on ";
  }

  public String sayHello(){
    return "Hi! I'm an astronaut!";
  }

  public static Astronaut makeAstronaut(String name,String mission){
    return new Astronaut(name,mission);
  }

}
