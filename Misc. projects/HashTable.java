import java.util.ArrayList;
import java.util.Iterator;

public class HashTable {
  private int capacity;
  private int size;
  private LinkedList[] hashtable;
  private LinkedList keys = new LinkedList();
  private LinkedList values = new LinkedList();
  
  public HashTable(int capacity) {
    if(capacity < 1) {
      System.out.println("Error. Capacity is too small.");
    }
    else {
      this.capacity = capacity;
      this.hashtable = new LinkedList[this.capacity];
    }
  }
  
  public int getHash(Object key) {
    if(key == null)
    return 0;
    int hash = 19;
    return key.hashCode() * hash % capacity;
  }
  
  public void add(Object key, Object value) {
    int hash = this.getHash(key);
    if(this.hashtable[hash] == null) {
      this.hashtable[hash] = new LinkedList();
      this.hashtable[hash].add(value);
      this.keys.add(key);
      this.values.add(value);
      size++;
    }
    else {
      System.out.println("Key already in hash table");
      if(this.hashtable[hash].contains(value))
      System.out.println("Value already in hash table");
      else {
        this.values.add(value);
        this.hashtable[hash].add(value);
        size++;
      }
    }
    
  }
  
  public void remove(Object key) {
    int hash = this.getHash(key);
    if(this.hashtable[hash] == null) {
      System.out.println("Key not found.");
    } else {
      this.hashtable[hash] = null;
    }
  }
  
  public LinkedList keys() {
    return this.keys;
  }
  
  public LinkedList values() {
    return this.values;
  }
  
  public int size() {
    return size;
  }
  
  public Object get(Object key) {
    LinkedList test = this.hashtable[this.getHash(key)];
    if(test == null) {
      return null;
    } else if(test.root.next == null) {
      return test.root.value;
    } else {
      Node head = test.root;
      ArrayList<Object> objs = new ArrayList<>();
      while(head != null) {
        objs.add(head.value);
        head = head.next;
      }
      return objs;
    }
  }
  
}

class LinkedList implements Iterable<Object> {
   Node root;
   Node tail;
   ArrayList<Object> list = new ArrayList<>();
  public Iterator<Object> iterator() {
    return list.iterator();
  }
  
  public void add(Object value) {
    if(root == null) {
      root = new Node(value);
      tail = root;
    } else {
      tail.next = new Node(value);
      tail = tail.next;
    }
    list.add(value);
  }
  
  public boolean contains(Object value) {
    if(root == null) {
      return false;
    }
    else if(root.value.equals(value) || tail.value.equals(value)) {
      return true;
    }
    Node currentNode = root;
    while(currentNode != null) {
      if(currentNode.value.equals(value)) {
        return true;
      }
      currentNode = currentNode.next;
    }
    return false;
  }
  
   
}

class Node {
   Object value;
   Node next;
  
  public Node(Object value) {
    this.value = value;
  }
}
