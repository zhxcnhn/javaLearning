package au.edu.uq.itee.comp3506.assn1.adts;


/**
 * A simple collection that holds items and provides access to each item.
 * There is an implied ordering of elements to allow iteration through the items in the bag,
 * but there is no guarantee that the order in which items are added will reflect the actual
 * order of how they are stored, or accessed during iteration through the collection.
 * 
 * <p>The bag maintains a 'cursor' that is a reference to an element in the bag, 
 * providing a mechanism to iterate over the collection via {@code firstItem} and {@code nextItem}.
 * <blockquote><pre><code>
 * {@code if (bag.size() > 0)} {
 *      item = bag.firstItem();
 *      while (!bag.isLast()) {
 *          // Make use of item.
 *          item = bag.nextItem();
 *      }
 *  }
 * </blockquote></code></pre></p>
 * 
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 * @param <T> The type of element held in the bag.
 */
public interface RemovableBag<T> {
	/**
	 * Add an item to the bag.
	 * @param item The item to be added.
	 * @return true if item is added to the bag; false if can't be added. 
	 */
	boolean add(T item);
	
	/**
	 * Remove the item from the bag.
	 * Searches from the current cursor position and removes the first occurrence of {@code item} found in the bag.
	 * @param item The item to be removed.
	 * @return true if item is removed from the bag; false if item was not in bag.
	 */
	boolean remove(T item);
	
	/**
	 * Set the internal cursor to refer to the first item in the bag.
	 * @return The first item or null if bag is empty.
	 */
	T firstItem();
	
	/**
	 * Move the internal cursor to the next item in the bag.
	 * If the internal cursor refers to the last item, do not move the cursor and return {@code null}.
	 * @return The next item or null if there is no next item.
	 */
	T nextItem();
	
	/**
	 * @return true if the internal cursor is at the last item in the bag; false otherwise.
	 */
	boolean isLast();
	
	/**
	 * @return The number of items in the bag.
	 */
	int size();
}
