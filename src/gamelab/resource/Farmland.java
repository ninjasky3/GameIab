package gamelab.resource;

import gamelab.citizen.item.Item;
import gamelab.rendering.SpriteSheet;

import com.snakybo.sengine.rendering.Texture;

//TODO: Farmland
/** @author Kevin Krol
 * @since Jun 12, 2014 */
public class Farmland extends Resource {
	private static final SpriteSheet FARM_LAND_SPRITESHEET = new SpriteSheet(new Texture("farmland.png"), 12, 1);
	
	private static final int[] sprites = new int[] {2, 4, 7, 10};
	
	private static final float TIME_UNTIL_GROWTH = 45f;
	
	private static final int MAX_STAGE = 4;
	
	private static final int FARM_LAND_WIDTH = 32;
	private static final int FARM_LAND_HEIGHT = 32;
	
	private float timer;
	
	private int stage;
	
	public Farmland(int x, int y) {
		this.spriteSheet = FARM_LAND_SPRITESHEET;
		this.x = x;
		this.y = y;
		this.spriteId = 1;
		this.width = FARM_LAND_WIDTH;
		this.height = FARM_LAND_HEIGHT;
		this.offset = 0;
		
		this.timer = 0f;
		this.stage = 1;
	}
	
	@Override
	protected void update(float delta) {	
		if(stage < MAX_STAGE) {
			timer += delta;
			
			if(timer >= TIME_UNTIL_GROWTH) {
				timer = 0;
				
				stage++;
				renderer.setActiveSprite(sprites[stage - 1]);
			}
		}
	}
	
	@Override
	public Item harvest() {
		this.stage = 1;
		
		renderer.setActiveSprite(stage);
		
		return new Item(Item.TREE);
	}
	
	@Override
	public boolean canHarvest() {
		return stage == MAX_STAGE;
	}
}
