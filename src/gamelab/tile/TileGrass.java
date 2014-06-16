package gamelab.tile;

/** @author Kevin Krol
 * @since Jun 1, 2014 */
public class TileGrass extends Tile {
	private static int[] spriteIds = new int[] {4};
	
	public TileGrass(int x, int y, int rawX, int rawY) {
		super(Tile.GRASS, x, y, rawX, rawY);
		
		updateSprite(getRandomSpriteId(spriteIds));
	}
}
