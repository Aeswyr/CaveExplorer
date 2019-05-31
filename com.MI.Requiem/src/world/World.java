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

		int pcx = handler.getPlayer().getChunkX();
		int pcy = handler.getPlayer().getChunkY();

		if (currChunk.getX() != pcx || currChunk.getY() != pcy) {
			currChunk = new Chunk(pcx, pcy);

			int cx = currChunk.getX();
			int cy = currChunk.getY();

			ArrayList<Chunk> test = new ArrayList<Chunk>();
			for (int x = -2; x < 3; x++) {
				for (int y = -2; y < 3; y++) {
					if (cx + x >= 0 && cx < maxChunks && cy + y >= 0 && cy < maxChunks)
						test.add(new Chunk(cx + x, cy + y));
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
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
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
	
	/**
	 * returns the tile id at the given position
	 * 
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
	 */
	public int getTileID(int x, int y) {
		int id = -1;
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i) != null) {
				int pos = chunks.get(i).tileAt(x / Tile.tileSize, y / Tile.tileSize);
				if (pos != -1)
					id = pos;
			}
		}
		if (id == -1) return 0;
		return id;
	}

	public void setTile(int x, int y, int id) {
		for (int i = 0; i < chunks.size(); i++) chunks.get(i).setTile(x / Tile.tileSize, y / Tile.tileSize, id);
	}
	
	public EntityManager getEntities() {
		return entities;
	}

}
