

public class node {
	 int probability;
	    String c;
	    node left=null;
	    node right =null;
	    public String toString() {
	        return "node [probability=" + probability + ", character=" + c +"]";
	    }
	    public int getFreq() {
	        return probability;}
	    
	    public void setFreq(int freq) {
	        this.probability = freq;
	    }
	    public String getC() {
	        return c;
	    }
	    public void setC(String c) {
	        this.c = c;
	    }
	    public node getLeft() {
	        return left;
	    }
	    public void setLeft(node left) {
	        this.left = left;
	    }
	    public node getRight() {
	        return right;
	    }
	    public void setRight(node right) {
	        this.right = right;
	    }

}
