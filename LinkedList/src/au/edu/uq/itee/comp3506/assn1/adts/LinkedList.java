package au.edu.uq.itee.comp3506.assn1.adts;


/**
 * An ordered sequential list of items.
 * Maintains a 'cursor' that is a reference to an element in the list, providing a mechanism to iterate over the list.
 * The cursor is the position at which non-fixed-positioning operations on the list occur 
 * (e.g. insert, remove, getNext, getPrevious).
 *
 * Space efficiency: S(n) = O(n)
 * Since the space efficiency depends on the numbers of element in the single linkedlist
 * which would grow linearly depending on how many elements is being put into it.
 * It is up to what n element is.
 * 
 * @param <T> The type of element held in the list.
 */

public class LinkedList<T> implements GameList<T> {
	
	private class Data{
		private Object obj;  
        private Data next = null;  
          
        Data(Object obj){  
            this.obj = obj; 
        }
	}
	
	private Data first = null;
	private Data last = null;
	private Data cursor;
	
	/**
     * LinkedList Class Constructor
     * 
     */	
	public LinkedList(){
		
	}

	/**
	 * Add an item to the end of the list.
	 * The cursor will refer to the newly added item.
	 * If the list is empty {@code item} becomes the first and last item in the list.
	 * 
	 * Operations f(n) = 8
     * Run-time efficiency: T(n) = O(1) 
	 * 
	 * @param item The item to be added to the list.
	 * @throws NullPointerException if item is null
	 */
	@Override
	public void addToEnd(T item) throws NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		Data inputData = new Data(item);
		if (first == null){
			first = inputData;
		}
		else{  
            last.next = inputData;  
  
        }  
        last = inputData;
        cursor = last;
		
	}

	/**
	 * Insert an item in front of the current cursor position in the list.
	 * The cursor will refer to the newly inserted item.
	 * If the list is empty {@code item} becomes the first and last item in the list.
	 * 
	 * Operations f(n) = 5n + 12
     * Run-time efficiency: T(n) = O(n) 
	 * 
	 * @param item The item to be inserted into the list.
	 * @throws NullPointerException if item is null
	 */
	@Override
	public void insert(T item) throws NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		Data inputData = new Data(item);
		if (first == null){
			first = inputData;
			last = inputData;
			cursor = last;
		}
		else if (first.equals(cursor)){
			inputData.next = first;
			first = inputData;
			cursor = inputData;
		}
		else{
			Data temp = first;
			while(!temp.next.equals(cursor)){
				temp = temp.next;
			}
			temp.next = inputData;
			inputData.next = cursor;
			cursor = inputData;
		}
		
	}

	/**
	 * Removes the item, at the current cursor position, from the list. 
	 * Ensures that the previous item is correctly connected to the next item.
	 * After the removal the cursor will refer to the next item in the list. 
	 * If the removed item was the last element in the list, then the cursor will
	 * refer to the previous element, which is now the last item in the list.
	 * 
	 * Operations f(n) = 5n + 10
     * Run-time efficiency: T(n) = O(n) 
	 * 
	 * @throws IndexOutOfBoundsException If an attempt is made to remove an element from an empty list.
	 */
	@Override
	public void remove() throws IndexOutOfBoundsException {
		if (first == null){
			throw new IndexOutOfBoundsException(
                    "Cannot remove an "
                    + "element from an empty list");
		}
		if(first.equals(cursor)){
			if (last.equals(cursor)){
				first = null;
				last = null;
			}
			else{
				first = first.next;
	            cursor = first;
			}
            
        } 
		else{
			Data temp = first;
			while(!temp.next.equals(cursor)){
				temp = temp.next;
			}
			if (cursor.equals(last)){
				last = temp;
				cursor = temp;
			}
			else{
				temp.next = cursor.next;
				cursor = cursor.next;
			}
		}							
	}

	/**
	 * Move the internal cursor to the first element in the list.
	 * 
	 * Operations f(n) = 5
     * Run-time efficiency: T(n) = O(1) 
	 * 
	 * @return The item at the first position in the list; null if the list is empty.
	 */
	@Override
	public T getFirst() {
		if (first == null){
			return null;
		}
		cursor = first;
		return (T) cursor.obj;
	}

	/**
	 * Move the internal cursor to the next element in sequential order in the list.
	 * If the cursor is at the end of the list it remains at that position, and returns the item at that position.
	 * 
	 * Operations f(n) = 8
     * Run-time efficiency: T(n) = O(1) 
     * 
	 * @return The item at the new cursor position; null if the list is empty.
	 */
	@Override
	public T getNext() {
		if (first == null){
			return null;
		}
		if (cursor.next == null){
			return (T) cursor.obj;
		}
		else{
			cursor = cursor.next;
			return (T) cursor.obj;
		}
	}

	/**
	 * Move the internal cursor to the last element in the list.
	 * 
	 * Operations f(n) = 5
     * Run-time efficiency: T(n) = O(1) 
     * 
	 * @return The item at the last position in the list; null if the list is empty.
	 */
	@Override
	public T getLast() {
		if (first == null){
			return null;
		}
		cursor = last;
		return (T) cursor.obj;
	}

	/**
	 * Move the internal cursor to the previous element in sequential order in the list.
	 * If the cursor is at the beginning of the list it remains at that position, and returns the item at that position.
	 * 
	 * Operations f(n) = 5n + 8
     * Run-time efficiency: T(n) = O(n) 
     * 
	 * @return The item at the new cursor position; null if the list is empty.
	 */
	@Override
	public T getPrevious() {
		if (first == null){
			return null;
		}
		if(first.equals(cursor)){  
            return (T) cursor.obj;
        }
		else{
			Data temp = first;
			while(!temp.next.equals(cursor)){
				temp = temp.next;
			}
			cursor = temp;
			return (T) cursor.obj;
		}
		
	}

	/**
	 * Finds an item in the list, moving the cursor to the item's position in the list.
	 * Starts searching from the beginning of the list, and stops when it finds the first instance of the item in the list.
	 * If the item is not found the cursor remains at the end of the list.
	 * 
	 * Operations f(n) = 6n + 4
     * Run-time efficiency: T(n) = O(n) 
     * 
	 * @param item The item to be found.
	 * @throws NullPointerException if item is null
	 * @return true if the item has been found in the list; false otherwise.
	 */
	@Override
	public boolean find(T item) throws NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		Data temp = first;
		while(temp != null){
			if (temp.obj.equals(item)){
				cursor = temp;
				return true;
			}
			cursor = temp;
			temp = temp.next;			
		}
		return false;
	}

	/**
	 * Indicates if the list is empty or not.
	 * 
	 * Operations f(n) = 2
     * Run-time efficiency: T(n) = O(1) 
     * 
	 * @return true if the list is empty (has no elements); false otherwise.
	 */
	@Override
	public boolean isEmpty() {
		if (first == null){
			return true;
		}
		return false;		
	}

	/**
	 * Indicates if the cursor is at the last element in the list.
	 * Will return false if the list is empty.
	 * 
	 * Operations f(n) = 3
     * Run-time efficiency: T(n) = O(1) 
     * 
	 * @return true if the cursor position is the last element in the list; false otherwise.
	 */
	@Override
	public boolean isLast() {
		if (first == null){
			return false;
		}
		if (cursor.equals(last)){
			return true;
		}
		return false;
	}
	
	/* -------------------------------------------------------------------------------------
	 * Justification
	 * -------------------------------------------------------------------------------------
	 * In the beginning, I implemented a private inner class which is used to store
	 * the node information of LinkedList, such as the next element and the stored
	 * item object.
	 * 
	 * In this class, I implemented single LinkedList. Other types of LinkedList has 
	 * benefit in some operations. For instance, double LinkedList has better run-time
	 * efficiency when looking for previous node. However, Single LinkedList has the 
	 * better space efficiency than other types of LinkedList. In this stage, the 
	 * time efficiency of getPrevious() method is O(n), which is satisfy the 
	 * requirement. The single LinkedList could have a balanced performance on both
	 * run-time & space efficiency.
	 * 
	 * 
	 * -------------------------------------------------------------------------------------
	 */

}
