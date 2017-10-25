public class Run {
  public static void main(String[] args) {
    PackageTree tree = new PackageTree();
    
    tree.add("java.lang.String");
    tree.add("com.google.Search");
    tree.add("java.lang.Boolean");
    tree.add("java.util.Scanner");
    tree.add("java.util.LinkedList");
    tree.add("java.lang.Double");
    tree.add("java.lang.Double");
    System.out.println("Tree size: " + tree.size());
    System.out.println("Tree height: " + tree.height());
    System.out.println("Tree contains 'java.util.Integer': " + tree.contains("java.util.Integer"));
    System.out.println("Tree contains 'java.util.Scanner': " + tree.contains("java.util.Scanner"));
    tree.printSimpleClassNames();
  }
}
