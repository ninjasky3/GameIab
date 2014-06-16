package com.snakybo.sengine.rendering;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import com.snakybo.sengine.core.utils.Buffer;
import com.snakybo.sengine.rendering.resourceManagement.TextureResource;

/** Texture class
 * 
 * @author Kevin Krol
 * @since Apr 5, 2014 */
public class Texture {
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	
	private TextureResource resource;
	private String fileName;
	
	/** Constructor for the texture
	 * @param fileName The texture file to load */
	public Texture(String fileName) {
		this.fileName = fileName;
		
		TextureResource oldResource = loadedTextures.get(fileName);
		
		if(oldResource != null) {
			resource = oldResource;
			resource.addReference();
		} else {
			resource = loadTexture(fileName);
			loadedTextures.put(fileName, resource);
		}
	}
	
	@Override
	protected void finalize() {
		if(resource.removeReference() && !fileName.isEmpty()) {
			loadedTextures.remove(fileName);
		}
	}
	
	/** Bind the texture and allow it to be used for OpenGL methods */
	public void bind() {
		bind(0);
	}
	
	/** Bind the texture and allow it to be used for OpenGL methods
	 * @param samplerSlot The sampler slot the texture belongs to */
	public void bind(int samplerSlot) {
		assert (samplerSlot >= 0 && samplerSlot <= 31);
		
		glActiveTexture(GL_TEXTURE0 + samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getTextureId());
	}
	
	/** @return The ID of the texture */
	public int getTextureId() {
		return resource.getTextureId();
	}
	
	public int getWidth() {
		return resource.getWidth();
	}
	
	public int getHeight() {
		return resource.getHeight();
	}
	
	/** Load a texture
	 * @param fileName The texture file
	 * @return A texture resource */
	private static TextureResource loadTexture(String fileName) {
		try {
			File imageFile = new File("./res/textures/" + fileName);
			
			if(!imageFile.exists() || imageFile.isDirectory())
				throw new IIOException("The file '" + fileName + "' doesn't exist or is a dictionary");
			
			BufferedImage image = ImageIO.read(imageFile);	
			ByteBuffer buffer = Buffer.createByteBuffer(image.getHeight() * image.getWidth() * 4);
			
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			boolean hasAlpha = image.getColorModel().hasAlpha();
			
			for(int y = 0; y < image.getHeight(); y++) {
				for(int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha)
						buffer.put((byte)((pixel >> 24) & 0xFF));
					else
						buffer.put((byte)(0xFF));
				}
			}
			
			buffer.flip();
			
			TextureResource resource = new TextureResource(image.getWidth(), image.getHeight());
			
			glBindTexture(GL_TEXTURE_2D, resource.getTextureId());
			
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
					buffer);
			
			return resource;
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}
