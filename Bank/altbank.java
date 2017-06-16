import java.util.*;

class Main{
  public static void main(String [] args){
    Customer john = new Customer("John",1000.00);
    Branch la = new Branch("Los Angeles");
    la.addCustomer(john);
    Bank boa = new Bank("Bank of America");
    Bank.bankMenu(john,boa,la);
  }
}

class Bank{
  private String name;
  Bank(String name){
    this.name=name;
  }
  List<Branch> branches = new ArrayList<>();
  public void addBranch(Branch b){
    if(!branches.contains(b))
    branches.add(b);
  }
  public void addCustomer(Branch b,Customer c){
      if(branches.contains(b)){
        b.addCustomer(c);
      }
  }
  public void addTransaction(Branch b,Customer c,Double transaction){
      if(branches.contains(b)){
        b.addTransaction(c,transaction);
      }
  }
  public void showCustomerInfo(Branch b){
    if(branches.contains(b)){
      System.out.println(getName() + " Bank\n" +"Branch: "+ b.getName());
    for(Customer c:(List<Customer>) b.getCustomers()){
      System.out.println("\nCustomer Name: "+c.getName()+"\nTransactions:");
      c.displayTransactions();
    }
    }
  }
  public String getName(){
    return this.name;
  }
  public static void bankMenu(Customer c,Bank bank,Branch b){
    boolean quit = false;
    Scanner scan = new Scanner(System.in);
    System.out.println("Welcome, "+ c.getName()+ ", to the " + b.getName() +" branch of "+ bank.getName()+"!\nPlease select an option. You can press 5 at any time to go back the main menu.\n");
    System.out.println("1 - Make a transaction\n2 - Display your account info\n3 - Close your account\n4 - Exit\n");
    while(!quit){
  
      int option = scan.nextInt();
      switch(option){
        case 1:
          System.out.println("Please enter a transaction amount:\n");
          double transaction = scan.nextDouble();
          b.addTransaction(c,transaction);
          break;
        case 2:
          b.showCustomerInfo(c);
          System.out.println();
          break;
        case 3:
          System.out.println("Are you sure?");
          String choice = scan.next();
          if(choice.equals("yes".toLowerCase())){
            b.getCustomers().remove(c);
            return;
          }
          else bankMenu(c,bank,b);
          break;
        case 4:
          quit = true;
          System.out.println("Have a great day!");
          return;
        case 5:
          bankMenu(c,bank,b);
          break;
      }
    }
  }
}

class Branch{
  private String name;
  private List<Customer> customers = new ArrayList<>();
  Branch(String name){
    this.name=name;
  }
  public void showCustomerInfo(Customer c){
    if(customers.contains(c)){
      c.displayTransactions();
    }
  }
  public void addCustomer(Customer cust){
      for(Customer c:customers){
        if(c.equals(cust)){
        System.out.println("Error. Could not find customer.");
        return;}
      }
    customers.add(cust);
  }
  public void addTransaction(Customer cust,double transaction){
    for(Customer c:customers){
      if(c.equals(cust)){
        if((transaction<0 && (c.sumTransactions()-Math.abs(transaction))>=0) || transaction>0){
        cust.getTransactions().add(transaction);
        System.out.println("Thank you! Your transaction has been processed.\n");
        }
        else System.out.println("Error. Could not process transaction.\n");
        break;
      }
    }
  }
  public List getCustomers(){
    return this.customers;
  }
  public String getName(){
    return this.name;
  }
}

class Customer{
  private String name;
  private double balance;
  private List<Double> transactions = new ArrayList<>();
  Customer(String name,double transaction){
    this.name=name;
    balance = transaction;
    transactions.add(transaction);
  }
  public String getName(){
    return this.name;
  }
  public void setName(String name){
    this.name=name;
  }
  public double getBalance(){
    return balance;
  }
  public List getTransactions(){
    return this.transactions;
  }
  public void displayTransactions(){
      for(Double d:transactions){
        System.out.println("$"+d);
      }
      System.out.println("Total balance: $"+sumTransactions());
  }
  public double sumTransactions(){
    double sum=0;
    for(Double d:transactions){
      sum+=d;
    }
    return sum;
  }
}
