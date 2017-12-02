import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class HashTable<K, V> {
  private int capacity;
  private int size;
  private double loadFactor;
  private LinkedList[] hashtable;
  private ArrayList<K> keys = new ArrayList<>();
  private ArrayList<V> values = new ArrayList<>();
  
  public HashTable(int capacity) {
    if(capacity < 1) {
      System.out.println("Error. Capacity is too small.");
    }
    else {
      this.capacity = capacity;
      this.loadFactor = 0.75;
      this.hashtable = new LinkedList[capacity];
    }
  }
  
  public int getHash(K key) {
    if(key == null)
    return 0;
    int hash = 19;
    return Math.abs(key.hashCode() * hash % capacity);
  }
  
  public void add(K key, V value) {
    if(size >= (capacity * loadFactor)) {
      resize();
    }
    int hash = getHash(key);
    if(hashtable[hash] == null) {
      hashtable[hash] = new LinkedList();
      hashtable[hash].add(key, value);
      size++;
    }
    else {
      if(containsKey(key)) {
        System.out.println("Key already in hash table");
        return;
      }
      else {
        hashtable[hash].add(key, value);
        size++;
      }
    }
    if(!values.contains(value)) {
      values.add(value);
    }
    if(!keys.contains(key)) {
      keys.add(key);
    }
  }
  
  public void resize() {
    LinkedList[] newHashTable = new LinkedList[hashtable.length + (int)(100 * loadFactor)];
    for(int i = 0; i < hashtable.length; i++) {
      newHashTable[i] = hashtable[i];
    }
    hashtable = newHashTable;
  }
  
  public void clear() {
    Arrays.fill(hashtable, null);
  }
  
  public void remove(K key) {
    int hash = getHash(key);
    if(hashtable[hash] == null) {
      System.out.println("Key not found.");
    } else {
      this.hashtable[hash] = null;
    }
  }
  
  public V replace(K key, V value) {
    if(!containsKey(key)) {
      System.out.println("Key not in hash table.");
      return null;
    }
    Node temp = hashtable[getHash(key)].root;
    while(temp.next != null && temp.value != value) {
      temp = temp.next;
    }
    temp.value = value;
    return value;
  }
  
  public ArrayList<K> keys() {
    return keys;
  }
  
  public ArrayList<V> values() {
    return values;
  }
  
  public int size() {
    return size;
  }
  
  public boolean isEmpty() {
    return size == 0;
  }
  
  public boolean containsKey(K key) {
    LinkedList list = hashtable[getHash(key)];
    return list != null && list.contains(key);
  }
  
  
  public V get(K key) {
    LinkedList test = hashtable[getHash(key)];
    if(test == null) {
      return null;
    }
    Node temp = test.root;
    while(temp != null && temp.key != key) {
      temp = temp.next;
    }
    return (V) temp.value;
    
  }
  
}

class LinkedList<K, V> implements Iterable<V> {
  Node root;
  Node tail;
  ArrayList<V> list = new ArrayList<>();
   
  public Iterator<V> iterator() {
    return list.iterator();
  }
  
  public void add(K key, V value) {
    if(root == null) {
      root = new Node(key, value);
      tail = root;
    } else {
      tail.next = new Node(key, value);
      tail = tail.next;
    }
    list.add(value);
  }
  
  public boolean contains(K key) {
    if(root == null) {
      return false;
    }
    else if(root.key.equals(key) || tail.key.equals(key)) {
      return true;
    }
    Node currentNode = root;
    while(currentNode != null) {
      if(currentNode.key.equals(key)) {
        return true;
      }
      currentNode = currentNode.next;
    }
    return false;
  }
  
   
}

class Node<K, V> {
   K key;
   V value;
   Node next;
  
  public Node(K key, V value) {
    this.key = key;
    this.value = value;
  }
}
