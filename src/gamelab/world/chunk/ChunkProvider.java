package gamelab.world.chunk;

import gamelab.tile.Tile;
import gamelab.world.World;

import java.util.Vector;

/** @author Kevin Krol
 * @since May 20, 2014 */
public class ChunkProvider {
	private Vector<Chunk> chunks;
	
	private World world;
	
	private long seed;
	
	public ChunkProvider(World world, long seed) {
		chunks = new Vector<Chunk>();
		
		this.world = world;
		this.seed = seed;
		
		new ChunkChecker(this, world.getCamera());
	}
	
	public boolean chunkExists(int x, int y) {
		// TODO: Check if a chunk exists at the specified X and Y coordinates
		return false;
	}

	public Chunk provideChunk(int x, int y) {
		seed = (long)x * 996746212733L + (long)y * 837299812787L;
		
		Chunk chunk = new Chunk(x, y);
		chunks.add(chunk);
		
		generateTerrain(chunk);
		
		return chunk;
	}
	
	public Chunk[] getChunkList() {
		return chunks.toArray(new Chunk[chunks.size()]);
	}
	
	public Chunk getChunkAt(int x, int y) {
		for(Chunk chunk : chunks)
			if(x == chunk.getX() && y == chunk.getY())
				return chunk;
		
		return null;
	}
	
	private void generateTerrain(Chunk chunk) {
		if(!chunkExists(chunk.getX(), chunk.getY())) {
			for(int i = 0; i < chunk.getTileStorageLength(); i++) {
				int x = i & 0xF;
				int y = i >> 4;
			
				world.setTile(chunk, x, y, Tile.DIRT);
			}
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public long getSeed() {
		return seed;
	}
}
