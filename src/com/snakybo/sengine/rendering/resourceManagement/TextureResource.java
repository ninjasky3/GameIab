package com.snakybo.sengine.rendering.resourceManagement;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/** Texture resource
 * 
 * Resource manager for textures
 * 
 * @author Kevin Krol
 * @since Apr 5, 2014 */
public class TextureResource {
	private int id;
	private int width;
	private int height;
	
	private int refCount;
	
	/** Constructor for the shader resource */
	public TextureResource(int width, int height) {
		this.id = glGenTextures();
		this.width = width;
		this.height = height;
		this.refCount = 1;
	}
	
	@Override
	protected void finalize() {
		glDeleteBuffers(id);
	}
	
	/** Add a reference to the resource */
	public void addReference() {
		refCount++;
	}
	
	/** Remove a reference from the resource
	 * @return Whether or not there are no more references remaining */
	public boolean removeReference() {
		refCount--;
		
		return refCount == 0;
	}
	
	/** @return The ID of the texture */
	public int getTextureId() {
		return id;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
