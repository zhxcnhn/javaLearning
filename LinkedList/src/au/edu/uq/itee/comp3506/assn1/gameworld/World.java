package au.edu.uq.itee.comp3506.assn1.gameworld;

import au.edu.uq.itee.comp3506.assn1.adts.GameList;
import au.edu.uq.itee.comp3506.assn1.adts.LinkedList;


public class World {
	private GameList<Room> rooms = new LinkedList<Room>();
	
	public void createLevel(int level) {
		final int ROOMS_PER_LEVEL = 2;
		for (int i=0; i<level*ROOMS_PER_LEVEL; i++){
			rooms.addToEnd(new Room());
		}
	}
	
	@Override
	public String toString() {
		int roomNumber = 0;
		StringBuilder gameStr = new StringBuilder();
		
		gameStr.append("Room ").append(++roomNumber).append('\n');
		gameStr.append(rooms.getFirst());
		while (!rooms.isLast()) {
			gameStr.append("\nRoom ").append(++roomNumber).append('\n');
			gameStr.append(rooms.getNext());
		}
		
		return gameStr.toString();
	}
}
