package au.edu.uq.itee.comp3506.assn1.adts;


/**
 * A two-dimensional grid to hold items in a positional relationship to each other.
 *
 * Space efficiency: S(n) = O(n)
 * Since the space efficiency depends on the numbers of element in the two-dimensional
 * array which would grow linearly depending on how many elements is being put into it.
 * It is up to what n element is.
 * 
 * @param <T> The type of element held in the grid.
 */
public class RectangularGrid<T> implements Grid<T> {
	private T ob;
	private int length;
	private int width;
	private Object [] [] obj;
		
	
	/**
     * Creates a new RectangularGrid 
     * Operations f(n) = 8 + n
     * Run-time efficiency: T(n) = O(n)
     * 
     * @param length
     *            the length of grid
     * @param width
     *            the width of grid
     * @throws IllegalArgumentException
     *             if the length or width is equal or less than 0
     */
	
	public RectangularGrid(int length, int width){
        if (length <= 0 || width <= 0) {
            throw new IllegalArgumentException(
                    "The length or width cannot be"
                    + " equal or less than 0");
        }
		this.length = length;
		this.width = width;
		obj = new Object [length] [width];
	}
	
	/**
	 * Place an item at a fixed position in the grid.
	 * Overwrites whatever was at the position.
	 * Operations f(n) = 12
     * Run-time efficiency: T(n) = O(1)
	 * 
	 * @param item Item to be placed in the grid.
	 * @param x X Coordinate of the position of the item.
	 * @param y Y Coordinate of the position of the item.
	 * @throws ArrayIndexOutOfBoundsException If x or y coordinates are out of bounds.
	 * @throws NullPointerException if item is null
	 */
	public void place(T item, int x, int y) throws ArrayIndexOutOfBoundsException, NullPointerException{
		if (item==null){ 
			throw new NullPointerException("Item cannot be null.");
		  }
		if (x < 0 || y < 0 || x > (length - 1) || y > (width - 1)) {
            throw new ArrayIndexOutOfBoundsException(
            		"x or y coordinates are out of bounds");
        }
		obj [x] [y] = item;
	}
	
	/**
	 * Return the item at the indicated position.
	 * Operations f(n) = 12
     * Run-time efficiency: T(n) = O(1)
	 * 
	 * @param x X Coordinate of the position of the item.
	 * @param y Y Coordinate of the position of the item.
	 * @return Item at this position or null.
	 * @throws ArrayIndexOutOfBoundsException If x or y coordinates are out of bounds.
	 */
	public T get(int x, int y) throws ArrayIndexOutOfBoundsException{
		if (x < 0 || y < 0 || x > (length - 1) || y > (width - 1)) {
            throw new ArrayIndexOutOfBoundsException(
                    "x or y coordinates are out of bounds");
        }		
		
		return (T) obj [x] [y];
	}

}

	/* -------------------------------------------------------------------------------------
	 * Justification:
	 * -------------------------------------------------------------------------------------
	 * In this class, I used two-dimensional array to implement the grid. Since we
	 * are not allow to used java collection framework in this assignment, basic array
	 * could be the possible solution. It is just like to put an array into another 
	 * array.
	 * 
	 * The logic of this class is quite simple. I used the two parameters, length and width
	 * to create the two-dimensional array when the class is initialized.
	 * 
	 * Since we used generic in this class, the type of array will be Object.
	 * 
	 * I used if statement to check the condition of Exceptions.
	 * 
	 * The class has a good run-time efficiency which is O(n) for the constructor,
	 * O(1) for rest of method. The space efficiency would grow linearly depending 
	 * on how many elements is being put into it.It could be O(n).
	 * 
	 * -------------------------------------------------------------------------------------
	 * Limitations of RectangularGrid:
	 * -------------------------------------------------------------------------------------
	 * There are some limitations in this class. Since I used array to store data.
	 * However, if a RectangularGrid object is created, but very least of items 
	 * are place into the grid. The size is fixed since it has been initialized.
	 * In this situation, the space is wasted. Meanwhile, since the size is fixed,
	 * item, the size could not be changed after initialization.
	 * 
	 * Possible alternative solution --> I prefer to use two-dimensional ArrayList.
	 * Since the size of ArrayList is dynamics, it will not need be declare the size
	 * at first. Meanwhile, compare to other list, ArrayList has better time efficiency
	 * when doing get element and add element. And it also has a better space efficiency.
	 * 
	 * --------------------------------------------------------------------------------------
	 */
