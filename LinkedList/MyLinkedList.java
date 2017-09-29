public class MyLinkedList implements MyList {
  Node head;
  Node tail;
  
  public void add(String str) {
    if(head == null) {
      head = new Node(str);
      tail = head;
    } else {
      tail.next = new Node(str);
      tail = tail.next;
      // Node currentNode = head;
      // while(currentNode.hasNext()) {
      //   currentNode = currentNode.next;
      // }
      // currentNode.next = new Node(str);
    }
  }
  
  public boolean contains(String str) {
    if(head == null) {
      return false;
    }
    else if(head.value.equals(str) || tail.value.equals(str)) {
      return true;
    }
    Node currentNode = head;
    while(currentNode != null) {
      if(currentNode.value.equals(str)) {
        return true;
      }
      currentNode = currentNode.next;
    }
    return false;
  }
  
  public String get(int index) {
    if(index < 0 || head == null || index >= this.size()) {
      return null;
    }
    if(index == 0) {
      return head.value;
    }
    int sizeCount = 0;
    Node currentNode = head;
    while(currentNode != null) {
      currentNode = currentNode.next;
      sizeCount++;
      if(sizeCount == index) {
        return currentNode.value;
      }
    }
    return null;
  }
  
  public int size() {
    int sizeCount = 0;
    if(head == null) {
      return sizeCount;
    }
    Node currentNode = head;
    
    while(currentNode != null) {
      currentNode = currentNode.next;
      sizeCount++;
    }
    return sizeCount;
  }
  
  public boolean remove(String str) {
    if(head == null) {
      return false;
    }
    
    else if(head.value.equals(str)) {
      head = head.next;
      return true;
    }
    
    Node currentNode = head;
    while(currentNode.hasNext()) {
      if(currentNode.next.value.equals(str)) {
        currentNode.next = currentNode.next.next;
        return true;
      } 
      currentNode = currentNode.next;
    }
    return false;
  }
  
}

