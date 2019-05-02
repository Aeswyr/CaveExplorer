package world;

import java.util.ArrayList;
import entity.EntityManager;
import gfx.DrawGraphics;
import runtime.Handler;

public class World {

	Handler handler;

	EntityManager entities;

	ChunkLoader chunkLoader;
	ArrayList<Chunk> chunks;
	Chunk currChunk;
	public static int maxChunks;

	public World(Handler handler) {
		this.handler = handler;
		maxChunks = 64;
		entities = new EntityManager();
		entities.addEntity(handler.getPlayer());
		MapGenerator.generateMap();

		Chunk.handler = this.handler;
		chunkLoader = new ChunkLoader();
		chunks = chunkLoader.getActive();
		currChunk = new Chunk(-1, -1);

		chunkLoader.start();

	}

	public void render(DrawGraphics g) {

		synchronized (chunks) {
			for (int i = 0; i < chunks.size(); i++) {
				chunks.get(i).render(g);
			}
		}
		handler.getLights().render(g);

		entities.renderEntityUI(g);
	}

	public void update() {
		updateChunks();
		entities.update();
	}

	private void updateChunks() {
		if (currChunk.getX() != handler.getPlayer().getChunkX()
				|| currChunk.getY() != handler.getPlayer().getChunkY()) {
			currChunk = new Chunk(handler.getPlayer().getChunkX(), handler.getPlayer().getChunkY());
			ArrayList<Chunk> test = new ArrayList<Chunk>();
			for (int x = -2; x < 3; x++) {
				for (int y = -2; y < 3; y++) {
					if (currChunk.getX() + x >= 0 && currChunk.getX() < maxChunks && currChunk.getY() + y >= 0
							&& currChunk.getY() < maxChunks)
						test.add(new Chunk(currChunk.getX() + x, currChunk.getY() + y));
				}
			}

			for (int i = 0; i < chunks.size(); i++) {
				if (!test.contains(chunks.get(i)))
					chunkLoader.unload(chunks.get(i));
			}
			for (int i = 0; i < test.size(); i++) {
				if (!chunks.contains(test.get(i)))
					chunkLoader.load(test.get(i));
			}

		}
		synchronized (chunks) {
			chunks.clear();
			chunks.addAll(chunkLoader.getActive());
		}
	}

	/**
	 * returns the tile at the given position
	 * 
	 * @param x - the x coordinate of the tile (screen position)
	 * @param y - the y coordinate of the tile (screen position)
	 */
	public Tile getTile(int x, int y) {
		int id = -1;
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i) != null) {
				int pos = chunks.get(i).tileAt(x / Tile.tileSize, y / Tile.tileSize);
				if (pos != -1)
					id = pos;
			}
		}
		if (id == -1)
			return Tile.toTile(0);
		return Tile.toTile(id);
	}

	public EntityManager getEntities() {
		return entities;
	}

}
