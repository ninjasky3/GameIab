package gamelab.world.chunk;

import com.snakybo.sengine.components.Camera;
import com.snakybo.sengine.core.utils.Bounds;

/** @author Kevin Krol
 * @since May 28, 2014 */
public class ChunkChecker implements Runnable {
	private static Thread chunkChecker;
	
	private ChunkProvider chunkProvider;
	private Camera camera;
	
	public ChunkChecker(ChunkProvider chunkProvider, Camera camera) {
		this.chunkProvider = chunkProvider;
		this.camera = camera;
		
		if(chunkChecker == null) {
			chunkChecker = new Thread(this, "chunkChecker");
			chunkChecker.start();
		}
	}
	
	@Override
	public void run() {
		while(chunkChecker.isAlive()) {
			if(camera.getTransform().hasChanged()) {
				final Bounds cameraBounds = camera.toBounds();
				final Chunk[] chunkList = chunkProvider.getChunkList();
				
				for(Chunk chunk : chunkList) {
					final Bounds chunkBounds = chunk.toBounds();
					
					boolean changed = false;
					
					if(chunkBounds.getLeft() <= cameraBounds.getRight() && chunkBounds.getRight() >= cameraBounds.getLeft()) {
						if(chunkBounds.getTop() >= cameraBounds.getBottom() && chunkBounds.getBottom() <= cameraBounds.getTop()) {
							changed = true;
							
							if(!chunk.isLoaded()) {
								System.out.println("Loaded a chunk");
								chunk.load();
							}
						}
					}
					
					if(!changed && chunk.isLoaded()) {
						System.out.println("Unloaded a chunk");
						chunk.unload();
					}
				}
			}
		}
	}
}