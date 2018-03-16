import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.In;
class MyMST {

   private long weight;                              // weight of MST
   private Queue mst = new Queue();  // edges in MST
   private int useful  = 0;  // number of edges in some spanning tree
   private int useless = 0;  // number of edges in no spanning tree
   private int crucial = 0;  // number of edges in all spanning trees

   public long weight() { return weight; }
   public int useful()  { return useful; }
   public int useless() { return useless; }
   public int crucial() { return crucial; }

   public Iterable<MyEdge> edges() { return mst; }

   public MyMST( MyEdgeWeightedGraph G ) {
	   MyEdge[] X = new MyEdge[G.E()];
       int i = 0;
       for (MyEdge e : G.edges()) X[i++] = e;
       MinPQ<MyEdge> pq = new MinPQ<MyEdge>( X ); // O(n) heap build.
       UF uf = new UF( G.V() );
       while (!pq.isEmpty() && mst.size() <= G.V() - 1) {
           MyEdge e = pq.delMin();
           int v = e.minv();
           int w = e.maxv();
           if (!uf.connected(v, w)) { // v-w does not create a cycle
               uf.union(v, w);  // merge v and w components
               mst.enqueue(e);  // add edge e to mst
               weight += e.weight();
           }
       }
   }

   public void computeStats( MyEdgeWeightedGraph G ) {
	 
	   for ( MyEdge e : G.edges() ) {
		   long w = e.weight();
		   if(w>=0){
	     	   e.changeWeight( 0 );
	     	   MyMST mst2 = new MyMST( G );
	     	   if (mst2.weight()+w==this. weight){
	     		   useful++;
	     	   }else{
	     		   useless++;
	     	   }
	     	   e.changeWeight(1000);
	     	   MyMST mst3 = new MyMST( G );
	     	   if (mst3.weight()!=this. weight){
	     		   crucial++;
	     	   }
	        }else{
	     	   e.changeWeight( -1001 );
	     	   MyMST mst4 = new MyMST( G );
	           if ((mst4.weight()+1001+w)==this. weight){
	        	   
	         	   useful++;
	          }else{
	         	   useless++;
	            }
	            e.changeWeight(1);
	     	   MyMST mst5 = new MyMST( G );
	     	   if (mst5.weight()!=this. weight){
	     		   crucial++;
	     	   }
	        }
	        e.changeWeight( w );
          /* MyEdge[] X = new MyEdge[G.E()];
           //X[i++] = e;
           int i = 0;
           for (MyEdge ne : G.edges()) X[i++] = ne;
           MinPQ<MyEdge> pq = new MinPQ<MyEdge>( X ); // O(n) heap build.
           UF uf = new UF( G.V() );
           /*while (!pq.isEmpty() && mst2.size() <= G.V() - 1){
        	   MyEdge nne = pq.delMin();
               int v = nne.minv();
               int w = nne.maxv();
               if (!uf.connected(v, w)) { // v-w does not create a cycle
                   uf.union(v, w);  // merge v and w components
                   mst2.enqueue(nne);  // add edge e to mst
               }
               if (mst2.size() == G.V()-1) useful++;
               else useless++;
               if(useful+useless == G.E()) break;
           }*/
          
           
           
	   }
       
	   
   }
   public static void main( String[] args ) {

       In in = new In( args[0] );
       MyEdgeWeightedGraph G = new MyEdgeWeightedGraph( in );

       MyMST mst = new MyMST( G );
       System.out.println( "\nMinST "+mst.weight() );
       for (MyEdge e : mst.edges() ) System.out.print( e+", " );   System.out.println();
       mst.computeStats( G );
       System.out.println( "min stats = "+mst.useless()+","+mst.useful+","+mst.crucial() );

       G.negateWeights();
       mst = new MyMST( G );
       mst.computeStats( G );
       G.negateWeights();
       System.out.println( "\nMaxST "+(-mst.weight()) );
       for (MyEdge e : mst.edges() ) System.out.print( e+", " );   System.out.println();
       System.out.println( "max stats = "+mst.useless()+","+mst.useful+","+mst.crucial() );

   }

	 

}
