import java.io.*;
import java.util.Scanner;
import java.math.BigInteger;

public class CountBinarySearch {

   private long theCount;
   public BT tree;
   BT root;
   String s1;
   String s2;

 
   CountBinarySearch( Scanner in ) {
     int n = in.nextInt();
	  BT root =  null ;
	
	  for(int i =0; i < n ; i++){
		  root = insert(in.nextInt(), root);
	  }
	  s1 = countInsertionOrderings(root).toString();
	  if(s1.length() <= 8) theCount = countInsertionOrderings(root).longValue();
	  else{
		  s2 = s1.substring(s1.length() - 8);
	      theCount= Long.parseLong(s2);
		  }
   }

   public BT insert(int val, BT t){
      if(t == null) {
		  t = new BT(val);
	  }
	  if(val < t.val){ 
		  t.L = insert(val, t.L);
      }else if(val > t.val) {
	      t.R = insert(val, t.R);
      }else{}
	  return t;
	  
		
    }
	public int numElementsIn(BT t){
		
		int c = 1;
		
		if ( t == null) return 0;
	    if ( t.L != null ) c +=numElementsIn(t.L);   
        if ( t.R != null ) c +=numElementsIn(t.R);        
		       
		return c;
		
	

	}
	public BigInteger factorial(int n){
		BigInteger a = new BigInteger("1");
		for (int i = 1; i <= n; i++){
			BigInteger b = new BigInteger(""+i);
			a = a.multiply(b);
		}
		return a;
	}
	public BigInteger countInsertionOrderings(BT t) {
        BigInteger s = new BigInteger("1");
	if (t == null){
		return s;
    }else{
        int m = numElementsIn(t.L);
        int n = numElementsIn(t.R);
	    BigInteger result = new BigInteger(""+factorial(m+n).divide(factorial(m).multiply(factorial(n))));
	   return result.multiply(countInsertionOrderings(t.L).multiply(countInsertionOrderings(t.R)));
	}
                    
    }
    public long getCount() { return theCount; }
   

    public static void main ( String[] args ) {
      // SIMILAR TO HOW WE WILL CALL YOUR PROGRAM
      Scanner in = new Scanner( System.in );
      int cases = in.nextInt();
      for (int c=0; c<cases; ++c) {
         CountBinarySearch cbs = new CountBinarySearch( in );
         System.out.println( cbs.getCount() );
      }
   }

}
class BT {
	int val;  
	BT L; 
	BT R;
   
	BT( BT l, BT r, int value ) { 
		L = l; 
		R = r; 
		val = value;
	}
	BT(int value){
		val = value;
	}
}
	
