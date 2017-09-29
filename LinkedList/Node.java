public class Node {
  Node next;
  String value;
  
  public boolean hasNext() {
    return next != null;
  }
  
  public Node() {}
  
  public Node(String value) {
    this.value = value;
  }
}
