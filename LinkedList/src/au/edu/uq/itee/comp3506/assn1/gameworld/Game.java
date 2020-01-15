package au.edu.uq.itee.comp3506.assn1.gameworld;


public class Game {

	public static void main(String[] args) {
		World world = new World();
		world.createLevel(1);
		System.out.println(world);
	}

}
