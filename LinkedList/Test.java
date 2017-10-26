class Test {
  public static void main(String[] args) {
    MyLinkedList linkedList = new MyLinkedList();
    linkedList.add("Tea");
    linkedList.add("Coffee");
    linkedList.add("Coke");
    linkedList.add("Sprite");
    linkedList.add("Water");
    System.out.println(linkedList.contains("Tea"));
    System.out.println(linkedList.contains("Frappu") + "\n");
    for(int i = 0; i < linkedList.size(); i++) {
      System.out.println(linkedList.get(i));
    }
    System.out.println();
    linkedList.remove("Coke");
    linkedList.remove("Sprite");
    linkedList.insertAt("Zevia",0);
    linkedList.insertAt("Ginger ale", 3);
    for(int i = 0; i < linkedList.size(); i++) {
      System.out.println(linkedList.get(i));
    }
  }
}
