package gamelab.tile;

/** @author Kevin Krol
 * @since May 28, 2014 */
public class TileDirt extends Tile {
	private static int[] spriteIds = new int[] {27, 28, 29};
	
	public TileDirt(int x, int y, int rawX, int rawY) {
		super(Tile.DIRT, x, y, rawX, rawY);
		
		updateSprite(getRandomSpriteId(spriteIds));
	}
}
