public class Run {
  public static void main(String[] args) {
    PackageTree tree = new PackageTree();
    tree.add("java.lang.String",0,tree.getRoot());
    tree.add("com.google.Search",0,tree.getRoot());
    tree.add("java.lang.Boolean",0,tree.getRoot());
    tree.add("java.util.Scanner",0,tree.getRoot());
    tree.add("java.util.LinkedList",0,tree.getRoot());
    tree.add("java.lang.Double",0,tree.getRoot());
    System.out.println("Tree size: " + tree.size());
    System.out.println("Tree height: " + tree.height(tree.getRoot()));
    System.out.println("Tree contains 'java.util.Integer': " + tree.contains("java.util.Integer",0,tree.getRoot()));
    System.out.println("Tree contains 'java.util.Scanner': " + tree.contains("java.util.Scanner",0,tree.getRoot()));
    tree.printSimpleClassNames(tree.getRoot());
    
  }
}
