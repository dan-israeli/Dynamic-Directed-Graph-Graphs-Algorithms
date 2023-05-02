import java.io.DataOutputStream;
import java.io.IOException;

public class RootedTree {
    RootedTreeNode root;

    //default constructor
    public RootedTree(){};

    public void setRoot(int key){
        this.root = new RootedTreeNode(key);
        this.root.parent = null;
    }

    public void setImaginaryRoot(){
        this.root = new RootedTreeNode(0);
        this.root.parent = null;
    }

    public RootedTreeNode insertChildNode(RootedTreeNode parent, int child_key){
        RootedTreeNode new_child = new RootedTreeNode(child_key);
        if (parent.left_child == null){
            parent.left_child = new_child;
        }
        else {
            RootedTreeNode child = parent.most_right_child;
            child.right_sibling = new_child;
        }
        parent.most_right_child = new_child;
        new_child.parent = parent;
        parent.incrementChildNum();
        return new_child;
    }

    public void printByLayer(DataOutputStream out) throws IOException {
        RootedTreeNode X = this.root;
        if (X == null){
            return;
        }
        Queue<RootedTreeNode> Q = new Queue<>();
        out.writeBytes(String.valueOf(X.getKey()));
        int node_num_current_level = X.getChild_num();
        int node_num_next_level = 0;
        int counter = 0;
        // the root is the only level in the rooted tree
        if (node_num_current_level == 0){
            return;
        }
        out.writeBytes(System.lineSeparator());
        Q.inQueue(X.left_child);
        while (Q.Head != null){
            X = Q.deQueue();
            while (X != null){
                counter++;
                node_num_next_level += X.getChild_num();
                out.writeBytes(String.valueOf(X.getKey()));
                Q.inQueue(X.left_child);
                X = X.right_sibling;
                if (counter != node_num_current_level){
                    out.writeBytes(",");
                }
                //we discovered all the nodes in the current level
                //we need to print a new line separator
                else if (node_num_next_level > 0){
                    counter = 0;
                    node_num_current_level = node_num_next_level;
                    node_num_next_level = 0;
                    out.writeBytes(System.lineSeparator());
                }
            }
        }
    }

    public void preorderPrint(DataOutputStream out) throws IOException {
        RootedTreeNode parent = this.root;
        if (parent == null){
            return;
        }
        out.writeBytes(String.valueOf(parent.getKey()));
        RootedTreeNode child = parent.left_child;
        while (child != null) {
            preorderPrintAux(out, child);
            child = child.right_sibling;
        }
    }

    private void preorderPrintAux(DataOutputStream out, RootedTreeNode parent) throws IOException {
        out.writeBytes("," + parent.getKey());
        RootedTreeNode child = parent.left_child;
        while (child != null){
            preorderPrintAux(out, child);
            child = child.right_sibling;
        }
    }
}
