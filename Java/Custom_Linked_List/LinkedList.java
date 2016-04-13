/*
 * Author:  Lucas Santos, ldossantos2015@my.fit.edu
 * Course:  CSE 2010, Section 02, Fall 2015
 * Project: Lab 03: Editor
 */

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public class LinkedList<E> implements Iterable<E> {

	private class Node {
		E data;
		Integer index_next; 		// pointer to the next element in the array elements
		Integer index_array;		// pointer to the current element in the array elements
		Integer index_previous;		// pointer to the previous element in the array elements

		public Node(E data_) {
			data = data_;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(data.toString() + " (" + index_previous + ", " + index_array + ", " + index_next + ")");
			return new String(sb);
		}

	}

	// hold all elements in this array
	Node[] elements; 							
	
	// Initial size of the array.
	private final int INITIAL_SIZE = 8; 		
	private Integer tail;                       // pointer to the tail
	private Integer head;

	// these variable are optional
	private int current_index = 0;              // index in the array to use when adding element
	private int size = 0;

	@SuppressWarnings("unchecked")
	public LinkedList() {
		// ugly cast is necessary as Java doesn't
		// allow creating generic arrays using
		// E[] e = new E[10]; <- Does not work
		elements = (Node[]) Array.newInstance(Node.class, INITIAL_SIZE);
		tail = null;
		head = null;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public void addFirst(E t) {
		// add in the beginning
		

		// Add the element if its the first element being added
		if(head == null){
			head = 0;
			tail = 0;
			
			elements[head] = new Node(t);
			elements[head].index_array = 0;
			elements[head].index_next = null;
			elements[head].index_previous = null;

			current_index++;
		}
		else{

			// Check if is necessary to increase the size of the array
			if(elements.length == this.size()+1)
				this.resizeBidirectional(true);
			
			// Create the new element
			int new_index = current_index;
			elements[new_index] = new Node(t);
			elements[new_index].index_array = new_index;
			elements[new_index].index_next = head;
			elements[new_index].index_previous = null;
			

			// Update the information of the element after it
			elements[head].index_previous = new_index;
			
			// Update the head value
			head = new_index;

			current_index++;
		}
	}
	
	public void addLast(E t) {
		// add in the end
		
		// Add the element if its the first element being added
		if(tail == null){
			head = 0;
			tail = 0;
			
			// Create the new element
			elements[tail] = new Node(t);
			elements[tail].index_array = 0;
			elements[tail].index_next = null;
			elements[tail].index_previous = null;
			
			current_index++;
		}
		else{

			// Check if is necessary to increase the size of the array
			if(elements.length == this.size()+1)
				this.resizeBidirectional(true);
			
			// Create the new element
			int new_index = current_index;
			elements[new_index] = new Node(t);
			elements[new_index].index_array = new_index;
			elements[new_index].index_next = null;
			elements[new_index].index_previous = tail;
			
			// Update the information of the element before it
			elements[tail].index_next = new_index;
			
			// Update the tail value
			tail = new_index;

			current_index++;
		}
	}

	private void resizeBidirectional(boolean direction) {
		// function to dynamically resize array, 
		// if direction == true -> elements.length -> 2 x elements.length
		// if false true -> elements.length -> 1/2 x elements.length

		if(direction == true){
			Node[] tmp;
			tmp = (Node[]) Array.newInstance(Node.class, elements.length*2);
	        System.arraycopy(elements,0,tmp,0,elements.length); 
	        elements = tmp;
		}
		
		if(direction == false){
			Node[] tmp; 
			tmp = (Node[]) Array.newInstance(Node.class, elements.length);
	        System.arraycopy(elements,0,tmp,0,elements.length); 
	        elements = tmp;
		}
	}

	public void add(int index, E t) {
		// add at index. Note that this is not an index in the array 'elements', but rather
		// in the linked list itself.

		// Check if is necessary to increase the size of the array
		if(elements.length == this.size()+1)
			this.resizeBidirectional(true);

		// Find the element in the array
		int array_index;
		Integer aux = head;
		for(int i=0; i<index && elements[aux].index_next != null; i++)
			aux = elements[aux].index_next;
		array_index = elements[aux].index_array;
		
		// Create the element in the position
		elements[current_index] = new Node(t);
		elements[current_index].index_array = current_index;
		elements[current_index].index_next = elements[array_index].index_array;
		elements[current_index].index_previous = elements[array_index].index_previous;

		// Set the new parameters for the previous and next elements
		elements[array_index].index_previous = current_index;
		elements[elements[current_index].index_previous].index_next = current_index;
		

		// If the element is the head or the tail, update its parameters
		if(head == array_index) 
			head = current_index;
		if(tail == array_index) 
			tail = current_index;

		current_index++;
	}

	public Integer size() {
		// return size
		return (current_index-1);
	}

	public E get(int index) {
		// return element at index in the list
		
		Integer aux = head;
		for(int i=0; i<index && elements[aux].index_next != null; i++)
			aux = elements[aux].index_next;
		
		return(elements[aux].data);
	}

	public E getFirst() {
		// return first element
		return(elements[head].data);
	}

	public E getLast() {
		// return last element
		return(elements[tail].data);
	}

	public E removeLast() {
		// return last and remove it from the list

		// Check if is necessary to reduce the size of the array
		if(elements.length/2 == this.size()-1)
			this.resizeBidirectional(false);
		
		// Copy the information before deleting the element
		E value = elements[tail].data;
		int previous = elements[tail].index_previous;

		// Set the new parameters for the next element and tail
		elements[previous].index_next = null;
		tail = elements[previous].index_array;

		// Copy the last element to the position of the deleted one
		if(elements[tail].index_array != current_index){
			elements[current_index].index_array = elements[tail].index_array;
			elements[tail] = elements[current_index];
			elements[current_index] = null;
		}
			
		current_index--;
		
		return(value);
	}

	public E removeFirst() {
		// return first and remove it from the list

		// Check if is necessary to reduce the size of the array
		if(elements.length/2 == this.size()-1)
			this.resizeBidirectional(false);

		// Copy the information before deleting the element
		E value = elements[head].data;
		int next = elements[head].index_next;
		
		// Set the new parameters for the next element and head
		elements[next].index_previous = null;
		head = elements[next].index_array;

		// Copy the last element to the position of the deleted one
		if(elements[head].index_array != current_index){
			elements[current_index].index_array = elements[head].index_array;
			elements[head] = elements[current_index];
		}

		current_index = current_index-1;
		
		return(value);
	}

	public E remove(int index) {
		// remove at index in the linked list
		
		// Check if is necessary to reduce the size of the array
		if(elements.length/2 == this.size()-1)
			this.resizeBidirectional(false);

		// Find the element in the array
		Integer aux = head;
		int array_index;
		for(int i=0; i<index && elements[aux].index_next != null; i++)
			aux = elements[aux].index_next;
		array_index = elements[aux].index_array;
		
		// Copy the information before deleting the element
		E value = elements[array_index].data;
		Integer next = elements[array_index].index_next;
		Integer previous = elements[array_index].index_previous;

		// Set the new parameters for the previous and next elements
		if(previous != null)
			elements[previous].index_next = next;
		if(next != null)
			elements[next].index_previous = previous;
		
		// If the element is the head or the tail, update its parameters
		if(array_index == head && next != null)
			head = elements[next].index_array;
		if(array_index == tail && previous != null)
			tail = elements[previous].index_array;
		
		// Copy the last element to the position of the deleted one
		if(elements[array_index].index_array != current_index-1){
			elements[current_index-1].index_array = elements[array_index].index_array;
			elements[array_index] = elements[current_index-1];
		}
		
		current_index = current_index-1;
		
		return(value);
	}

	@Override
	public Iterator<E> iterator() {
		// return iterator so that it was possible to iterate over elements
		// of the list
        Iterator<E> ite = new Iterator<E>() {

            private int currentIndex = tail;

            @Override
            public boolean hasNext() {
                return currentIndex < size() && elements[currentIndex].index_next != null;
            }

            @Override
            public E next() {
            	return elements[elements[currentIndex].index_next].data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return ite;
	}
}