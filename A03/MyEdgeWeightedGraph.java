import edu.princeton.cs.algs4.*;
class MyEdgeWeightedGraph {
    private final int V;
    private final int E;
    private Bag<MyEdge> edges;

    public int V() { return V; }
    public int E() { return E; }

    public Iterable<MyEdge> edges() { return edges; }

    public MyEdgeWeightedGraph( In in ) {
    	 V = in.readInt();
         E = in.readInt();
         edges = new Bag<MyEdge>();
         for (int i = 0; i < E; i++) {
             int v = in.readInt();
             int w = in.readInt();
             long weight = in.readLong();
             MyEdge e = new MyEdge( v, w, weight );
             edges.add( e );
         }
     }    
    

    public void negateWeights() {
        for(MyEdge nw : edges()) nw.changeWeight(-1*(nw.weight()));
    }
}