import java.util.*;

class MobilePhone{
  List<Contact> contactList = new ArrayList<>();

  public void add(Contact c){
    contactList.add(c);
  }
  public Contact findContact(String name){
    for(Contact c: contactList){
      if(c.getName().equals(name))
      return c;
    }
    System.out.println("Error. Contact couldn't be found.\n");
    return null;
  }
  public void removeContact(String name){
    contactList.remove(findContact(name));
  }
  public void displayContacts(){
    Collections.sort(contactList, Comparator.comparing(Contact::getName));
    for(Contact c:contactList){
      System.out.println("Name: "+c.getName()+"\nPhone: "+c.getPhone()+"\n");
    }

  }
  public void contactMenu(){
    Scanner scan = new Scanner(System.in);
    boolean stop = false;
    while(!stop){
      System.out.println("Welcome to the Contacts Menu!\n\nPress 1 to search for a contact\nPress 2 to see all contacts\nPress 3 to add a contact\nPress 4 to edit a contact\nPress 5 to delete a contact\nPress 6 to quit");
      int choice = scan.nextInt();
      switch(choice){
        case 1:
          System.out.println("Who would you like to search for?");
          String query = scan.next();
          if(findContact(query)!=null){
            System.out.println("\n"+findContact(query));
          }
          break;
        case 2:
          //display
          displayContacts();
          break;
        case 3:
          //add
          System.out.println("Enter name and number:");
          String newName = scan.next();
          String newPhone = scan.next();
          add(new Contact(newName,newPhone));
          System.out.println("Contact added!\n");
          break;
        case 4:
          //edit
          System.out.println("Which contact would you like to edit?");
          String editChoice = scan.next();
          if(findContact(editChoice)!=null){
          System.out.println("Contact found! Please enter the new name and number.");
          String editName = scan.next();
          String editPhone = scan.next();
          removeContact(editChoice);
          add(new Contact(editName,editPhone));
          System.out.println("Successfully edited!\n");
          }
          break;
        case 5:
          //delete
          System.out.println("Please enter the name of the contact you wish to delete.");
          String removeChoice = scan.next();
          if(findContact(removeChoice)!=null){
            removeContact(removeChoice);
          }
          System.out.println("Successfully removed!\n");
          break;
        case 6:
          //exit
          System.out.println("Bye!\n");
          stop = true;
          break;
        default:
          System.out.println("Error. That's not one of the choices.\n");
          break;
      }
    }
  }
}

class Contact{
  String name,phone;
  Contact(String name,String phone){
    this.name = name;
    this.phone = phone;
  }
  public String getName(){
    return name;
  }
  public String getPhone(){
    return phone;
  }
  public void setName(String name){
    this.name = name;
  }
  public void setPhone(String phone){
    this.phone=phone;
  }
  @Override
  public String toString(){
    return "Name: "+this.getName()+"\nPhone: "+this.getPhone()+"\n";
  }
}

class Main{
  public static void main(String [] args){
    MobilePhone mobilePhone = new MobilePhone();
    mobilePhone.contactMenu();
  }
}
