import java.util.*;

class Main {
  public static void main(String[] args) {
       ShoppingBasket basket = new ShoppingBasket();
        basket.add("milk", 3);
        basket.print();
        System.out.println("basket price: " + basket.price() +"\n");

        basket.add("buttermilk", 2);
        basket.print();
        System.out.println("basket price: " + basket.price() +"\n");

        basket.add("milk", 3);
        basket.print();
        System.out.println("basket price: " + basket.price() +"\n");

        basket.add("milk", 3);
        basket.print();
        System.out.println("basket price: " + basket.price() +"\n");
  }
}

class Storehouse{
  public static HashMap<String, Integer> prices = new HashMap<>();
  public static HashMap<String, Integer> stocks = new HashMap<>();

  String product; int price; int stock;
  public Storehouse(String product,int price,int stock){
    this.product = product;
    this.price = price;
    this.stock = stock;
  }

  public Storehouse(){

  }

  public void addProduct(String product, int price, int stock){
    Storehouse sh = new Storehouse(product,price,stock);
    prices.put(product,price);
    stocks.put(product,stock);
  }

  public int price(String product){
    for(String key:prices.keySet()){
      if (key.equals(product))
      return prices.get(key);

    }
    return -99;
  }

  public int stock(String product){
     for(String key:stocks.keySet()){
      if(key.equals(product))
      return stocks.get(key);
     }
     return 0;
  }

  public boolean take(String product){
    Integer quantity = (Integer) stock(product);
    if(quantity>0){
      stocks.put(product,quantity-1);
      return quantity>=0;
    }
    return false;
    }

    public Set<String> products(){
      Set<String> set = stocks.keySet();
      return set;
    }

}

class Purchase{


  String product; int amount; int unitPrice;
  int price;
  public Purchase(String product, int amount, int unitPrice){
    this.product = product;
    this.amount = amount;
    this.unitPrice = unitPrice;
  }

  public Purchase(String product, int price){
    this.product = product;
    this.price = price;
  }


  public int price(){
    return this.unitPrice*this.amount;
  }

  public void increaseAmount(){
    this.amount+=1;
  }

  @Override
  public String toString(){
    return this.product + ": " + this.amount;
  }
}

class ShoppingBasket{
  Map<Purchase,Integer> purchases = new HashMap<>();

  public void add(String product, int price){
    Purchase pur = new Purchase(product,price);
    if(!purchases.containsKey(product))
    purchases.put(pur,pur.price);
    else {

      for(Purchase p:purchases.keySet()){
        if(p.product.equals(product))
        p.increaseAmount();
      }
    }
  }

  public int price(){
    Integer sum= new Integer(0);
    for(Integer p:purchases.values()){
      sum+=p;
    }
    return sum;
  }

  public void print(){
    int count = 0;
    for(Purchase p:purchases.keySet()){
      System.out.println(p.product+" : " + "1");
    }

  }
}
