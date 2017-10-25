import java.util.*;

public class LeafIterator implements Iterator<PackageNode> {
  private Queue<PackageNode> treeIterator;
  
  public LeafIterator(PackageNode root) {
    this.treeIterator = new LinkedList(Arrays.asList(root));
  }
  
  public PackageNode next() {
    return this.treeIterator.remove();
  }
  
  public boolean hasNext() {
    return this.treeIterator.peek() == null;
  }
}
