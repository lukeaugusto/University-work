
public class NodeType{
	
	Integer value;
	NodeType left;
	NodeType right;
	NodeType parent;
	
	NodeType(int value){
		this.value = value;
		this.left = null;
		this.right = null;
		this.parent = null;
	}
	
	public void setLeft(NodeType left){
		this.left = left;
	}

	public void setRight(NodeType right){
		this.right = right;
	}

	public void setParent(NodeType parent){
		this.parent = parent;
	}

	public void setValue(int value){
		this.value = value;
	}
	
	public NodeType getLeft(){
		return left;
	}
	
	public NodeType getRight(){
		return right;
	}
	
	public NodeType getParent(){
		return parent;
	}
	
	public Integer getValue(){
		return value;
	}
	
	public String toString() {
		return value + "";
	}
	
}