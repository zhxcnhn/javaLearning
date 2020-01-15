package au.edu.uq.itee.comp3506.assn1.adts;


/**
 * An ordered sequential list of items.
 * Maintains a 'cursor' that is a reference to an element in the list, providing a mechanism to iterate over the list.
 * The cursor is the position at which non-fixed-positioning operations on the list occur 
 * (e.g. insert, remove, getNext, getPrevious).
 * 
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 * @param <T> The type of element held in the list.
 */
public interface GameList<T> {
	/**
	 * Add an item to the end of the list.
	 * The cursor will refer to the newly added item.
	 * If the list is empty {@code item} becomes the first and last item in the list.
	 * 
	 * @param item The item to be added to the list.
	 */
	void addToEnd(T item);
	
	/**
	 * Insert an item in front of the current cursor position in the list.
	 * The cursor will refer to the newly inserted item.
	 * If the list is empty {@code item} becomes the first and last item in the list.
	 * 
	 * @param item The item to be inserted into the list.
	 */
	void insert(T item);
	
	/**
	 * Removes the item, at the current cursor position, from the list. 
	 * Ensures that the previous item is correctly connected to the next item.
	 * After the removal the cursor will refer to the next item in the list. 
	 * If the removed item was the last element in the list, then the cursor will
	 * refer to the previous element, which is now the last item in the list.
	 * 
	 * @throws IndexOutOfBoundsException If an attempt is made to remove an element from an empty list.
	 */
	void remove() throws IndexOutOfBoundsException;
	
	/**
	 * Move the internal cursor to the first element in the list.
	 * 
	 * @return The item at the first position in the list; null if the list is empty.
	 */
	T getFirst();
	
	/**
	 * Move the internal cursor to the next element in sequential order in the list.
	 * If the cursor is at the end of the list it remains at that position, and returns the item at that position.
	 * 
	 * @return The item at the new cursor position; null if the list is empty.
	 */
	T getNext();
	
	/**
	 * Move the internal cursor to the last element in the list.
	 * 
	 * @return The item at the last position in the list; null if the list is empty.
	 */
	T getLast();
	
	/**
	 * Move the internal cursor to the previous element in sequential order in the list.
	 * If the cursor is at the beginning of the list it remains at that position, and returns the item at that position.
	 * 
	 * @return The item at the new cursor position; null if the list is empty.
	 */
	T getPrevious();
	
	/**
	 * Finds an item in the list, moving the cursor to the item's position in the list.
	 * Starts searching from the beginning of the list, and stops when it finds the first instance of the item in the list.
	 * If the item is not found the cursor remains at the end of the list.
	 * 
	 * @param item The item to be found.
	 * @return true if the item has been found in the list; false otherwise.
	 */
	boolean find(T item);
	
	/**
	 * Indicates if the list is empty or not.
	 * 
	 * @return true if the list is empty (has no elements); false otherwise.
	 */
	boolean isEmpty();
	
	/**
	 * Indicates if the cursor is at the last element in the list.
	 * Will return false if the list is empty.
	 * 
	 * @return true if the cursor position is the last element in the list; false otherwise.
	 */
	boolean isLast();
}
