public class GraphNode {
    private final int key;
    //link list for the graph's nodes
    public GraphNode next;
    public GraphNode prev;

    //link list foe each node's edges (in-edge and out-edge)
    public GraphEdge newest_out_edge;
    public GraphEdge newest_in_edge;
    public GraphEdge oldest_out_edge;
    public GraphEdge oldest_in_edge;

    //for creating the root tree
    public RootedTreeNode link_rooted_tree;

    //for creating reverse G
    public GraphNode link_reverse_G;

    //DFS, DBS algorithms attributes
    public int dist;
    public int d;
    public int f;
    private int color;

    public GraphNode(int key){
        this.key = key;
    }

    public int getKey(){
        return this.key;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void addInEdge(GraphEdge new_in_edge){
        if (this.newest_in_edge == null){
            this.oldest_in_edge = new_in_edge;
        }
        else {
            GraphEdge in_edge = this.newest_in_edge;
            in_edge.in_next = new_in_edge;
            new_in_edge.in_prev = in_edge;
        }
        this.newest_in_edge = new_in_edge;
    }

    public void deleteInEdge(GraphEdge edge){
        if (edge == this.newest_in_edge && this.newest_in_edge == this.oldest_in_edge){
            this.newest_in_edge = null;
            this.oldest_in_edge = null;
        }
        else if (edge == this.newest_in_edge){
            edge.in_prev.in_next = null;
            this.newest_in_edge = edge.in_prev;
        }
        else if (edge == this.oldest_in_edge){
            edge.in_next.in_prev = null;
            this.oldest_in_edge = edge.in_next;
        }
        else {
            edge.in_next.in_prev = edge.in_prev;
            edge.in_prev.in_next = edge.in_next;
        }
    }

    public void addOutEdge(GraphEdge new_out_edge){
        if (this.newest_out_edge == null){
            this.oldest_out_edge = new_out_edge;
        }
        else {
            GraphEdge out_edge = this.newest_out_edge;
            out_edge.out_next = new_out_edge;
            new_out_edge.out_prev = out_edge;
        }
        this.newest_out_edge = new_out_edge;
    }

    public void deleteOutEdge(GraphEdge edge){
        if (edge == this.newest_out_edge && this.newest_out_edge == this.oldest_out_edge){
            this.newest_out_edge = null;
            this.oldest_out_edge = null;
        }
        else if (edge == this.newest_out_edge){
            edge.out_prev.out_next = null;
            this.newest_out_edge = edge.out_prev;
        }
        else if (edge == this.oldest_out_edge){
            edge.out_next.out_prev = null;
            this.oldest_out_edge = edge.out_next;
        }
        else{
            edge.out_next.out_prev = edge.out_prev;
            edge.out_prev.out_next = edge.out_next;
        }
    }

    public int getInDegree(){
        GraphEdge edge = this.newest_in_edge;
        int counter = 0;
        while (edge != null){
            edge = edge.in_prev;
            counter++;
        }
        return counter;
    }

    public int getOutDegree(){
        GraphEdge edge = this.newest_out_edge;
        int counter = 0;
        while (edge != null){
            edge = edge.out_prev;
            counter++;
        }
        return counter;
    }
}