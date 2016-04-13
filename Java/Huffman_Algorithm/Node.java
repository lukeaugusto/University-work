/**
 * Lucas Augusto dos Santos
 * CSE2010
 * Lab8 - Huffman coding
*/

public class Node implements Comparable<Node>{

	private Node childLeft;
	private Node childRight;
	private Character key;
	private int frequences;

	public Node getChildLeft() {
		return childLeft;
	}

	public void setChildLeft(Node childLeft) {
		this.childLeft = childLeft;
	}

	public void setChildRight(Node childRight) {
		this.childRight = childRight;
	}

	public void setFrequency(int frequences) {
		this.frequences = frequences;
	}

	public void setKey(Character key) {
		this.key = key;
	}

	public Node getChildRight() {
		return childRight;
	}

	public int getFrequency() {
		return frequences;
	}

	public Character getKey() {
		return key;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Function to compare two Nodes
	 */
	public int compareTo(Node o) {
        return this.getFrequency() - o.getFrequency();
    }
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * Print the node in dfs going left first
	 */
	public String toString() {
		String s = "";
		s = "[" + frequences + ", " + key + "]\n";
		if(getChildLeft() != null) s = s + getChildLeft().toString();
		if(getChildRight() != null) s = s + getChildRight().toString();
		return s;
	}
	


}
