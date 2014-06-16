package gamelab.tile;

import gamelab.GameLab;
import gamelab.citizen.Citizen;
import gamelab.rendering.SpriteRenderer;
import gamelab.rendering.SpriteSheet;
import gamelab.resource.Farmland;
import gamelab.resource.Resource;
import gamelab.resource.Stone;
import gamelab.resource.Tree;

import java.util.Random;

import com.snakybo.sengine.core.GameObject;
import com.snakybo.sengine.core.utils.Quaternion;
import com.snakybo.sengine.core.utils.Vector2f;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.core.utils.Vector3f;
import com.snakybo.sengine.rendering.Texture;

/** @author Kevin Krol
 * @since May 20, 2014 */
public class Tile {
	public static final int TILE_WIDTH = 32;
	public static final int TILE_HEIGHT = 32;
	
	public static final int DIRT = 0;
	public static final int GRASS = 1;
	public static final int FARMLAND = 2;
	
	private static final float TILE_LAYER = 0;
	
	private static final SpriteSheet TILE_SPRITESHEET = new SpriteSheet(new Texture("tiles.png"), 10, 10);
	
	private final GameObject tile;
	private final SpriteRenderer renderer;
	private final int tileId;
	
	private Resource resource;
	private Citizen user;
	
	private int rawX;
	private int rawY;
	
	public Tile(int tileId, int x, int y, int rawX, int rawY) {
		this.tileId = tileId;
		this.tile = new GameObject();
		this.renderer = new SpriteRenderer(TILE_SPRITESHEET, 0);
		
		this.rawX = rawX;
		this.rawY = rawY;
		
		tile.addComponent(renderer);
		
		tile.getTransform().setPosition(new Vector3f(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_LAYER));
		tile.getTransform().setRotation(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(270)));
		tile.getTransform().setScale(new Vector3f(TILE_WIDTH, 0, TILE_HEIGHT));
		
		tile.getTransform().rotate(new Vector3f(0, 0, 1), (float)Math.toRadians(180));

		GameLab.instance.addChild(tile);
	}
	
	/** Called when the tile enters the camera's viewport */
	public void load() {
		renderer.setEnabled(true);
		
		if(resource != null)
			resource.load();
	}
	
	/** Called when the tile leaves the camera's viewport */
	public void unload() {
		renderer.setEnabled(false);
		
		if(resource != null)
			resource.unload();
	}
	
	/** Update the tile's sprite
	 * @param spriteId The ID of the sprite on the spritesheet */
	public void updateSprite(int spriteId) {
		renderer.setActiveSprite(spriteId);
	}
	
	/** Set this tile 'unavailable' for citizens to use
	 * @param citizen The citizen that is currently using the tile */
	public void use(Citizen citizen) {
		this.user = citizen;
	}
	
	public void removeResource() {
		if(resource != null)
			Resource.destroy(resource);
	}
	
	/** Add a resource to this tile
	 * @param type The type of the resource */
	public void addResource(int type) {
		final Vector2f position = getGameObject().getTransform().getPosition().getXY();
		
		switch(type) {
		case Resource.TREE:
			resource = Resource.create(new Tree((int)position.getX(), (int)position.getY()));
			break;
		case Resource.FARMLAND:
			resource = Resource.create(new Farmland((int)position.getX(), (int)position.getY()));
			break;
		case Resource.STONE:
			resource = Resource.create(new Stone((int)position.getX(), (int)position.getY()));
			break;
		}
	}
	
	/** Make the tile 'available' for citizens to use */
	public void stopUsing() {
		user = null;
	}
	
	/** @return Whether the tile is currently being used */
	public boolean isBeingUsed() {
		return user != null;
	}
	
	/** @return A random sprite ID from a list of sprites
	 * @param spriteIds The list of sprites */
	public int getRandomSpriteId(int[] spriteIds) {
		Random random = new Random();
		
		return spriteIds[random.nextInt(spriteIds.length)];
	}
	
	/** @return The 'tile'-position of this tile */
	public Vector2i getPosition() {
		return new Vector2i(rawX, rawY);
	}
	
	/** @return The tile ID of this tile */
	public int getTileId() {
		return tileId;
	}
	
	/** @return The Game Object of this tile */
	public GameObject getGameObject() {
		return tile;
	}
	
	/** @return The resource this tile has */
	public Resource getResource() {
		return resource;
	}
}