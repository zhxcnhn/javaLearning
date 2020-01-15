package au.edu.uq.itee.comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


/* Implement your additional JUnit tests for RectangularGrid in this test class. */ 
public class MyRectangularGridTest {

	private GameObject [] itemToInsert;
	
	@Before
	public void setup() {
		itemToInsert = new GameObject[3];
		itemToInsert[0] = new GameObject("Item 1 to Insert");
		itemToInsert[1] = new GameObject("Item 2 to Insert");
		itemToInsert[2] = new GameObject("Item 3 to Insert");
	}

	@Test
	public void boundaryValueConstruction() {
		RectangularGrid<GameObject> testGrid = new RectangularGrid<GameObject>(2, 3);
		
		GameObject itemRetrieved1;
		GameObject itemRetrieved2;
		testGrid.place(itemToInsert[0], 0, 0);
		itemRetrieved1 = testGrid.get(0, 0);
		testGrid.place(itemToInsert[1], 1, 2);
		itemRetrieved2 = testGrid.get(1, 2);
		assertThat("Item retrieved does not match item inserted at the same position", 
			    itemToInsert[0], is(equalTo(itemRetrieved1)));
		assertThat("Item retrieved does not match item inserted at the same position", 
			    itemToInsert[1], is(equalTo(itemRetrieved2)));
	}
	
	@Test (expected = ArrayIndexOutOfBoundsException.class)
	public void invalidUpdateCoordinates() {
		RectangularGrid<GameObject> testGrid = new RectangularGrid<GameObject>(3, 4);
		testGrid.place(itemToInsert[0], 4, 5);
	}
	
	@Test (expected = NullPointerException.class)
	public void invalidUpdateItem() {
		RectangularGrid<GameObject> testGrid = new RectangularGrid<GameObject>(3, 4);
		testGrid.place(null, 1, 1);
	}
	
	@Test
	public void testPlaceGetMethod() {
		RectangularGrid<GameObject> testGrid = new RectangularGrid<GameObject>(3, 4);
		
		//place item into the positions
		testGrid.place(itemToInsert[0], 0, 0);
		testGrid.place(itemToInsert[1], 1, 1);
		
		//check result
		Assert.assertEquals(itemToInsert[0], testGrid.get(0, 0));
		Assert.assertEquals(itemToInsert[1], testGrid.get(1, 1));
		
		//overwrite
		testGrid.place(itemToInsert[2], 1, 1);
		//check overwrite
		Assert.assertEquals(itemToInsert[2], testGrid.get(1, 1));

	}
	


}
