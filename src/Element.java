public class Element<N>{
    N data;
    Element<N> next;
    Element<N> prev;

    public Element(N data){
        this.data = data;
    }

    public Element(){};
}
