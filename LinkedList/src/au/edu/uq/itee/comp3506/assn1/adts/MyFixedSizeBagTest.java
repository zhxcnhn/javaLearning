package au.edu.uq.itee.comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


/* Implement your additional JUnit tests for FixedSizeBag in this test class. */ 
public class MyFixedSizeBagTest {

	private GameObject [] itemToInsert;
	
	@Before
	public void setup() {
		itemToInsert = new GameObject[4];
		itemToInsert[0] = new GameObject("Item 1 to Insert");
		itemToInsert[1] = new GameObject("Item 2 to Insert");
		itemToInsert[2] = new GameObject("Item 3 to Insert");
		itemToInsert[3] = new GameObject("Item 4 to Insert");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void invalidConstruction() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(0);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullAddItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		bag.add(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullRemoveItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		bag.remove(null);
	}
	
	@Test
	public void testRemoveFirstAddedItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		
		//add items
		bag.add(itemToInsert[0]);
		bag.add(itemToInsert[1]);
		bag.add(itemToInsert[2]);
		
		//remove item 1
		assertThat("First item is removed sucessfully", 
				bag.remove(itemToInsert[0]), is(equalTo(true)));
		assertThat("First item becomes item 2", 
				bag.firstItem(), is(equalTo(itemToInsert[1])));
		assertThat("Size becomes 2", 
				bag.size(), is(equalTo(2)));
		
	}
	
	@Test
	public void testRemoveHalfwayAddedItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		
		//add items
		bag.add(itemToInsert[0]);
		bag.add(itemToInsert[1]);
		bag.add(itemToInsert[2]);
		
		//remove item 2
		assertThat("Item 2 is removed sucessfully", 
				bag.remove(itemToInsert[1]), is(equalTo(true)));
		assertThat("Size becomes 2", 
				bag.size(), is(equalTo(2)));
	}
	
	@Test
	public void testRemoveLastAddedItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		
		//add items
		bag.add(itemToInsert[0]);
		bag.add(itemToInsert[1]);
		bag.add(itemToInsert[2]);
		
		//remove item 3
		assertThat("Item 3 is removed sucessfully", 
				bag.remove(itemToInsert[2]), is(equalTo(true)));
		assertThat("Size becomes 2", 
				bag.size(), is(equalTo(2)));
	}
	
	@Test
	public void testRemoveNoneAddedItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		
		//add items
		bag.add(itemToInsert[0]);
		bag.add(itemToInsert[1]);
		bag.add(itemToInsert[2]);
		
		//remove item 4, which is not in the bag
		assertThat("Item 4 is removed unsucessfully", 
				bag.remove(itemToInsert[3]), is(equalTo(false)));
		assertThat("Size is still 3", 
				bag.size(), is(equalTo(3)));
	}
	
	@Test
	public void testRemoveFromEmptyBag() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		
		//remove item 1 from list
		assertThat("Item is removed unsucessfully from empty bag", 
				bag.remove(itemToInsert[0]), is(equalTo(false)));
		assertThat("Size is 0", 
				bag.size(), is(equalTo(0)));
	}
	
	@Test
	public void testFirstItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		assertThat("The bag is empty", bag.firstItem(), is(equalTo(null)));
		
		//add item 1
		bag.add(itemToInsert[0]);
		//check result
		assertThat("The first is item 1", 
				bag.firstItem(), is(equalTo(itemToInsert[0])));
		assertThat("The next of current cursor is null", 
				bag.nextItem(), is(equalTo(null)));
		
		//add item 2
		bag.add(itemToInsert[1]);
		//check result
		assertThat("The first is item 1", 
				bag.firstItem(), is(equalTo(itemToInsert[0])));
		assertThat("The next of current cursor is item 2", 
				bag.nextItem(), is(equalTo(itemToInsert[1])));
		
		//add item 3
		bag.add(itemToInsert[2]);
		//remove item 1
		bag.remove(itemToInsert[0]);
		//check result
		assertThat("The first is item 2", 
				bag.firstItem(), is(equalTo(itemToInsert[1])));
		assertThat("The next of current cursor is item 3", 
				bag.nextItem(), is(equalTo(itemToInsert[2])));
		
	}
	
	@Test
	public void testNextItem() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(2);
		//test in empty bag
		assertThat("The bag is empty", bag.nextItem(), is(equalTo(null)));
		
		//add item 1
		bag.add(itemToInsert[0]);
		//check result
		bag.firstItem();
		assertThat("The next of first is null", bag.nextItem(), is(equalTo(null)));
		
		//add item 2
		bag.add(itemToInsert[1]);
		//check result
		bag.firstItem();
		assertThat("The next of first is item 2", bag.nextItem(), 
				is(equalTo(itemToInsert[1])));
	}
	
	@Test
	public void testIsLast() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(2);
		//test in empty bag
		assertThat("The bag is empty", bag.isLast(), is(equalTo(false)));
		
		//add item 1
		bag.add(itemToInsert[0]);
		//check result
		bag.firstItem();
		assertThat("The item 1 is at last", bag.isLast(), is(equalTo(true)));
		
		//add item 2
		bag.add(itemToInsert[1]);
		//check result
		assertThat("The item 1 is not at last", bag.isLast(), 
				is(equalTo(false)));
		bag.nextItem();
		assertThat("The item 2 is at last", bag.isLast(), 
				is(equalTo(true)));
	}
	
	@Test
	public void testSize() {
		FixedSizeBag<GameObject> bag = new FixedSizeBag<GameObject>(3);
		//test in empty bag
		assertThat("The bag is empty", bag.size(), is(equalTo(0)));
		
		//add item 1
		bag.add(itemToInsert[0]);
		//check result
		assertThat("The bag have 1 element", bag.size(), is(equalTo(1)));
		
		//add item 2, item 3
		bag.add(itemToInsert[1]);
		bag.add(itemToInsert[2]);
		//check result
		assertThat("The bag have 3 element", bag.size(), is(equalTo(3)));
		
		//remove item 3
		bag.remove(itemToInsert[2]);
		assertThat("The bag have 2 element", bag.size(), is(equalTo(2)));
	}

}
