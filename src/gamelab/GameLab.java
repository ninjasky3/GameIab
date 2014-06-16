package gamelab;

import gamelab.leap.LeapMove;
import gamelab.player.InputHandler;
import gamelab.player.LeapMotionManager;
import gamelab.world.World;

import com.snakybo.sengine.components.Camera;
import com.snakybo.sengine.components.FreeMove;
import com.snakybo.sengine.core.CoreEngine;
import com.snakybo.sengine.core.Game;
import com.snakybo.sengine.core.GameObject;
import com.snakybo.sengine.core.Input.KeyCode;
import com.snakybo.sengine.core.utils.Vector3f;
import com.snakybo.sengine.rendering.Window;

public class GameLab extends Game {
	public static GameLab instance;
	
	private World world;
	
	@Override
	protected void init(CoreEngine coreEngine) {
		GameLab.instance = this;
		
		coreEngine.getRenderingEngine().setAmbientLight(new Vector3f(1f, 1f, 1f));
		
		addCore();
	}
	
	private void addCore() {
		Camera camera = Camera.initOrthographicCamera(0, Window.getWidth(), 0, Window.getHeight(), -1000, 1000);
		
		new LeapMotionManager();
		
		addChild(new GameObject(
			camera,
			new FreeMove(300, KeyCode.NONE, KeyCode.NONE, KeyCode.A, KeyCode.D, KeyCode.W, KeyCode.S),
			new LeapMove(),
			new InputHandler(this)
		));
		
		world = new World();
		world.start(camera);
	}
	
	public World getWorld() {
		return world;
	}
}