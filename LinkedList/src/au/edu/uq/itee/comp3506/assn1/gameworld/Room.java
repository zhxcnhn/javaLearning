package au.edu.uq.itee.comp3506.assn1.gameworld;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

import au.edu.uq.itee.comp3506.assn1.adts.Grid;
import au.edu.uq.itee.comp3506.assn1.adts.RectangularGrid;
import au.edu.uq.itee.comp3506.assn1.gameworld.factory.GameObjectFactory;
import au.edu.uq.itee.comp3506.assn1.gameworld.factory.ObstacleFactory;
import au.edu.uq.itee.comp3506.assn1.gameworld.factory.WeaponFactory;
import au.edu.uq.itee.comp3506.assn1.gameworld.factory.EnemyFactory;
import au.edu.uq.itee.comp3506.assn1.gameworld.factory.LootFactory;


/**
 * A room in which the player can interact with game objects and enemies.
 * Game objects and enemies are randomly placed throughout the room.
 * The room is a rectangular grid. Players enter the room at coordinate (0, 0).
 * Players exit through a door at coordinate (length-1, width-1).
 * 
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 */
public class Room {
	private Grid<GameObject> floorPlan;
	private int length;
	private int width;
	private final int MAX_DIMENSION = 12;
	private final int MIN_DIMENSION = 4;

	/**
	 * Generate a room of a constrained random size with a constrained random number of game objects 
	 * and enemies populated in the room.
	 */
	public Room() {
		Random randomNumberGenerator = new Random();
		length = generateDimension(randomNumberGenerator);
		width = generateDimension(randomNumberGenerator);
		int roomSize = length * width;
		int numObstacles = randomNumberGenerator.nextInt(roomSize/4);
		int numEnemies = randomNumberGenerator.nextInt(roomSize/8) + 1;
		int numWeapons = randomNumberGenerator.nextInt(roomSize/12);
		int numLoot = randomNumberGenerator.nextInt(roomSize/12);
		floorPlan = new RectangularGrid<GameObject>(length, width);
		generateContents(numObstacles, numEnemies, numWeapons, numLoot);
	}
	
	private void generateContents(int numObstacles, int numEnemies, int numWeapons, int numLoot) {
		List<Integer> layoutPositions = generatePositions();
		generateElements(new ObstacleFactory(), layoutPositions, numObstacles, 0);
		generateElements(new EnemyFactory(), layoutPositions, numEnemies, numObstacles);
		generateElements(new WeaponFactory(), layoutPositions, numWeapons, numObstacles+numEnemies);
		generateElements(new LootFactory(), layoutPositions, numLoot, numObstacles+numEnemies+numWeapons);
	}

	/**
	 * Generate a random list of integers that correspond to coordinate positions in the room.
	 * The first and last coordinates are not used as these are fixed locations for the entrance to, 
	 * and exit from, the room.
	 * 
	 * @return Random list of integer values that can be used to generate a valid coordinate position in the room.
	 */
	private List<Integer> generatePositions() {
		List<Integer> positions = new ArrayList<Integer>(length*width);
		for (int i=1; i<length*width-1; i++) {
			positions.add(i);
		}
		Collections.shuffle(positions);
		return positions;
	}

	/**
	 * Generate random GameObjects and place them in random positions in the room.
	 * 
	 * @param elementFactory  Factory used to generate random game objects of some type.
	 * @param layoutPositions List of random integer values that can be used to determine valid positions in the room.
	 * @param numElements     Number of this type of game object to put into this room.
	 * @param offset          Used to ensure that layoutPositions are not reused for different types of game objects.
	 */
	private void generateElements(GameObjectFactory elementFactory, List<Integer> layoutPositions, 
			                      int numElements, int offset) {
		for (int i=0; i<numElements; i++) {
			int xCoord = layoutPositions.get(i+offset) / width;
			int yCoord = layoutPositions.get(i+offset) % width;
			floorPlan.place(elementFactory.createRandomGameObject(), xCoord, yCoord);
		}
	}

	private int generateDimension(Random randomNumberGenerator) {
		int dimension = randomNumberGenerator.nextInt(MAX_DIMENSION);
		if (dimension < MIN_DIMENSION) {
			return MIN_DIMENSION;
		} else {
			return dimension;
		}
	}
	
	/**
	 * Simple textual layout representation of the room and its contents.
	 */
	@Override
	public String toString() {
		StringBuilder roomStr = new StringBuilder(length*width+length);
		
		for (int i=0; i<length; i++) {
			for (int j=0; j<width; j++) {
				if (floorPlan.get(i, j) == null) {
					roomStr.append('.');
				} else if (floorPlan.get(i, j).toString().equals("Obstacle")) {
					roomStr.append('*');
				} else if (floorPlan.get(i, j).toString().equals("Enemy")) {
					roomStr.append('E');
				} else if (floorPlan.get(i, j).toString().equals("Weapon")) {
					roomStr.append('W');
				} else if (floorPlan.get(i, j).toString().equals("Gold")) {
					roomStr.append('$');
				}
			}
			roomStr.append('\n');
		}
		
		return roomStr.toString();
	}
	
	public static void main(String[] args) {
		Room room = new Room();
		System.out.println(room);
	}
}
