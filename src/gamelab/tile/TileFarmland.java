package gamelab.tile;

/** @author Kevin Krol
 * @since Jun 12, 2014 */
public class TileFarmland extends Tile {
	private static int[] spriteIds = new int[] {31};
	
	public TileFarmland(int x, int y, int rawX, int rawY) {
		super(Tile.FARMLAND, x, y, rawX, rawY);
		
		updateSprite(getRandomSpriteId(spriteIds));
	}
}
