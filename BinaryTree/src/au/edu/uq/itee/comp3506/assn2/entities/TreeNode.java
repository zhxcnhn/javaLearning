package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * The node of tree. Able to store id, value, left child and right child
 * 
 * Space efficiency: S(n) = O(1)
 * 
 * @author Haoxiang Zheng
 */
public class TreeNode<T> {
	
	private int id;
	private T value;
	private TreeNode<T> leftChild;
	private TreeNode<T> rightChild;
	
	/**
	 * Initialize a new TreeNode
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @param id The key of node
	 * @param value The stored value
	 */
	public TreeNode(int id, T value) {
		this.id = id;
		this.value = value;
	}
	
	/**
	 * Use to get the id of a tree node.
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @return node id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Use to get the stored value
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @return value
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Use to get the left node
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @return leftchild
	 */
	public TreeNode<T> getLeftNode() {
		return leftChild;
	}
	
	/**
	 * Use to get the right node
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @return rightchild
	 */
	public TreeNode<T> getRightNode() {
		return rightChild;
	}
	
	/**
	 * Use to set the left node
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 */
	public void setLeftNode(TreeNode<T> n) {
		leftChild = n;
	}
	
	/**
	 * Use to set the right node
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 */
	public void setRightNode(TreeNode<T> n) {
		rightChild = n;
	}

}
