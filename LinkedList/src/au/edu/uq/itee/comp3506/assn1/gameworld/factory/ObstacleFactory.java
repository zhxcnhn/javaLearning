package au.edu.uq.itee.comp3506.assn1.gameworld.factory;

import au.edu.uq.itee.comp3506.assn1.gameworld.GameObject;


public class ObstacleFactory extends GameObjectFactory {

	@Override
	public GameObject createRandomGameObject() {
		return new GameObject("Obstacle");
	}

}
