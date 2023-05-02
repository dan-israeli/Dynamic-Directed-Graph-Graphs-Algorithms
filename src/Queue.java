class Queue<N>{
    Element<N> Head;
    Element<N> Tail;

    public Queue(){
        this.Head = null;
        this.Tail = null;
    }

    public void inQueue(N data){
        if (data == null){
            return;
        }
        Element<N> X = new Element<N>(data);
        if (this.Head == null){
            this.Tail = X;
        }
        else{
            Element<N> Y = this.Head;
            Y.prev = X;
            X.next = Y;
        }
        this.Head = X;

    }

    public N deQueue(){
        if (this.Head == null){
            return null;
        }
        if (this.Head == this.Tail){
            Element<N> X = this.Head;
            this.Head = null;
            this.Tail = null;
            return X.data;
        }
        Element<N> X = this.Tail;
        X.prev.next = null;
        this.Tail = X.prev;
        return X.data;
    }
}