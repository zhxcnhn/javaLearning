package au.edu.uq.itee.comp3506.assn1.adts;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


/**
 * Test driver for the RectangularGrid.
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 */
public class RectangularGridTest {
	private RectangularGrid<GameObject> grid;

	/**
	 * Create a small RectangularGrid to be used for testing.
	 * The grid is 3 cells in length and 4 cells in width.
	 */
	@Before
	public void setupRectangularGrid() {
		grid = new RectangularGrid<GameObject>(3, 4);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidConstruction() {
		new RectangularGrid<GameObject>(0, 0);
	}

	@Test (expected = ArrayIndexOutOfBoundsException.class)
	public void invalidGridAccess() {
		grid.get(4, 3);
	}

	@Test
	public void validGridAccess() {
		GameObject itemToInsert = new GameObject("Item to Insert");
		GameObject itemRetrieved;
		grid.place(itemToInsert, 0, 0);
		itemRetrieved = grid.get(0, 0);
		assertThat("Item retrieved does not match item inserted at the same position", 
				    itemToInsert, is(equalTo(itemRetrieved)));
	}
}
