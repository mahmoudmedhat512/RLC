import java.util.*;
import java.util.Map.Entry;





public class Rlc {
	
	static ArrayList<String> RLC = new ArrayList<>();
	static ArrayList<String> AfterRLC = new ArrayList<>();
	static HashMap<String, String> overhead = new HashMap<String, String>();
	static ArrayList<String> characters =new ArrayList<String>();
	static ArrayList<Integer> freq =new ArrayList<Integer>();
	static HashMap<String, Integer> tagprob = new HashMap<String, Integer>(); 

	public static void main(String []args) {

		int[] data = {-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		//BuildTree(generateTags(data));
		
		comp(data);
		//Binary(-4);
		/*System.out.println("--------------Run Length Coding tags-----------");
		for(String s: RLC) {
			System.out.print("<"+s+">\t");
		}
		System.out.println("\r\n\r\n\r\n--------------After Run Length Coding tags-----------");
		for(String s: AfterRLC) {
			System.out.print("<"+s+">\t");
		}
		System.out.println("\r\n\r\n\r\n--------------Data in Overhead-----------");
		for(Entry<String, String> entry : overhead.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    System.out.println("Key(" +key+") =>"+ value );
		    // do what you have to do here
		    // In your case, another loop.
		}*/
	}

	
	
public static void comp(int[] data) {
	BuildTree(generateTags(data));
	String s = new String();
	for(int i =0 ; i< AfterRLC.size();i++) {
		//System.out.println("Tag : "+AfterRLC.get(i));
		System.out.print(overhead.get(AfterRLC.get(i)));
		char c= RLC.get(i).charAt(RLC.get(i).length()-2);
		if(c == '-') {
			c = RLC.get(i).charAt(RLC.get(i).length()-1);
			s = Binary(-1*Integer.valueOf(c+""));
		}
		
		else if(c != '-' && c != 'O') {
			c = RLC.get(i).charAt(RLC.get(i).length()-1);
			s = Binary(Integer.valueOf(c+""));
		}
		
		 
		System.out.print(s);
		//System.out.println("-------------");
	
	}
	
	System.out.print(overhead.get("EOF"));
	
	}
	public static ArrayList<String> generateTags(int []data) {



		// data = {-2,0,0,2,0,0,3,2,0,1,0,0,-2,0,-1,0,0,1,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		int count =0 ;


		//List<Map<String, String>> listOfMaps = new ArrayList<Map<String, String>>();



		ArrayList<String> tags = new ArrayList<String>();
		//ArrayList<Integer> num = new ArrayList<Integer>();		
		for(int i= 0; i<data.length ;i++) {
			if(data[i]!=0) {

				//System.out.println(count + ","+data[i]);
				RLC.add(count+","+data[i]);
				if(data[i]>= -1 && data[i]<=1) {
					data[i] = 1;
				}

				else if(data[i]>= -3 && data[i]<=3) {
					data[i] = 2;
				}

				else if(data[i]>= -7 && data[i]<=7) {
					data[i] = 3;
				}

				else if(data[i]>= -15 && data[i]<=15) {
					data[i] = 4;
				}

				tags.add(count+","+data[i]);
				AfterRLC.add(count+","+data[i]);
				count  = 0;

			}
			else {
				count++;
			}
			if (i == data.length-1) {
				tags.add("EOF");
				//System.out.println("EOF => "+count);
			}
		}
		/*for(String s : tags ) {
			System.out.println(s);
		}
*/


		return tags;

	}

	private static String Binary(int x) {
		String s1 = new String() ;


		boolean negative = false; 
		String s = ""; 
		if (x == 0) 
			return "0"; 
		negative = (x < 0); 
		if (negative) 
			x = -1 * x; 
		while (x != 0)  
		{ 
			// add char to 
			// front of s 
			s = (x % 2) + s;  

			// integer division  
			// gives quotient 
			x = x / 2;  
		} 
		if (negative) {
			for(int i = 0 ;i <s.length() ;i++) {
				if(s.charAt(i) == '0') {
					s1 += "1";
				}
				else if(s.charAt(i) == '1')
					s1 += "0";
			}
			//System.out.println("negative : "+ s1);
			return s1;
			} 
		//System.out.println( "Positive : "+ s);
		return s;
	} 
	
	
	public static void BuildTree (ArrayList<String> data) //Data -> input data
	{
		
		for(String tag : data) {
			if(!tagprob.containsKey(tag)) {
				tagprob.put(tag, 1);
			}
			else if(tagprob.containsKey(tag)) {
				int v = tagprob.get(tag);
				tagprob.put(tag, ++v);

			}

			if(!characters.contains(tag)) {
				characters.add(tag);
			}

		}
		
		for(String s : characters) {
			freq.add(tagprob.get(s));
			//System.out.println(s +" => "+tagprob.get(s));
		}
		
		
		
		 ArrayList<node> tree=new ArrayList<node>();
	        for(int i=0;i<tagprob.size();i++)//build arraylist of nodes for tree 
	        {
	            node temp=new node();
	            temp.c=characters.get(i);
	            temp.probability=tagprob.get(temp.c);
	            temp.left=temp.right=null;
	            tree.add(temp);
	        }
	        node root =new node();
	        while (tree.size()!=1)
	        {
	            tree.sort(Comparator.comparing(node::getFreq).reversed());
	            node left=tree.remove(tree.size()-1);  // to remove the least two probability
	            node right=tree.remove(tree.size()-1);
	            node temp=new node();
	            temp.probability=left.probability+right.probability;
	            temp.c="/";
	            temp.left=left;
	            temp.right=right;
	            root=temp;
	            tree.add(temp);
	        // at the end of loop the root will be equal tree 
	        }
	     
	        generate_huffman_code(root,"");
	}

	
	public static void generate_huffman_code(node root,String s)
    {
        if(root==null)
            return;
        if(!root.c.equals("/"))
        {
            //System.out.println(root.c+"-->"+s);
            overhead.put(root.c,s);
        }
        generate_huffman_code(root.left,s+'0');
        generate_huffman_code(root.right,s+'1');
    }


}
