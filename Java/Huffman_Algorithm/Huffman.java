/**
 * Lucas Augusto dos Santos
 * CSE2010
 * Lab8 - Huffman coding
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicInteger;

public class Huffman {

	private Node root = null; // Tree representing Huffman code
	
	
	public Node getHuffmanTree(){
		return root;
	}
	
	/**
	 * Create Huffman tree given string	
	 * @param str - string to use to create Huffman tree
	 */
	public void createHuffmanTree(String str) {
		
		char letter;
		int total = str.length();
		List<Character> letters = new ArrayList();
		int[] count = new int[total];
		
		for(int i=0; i<total; i++)
			count[i] = 0;
		
		// Iterate over the string and count the appearance of the letters
		for(int i=0; i<str.length(); i++){
			letter = str.charAt(i);
			
			if(letters.indexOf(letter) == -1){
				letters.add(letter);
				count[letters.indexOf(letter)] = 1;
			}
			else
				count[letters.indexOf(letter)]++;
		}

		// Create a LinkedList to use its stable sort method
		LinkedList<Node> list = new LinkedList<Node>();
		
		// Populate the LinkedList with all the leafs values
		for(int i=0; i<letters.size(); i++){
			Node temp = new Node();
			temp.setKey(letters.get(i));
			temp.setFrequency(count[i]);
			list.add(temp);
		}
		
		Collections.sort(list);
		
		// Iterate over the LinkedList and build the tree
		while(list.size() > 1){
			Node parent = new Node();
			Node n1 = list.remove();
			Node n2 = list.remove();
			
			parent.setFrequency(n1.getFrequency() + n2.getFrequency());
			if(n1.getFrequency() >= n2.getFrequency()){
				parent.setChildRight(n2);
				parent.setChildLeft(n1);
			}
			else{
				parent.setChildRight(n1);
				parent.setChildLeft(n2);
			}
			list.add(parent);
			Collections.sort(list);
		}
		
		root = list.remove();
		
		
		
		
	}
	/*
	 * Recursive function to build the path from the root to a given character
	 */
	public LinkedList<Boolean> findPath(Character key, LinkedList<Boolean> path, Node n){
		
		LinkedList<Boolean> temp = new LinkedList<Boolean>(); 
		
		// Add true if the element has a right child
		if(n.getChildRight() != null){
			path.add(true);
			temp = findPath(key, path, n.getChildRight());
			if(temp != null) return temp;
			path.removeLast();
		}

		// Add false if the element has a left child
		if(n.getChildLeft() != null){
			path.add(false);
			temp = findPath(key, path, n.getChildLeft());
			if(temp != null) return temp;
			path.removeLast();
		}
		
		if(n.getKey() == key)
			return path;
		
		return null;
	}
	
	/**
	 * Encode a given message using Huffman code and return a list of bits.
	 * @param message - to be encoded
	 * @return list of bits (code)
	 */
	public LinkedList<Boolean> encode(String message){
		
		LinkedList<Boolean> code = new LinkedList<Boolean>();
		
		// Iterate over the string and build the path
		for(int i=0; i<message.length(); i++){
			LinkedList<Boolean> path = new LinkedList<Boolean>();
			path = findPath(message.charAt(i), path, root);
			if(path != null) code.addAll(path);
		}
		
		return code;
	}
	
	/**
	 * Decode list of bits into string
	 * @param code - string code
	 * @return Original string
	 */
	public String decode(LinkedList<Boolean> code){	
		
		Node n = root;
		StringBuilder s = new StringBuilder();
		
		// Iterate over the code and build the string
		for(int i=0; i<code.size(); i++){
			
			if(code.get(i) == true)
				n = n.getChildRight();
			if(code.get(i) == false)
				n = n.getChildLeft();
			
			// If the node is a leaf get its value and go back to the root
			if(n.getChildRight() == null  && n.getChildLeft() == null){
				s.append(n.getKey());
				n = root;
			}
		}
		return s.toString();
	}
	
	public String toString(){
		System.out.println("Tree:");
		return root.toString();
	}

}
