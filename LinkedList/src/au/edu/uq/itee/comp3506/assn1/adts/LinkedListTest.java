package au.edu.uq.itee.comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;

import org.junit.Test;

public class LinkedListTest {
	private LinkedList<GameObject> list;
	
	@Before
	public void createEmptyLinkedList() {
		list = new LinkedList<GameObject>();
	}
	
	@Test
	public void newListIsEmpty() {
		assertThat("A newly created list is not empty.", list.isEmpty(), is(equalTo(true)));
	}

	@Test
	public void addOneItem() {
		GameObject itemToAdd = new GameObject("Item to be Added");
		list.addToEnd(itemToAdd);
		assertThat("Adding an item resulted in an empty list.", list.isEmpty(), is(equalTo(false)));
		assertThat("Adding one item did not result in it being the last item.", list.isLast(), is(equalTo(true)));
		assertThat("The only item added to the list is not the first item in the list.", 
		           list.getFirst(), is(equalTo(itemToAdd)));
		assertThat("The only item added to the list is not the last item in the list.", 
		           list.getLast(), is(equalTo(itemToAdd)));
	}
	
	@Test
	public void insertOneItem() {
		GameObject itemToInsert = new GameObject("Item to be Inserted");
		list.insert(itemToInsert);
		assertThat("Inserting an item resulted in an empty list.", list.isEmpty(), is(equalTo(false)));
		assertThat("Inserting one item did not result in it being the last item.", list.isLast(), is(equalTo(true)));
		assertThat("The only item inserted into the list is not the first item in the list.", 
		           list.getFirst(), is(equalTo(itemToInsert)));
		assertThat("The only item inserted into the list is not the last item in the list.", 
		           list.getLast(), is(equalTo(itemToInsert)));
	}
	
	@Test
	public void addTwoItems() {
		GameObject item1ToAdd = new GameObject("Item 1");
		GameObject item2ToAdd = new GameObject("Item 2");
		list.addToEnd(item1ToAdd);
		list.addToEnd(item2ToAdd);
		assertThat("Adding multiple items to end of list did not result in the last one being the last item.", 
		           list.isLast(), is(equalTo(true)));
		assertThat("The first item added to the list is not the first item in the list.", 
		           list.getFirst(), is(equalTo(item1ToAdd)));
		assertThat("The last item added to the list is not the last item in the list.", 
		           list.getLast(), is(equalTo(item2ToAdd)));
	}
	
	@Test
	public void insertTwoItems() {
		GameObject item1ToInsert = new GameObject("Item 1");
		GameObject item2ToInsert = new GameObject("Item 2");
		list.insert(item1ToInsert);
		list.insert(item2ToInsert);
		assertThat("Inserting multiple items into an empty list resulted in the cursor refering to the end of the list.", 
		           list.isLast(), is(equalTo(false)));
		assertThat("The first item inserted into an empty list is not the last item in the list.", 
		           list.getLast(), is(equalTo(item1ToInsert)));
		assertThat("The last item inserted into an empty list is not the first item in the list.", 
		           list.getFirst(), is(equalTo(item2ToInsert)));
	}
}
