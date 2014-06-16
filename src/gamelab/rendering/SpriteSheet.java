package gamelab.rendering;

import com.snakybo.sengine.core.utils.Vector2f;
import com.snakybo.sengine.rendering.Texture;

/** @author Kevin Krol
 * @since May 14, 2014 */
public class SpriteSheet {
	private Texture texture;
	
	private int rows;
	private int cols;
	
	public SpriteSheet(Texture texture, int rows, int cols) {
		this.texture = texture;
		
		this.rows = rows;
		this.cols = cols;
	}
	
	/** Calculate the sprite coordinates on the spritesheet from the spriteId
	 * @param spriteId The ID of the sprite */
	public Vector2f spriteIdToTextureCoords(int spriteId) {
		int spriteX = spriteId;
		int spriteY = 0;
		
		while(spriteX >= rows) {
			spriteX -= rows;
			spriteY++;
		}
		
		final Vector2f result = new Vector2f(0, 0);
		final Vector2f spriteSize = getSpriteSize();
		
		result.setX(spriteX * spriteSize.getX());
		result.setY(spriteY * spriteSize.getY());
		
		return result;
	}
	
	/** @return The size of each individual sprite on the spritesheet */
	public Vector2f getSpriteSize() {
		final Vector2f result = new Vector2f(0, 0);
		
		result.setX(1.0f / rows);
		result.setY(1.0f / cols);
		
		return result;
	}
	
	/** @return The texture of the spritesheet */
	public Texture getTexture() {
		return texture;
	}
	
	/** @return The amount of rows */
	public int getRows() {
		return rows;
	}
	
	/** @return The amount of collums */
	public int getCols() {
		return cols;
	}
}
