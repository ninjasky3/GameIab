package gamelab.world.chunk;

import gamelab.GameLab;
import gamelab.tile.Tile;
import gamelab.tile.TileDirt;
import gamelab.tile.TileFarmland;
import gamelab.tile.TileGrass;

import com.snakybo.sengine.core.utils.Bounds;

/** @author Kevin Krol
 * @since May 13, 2014 */
public class Chunk {
	public static final int CHUNK_SIZE = 16;
	
	private Tile[] tileStorage;
	
	private int chunkX;
	private int chunkY;
	
	private boolean isLoaded;
	
	public Chunk(int chunkX, int chunkY) {
		tileStorage = new Tile[CHUNK_SIZE * CHUNK_SIZE];
		
		this.chunkX = chunkX;
		this.chunkY = chunkY;
		
		isLoaded = false;
	}
	
	public void load() {
		isLoaded = true;
		
		for(Tile tile : tileStorage)
			if(tile != null)
				tile.load();
	}
	
	public void unload() {
		isLoaded = false;
		
		for(Tile tile : tileStorage)
			if(tile != null)
				tile.unload();
	}
	
	public Bounds toBounds() {
		return new Bounds(chunkX * (CHUNK_SIZE * Tile.TILE_WIDTH), chunkY * (CHUNK_SIZE * Tile.TILE_HEIGHT), (chunkX + 1) * (CHUNK_SIZE * Tile.TILE_WIDTH), (chunkY + 1) * (CHUNK_SIZE * Tile.TILE_HEIGHT));
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	public boolean setTile(int x, int y, int tileId) {
		int xPos = x + (chunkX * CHUNK_SIZE);
		int yPos = y + (chunkY * CHUNK_SIZE);
		
		Tile tile = getTileAt(x, y);
		
		if(tile != null) {
			if(tile.getTileId() == tileId)
				return false;
			
			tile.removeResource();
			GameLab.instance.removeChild(tile.getGameObject());
		}
		
		switch(tileId) {
		case Tile.DIRT:
			tile = new TileDirt(xPos, yPos, x, y);
			break;
		case Tile.GRASS:
			tile = new TileGrass(xPos, yPos, x, y);
			break;
		case Tile.FARMLAND:
			tile = new TileFarmland(xPos, yPos, x, y);
			break;
		default:
			throw new IllegalArgumentException("The tile with the ID " + tileId + " has not been referenced to a tile.");
		}
		
		tileStorage[x * Chunk.CHUNK_SIZE + y] = tile;
		
		return true;
	}
	
	public Tile getTileAt(int x, int y) {
		if(x >= 16)
			x = 0;
		
		if(y >= 16)
			y = 0;
		
		return tileStorage[x * Chunk.CHUNK_SIZE + y];
	}
	
	public Tile getTileFromMouseCoords(int x, int y) {
		x = ((x + 1) / 2) & 0xF;
		y = ((y + 1) / 2) & 0xF;
		
		return tileStorage[x * Chunk.CHUNK_SIZE + y];
	}
	
	public int getX() {
		return chunkX;
	}
	
	public int getY() {
		return chunkY;
	}
	
	public int getTileStorageLength() {
		return tileStorage.length;
	}
	
	public Tile[] getTiles() {
		return tileStorage;
	}
}