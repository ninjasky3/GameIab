package gamelab.resource;

import gamelab.GameLab;
import gamelab.citizen.item.Item;
import gamelab.rendering.SpriteSheet;

import com.snakybo.sengine.rendering.Texture;

/** @author Kevin Krol
 * @since Jun 12, 2014 */
public class Stone extends Resource {
	private static final SpriteSheet STONE_SPRITESHEET = new SpriteSheet(new Texture("stones.png"), 4, 1);
	
	private static final int STONE_WIDTH = 37;
	private static final int STONE_HEIGHT = 26;
	
	public Stone(int x, int y) {
		this.spriteSheet = STONE_SPRITESHEET;
		this.x = x;
		this.y = y;
		this.spriteId = getRandomSpriteId(new int[] {0, 1, 2, 3});
		this.width = STONE_WIDTH;
		this.height = STONE_HEIGHT;
	}
	
	@Override
	public Item harvest() {
		GameLab.instance.removeChild(getParent());
		
		return new Item(Item.TREE);
	}
	
	@Override
	public boolean canHarvest() {
		return true;
	}
}
