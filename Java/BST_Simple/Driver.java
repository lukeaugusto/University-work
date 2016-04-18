
import java.util.Vector;

public class Driver {
	
	private static NodeType[] BST;
	// Number of elements in the BST
	private static int n = 11;
	
	public static void main(String[] args) {
		// Create and populate the BST
		BST = new NodeType[n];
		
        
        // Bad hardcoded program, the focus here is to test the bst
		BST[7] = new NodeType(7);
		BST[4] = new NodeType(4);
		BST[3] = new NodeType(3);
		BST[5] = new NodeType(5);
		BST[9] = new NodeType(9);
		BST[10] = new NodeType(10);
		

		BST[7].parent = null;
		BST[7].left = BST[4];
		BST[7].right = BST[9];
		
		BST[4].parent = BST[7];
		BST[4].left = BST[3];
		BST[4].right = BST[5];
		
		BST[3].parent = BST[4];
		
		BST[5].parent = BST[4];
		
		BST[9].parent = BST[7];
		BST[9].right = BST[10];
		
		BST[10].parent = BST[9];
		
		NodeType parent = findAncestor(3,5);
		
		System.out.println("First Ancestor: " + parent.value);
		System.out.println("Level Order Traversal: ");
		traversalPrint(BST);
	}
	
	public static NodeType findAncestor(int v1, int v2){
		NodeType n1, n2, parent;
		int level1 = 0, level2 = 0;
		
		n1 = new NodeType(-1);
		n2 = new NodeType(-1);
		
		// Search the node in the BST
		for(int i=0; i<n; i++){
			if(BST[i] != null && BST[i].value == v1) n1 = BST[i];
			if(BST[i] != null && BST[i].value == v2) n2 = BST[i];
		}
		
		// Break condition, if the values of nodes are the same
		if(v1 == v2) return n1;
		
		// Set the level of the tree for each node
		parent = n1;
		while(parent != null){
			parent = parent.parent;
			level1 ++;
		}
		
		parent = n2;
		while(parent != null){
			parent = parent.parent;
			level2 ++;
		}
		
		// Compare the levels and do the recurrence
		if(level1 < level2) return findAncestor(n1.value, n2.parent.value);
		if(level1 > level2) return findAncestor(n1.parent.value, n2.value);
		if(level1 == level2) return findAncestor(n1.parent.value, n1.parent.value);

		return null;
	}
	
	public static void traversalPrint(NodeType[] bst){
		
		int count = 0;	
		NodeType[] aux = bst;
		
		// Iterate over the bst
		for(int i=0; i<aux.length; i++){
			
			// If the node is a leaf
			if(aux[i] != null && aux[i].left == null && aux[i].right == null){
				
				count++;
				System.out.print(aux[i].value + " ");
				
				// Delete the note from the tree structure
				if(aux[i].parent != null){
					int parent = aux[i].parent.value;
					if(aux[parent] != null && aux[parent].right != null && aux[parent].right.value == aux[i].value) aux[parent].right = null;
					if(aux[parent] != null && aux[parent].left != null && aux[parent].left.value == aux[i].value) aux[parent].left = null;
				}
				aux[i] = null;
			}
		}
		System.out.println("");
		
		// Break Condition, if the tree is empty it does not call recurrence
		if(count > 0) traversalPrint(aux);
	}
	
	
}
