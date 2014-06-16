package gamelab.world;

import gamelab.city.City;
import gamelab.tile.Tile;
import gamelab.world.chunk.Chunk;
import gamelab.world.chunk.ChunkProvider;

import java.util.Random;

import com.snakybo.sengine.components.Camera;

/** @author Kevin Krol
 * @since May 12, 2014 */
public class World {
	public static final int MIN_WORLD_X = -5000;
	public static final int MIN_WORLD_Y = -5000;
	public static final int MAX_WORLD_X = 5000;
	public static final int MAX_WORLD_Y = 5000;
	
	private ChunkProvider chunkProvider;
	private Random random;
	private Camera camera;	
	
	/** Initialize the world */
	public void start(Camera camera) {
		this.camera = camera;
		
		random = new Random();
		chunkProvider = new ChunkProvider(this, random.nextLong());
		
		for(int x = 0; x < 3; x++)
			for(int y = 0; y < 2; y++)
				chunkProvider.provideChunk(x, y);
		
		new City(this).addBuilding();
	}
	
	/** Set a tile in the specified chunk at the specified coordinates
	 * @param chunk The chunk to place the tile in
	/** @param x The X position in the chunk
	/** @param y The Y position in the chunk
	/** @param tileId The ID of the tile to place */
	public boolean setTile(Chunk chunk, int x, int y, int tileId) {
		if(x >= MIN_WORLD_X && x <= MAX_WORLD_X && y >= MIN_WORLD_X && y <= MAX_WORLD_Y)
			return chunk.setTile(x & 0xF, y & 0xF, tileId);
		
		return false;
	}
	
	/** @return The tile at the specified coordinates */
	public Tile getTileAt(int x, int y) {
		Chunk c = getChunkFromTileCoords(x, y);
		
		if(c != null)
			return c.getTileAt(x / Tile.TILE_WIDTH, y / Tile.TILE_HEIGHT);
		
		new Exception().printStackTrace();
		
		return null;
	}
	
	/** @return The chunk at the specified tile coordinates */
	public Chunk getChunkFromTileCoords(int x, int y) {
		return getChunkFromChunkCoords((x / Tile.TILE_WIDTH) >> 4, (y / Tile.TILE_HEIGHT) >> 4);
	}
	
	/** @return The chunk at the specified coordinates */
	public Chunk getChunkFromChunkCoords(int x, int y) {
		return chunkProvider.getChunkAt(x, y);
	}
	
	/** @return The chunk at the mouse coordinates */
	public Chunk getChunkFromMouseCoords(int x, int y) {		
		if((float)x / 2 + 1 >= 16)
			x += 1;
		
		if((float)y / 2 + 1 >= 16)
			y += 1;
		
		x = (x / 2) >> 4;
		y = (y / 2) >> 4;
		
		return chunkProvider.getChunkAt(x, y);
	}
	
	/** @return The camera */
	public Camera getCamera() {
		return camera;
	}
}
