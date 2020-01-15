package au.edu.uq.itee.comp3506.assn1.gameworld;

import au.edu.uq.itee.comp3506.assn1.adts.RemovableBag;
import au.edu.uq.itee.comp3506.assn1.adts.FixedSizeBag;


public class Player extends GameObject {
	private final int NUM_ITEMS = 5;
	private RemovableBag<GameObject> sack = new FixedSizeBag<GameObject>(NUM_ITEMS);
	
	public Player(String description) {
		super(description);
	}
	
	public void pickUpItem(GameObject item) {
		sack.add(item);
	}
	
	public RemovableBag<GameObject> itemsInSack() {
		return sack;
	}
}
