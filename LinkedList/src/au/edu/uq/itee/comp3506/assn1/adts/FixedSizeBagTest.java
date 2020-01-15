package au.edu.uq.itee.comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


/**
 * Test driver for the FixedSizeBag.
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 */
public class FixedSizeBagTest {
	private FixedSizeBag<GameObject> bag;
	
	@Before
	public void setupFixedSizeBag() {
		bag = new FixedSizeBag<GameObject>(3);
	}
	
	@Test
	public void newBagIsEmpty() {
		assertThat("A newly created bag does not have a size of 0.", bag.size(), is(equalTo(0)));
	}

	@Test
	public void addOneItem() {
		GameObject itemToAdd = new GameObject("Item to be Added");
		assertThat("Item not successfully added to an empty bag.", bag.add(itemToAdd), is(equalTo(true)));
		assertThat("Bag size is not 1 after adding 1 item.", bag.size(), is(equalTo(1)));
		assertThat("The only item added to the bag is not the first item in the bag.", 
		           bag.firstItem(), is(equalTo(itemToAdd)));
	}
	
	@Test
	public void addMultipleItems() {
		GameObject item1ToAdd = new GameObject("Item 1 to be Added");
		GameObject item2ToAdd = new GameObject("Item 2 to be Added");
		bag.add(item1ToAdd);
		bag.add(item2ToAdd);
		assertThat("Bag size is not 2 after adding 2 items.", bag.size(), is(equalTo(2)));
	}
	
	@Test
	public void overFillBag() {
		GameObject item1ToAdd = new GameObject("Item 1 to be Added");
		GameObject item2ToAdd = new GameObject("Item 2 to be Added");
		GameObject item3ToAdd = new GameObject("Item 3 to be Added");
		GameObject itemTooManyToAdd = new GameObject("Item that should not be Added");
		bag.add(item1ToAdd);
		bag.add(item2ToAdd);
		bag.add(item3ToAdd);
		assertThat("Added more items than maximum size of bag.", bag.add(itemTooManyToAdd), is(equalTo(false)));
		assertThat("Bag size is not 3 after over filling a bag with a maximum size of 3.", bag.size(), is(equalTo(3)));
	}
}
