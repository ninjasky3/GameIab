package gamelab.player;

import gamelab.GameLab;
import gamelab.player.LeapMotionManager.LeapListener;
import gamelab.tile.Tile;
import gamelab.world.chunk.Chunk;

import com.snakybo.sengine.components.Camera;
import com.snakybo.sengine.core.Component;
import com.snakybo.sengine.core.Input;
import com.snakybo.sengine.core.Input.KeyCode;
import com.snakybo.sengine.core.Input.MouseButton;
import com.snakybo.sengine.core.utils.Vector2i;
import com.snakybo.sengine.rendering.RenderingEngine;
import com.snakybo.sengine.rendering.Shader;

/** @author Kevin Krol
 * @since May 22, 2014 */
public class InputHandler extends Component {
	private Camera camera;
	private GameLab game;
	
	public InputHandler(GameLab game) {
		this.game = game;
	}
	
	@Override
	protected void input(float delta) {
		if(camera == null)
			return;
		
		if(Input.getKeyDown(KeyCode.NUM_1)) {
			Data.selectedTile = Tile.DIRT;
		} else if(Input.getKeyDown(KeyCode.NUM_2)) {
			Data.selectedTile = Tile.GRASS;
		} else if(Input.getKeyDown(KeyCode.NUM_3)) {
			Data.selectedTile = Tile.FARMLAND;
		}
		
		Vector2i cursorPosition = null;
		
		if(Input.getMouse(MouseButton.LEFT)) {
			cursorPosition = camera.cursorToWorld(Input.getMousePosition());
			
		} else if(LeapListener.getPosition().getZ() < 10) {
			
			cursorPosition = camera.cursorToWorld(LeapListener.getPosition().getXY().toVector2i());
			
		}
		
		if(cursorPosition != null) {
			Chunk chunk = game.getWorld().getChunkFromMouseCoords(cursorPosition.getX(), cursorPosition.getY());
			
			if(chunk != null) {			
				final Tile tile = chunk.getTileFromMouseCoords(cursorPosition.getX(), cursorPosition.getY());
				final Vector2i tilePosition = tile.getPosition();
				
				game.getWorld().setTile(chunk, tilePosition.getX(), tilePosition.getY(), Data.selectedTile);
			}
		}
	}
	
	@Override
	protected void render(Shader shader, RenderingEngine renderingEngine) {
		if(camera == null)
			camera = renderingEngine.getMainCamera();
	}
}
