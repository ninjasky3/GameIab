package gamelab.resource;

import java.util.Random;

import gamelab.GameLab;
import gamelab.citizen.item.Item;
import gamelab.rendering.SpriteRenderer;
import gamelab.rendering.SpriteSheet;

import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.GameObject;
import com.snakybo.sengine.core.utils.Quaternion;
import com.snakybo.sengine.core.utils.Vector3f;

/** @author Kevin Krol
 * @since Jun 6, 2014 */
public class Resource extends Component {
	public static final int TREE = 0;
	public static final int FARMLAND = 1;
	public static final int STONE = 2;
	
	private static final float LAYER = 150;
	
	protected SpriteSheet spriteSheet;
	protected SpriteRenderer renderer;
	
	protected float x;
	protected float y;
	protected int spriteId;
	protected int width;
	protected int height;
	protected int offset;
	
	/** Called when the resource enters the camera's viewport */
	public void load() {
		renderer.setEnabled(true);
	}
	
	/** Called when the resource leaves the camera's viewport */
	public void unload() {
		renderer.setEnabled(false);
	}
	
	public Item harvest() {
		return new Item(0);
	}
	
	public boolean canHarvest() {
		return false;
	}
	
	/** @return A random sprite ID from a list of sprites
	 * @param spriteIds The list of sprites */
	public int getRandomSpriteId(int[] spriteIds) {
		Random random = new Random();
		
		return spriteIds[random.nextInt(spriteIds.length)];
	}
	
	public static Resource create(Resource resource) {
		GameObject resourceGo = new GameObject();
		SpriteRenderer renderer = new SpriteRenderer(resource.spriteSheet, resource.spriteId);
		
		resourceGo.addComponent(renderer);
		resourceGo.addComponent(resource);
		
		resourceGo.getTransform().setPosition(new Vector3f(resource.x, resource.y + resource.offset, LAYER));
		resourceGo.getTransform().setRotation(new Quaternion(new Vector3f(1, 0, 0), (float)Math.toRadians(270)));
		resourceGo.getTransform().setScale(new Vector3f(resource.width, 0, resource.height));
		
		resourceGo.getTransform().rotate(new Vector3f(0, 0, 1), (float)Math.toRadians(180));

		GameLab.instance.addChild(resourceGo);
		
		resource.renderer = renderer;
		
		return resource;
	}
	
	public static void destroy(Resource resource) {
		GameLab.instance.removeChild(resource.getParent());
	}
}
