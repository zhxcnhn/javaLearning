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
 * Space efficiency: S(n) = O(n)
 * Since the space efficiency depends on the numbers of element in the array
 * which would grow linearly depending on how many elements is being put into it.
 * It is up to what n element is.
 *
 * @param <T> The type of element held in the bag.
 */
public class FixedSizeBag<T> implements RemovableBag<T> {
	T[] bagArray;
	private int cursor;
	private int itemCount;

	/**
	 * Create a FixedSizeBag with contents set to null.
	 * 
	 * Operations f(n) = 5 + n
     * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param size Maximum number of items that can be contained in the bag.
	 * @throws IllegalArgumentException
     *             if the size is less than 1
	 */
	public FixedSizeBag(int size) {
		/*
		 * Due to type erasure (https://docs.oracle.com/javase/tutorial/java/generics/erasure.html)
		 * the dynamically allocated array has to be created as an array of Object references.
		 * For type safety this array of Object references is then cast to be an array of references
		 * to the generic type <T> of the actual elements to be held in bagArray.
		 */
		if (size < 1){
			throw new IllegalArgumentException(
                    "The size of Bag cannot be 0 or lesser");
		}
		bagArray = (T[]) new Object[size];
		cursor = -1;
		itemCount = 0;
	}

	/**
	 * Add an item to the bag.
	 * 
	 * Operations f(n) = 5n + 3
     * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param item The item to be added.
	 * @throws NullPointerException if item is null
	 * @return true if item is added to the bag; false if can't be added. 
	 */
	@Override
	public boolean add(T item) throws NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		for  (int i = 0; i < bagArray.length; i++){
			if (bagArray [i] == null){
				bagArray [i] = item;
				itemCount++;
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Remove the item from the bag.
	 * Searches from the current cursor position and removes the first occurrence 
	 * of {@code item} found in the bag.
	 * 
	 * Operations f(n) = 5n + 3
     * Run-time efficiency: T(n) = O(n)
	 * 
	 * @param item The item to be removed.
	 * @throws NullPointerException if item is null
	 * @return true if item is removed from the bag; false if item was not in bag.
	 */
	@Override
	public boolean remove(T item) throws NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		if (itemCount == 0){
			return false;
		}
		for  (int i = 0; i < bagArray.length; i++){
			if (bagArray [i].equals(item)){
				bagArray [i] = null;
				itemCount--;
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the internal cursor to refer to the first item in the bag.
	 * 
	 * Operations f(n) = 5n + 2
     * Run-time efficiency: T(n) = O(n)
     * 
	 * @return The first item or null if bag is empty.
	 */
	@Override
	public T firstItem() {
		if (itemCount == 0){
			return null;
		}
		
		for  (int i = 0; i < bagArray.length; i++){
			if (bagArray [i] != null){
				cursor = i;
				return bagArray [i];
			}
		}
		 return null;
	}

	/**
	 * Move the internal cursor to the next item in the bag.
	 * If the internal cursor refers to the last item, do not 
	 * move the cursor and return {@code null}.
	 * 
	 * Operations f(n) = 5n + 3
     * Run-time efficiency: T(n) = O(n)
     * 
	 * @return The next item or null if there is no next item.
	 */
	@Override
	public T nextItem() {
		for  (int i = cursor + 1; i < bagArray.length; i++){
			if (bagArray [i] != null){
				cursor = i;
				return bagArray [i];
			}
		}
		return null;
	}

	/**
	 * Used to check whether the current item is at last in the bag
	 * 
	 * Operations f(n) = 5n + 4
     * Run-time efficiency: T(n) = O(n)
     * 
	 * @return true if the internal cursor is at the last item in the bag; false otherwise.
	 */
	@Override
	public boolean isLast() {
		if (itemCount == 0){
			return false;
		}
		for (int i = cursor + 1; i < bagArray.length; i++){
			if (bagArray [i] != null){
				return false;
			}
		}
		return true;
	}

	/**
	 * To check the size of the bag
	 * 
	 * Operations f(n) = 1
     * Run-time efficiency: T(n) = O(1)
     * 
	 * @return The number of items in the bag.
	 */
	@Override
	public int size() {
		
		return itemCount;
	}

}

	/* -------------------------------------------------------------------------------------
	 * Justification:
	 * -------------------------------------------------------------------------------------
	 * Since the generic type <T> is used in this class, the array is created to be an
	 * Object array. The Object will be casting to the T to achieve the declared array 
	 * data type.
	 * 
	 * I used an integer variable as the cursor. Every operation relate to cursor 
	 * will cause the change of cursor.
	 * 
	 * The cursor is also a flag to help other method to start accessing bag from the
	 * correct location.
	 * 
	 * I used maximum one loop in each method to ensure a better run-time efficiency.
	 * Run-time efficiency of most of the methods would be O(n). Method such as size()
	 * has a run-time efficiency of O(1).
	 * 
	 * The space efficiency depends on the numbers of element in the array.
	 * It would grow linearly depending on how many elements is being put into it.
	 * 
	 * 
	 * -------------------------------------------------------------------------------------
	 */
