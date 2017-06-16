//Aaron Feigenbaum

import java.util.*;

public class Bank {
    public static void main(String[] args) {
        Customer john = new Customer();
        john.addSavingsAccount(1000.00);
        Customer steve = new Customer();
        steve.addCheckingAccount(1000.00);
        steve.addSavingsAccount(300.00);
        steve.getCheckingAccount().deposit(200);

        //prints out steve's balance until exception is thrown
        Runnable withdrawer = ()->{
            try{
                while(true){
                    Thread.sleep(250);
                    steve.getCheckingAccount().withdraw(50.00);
                    System.out.println(steve.getCheckingAccount()+".\n");}
            }catch(Exception e){System.out.println(e.getMessage()); return;}


        };
        new Thread(withdrawer).start();
    }

    private static Bank bank = null;
    private static ArrayList<Customer> customerList = new ArrayList<>();
    private static List<BankAccount> accountList = new ArrayList<>();

    //Bank can't be instantiated outside class
    private Bank(){}

    //set bank instance if null and return it
    public static synchronized Bank getBank(){
        if(bank == null)
            bank = new Bank();
        return bank;
    }

    //return list of customers
    public List<Customer> getCustomers(){
        return customerList;
    }

    //return list of accounts
    public List<BankAccount> getAccounts(){
        return accountList;
    }

    //add customer to customer list
    public synchronized void addCustomer(Customer c){
        if(!customerList.contains(c))
            customerList.add(c);
    }

    //add account to account list
    public synchronized void addAccount(BankAccount b){
        if(!accountList.contains(b))
            accountList.add(b);
    }

    //returns list of bank accounts that specified customer has
    public List<BankAccount> getCustomerAccounts(Customer c){
        List<BankAccount> accounts = new ArrayList<>();
        for(BankAccount account: (List<BankAccount>) Bank.getBank().getAccounts()){
            if(account.getOwner().equals(c)){
                accounts.add(account);
            }
        }
        return accounts;

    }
}

class Customer{
    //each customer gets id in increments of 1000
    private static int id=1000;
    private SavingsAccount savingsAccount = null;
    private CheckingAccount checkingAccount = null;
    public Customer(){
        this.id = id;
        id++;
        //bank class adds customer
        Bank.getBank().addCustomer(this);
    }

    //initialize savings account and add to Bank class' account list
    public void addSavingsAccount(double balance){

        if(savingsAccount == null){
            savingsAccount = new SavingsAccount(this,balance);
            Bank.getBank().addAccount(savingsAccount);
        }
        else System.out.println("Error. Customer "+id+" already has a savings account.");
    }

    //initialize checking account and add to Bank class' account list
    public void addCheckingAccount(double balance){
        if(checkingAccount == null){
            checkingAccount = new CheckingAccount(this,balance);
            Bank.getBank().addAccount(checkingAccount);
        }
        else System.out.println("Error. Customer "+id+" already has a checking account.");
    }

    //set customer's savings account to null to close it
    public void closeSavingsAccount(){
        Bank.getBank().getAccounts().remove(savingsAccount);
        savingsAccount = null;
    }

    //set customer's checking account to null to close it
    public void closeCheckingAccount(){
        Bank.getBank().getAccounts().remove(checkingAccount);
        checkingAccount = null;
    }

    //remove customer from Bank class' customer list
    public void deleteCustomer(){
        Bank.getBank().getCustomers().remove(this);
    }

    //getters for customer id, savings account and checking account
    public int getId(){
        return id;
    }
    public BankAccount getSavingsAccount(){
        return savingsAccount;
    }
    public BankAccount getCheckingAccount(){
        return checkingAccount;
    }
}

//account types each have balance, owner and different withdraw methods
class SavingsAccount extends BankAccount{
    private double balance;
    private Customer owner;

    public SavingsAccount(Customer owner, double balance){
        super(owner,balance);
    }

    //subtracts from balance if sufficient funds, otherwise throws exception
    public synchronized void withdraw(double amount) throws Exception{
        if(amount<=getBalance()){
            setBalance(getBalance()-amount);
        }
        else throw new InsufficientFundsException("Error: Insufficient funds in savings account #"+getAccountNo());
    }

    //output account info
    public String toString(){
        return "Savings account #"+getAccountNo()+": $"+getBalance()+".\n";
    }
}

class CheckingAccount extends BankAccount {
    private double balance;
    private Customer owner;

    public CheckingAccount(Customer owner, double balance){
        super(owner,balance);
    }

    //subtracts amount from balance if sufficient funds, then substracts amount from
    //customer's savings account if not null
    //then throws exception if both accounts have insufficient funds
    public synchronized void withdraw(double amount) throws Exception{

        if(amount<=getBalance()){
            setBalance(getBalance()-amount);
        }
        else if(getOwner().getSavingsAccount()!=null && getOwner().getSavingsAccount().getBalance()>=(amount-getBalance())){

            System.out.println(getOwner().getSavingsAccount().toString());
            getOwner().getSavingsAccount().withdraw(amount);
            deposit(amount);

        }
        else throw new InsufficientFundsException("Error. Insufficient funds in checking account #"+getAccountNo());
    }
    public String toString(){
        return "Checking account #"+getAccountNo()+": $"+getBalance();
    }
}

abstract class BankAccount{
    private static int accountNo = 4000;
    private Customer owner;
    private double balance;

    public BankAccount(Customer owner, double balance){
        this.accountNo = accountNo;
        accountNo += 10;
        this.owner = owner;
        this.balance = balance;
    }

    public int getAccountNo(){
        return accountNo;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double newamount){
        balance = newamount;
    }

    public Customer getOwner(){
        return owner;
    }

    public synchronized void deposit(double amount){
        if(amount>0){
            balance += amount;
        }
    }

    public abstract void withdraw(double amount) throws Exception;
}

class InsufficientFundsException extends Exception{
    public InsufficientFundsException(String message){
        super(message);
    }
}