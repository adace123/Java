import java.util.*;

public class PackageTree {
  private PackageNode root;
  
  public boolean add(String className) {
    //check if className contains 'java'
    String[] packages = className.split("\\.");
//counts number of matches className has in tree, starts with 1 since java is already in tree
    int duplicateCount = 1;
    if(!packages[0].equals("java"))
    return false;
    
    //if root is null, iterate through class names starting with second one
    //iterate through children/tree levels and each each class name to the tree
    else if(this.root == null) {
      root = new PackageNode("java");
      PackageNode temp = root;
      if(packages.length >= 2)
      for(int i = 1;i < packages.length;i++) {
        PackageNode current = new PackageNode(packages[i]);
        temp.children.add(current);
        temp = temp.children.get(0);
      }

    } 
    else {
      //create queue to iterate through tree starting at root
      if(packages.length < 2) {
        return false;
      }
      Queue<PackageNode> iterator = new LinkedList();
      PackageNode temp = root;
      
      iterator.add(temp);
      //iterate through class names starting with second one
      for(int i=1; i < packages.length; i++) {
          //dequeue current node
          PackageNode current = iterator.remove();
          //iterate through current node's children 
          for(PackageNode node: current.children) {
            //if current class name found in children add it to the queue
            if(node.value.equals(packages[i])) {
              
              duplicateCount++;
              iterator.add(node);
            }
          }
          //current class name not found in current node's children
          //add a new node to the tree with the current class name
          //add most recently added node to queue
          if(iterator.isEmpty()) {
            current.children.add(new PackageNode(packages[i]));
            iterator.add(current.children.get(current.children.size()-1));
          }
          
      }
    }
    if(duplicateCount == packages.length) {
      System.out.println(className + " already in tree.");
      return false;
    } else return true;
  }
  
  public void printSimpleClassNames() {
    //nothing to print
    if(root == null) {
    System.out.println("Error. Tree is empty.");
    return;
    }
    //ArrayList will store value of nodes
    ArrayList<String> treeValues = new ArrayList<>();
    //queue to iterate through subtrees starting at root's children
    Queue<List<PackageNode>> iterator = new LinkedList();
    iterator.add(root.children);
    while(!iterator.isEmpty()) {
      List<PackageNode> current = iterator.remove();
      //iterate through current node's children
      for(PackageNode p: current) {
          iterator.add(p.children);
          //if ArrayList doesn't already have value and child node has no children,
          if(!treeValues.contains(p.value) && p.isLeaf()) {
            treeValues.add(p.value);
          }
      }
      
    }
    for(String s: treeValues) {
      System.out.println(s);
    }
  }

  public boolean contains(String className) {
    //tree is empty or invalid className
    String[] packageNames = className.split("\\.");
    if(root == null || !packageNames[0].equals("java")) {
      System.out.println("Error.");
      return false;
    }
    
    Queue<List<PackageNode>> iterator = new LinkedList();
    iterator.add(root.children);
    ArrayList<String> treeValues = new ArrayList<>();
    while(!iterator.isEmpty()) {
      List<PackageNode> current = iterator.remove();
      //iterate through each node's children
      for(PackageNode p: current) {
          iterator.add(p.children);
          //if className has the current node's value
          //and value is not already in ArrayList, add it to ArrayList
          if(className.indexOf(p.value) > -1 && !treeValues.contains(p.value)) {
            treeValues.add(p.value);
          }
      }
      
    }
    //if ArrayList has all the values in the className return true, else return false
    return treeValues.size() == packageNames.length - 1;
  }
  
  public int height() {
    if(root == null) {
       return -1;
    } else if(root.isLeaf()) {
      return 0;
    }
    
    //iterate through nodes, use a null to separate each tree level
    Queue<PackageNode> iterator = new LinkedList();
    iterator.add(root);
    iterator.add(null);
    int height = 0;
    while(!iterator.isEmpty()) {
      PackageNode current = iterator.remove();
      //if null is reached this is a new level of the tree so increment height 
      if(current == null) {
        if(!iterator.isEmpty())
        iterator.add(null);
        height++;
      } else {
      for(PackageNode p: current.children) {
           if(p != null)
           iterator.add(p);
      }
      
      }
      
    }

    return height - 1;
  }
  
  public int size() {
    if(root == null) {
    return 0;
    } else if(root.isLeaf()) {
      return 1;
    }
    //use ArrayList to store all node values
    ArrayList<PackageNode> nodes = new ArrayList<>();
    Queue<List<PackageNode>> iterator = new LinkedList();
    iterator.add(root.children);
    while(!iterator.isEmpty()) {
      List<PackageNode> current = iterator.remove();
      for(PackageNode p: current) {
          iterator.add(p.children);
          //check if ArrayList already has child node's value
          //if not add it to the ArrayList
          if(!nodes.contains(p)) {
            nodes.add(p);
          }
      }
      
    }
    //ArrayList size + 1 represents size of tree
    return nodes.size() + 1;
  }
  
  public LeafIterator iterator() {
    return new LeafIterator(this.root);
  }
}
