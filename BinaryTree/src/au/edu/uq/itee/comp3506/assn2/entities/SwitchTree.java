package au.edu.uq.itee.comp3506.assn2.entities;

/**
 * This class was implemented to store data(Switches -- Integer) into binary tree structure.
 * 
 * Space efficiency: S(n) = O(n)
 * 
 * @author Haoxiang Zheng
 */
public class SwitchTree {
	
	private TreeNode<Integer> root;
	
	/**
	 * Find method 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param key Use as the key to search the node based on the node id.
	 * @return Node that id is equal to key.
	 */
	public TreeNode<Integer> find(int key){
		if(root == null){  
            System.out.println("The tree is empty!");  
            return null;  
        }  
		TreeNode<Integer> current = root;  
        while(current.getId() != key){  
            if(key > current.getId()){
            	current = current.getRightNode();  
            }               
            else{
            	current = current.getLeftNode(); 
            }                
            if(current == null)  {
            	return null;  
            }     
        }  
        return current;
	}
	
	/**
	 * Use to get the root node of a tree.
	 * Run-time efficiency: T(n) = O(1)
	 * 
	 * @return Root node.
	 */
	public TreeNode<Integer> getRoot() {
		return root;
	}
	
	/**
	 * Use to insert a new node into the tree.
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param record The node which is going to be insert
	 * @return True is if the insert is proceed successfully
	 */
	public boolean insert(TreeNode<Integer> record){
		if(root == null){  
            root = (TreeNode<Integer>) record;  
            return true;  
        }
		else {
			TreeNode<Integer> current = root;
			TreeNode<Integer> parent;
			
			while(true){
				parent  =  current;
				if(record.getId() < current.getId()){
					current = current.getLeftNode();
					if(current == null){
						parent.setLeftNode(record);
						return true;
					}
				}
				else{
					current = current.getRightNode();
					if(current == null){
						parent.setRightNode(record);
						return true;
					}
				}
			}
		}
	}
	

	
	/**
	 * Use to get the node with smallest id 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param node The root node
	 * @return Node with the smallest id
	 */
    public TreeNode<Integer> getMinNode(TreeNode<Integer> node){  
        if(this.find(node.getId()) == null){  
            System.out.println("Node dosen't exist!");  
            return null;  
        }  
        if(node.getLeftNode() == null)  
            return node;  
        TreeNode<Integer> current = node.getLeftNode();  
        while(current.getLeftNode() != null)  
            current = current.getLeftNode();  
        return current;  
    }  
      
    /**
	 * Use to get the node with biggest id 
	 * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param node The root node
	 * @return Node with the biggest id
	 */
    public TreeNode<Integer> getMaxNode(TreeNode<Integer> node){  
        if(this.find(node.getId()) == null){  
            System.out.println("Node dosen't exist!");  
            return null;  
        }  
        if(node.getRightNode() == null)  
            return node;  
        TreeNode<Integer> current = node.getRightNode();  
        while(current.getRightNode() != null)  
            current = current.getRightNode();  
        return current;  
    }  

}
