import java.util.LinkedList;

public class Stack<Key> {

	private LinkedList<Key> list;
	
	
	public Stack(){
		list=new LinkedList<Key>();
	}
	
	public void push(Key key){
		// add to the stack
		list.add(key);
	}
	
	public Key peek(){
		// return without removing
		return list.getLast();
	}
	
	public Key pop(){
		// pop the element from the stack
		return list.removeLast();
	}
	
	public boolean isempty(){
		return list.isEmpty();
	}

}
