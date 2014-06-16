package com.snakybo.sengine.core;

import com.snakybo.sengine.rendering.RenderingEngine;

/** The base game class, with essential functionality
 * 
 * @author Kevin Krol
 * @since Apr 4, 2014 */
public abstract class Game {
	/** Initialize the game */
	protected void init(CoreEngine coreEngine) {}
	
	/** Handle input in the game
	 * @param delta The current delta time */
	protected void input(float delta) {
		GameObject.root.inputAll(delta);
	}
	
	/** Update the game each frame
	 * @param delta The current delta time */
	protected void update(float delta) {
		GameObject.root.updateAll(delta);
	}
	
	/** Render the game each frame
	 * @param renderingEngine The rendering engine */
	protected void render(RenderingEngine renderingEngine) {
		renderingEngine.render(GameObject.root);
	}
	
	/** Add a game object to the scene engine */
	public void addChild(GameObject gameObject) {
		GameObject.root.addChild(gameObject);
	}
	
	public void removeChild(GameObject gameObject) {
		GameObject.root.removeChild(gameObject);
	}
	
	/** Set the core engine of the game
	 * @param engine The core engine */
	public void setEngine(CoreEngine engine) {
		if(GameObject.root == null)
			GameObject.root = new GameObject();
		
		GameObject.root.setEngine(engine);
	}
}
