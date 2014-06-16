package gamelab.city;

import gamelab.GameLab;
import gamelab.citizen.Citizen;
import gamelab.rendering.SpriteRenderer;
import gamelab.rendering.SpriteSheet;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.GameObject;
import com.snakybo.sengine.core.Input;
import com.snakybo.sengine.core.Input.KeyCode;
import com.snakybo.sengine.core.utils.Quaternion;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.core.utils.Vector3f;
import com.snakybo.sengine.rendering.Texture;

/** @author Kevin Krol
 * @since Jun 2, 2014 */
public class Building extends Component {
	public static final SpriteSheet BUILDING_SPRITESHEET = new SpriteSheet(new Texture("buildings.png"), 4, 1);
	
	public static final float BUILDING_LAYER = 50;
	
	public static final int BUILDING_WIDTH = 76;
	public static final int BUILDING_HEIGHT = 61;
	public static final int RADIUS_MULTIPLIER = 10;
	
	private List<Citizen> citizens;
	
	private City city;
	
	private int rawX;
	private int rawY;
	
	private int size;
	
	public Building(City city, int rawX, int rawY) {
		citizens = new ArrayList<Citizen>();
		
		this.city = city;
		this.rawX = rawX;
		this.rawY = rawY;
		
		size = 10;
	}
	
	@Override
	protected void input(float delta) {
		if(Input.getKeyDown(KeyCode.T))
			addCitizen();
	}
	
	/*private void recalculateCitizenRadius() {
		for(Citizen citizen : citizens)
			citizen.recalculateRadius();
	}*/
	
	/** Add citizens to the building */
	public void addCitizen() {
		final Vector3f position = getTransform().getPosition();
		
		GameObject citizenGo = new GameObject();
		Citizen citizen = new Citizen(this);
		
		citizenGo.addComponent(new SpriteRenderer(Citizen.CITIZENS_SPRITESHEET, 0));
		citizenGo.addComponent(citizen);
		
		citizenGo.getTransform().setPosition(new Vector3f(position.getX(), position.getY(), Citizen.CITIZEN_LAYER));
		citizenGo.getTransform().setRotation(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(270)));
		citizenGo.getTransform().setScale(new Vector3f(Citizen.CITIZEN_WIDTH, 0, Citizen.CITIZEN_HEIGHT));
		
		citizenGo.getTransform().rotate(new Vector3f(0, 0, 1), (float)Math.toRadians(180));

		GameLab.instance.addChild(citizenGo);
		
		citizen.recalculateRadius(getTransform().getPosition().getXY().toVector2i());
		
		citizens.add(citizen);
	}
	
	/** @return The raw position of the building */
	public Vector2i getPosition() {
		return new Vector2i(rawX, rawY);
	}
	
	/** @return The city this building belongs to */
	public City getCity() {
		return city;
	}
	
	/** @return The size of the building */
	public int getSize() {
		return size;
	}
}
