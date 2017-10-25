import java.util.*;

public class PackageTree {
  private PackageNode root;
  int size;
  
  public boolean add(String className, int classIndex, PackageNode root) {
    if(className.indexOf("java") == -1)
    return false;
    
    if(this.root == null) {
      this.root = new PackageNode("java");
      return add(className,classIndex,this.root);
    }
    
    String[] packageNames = className.split("\\.");
    
    if(packageNames.length == classIndex) {
       return true;
    }
    
    for(PackageNode p: root.children) {
      if(p.value.equals(packageNames[classIndex])) {
       return add(className,++classIndex,p);
        //return add(String.join(".",Arrays.copyOfRange(packageNames,1, packageNames.length)),p);
      }
    }
    
    root.children.add(new PackageNode(packageNames[classIndex]));
    size++;
    // return add(String.join(".",Arrays.copyOfRange(packageNames,1, packageNames.length)),root.children.get(root.children.size()-1));
    return add(className,++classIndex,root.children.get(root.children.size()-1));
  }
  
  public int size() {
    return size;
  }
  
  public void printSimpleClassNames(PackageNode root) {
    if(root == null)
    return;
    if(root.isLeaf())
    System.out.println(root.value);
    for(PackageNode p: root.children) {
      printSimpleClassNames(p);
    }
  }
  
  public boolean contains(String className, int classIndex, PackageNode root) {
    if(className.indexOf("java") == -1)
    return false;
    
    String[] packageNames = className.split("\\.");
    
    for(PackageNode p: root.children) {
      if(p.value.equals(packageNames[classIndex])) {
        return contains(className,++classIndex,p);
      }
    }
    
    return classIndex == packageNames.length;
  }
  
  public int height(PackageNode root) {
    if(root.isLeaf())
    return -1;
    int max = -1;
    
    for(PackageNode p: root.children) {
      int height = height(p);
      if(height > max)
        max = height;
    }
    return max + 1;
  }
  
  public PackageNode getRoot() {
    return root;
  }
}
