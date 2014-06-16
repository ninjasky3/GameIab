package gamelab.citizen;

import gamelab.citizen.item.Item;
import gamelab.city.Building;
import gamelab.player.Data;
import gamelab.rendering.SpriteSheet;
import gamelab.resource.Resource;
import gamelab.tile.Tile;
import gamelab.world.World;

import java.util.ArrayList;
import java.util.List;

import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.utils.Vector2f;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.core.utils.Vector3f;
import com.snakybo.sengine.rendering.Texture;

/** @author Kevin Krol
 * @since Jun 2, 2014 */
public class Citizen extends Component {
	public static final int FLAG_IS_AT_HOME = 0x00;
	public static final int FLAG_RETURN_TO_HOME = 0x01;
	public static final int FLAG_FIND_RESOURCE = 0x02;
	public static final int FLAG_MOVE_TO_RESOURCE = 0x03;
	public static final int FLAG_GATHER_RESOURCE = 0x04;
	public static final int FLAG_STORE_RESOURCE = 0x05;
	public static final int FLAG_PLANT_RESOURCE = 0x06;
	
	public static final SpriteSheet CITIZENS_SPRITESHEET = new SpriteSheet(new Texture("citizens.png"), 13, 1);
	
	public static final float CITIZEN_LAYER = 100;
	
	public static final int CITIZEN_WIDTH = 18;
	public static final int CITIZEN_HEIGHT = 23;
	
	private List<Tile> availableTiles;
	
	private Tile targetTile;
	private Building home;
	private Item item;
	
	private float attempts;
	
	private int flag;
	
	private boolean harvest;
	
	public Citizen(Building home) {
		availableTiles = new ArrayList<Tile>();
		
		this.home = home;
		
		flag = FLAG_IS_AT_HOME;
		attempts = 0;
	}
	
	@Override
	protected void update(float delta) {
		if(flag == FLAG_IS_AT_HOME)
			isAtHome(delta);
		
		if(flag == FLAG_RETURN_TO_HOME)
			returnToHome();
		
		if(flag == FLAG_FIND_RESOURCE)
			findResource();
		
		if(flag == FLAG_MOVE_TO_RESOURCE)
			moveToResource();
		
		if(flag == FLAG_GATHER_RESOURCE)
			gatherResource();
		
		if(flag == FLAG_STORE_RESOURCE)
			storeResource();
		
		if(flag == FLAG_PLANT_RESOURCE)
			plantResource();
	}
	
	/** Recalculate the radius of this citizen */
	public void recalculateRadius(Vector2i center) {
		availableTiles.clear();
		
		final int startX = -home.getSize() + 1;
		final int startY = -home.getSize() + 1;
		final int endX = home.getSize() + 2;
		final int endY = home.getSize() + 2;
		
		final World world = home.getCity().getWorld();
		
		for(int x = startX; x < endX; x++) {
			for(int y = startY; y < endY; y++) {
				int xPos = center.getX() + (x * Tile.TILE_WIDTH) + home.getPosition().getX();
				int yPos = center.getY() + (y * Tile.TILE_HEIGHT) + home.getPosition().getY();
				
				availableTiles.add(world.getTileAt(xPos, yPos));
			}
		}
	}
	
	/** Run as long as the citizen is at home */
	private void isAtHome(float delta) {
		attempts += delta;

		if(attempts >= 2.5f) {
			attempts = 0;
			flag = FLAG_FIND_RESOURCE;
		}
	}
	
	/** Run as long as the citizen should return to home */
	private void returnToHome() {
		if(targetTile != null)
			targetTile.stopUsing();
		
		final Vector2f position = getTransform().getPosition().getXY();
		final Vector2f homePosition = home.getTransform().getPosition().getXY();
		
		if(position.distance(homePosition) > 2) {
			final Vector3f target = homePosition.sub(position).normalize().mul(1).toVector3f();
			target.setZ(0);
			
			getTransform().translate(target);
		} else {
			flag = (item == null) ? FLAG_IS_AT_HOME : FLAG_STORE_RESOURCE;
		}
	}
	
	/** Run as long as the citizen should find a resource */
	private void findResource() {
		List<Tile> selectable = new ArrayList<Tile>();
		Tile tileToHarvest = null;		
		
		targetTile = null;
		harvest = false;
		
		recalculateRadius(home.getTransform().getPosition().getXY().toVector2i());
		
		for(Tile tile : availableTiles)
			if(!tile.isBeingUsed())
				if(tile.getTileId() != Tile.DIRT)
					selectable.add(tile);
		
		for(Tile tile : selectable) {
			if(tile.getResource() == null) {
				targetTile = tile;
				break;
			} else {
				if(tile.getResource().canHarvest())
					tileToHarvest = tile;
			}
		}
		
		if(targetTile == null && tileToHarvest != null) {
			targetTile = tileToHarvest;
			harvest = true;
		}
		
		if(targetTile != null) {
			flag = FLAG_MOVE_TO_RESOURCE;
			targetTile.use(this);
		} else {
			flag = FLAG_RETURN_TO_HOME;
		}
	}
	
	/** Run as long as the citizen should move to a resource */
	private void moveToResource() {
		final Vector2f position = getTransform().getPosition().getXY();
		final Vector2f tilePosition = targetTile.getGameObject().getTransform().getPosition().getXY();
		
		if(position.distance(tilePosition) > 2) {
			final Vector3f target = tilePosition.sub(position).normalize().mul(1).toVector3f();
			target.setZ(0);
			
			getTransform().translate(target);
		} else {
			System.out.println(harvest);
			flag = (harvest) ? FLAG_GATHER_RESOURCE : FLAG_PLANT_RESOURCE;
		}
	}
	
	/** Run as long the citizen should gather a resource */
	private void gatherResource() {
		if(targetTile != null)
			item = targetTile.getResource().harvest();
		
		flag = FLAG_RETURN_TO_HOME;
	}
	
	/** Run as long as the citizen should plant a resource */
	private void plantResource() {
		if(targetTile.getResource() != null) {
			flag = FLAG_FIND_RESOURCE;
			return;
		}
		
		int type = 0;
		
		switch(targetTile.getTileId()) {
		case Tile.GRASS:
			type = Resource.TREE;
			break;
		case Tile.FARMLAND:
			type = Resource.FARMLAND;
			break;
		}
		
		targetTile.addResource(type);
		targetTile.stopUsing();
	}
	
	/** Run as long as the citizen should store a resource */
	private void storeResource() {
		switch(item.getId()) {
		case Item.TREE:
			Data.woodAmount++;
			break;
		case Item.FOOD:
			Data.foodAmount++;
			break;
		}
		
		item = null;
		flag = FLAG_FIND_RESOURCE;
	}
}
