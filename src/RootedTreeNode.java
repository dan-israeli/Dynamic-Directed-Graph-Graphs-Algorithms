public class RootedTreeNode {
    public RootedTreeNode parent;
    public RootedTreeNode left_child;
    public RootedTreeNode right_sibling;
    public RootedTreeNode most_right_child;
    private int child_num;
    private final int key;

    public RootedTreeNode(int key){
        this.key = key;
    }

    public int getKey() {
        return key;
    }

    public int getChild_num() {
        return child_num;
    }

    public void incrementChildNum() {
        this.child_num++;
    }
}
