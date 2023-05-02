public class DynamicGraph {
    private GraphNode newest_node;
    private GraphNode oldest_node;
    private GraphEdge newest_edge_inserted;
    private GraphEdge oldest_edge_inserted;
    private static int TIME;

    //constants
    private final static int WHITE = 1;
    private final static int GREY = 2;
    private final static int BLACK = 3;
    private final static int INFINITY = -1;

    //default constructor
    public DynamicGraph(){};

    public GraphNode insertNode(int nodeKey){
        GraphNode new_node = new GraphNode(nodeKey);
        if (this.newest_node == null){
            this.oldest_node = new_node;
        }
        else {
            GraphNode node = this.newest_node;
            node.next = new_node;
            new_node.prev = node;
        }
        this.newest_node = new_node;
        return new_node;
    }

    public void deleteNode(GraphNode node){
        if (node.newest_in_edge != null || node.newest_out_edge != null){
            return;
        }
        if (node == this.newest_node && this.newest_node == this.oldest_node){
            this.newest_node = null;
            this.oldest_node = null;
        }
        else if (node == this.newest_node){
            node.prev.next = null;
            this.newest_node = node.prev;
        }
        else if (node == this.oldest_node){
            node.next.prev = null;
            this.oldest_node = node.next;
        }
        else{
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
    }

    public GraphEdge insertEdge(GraphNode from, GraphNode to){
        if (from == null || to == null){
            return null;
        }

        GraphEdge new_edge = new GraphEdge(from, to);
        if (this.newest_edge_inserted == null){
            this.oldest_edge_inserted = new_edge;
        }
        else {
            GraphEdge edge = this.newest_edge_inserted;
            new_edge.inserted_prev = edge;
            edge.inserted_next = new_edge;
        }
        this.newest_edge_inserted = new_edge;
        from.addOutEdge(new_edge);
        to.addInEdge(new_edge);
        return new_edge;
    }

    public void deleteEdge(GraphEdge edge){
        if (edge == null){
            return;
        }
        if (edge == this.newest_edge_inserted && this.newest_edge_inserted == this.oldest_edge_inserted){
            this.newest_edge_inserted = null;
            this.oldest_edge_inserted = null;
        }
        else if (edge == this.newest_edge_inserted){
            edge.inserted_prev.inserted_next = null;
            this.newest_edge_inserted = edge.inserted_prev;
        }
        else if (edge == this.oldest_edge_inserted){
            edge.inserted_next.inserted_prev = null;
            this.oldest_edge_inserted = edge.inserted_next;
        }
        else {
            edge.inserted_next.inserted_prev = edge.inserted_prev;
            edge.inserted_prev.inserted_next = edge.inserted_next;
        }
        edge.from.deleteOutEdge(edge);
        edge.to.deleteInEdge(edge);
    }

    public RootedTree scc(){
        DynamicGraph reverse_G = this.reverseGraph();
        //S contains the nodes sorted by their node.f value
        //the node with the highest node.f value is first
        Stack<GraphNode> S = this.DFS();
        RootedTree T = reverse_G.DFS_Reverse_G(S);
        return T;
    }

    public DynamicGraph reverseGraph(){
        DynamicGraph reverse_G = new DynamicGraph();
        GraphNode node = this.oldest_node;
        while (node != null){
            node.link_reverse_G = reverse_G.insertNode(node.getKey());
            node = node.next;
        }
        GraphEdge edge = this.oldest_edge_inserted;
        while (edge != null){
            //Reversing the edges of G
            reverse_G.insertEdge(edge.to.link_reverse_G, edge.from.link_reverse_G);
            edge = edge.inserted_next;
        }
        return reverse_G;
    }

    private Stack<GraphNode> DFS(){
        TIME = 0;
        Stack<GraphNode> S = new Stack<>();
        GraphNode node = this.oldest_node;
        while (node != null){
            node.setColor(WHITE);
            node = node.next;
        }
        node = this.newest_node;
        while (node != null){
            if (node.getColor() == WHITE){
                DFS_Visit(node, S);
            }
            node = node.prev;
        }
        return S;
    }

    private void DFS_Visit(GraphNode node, Stack<GraphNode> S){
        TIME++;
        node.d = TIME;
        node.setColor(GREY);
        GraphEdge edge = node.newest_out_edge;
        while (edge != null){
            GraphNode child_node = edge.to;
            if (child_node.getColor() == WHITE){
                DFS_Visit(child_node, S);
            }
            edge = edge.out_prev;
        }
        node.setColor(BLACK);
        TIME++;
        node.f = TIME;
        //set up the nodes of the reverse graph before push them to S
        //in order we could use them in DFS right after we pop them from S
        //the nodes in S are sorted by their node.f value (from large to small)
        node.link_reverse_G.setColor(WHITE);
        node.link_reverse_G.link_rooted_tree = null;
        S.Push(node.link_reverse_G);
    }

    private RootedTree DFS_Reverse_G(Stack<GraphNode> S){
        TIME = 0;
        RootedTree T = new RootedTree();
        T.setImaginaryRoot();
        while (S.Head != null){
            GraphNode node = S.Pop();
            if (node.getColor() == WHITE){
                node.link_rooted_tree = T.insertChildNode(T.root, node.getKey());
                DFS_Visit_Reverse_G(node, T);
            }
        }
        return T;
    }

    private void DFS_Visit_Reverse_G(GraphNode node, RootedTree T){
        TIME++;
        node.d = TIME;
        node.setColor(GREY);
        GraphEdge edge = node.newest_out_edge;
        while (edge != null){
            GraphNode child_node = edge.to;
            if (child_node.getColor() == WHITE){
                child_node.link_rooted_tree = T.insertChildNode(node.link_rooted_tree, child_node.getKey());
                DFS_Visit_Reverse_G(child_node, T);
            }
            edge = edge.out_prev;
        }
        node.setColor(BLACK);
        TIME++;
        node.f = TIME;
    }

    public RootedTree bfs(GraphNode source){
        RootedTree T = new RootedTree();
        Queue<GraphNode> Q = new Queue<>();
        this.bfs_initialization(source, T, Q);
        while (Q.Head != null){
            GraphNode main_node = Q.deQueue();
            GraphEdge edge = main_node.newest_out_edge;
            while (edge != null){
                GraphNode second_node = edge.to;
                if (second_node.getColor() == WHITE){
                    second_node.setColor(GREY);
                    second_node.dist = main_node.dist + 1;
                    second_node.link_rooted_tree = T.insertChildNode(main_node.link_rooted_tree, second_node.getKey());
                    Q.inQueue(second_node);
                }
                edge = edge.out_prev;
            }
            main_node.setColor(BLACK);
        }
        return T;
    }

    private void bfs_initialization(GraphNode source, RootedTree T, Queue<GraphNode> Q){
        GraphNode node = this.oldest_node;
        while (node != null){
            node.setColor(WHITE);
            node.dist = INFINITY;
            node.link_rooted_tree = null;
            node = node.next;
        }
        T.setRoot(source.getKey());
        source.link_rooted_tree = T.root;
        source.setColor(GREY);
        source.dist = 0;
        Q.inQueue(source);
    }
}
