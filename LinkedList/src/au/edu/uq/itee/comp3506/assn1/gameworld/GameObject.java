package au.edu.uq.itee.comp3506.assn1.gameworld;


/**
 * Superclass of all entities that can be placed in rooms and interacted with by the Player.
 * 
 * @author Richard Thomas <richard.thomas@uq.edu.au>
 *
 */
public class GameObject {
	private String description;
	
	public GameObject(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return description;
	}
}
