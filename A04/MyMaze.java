import java.util.Random;

public class MyMaze {

   private int[][] m;   // maze representation
   private int rows;    // number of rows in the maze
   private int cols;    // number of columns in the maze
   private final static byte[] TWO = { 1, 2, 4, 8, 16};
   private final static byte[] DX  = { 0,+1, 0,-1};
   private final static byte[] DY  = {-1, 0,+1, 0};
   private boolean done;  // used in finding a single solution.
   private long   count;  // used in finding the number of solutions.
   private Random r;      // for generating random integers.
   private long solutionCount;
   private boolean flag ;

   public int getRows() { return( rows ); }
   public int getCols() { return( cols ); }

   public MyMaze ( int nr, int nc, int seed ) {
      r = new Random( seed );
      rows = nr;  cols = nc;
      m = new int[nr+2][nc+2];
      for (int r=1; r<=nr; ++r )
         for (int c=1; c<=nc; ++c )
            m[r][c] = 15;
      for (int r=0; r<nr+2; ++r )
            m[r][0] = m[r][nc+1] = 16;
      for (int c=0; c<nc+2; ++c )
         m[0][c] = m[nr+1][c] = 16;
      Create( nr/2+1, nc/2+1, 0 );
   }

   // Wall in direction p?
   public boolean ok ( int x, int y, int p ) {
      return( (m[x][y] & TWO[p]) == TWO[p] );
   }

   private boolean downWall( int x, int y, int p ) {
      if (ok(x,y,p) && m[x+DX[p]][y+DY[p]] != 16) {
         m[x][y] ^= TWO[p];  m[x+DX[p]][y+DY[p]] ^= TWO[p^2];
         return true;
      }
      return false;
   }

   private void knockDown( int count ) {
      // Caution: make sure there are at least count walls!
      for (int i=0; i<count; ++i) {
         int x = 1+r.nextInt(rows);
         int y = 1+r.nextInt(cols);
         if (!downWall( x, y, r.nextInt(4))) --i;
      }
   }

   private void Create ( int x, int y, int val ) {
      int[] perm = randPerm( 4 );
      m[x][y] ^= val;
      for (int i=0; i<4; ++i) {
         int p = perm[i];
         if (m[x+DX[p]][y+DY[p]] == 15) {
            m[x][y] ^= TWO[p];
            Create( x+DX[p], y+DY[p], TWO[p^2] );
         }
      }
   }

   private int[] randPerm( int n ) {
      // This algorithm should look familiar!
      int[] perm = new int[n];
      for (int k=0; k<n; ++k) perm[k] = k;
      for (int k=n; k>0; --k) {
         int rand = r.nextInt(k);
         int t = perm[rand];  perm[rand] = perm[k-1];  perm[k-1] = t;
      }
      return( perm );
   }

   public String toString() {
      String s = "";
      for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        if (j == 1) {
          s += String.format("%2d", m[i][j]);
        } else {
          s += String.format("%3d", m[i][j]);
        }
      }
      s += "\n";
    }
    return s;
   }
   public int[][] copyMaze (int[][]arr){
        int[][]newA = new int[arr.length][arr.length];
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr.length;j++){
                newA[i][j]=arr[i][j];
            }
              
        } 
		return newA;     
    }
   public void solveMaze( int x0, int y0, int x1, int y1 ) {
   
	   int[][] n = new int[m.length][m.length];
	   n = copyMaze(m);
       findPath(false, x0, y0,x1,y1);
   }

    public long numSolutions(int x0, int y0, int x1, int y1) {
      
	   int[][] n = new int[m.length][m.length];
	   count = 0;
	   n = copyMaze(m);
	   findPath(true, x0, y0,x1,y1);
	   m = n;
	   return count;
   }
   
   private boolean findPath(boolean done, int sx, int sy,int fx,int fy) {
	   flag = false;
	   if(sx == fx && sy == fy) {
		   m[sx][sy] += 16;
		   if(done == false) {		   
			 flag = true;
			 return flag;
		   }
		   count++;
		   return flag;
	   }
	   
	   if(!ok(sx,sy,3)) {
		   m[sx - 1][sy] += 2;
		   m[sx][sy] += 8;
		   if(findPath(done, sx - 1, sy,fx,fy)) {
			   up(m,sx-1,sy);
			   return flag;
		   } else {
			   m[sx - 1][sy] -= 2;
			   m[sx][sy] -= 8;
		   }
		  
	   }
	   if(!ok(sx,sy,2)) {
		   m[sx][sy + 1] += 1;
		   m[sx][sy] += 4;
		   if(findPath(done, sx, sy + 1,fx,fy)) {
			   right(m,sx,sy+1);
			   return flag;
		   } else {
			   m[sx][sy + 1] -= 1;
			   m[sx][sy] -= 4;
		   }
	   }
	   if(!ok(sx,sy,1)) {
		   m[sx][sy] += 2;
		   m[sx+1][sy] += 8;
		   
		   if(findPath(done, sx + 1, sy,fx,fy)) {
			  down(m,sx+1,sy);
			   return flag;
		   } else {
			   m[sx][sy] -= 2;
			   m[sx + 1][sy] -= 8;
			   
		   }
		   
	   }
	   if(!ok(sx,sy,0)) {
		   m[sx][sy] += 1;
		   m[sx][sy - 1] += 4;
		   if(findPath(done, sx, sy - 1,fx,fy)) {
			  left(m,sx,sy-1);
			   return flag;
		   } else {
			   m[sx][sy] -= 1;
			   m[sx][sy - 1] -= 4;
		   }
	   }
	   flag = false;
	   return flag;
   }
   public void up(int[][] m, int x, int y){
	    m[x][y] -= 2;
	    m[x+1][y] -= 8;
		m[x+1][y] += 16;
			   
		flag = true;
   }
   public void down(int[][] m, int x, int y){
	    m[x-1][y] -= 2;
	    m[x][y] -= 8;
		m[x-1][y] += 16;
			   
		flag = true;
   }
   public void right(int[][] m, int x, int y){
	   m[x][y] -= 1;
	   m[x][y-1] -= 4;
	   m[x][y-1] += 16;
			   
	   flag = true;
   }
   public void left(int[][] m, int x, int y){
	    m[x][y+1] -= 1;
		m[x][y] -= 4;
		m[x][y+1] += 16;
		flag = true;
   }
   public static void main ( String[] args ) {
      int row = Integer.parseInt( args[0] );
      int col = Integer.parseInt( args[1] );
      int sx = Integer.parseInt( args[2] );
      int sy = Integer.parseInt( args[3] );
      int fx = Integer.parseInt( args[4] );
      int fy = Integer.parseInt( args[5] );
      MyMaze maz = new MyMaze( row, col, 9999 );
      System.out.print( maz );
      System.out.println( "Solutions = "+maz.numSolutions( sx, sy, fx, fy ) );
      maz.knockDown( (row+col)/4 );
      System.out.print( maz );
      System.out.println( "Solutions = "+maz.numSolutions( sx, sy, fx, fy ) );
      maz = new MyMaze( row, col, 9999 );  // creates the same maze anew.
      maz.solveMaze( sx, sy, fx, fy );
      System.out.print( maz );
   }
}