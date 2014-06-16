package gamelab.city;

import gamelab.GameLab;
import gamelab.rendering.SpriteRenderer;
import gamelab.world.World;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine.core.GameObject;
import com.snakybo.sengine.core.utils.Quaternion;
import com.snakybo.sengine.core.utils.Vector3f;

/** @author Kevin Krol
 * @since Jun 2, 2014 */
public class City {
	private static List<City> cities = new ArrayList<City>();
	
	private List<Building> buildings;
	
	private World world;
	
	public City(World world) {
		buildings = new ArrayList<Building>();
		
		this.world = world;
		
		cities.add(this);
	}
	
	/** Add a building to this city */
	public void addBuilding() {
		GameObject buildingGo = new GameObject();
		Building building = new Building(this, 2, 2);
		
		buildingGo.addComponent(new SpriteRenderer(Building.BUILDING_SPRITESHEET, 0));
		buildingGo.addComponent(building);
		
		buildingGo.getTransform().setPosition(new Vector3f(5 * Building.BUILDING_WIDTH - (Building.BUILDING_WIDTH / 2), 5 * Building.BUILDING_HEIGHT - (Building.BUILDING_HEIGHT / 2), Building.BUILDING_LAYER));
		buildingGo.getTransform().setRotation(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(270)));
		buildingGo.getTransform().setScale(new Vector3f(Building.BUILDING_WIDTH, 0, Building.BUILDING_HEIGHT));
		
		buildingGo.getTransform().rotate(new Vector3f(0, 0, 1), (float)Math.toRadians(180));

		GameLab.instance.addChild(buildingGo);
		
		buildings.add(building);
		
		building.addCitizen();
	}
	
	/** @return The world this building is in */
	public World getWorld() {
		return world;
	}
	
	/** @return The size of this city */
	public int getSize() {
		return buildings.size();
	}
}
