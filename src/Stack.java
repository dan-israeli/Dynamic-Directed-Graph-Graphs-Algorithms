public class Stack<N> {
    Element<N> Head;

    public Stack(){
        this.Head = null;
    }

    public void Push(N data){
        if (data == null){
            return;
        }
        Element<N> X = new Element<N>(data);
        if (this.Head != null){
             Element<N> Y = Head;
             Y.prev = X;
             X.next = Y;
        }
        Head = X;
    }

    public N Pop(){
        if (this.Head == null){
            return null;
        }
        Element<N> X = this.Head;
        if (X.next == null){
            this.Head = null;
            return X.data;
        }
        X.next.prev = null;
        this.Head = X.next;
        return X.data;
    }
}


