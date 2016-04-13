/*
 * Author:  Lucas Santos, ldossantos2015@my.fit.edu
 * Course:  CSE 2010, Section 02, Fall 2015
 * Project: Lab 03: Editor
 */

public class Editor {
	
	LinkedList<Character> content;  // this is LinkedList class you implemented
	// submission which use java.util.LinkedList will get 0 for this java class.

	public Editor(){
		content = new LinkedList<Character>();
	}
	
	public void insert(int location, String s){
		// insert characters from string into linked list content 
		// starting from 'location'
		for(int i=0; i<s.length(); i++)
			this.content.add(location, s.charAt(i));
	}
	
	public void insert(int location, Character s){
		// insert Character 's' at 'location' in the linked list
		this.content.add(location, s);
	}
	
	public void insertInTheEnd(Character s){
		// insert char in the end
		this.content.addLast(s);
	}
	
	
	public void insertInTheEnd(String s){
		// insert characters from 's' in the end
		for(int i=0; i<s.length(); i++)
			this.content.addLast(s.charAt(i));
	}
	
	
	public Character delete(int location){
		// delete character at location
		return(this.content.remove(location));
	}
	
	public String delete(int from, int to){
		// delete characters given range
		String deleted = "";
		
		// Delete all elements in the range
		for(int i=0; i<(to-from); i++)
			deleted = deleted + this.content.remove(from).toString();
		
		return(deleted);
	}
	
	
	public String showText(){
		// return String which consists of all characters of the linked list
		String text = "";
		int i;
		
		// Get all elements
		for(i=0; i<this.content.size(); i++)
			text = text + this.content.get(i).toString();
		
		return(text);
	}
	

}