package au.edu.uq.itee.comp3506.assn1.adts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


/* Implement your additional JUnit tests for LinkedList in this test class. */ 
public class MyLinkedListTest {

	private GameObject [] itemToInsert;
	
	@Before
	public void setup() {
		itemToInsert = new GameObject[4];
		itemToInsert[0] = new GameObject("Item 1 to Insert");
		itemToInsert[1] = new GameObject("Item 2 to Insert");
		itemToInsert[2] = new GameObject("Item 3 to Insert");
		itemToInsert[3] = new GameObject("Item 4 to Insert");
	}
	
	@Test (expected = NullPointerException.class)
	public void nullAddedItem() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		list.addToEnd(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullInsertItem() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		list.insert(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void nullFindItem() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		list.find(null);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void invalidRemove() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		list.remove();
	}
	
	@Test
	public void testRemove() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		list.addToEnd(itemToInsert[0]);
		list.addToEnd(itemToInsert[1]);
		list.addToEnd(itemToInsert[2]);
		list.addToEnd(itemToInsert[3]);
		
		
		//item 4 is removed
		list.remove();
		//check result
		assertThat("One item is left in list", list.isEmpty(), is(equalTo(false)));
		assertThat("Cursor should refer to previous item, which is now the last "
				+ "item in the list", list.isLast(), is(equalTo(true)));
		assertThat("Item 3 should become the last item now", list.getLast(), 
				is(equalTo(itemToInsert[2])));
		
		list.getFirst();
		//First element item 1 is removed, cursor become item 2
		list.remove();
		
		//check result
		assertThat("Cursor refer to next item which is item3",
				list.getNext(), is(equalTo(itemToInsert[2])));
		assertThat("Item 2 should become the first one in the list", list.getFirst(), 
				is(equalTo(itemToInsert[1])));		
		
	}
	
	@Test
	public void testGetFirst() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check first item of an empty list
		GameObject game = list.getFirst();
		assertThat("Empty list, first item should return null", game, 
				is(equalTo(null)));
		
