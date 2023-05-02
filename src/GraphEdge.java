public class GraphEdge {
    public GraphNode from;
    public GraphNode to;
    //link list for node's in-edges
    public GraphEdge in_next;
    public GraphEdge in_prev;
    //link list for node's out-edges
    public GraphEdge out_next;
    public GraphEdge out_prev;
    //link list for graph's edges insertion order
    public GraphEdge inserted_next;
    public GraphEdge inserted_prev;

    public GraphEdge(GraphNode from, GraphNode to){
        this.from = from;
        this.to = to;
    }
}
