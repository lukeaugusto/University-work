import java.util.Collections;
import java.util.Stack;

public class SuperStack<Item extends Comparable<Item>> {
    private Stack<Item> main;  // main stack
    private Stack<Item> max;   // helper stack, use to implement functions using max element
    
    public SuperStack() {
    	main = new Stack<Item>();
		max = new Stack<Item>();
	}

    
    public final Item pop () {
    	// pop element from the SuperStack
    	
    	if(!this.isEmpty()){
	    
    		int same = 0;
	    	Item i, element;
	    	Stack<Item> aux = new Stack<Item>();
	    	
	    	i = main.pop();
	    	
	    	// Find the selected element in the max stack
	    	for(int j=max.size(); j>0 && same == 0; j--){
	    		element = max.pop();
	    		
	    		// Check if the elements are equal
	    		if(i.compareTo(element) != 2)
	    			aux.push(element); // Use the stack aux to put the elements above the selected item
	    		else
	    			same = 1;
	    	}
	    	
	    	// Put the stacks together without the selected item
	    	Collections.reverse(aux);
	    	max.addAll(aux);
	    	
	    	return(i);
    	}
    	return(null);
    }
    
    
    public final Item peek () {
    	// peek (return) last element added, without removing the element
    	// from the SuperStack

    	if(!this.isEmpty())
    		return(main.peek());
    	
    	return(null);
    }

    
    public final void push (final Item i) {
    	// add element to the SuperStack
    	
    	int bigger = 1;
    	Stack<Item> aux = new Stack<Item>();
    	
    	// Organize the stack using aux to save the bigger elements 
    	for(int j=max.size(); j>0 && bigger == 1; j--){
    		if(i.compareTo(max.peek()) == -1)
    			aux.push(max.pop());
    		else
    			bigger = 0;
    	}
    	
    	// Add the element in max after removing all elements bigger than it
    	max.push(i);
    	
    	// Add the elements bigger than i
    	Collections.reverse(aux);
    	max.addAll(aux);

    	// Add i in the main stack
    	main.push(i);
    }
    
    
    public final Stack<Item> getMainStack () {
    	return main;
    }

    
    public final Item max () {
    	// return, without removing, the largest element in the SuperStack
    	
    	if(!this.isEmpty())
    		return(max.peek());
    	
    	return(null);
    }

    public final Item popMax () {
    	// return and remove the largest element in the SuperStack
    	
    	if(!this.isEmpty()){
	    
    		int same = 0;
	    	Item i;
	    	
	    	i = max.pop();
	
	    	// Find the biggest element in the main stack
	    	for(int j=main.size(); j>0 && same == 0; j--){
	    		
	    		// Check if the element is the biggest
	    		if(i.compareTo(main.peek()) == 2){
	    			main.pop(); // Remove the biggest element
	    			same = 1;
	    		}
	    	}
	    	
	    	return(i);
    	}
    	return(null);
    }


    public final boolean isEmpty () { 
    	return main.isEmpty();
    }

}