		//add item into list
		list.addToEnd(itemToInsert[0]);
		list.addToEnd(itemToInsert[1]);
		list.addToEnd(itemToInsert[2]);
		//retrieve first item from list
		game = list.getFirst();
		//check result correctness of cursor and returned value
		assertThat("First element item 1 should be returned", game, 
				is(equalTo(itemToInsert[0])));
		assertThat("Cursor should become the first position in the list, "
				+ "the next item should be item 2", list.getNext(), 
				is(equalTo(itemToInsert[1])));
		
	}
	
	@Test
	public void testGetNext() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check next item of an empty list
		GameObject game = list.getNext();
		assertThat("Empty list, next item should return null", game, 
				is(equalTo(null)));
				
		//insert item into list (first element will be item 3)
		list.insert(itemToInsert[0]);
		list.insert(itemToInsert[1]);
		list.insert(itemToInsert[2]);
		
		//check result correctness
		assertThat("Next element of item 3 should be item 2", list.getNext(), 
				is(equalTo(itemToInsert[1])));
		assertThat("Next element of item 2 should be item 1", list.getNext(), 
				is(equalTo(itemToInsert[0])));
		assertThat("Next element of item 1 (already last of list) should be "
				+ "still item 1", list.getNext(), is(equalTo(itemToInsert[0])));

		
	}
	
	@Test
	public void testGetLast() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check last item of an empty list
		GameObject game = list.getLast();
		assertThat("Empty list, last item should return null", game, 
				is(equalTo(null)));
				
		//insert item into list (first element will be item 3)
		list.insert(itemToInsert[0]);
		list.insert(itemToInsert[1]);
		list.insert(itemToInsert[2]);
		
		//check result correctness
		assertThat("Last element item 1 should be returned",list.getLast(), 
				is(equalTo(itemToInsert[0])));
		assertThat("Cursor should become the last position in the list, "
				+ "the previous item should be item 2", list.getPrevious(), 
				is(equalTo(itemToInsert[1])));
		
	}
	
	@Test
	public void testGetPrevious() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check previous item of an empty list
		GameObject game = list.getPrevious();
		assertThat("Empty list, previous item should return null", game, 
				is(equalTo(null)));
				
		//insert item into list (first element will be item 3)
		list.insert(itemToInsert[0]);
		list.insert(itemToInsert[1]);
		list.insert(itemToInsert[2]);
		
		//check result correctness
		assertThat("Previous element of item 3 (already beginning of list) should be "
				+ "still item 3", list.getPrevious(), is(equalTo(itemToInsert[2])));
		//cursor come to the last of list
		list.getLast();
		assertThat("Previous element of item 1 should be item 2", list.getPrevious(), 
				is(equalTo(itemToInsert[1])));
		assertThat("Previous element of item 2 should be item 3", list.getPrevious(), 
				is(equalTo(itemToInsert[2])));
		
		
	}
	
	@Test
	public void testFind() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check an empty list
		assertThat("Empty list, result of find item 1 should return false", 
				list.find(itemToInsert[0]), is(equalTo(false)));	
				
		//add item 1 into list
		list.addToEnd(itemToInsert[0]);
		
		//Cannot find item 2, list with 1 element
		assertThat("List with 1 element, result of find item 2 should return false", 
				list.find(itemToInsert[1]), is(equalTo(false)));
		assertThat("Cursor should at last", 
				list.isLast(), is(equalTo(true)));
		
		
		//Able to find item 1, list with 1 element
		assertThat("List with 1 element, result of find item 1 should return true", 
				list.find(itemToInsert[0]), is(equalTo(true)));
		assertThat("Cursor is item 1. Next item is still item 1", 
				list.getNext(), is(equalTo(itemToInsert[0])));	
		
		//add item 2, item 3 into list
		list.addToEnd(itemToInsert[1]);
		list.addToEnd(itemToInsert[2]);
		
		//Cannot find item 4, list with 3 element
		assertThat("List with 3 element, result of find item 4 should return false", 
				list.find(itemToInsert[3]), is(equalTo(false)));
		assertThat("Cursor should at last", 
				list.isLast(), is(equalTo(true)));
		//Able to find item 1, list with 3 element
		assertThat("List with 3 element, result of find item 1 should return true", 
				list.find(itemToInsert[0]), is(equalTo(true)));
		assertThat("Cursor is item 1. Next item is item 2", 
				list.getNext(), is(equalTo(itemToInsert[1])));
		//Able to find item 2, list with 3 element
		assertThat("List with 3 element, result of find item 2 should return true", 
				list.find(itemToInsert[1]), is(equalTo(true)));
		assertThat("Cursor is item 2. Next item is item 3", 
				list.getNext(), is(equalTo(itemToInsert[2])));
	}
	
	@Test
	public void testIsEmpty() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check an empty list
		assertThat("Empty list, returned is true", list.isEmpty(), 
				is(equalTo(true)));
				
		//insert item into list
		list.insert(itemToInsert[0]);		
		
		//check result correctness
		assertThat("List with 1 element, returned is false", list.isEmpty(), 
				is(equalTo(false)));
		
		//remove element from list
		list.remove();
		
		//check the list again
		assertThat("Become empty list again, returned is true", list.isEmpty(), 
				is(equalTo(true)));
	
	}
	
	@Test
	public void testIsLast() {
		LinkedList<GameObject> list = new LinkedList<GameObject>();
		
		//check an empty list
		assertThat("Should return false because the list is empty", list.isLast(), 
				is(equalTo(false)));
						
		//insert item into list
		list.insert(itemToInsert[0]);
		list.insert(itemToInsert[1]);
		list.insert(itemToInsert[2]);
		
		//cursor is at first element item 3, check result
		assertThat("This is First element", list.isLast(), 
				is(equalTo(false)));
		
		//cursor become last
		list.getLast();
		//check result
		assertThat("This is last element", list.isLast(), 
				is(equalTo(true)));
	}

}
