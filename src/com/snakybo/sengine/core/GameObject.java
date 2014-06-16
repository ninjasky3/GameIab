package com.snakybo.sengine.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.snakybo.sengine.rendering.RenderingEngine;
import com.snakybo.sengine.rendering.Shader;

/** Game object class
 * 
 * <p>
 * Every object in the game is a game object
 * </p>
 * 
 * @author Kevin Krol
 * @since Apr 4, 2014 */
public class GameObject {
	static GameObject root;
	
	private List<GameObject> children;
	private List<Component> components;
	
	private Transform transform;
	private CoreEngine engine;
	
	/** Constructor for the game object
	 * @param componentsToAdd A list of components to add to the game object */
	public GameObject(Component... componentsToAdd) {
		children = new CopyOnWriteArrayList<GameObject>();
		components = new CopyOnWriteArrayList<Component>();
		
		transform = new Transform();
		engine = null;
		
		for(Component component : componentsToAdd)
			addComponent(component);
	}
	
	/** Add a child to the game object
	 * @param child The child */
	public void addChild(GameObject child) {
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}
	
	/** Add a component to the game object
	 * @param component The component to add to the game object
	 * @return This game object, used for chaining */
	public GameObject addComponent(Component component) {
		components.add(component);
		component.setParent(this);
		
		return this;
	}
	
	public void removeChild(GameObject child) {
		children.remove(child);
	}
	
	public <T extends Component> T getComponent(Class<T> type) {
		for(Component component : components)
			if(type.equals(component.getClass()))
				return type.cast(component);

		return null;
	}
	
	/** Handle input for this game object and all of it's children
	 * @param delta The current delta time */
	public void inputAll(float delta) {
		input(delta);
		
		Iterator<GameObject> it = children.iterator();
		while(it.hasNext())
			it.next().inputAll(delta);
	}
	
	/** Update this game object and all of it's children
	 * @param delta The current delta time */
	public void updateAll(float delta) {
		update(delta);
		
		Iterator<GameObject> it = children.iterator();
		while(it.hasNext())
			it.next().updateAll(delta);
	}
	
	/** Render this game object and all of it's children
	 * @param shader The active shader
	 * @param renderingEngine The rendering engine */
	public void renderAll(Shader shader, RenderingEngine renderingEngine) {
		render(shader, renderingEngine);
		
		Iterator<GameObject> it = children.iterator();
		while(it.hasNext())
			it.next().renderAll(shader, renderingEngine);
	}
	
	/** Handle input for the game object and it's components
	 * @param delta The current delta time */
	public void input(float delta) {
		transform.update();
		
		Iterator<Component> it = components.iterator();
		while(it.hasNext())
			it.next().input(delta);
	}
	
	/** Update the game object and it's components
	 * @param delta The current delta time */
	public void update(float delta) {
		Iterator<Component> it = components.iterator();
		while(it.hasNext())
			it.next().update(delta);
	}
	
	/** Render the game object and it's components
	 * @param shader The active shader
	 * @param renderingEngine The rendering engine */
	public void render(Shader shader, RenderingEngine renderingEngine) {
		Iterator<Component> it = components.iterator();
		while(it.hasNext())
			it.next().render(shader, renderingEngine);
	}
	
	/** Set the core engine of the game object
	 * @param engine The core engine */
	public void setEngine(CoreEngine engine) {
		if(this.engine != engine) {
			this.engine = engine;
			
			for(Component component : components)
				component.addToEngine(engine);
			
			for(GameObject child : children)
				child.setEngine(engine);
		}
	}
	
	/** @return An list of all the game objects connected to this one */
	public ArrayList<GameObject> getAllAttached() {
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child : children)
			result.addAll(child.getAllAttached());
		
		result.add(this);
		
		return result;
	}
	
	/** @return The transform of the game object */
	public Transform getTransform() {
		return transform;
	}
}
