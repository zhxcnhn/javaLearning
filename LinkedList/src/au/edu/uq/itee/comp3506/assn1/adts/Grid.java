package au.edu.uq.itee.comp3506.assn1.adts;


/**
 * A two-dimensional grid to hold items in a positional relationship to each other.
 * 
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 * @param <T> The type of element held in the grid.
 */
public interface Grid<T> {
	/**
	 * Place an item at a fixed position in the grid.
	 * Overwrites whatever was at the position.
	 * 
	 * @param item Item to be placed in the grid.
	 * @param x X Coordinate of the position of the item.
	 * @param y Y Coordinate of the position of the item.
	 * @throws IndexOutOfBoundsException If x or y coordinates are out of bounds.
	 */
	void place(T item, int x, int y) throws IndexOutOfBoundsException;
	
	/**
	 * Return the item at the indicated position.
	 * 
	 * @param x X Coordinate of the position of the item.
	 * @param y Y Coordinate of the position of the item.
	 * @return Item at this position or null.
	 * @throws IndexOutOfBoundsException If x or y coordinates are out of bounds.
	 */
	T get(int x, int y) throws IndexOutOfBoundsException;
}
