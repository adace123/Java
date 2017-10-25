import java.util.List;
import java.util.ArrayList;

public class PackageNode {
  String value;
  List<PackageNode> children;
  
  public PackageNode(String value) {
    this.value = value;
    children = new ArrayList<>();
  }
  
  public boolean isLeaf() {
    return children.size() == 0;
  }
}
