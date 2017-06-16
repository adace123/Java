import java.util.*;

interface CarDescription{
  public String getModel();
  public int getYear();
}

interface RocketDescription{
  public int getSpeed();
  public int getHP();
  public String getModel();
}

 abstract class AbstractFactory{
  public abstract Car getCar(String c,int year);
  public abstract Rocketship getRocketship(String r,int hp, int speed);
}

 class VehicleFactoryCreator{
  public AbstractFactory getFactory(String f){
    if(f.equalsIgnoreCase("car"))
    return new CarFactory();
    if(f.equalsIgnoreCase("Rocketship"))
    return new RocketshipFactory();
    return null;
  }

}

 class CarFactory extends AbstractFactory{
  public Rocketship getRocketship(String r,int hp, int speed){
    return null;
  }
  public Car getCar(String c,int year){
    if(c.equalsIgnoreCase("Maserati"))
    return new Car("Maserati",year);
    else if(c.equalsIgnoreCase("Tesla"))
    return new Car("Tesla",year);
    return null;
  }

}

 class RocketshipFactory extends AbstractFactory{
  public Car getCar(String c,int year){
    return null;
  }

  public Rocketship getRocketship(String r,int hp, int speed){
    if(r.equalsIgnoreCase("Saturn-V"))
    return new Rocketship(hp,speed,"Saturn-V");
    if(r.equalsIgnoreCase("Falcon-9"))
    return new Rocketship(hp,speed,"Falcon-9");
    return null;
  }

}

 class Car extends CarFactory implements CarDescription{
  public String model; int year;

  public String getModel(){
    return this.model;
  }
  public int getYear(){
    return this.year;
  }

  public Car(String model,int year){
    this.model=model;
    this.year=year;
  }
}

 class Rocketship extends RocketshipFactory implements RocketDescription{
  public int hp; public int speed;String model;
  public Rocketship(int hp,int speed,String model){
    this.hp = hp;
    this.speed=speed;
    this.model=model;
  }
  public int getSpeed(){
    return this.speed;
  }
  public int getHP(){
    return this.hp;
  }
  public String getModel(){
    return this.model;
  }
}


class Main {
  public static void main(String[] args) {
    VehicleFactoryCreator vfc = new VehicleFactoryCreator();
    CarFactory cf = (CarFactory)vfc.getFactory("car");
    Car maserati = cf.getCar("Maserati",2001);
    System.out.println("I am of type " + maserati.getModel() + " and I was made in " + maserati.getYear());
    RocketshipFactory rf = (RocketshipFactory)vfc.getFactory("Rocketship");
    Rocketship saturnv = rf.getRocketship("Saturn-V",200000,100000);
    System.out.println("I am of type " + saturnv.getModel() + " and I travel at a max speed of " + saturnv.getSpeed());
  }
}
