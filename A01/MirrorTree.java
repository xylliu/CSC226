import edu.princeton.cs.algs4.StdIn;

public class MirrorTree{
	public BT tree, mirror; //A tree and its mirror

	private char[] a;  //0/1 array for tree
	private int k;		//used by build


	MirrorTree(String s){
		a = s.toCharArray();
		k = -1;
		tree = build();

		mirror = mirrorTree(clone(tree));
	}

	BT build() { return( a[++k] == '0' ? null : new BT( build(), build() )); }

	public String preord(BT t) {
		String output = "";

		if(t == null){
			return "0";
		}
		else{
			output = "1";
		}

		output += preord(t.L);
		output += preord(t.R);
		return output;
	}

	public String toString() {
		String s = "";
		s += preord(tree);
		s += '\n';
		s += preord(mirror);

		return s;
	}

	public BT clone(BT t){
		BT newTree = new BT(null,null);
		if(t != null){
			newTree.L = clone(t.L);
			newTree.R = clone(t.R);
		}else{
			return null;
		}

		return newTree;
	}


	/*public BT mirrorTree(BT t){
		if (t != null) {
			mirrorTree(t.L);
			mirrorTree(t.R);

			BT temp = t.L;
			t.L = t.R;
			t.R = temp;
    	}
    	else{
    		return null;
    	}
    	return t;
	}*/
	public static void main(String[]args){
		MirrorTree mt = new MirrorTree(args[0]);
		System.out.println(mt+"\ntree and mirror");
		System.out.println(new MirrorTree(mt.preord(mt.mirror)));
	}


	public void mirrorRecursively(BT t){
		if(t == null)
			return;
		if(t.L == null && t.R == null)
			return;

		BT pTemp = t.L;
		t.L = t.R;
		t.R = pTemp;

		if(t.L != null)
			mirrorRecursively(t.L);
		if(t.R != null)
			mirrorRecursively(t.R);
	}

	public BT mirrorTree(BT t){
		mirrorRecursively(t);
		return t;
	}
}