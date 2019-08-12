package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import core.Driver;
import entity.EntityManager;
import gfx.DrawGraphics;
import runtime.Handler;
import utility.Loader;

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

		Chunk.handler = this.handler;
		chunkLoader = new ChunkLoader();
		chunks = chunkLoader.getActive();
		currChunk = new Chunk(-1, -1);

	}

	public void init() {
		MapGenerator.generateMap(this, handler);
		chunkLoader.start();

		BufferedReader info = Loader.loadTextFromFile(Driver.saveDir + "saves/world/data.dat");
		try {
			String[] playerCoord = info.readLine().split(" ");
			handler.getPlayer().setX(Integer.parseInt(playerCoord[0]) * Tile.tileSize);
			handler.getPlayer().setY(Integer.parseInt(playerCoord[1]) * Tile.tileSize);
			info.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void render(DrawGraphics g) {

		synchronized (chunks) {
			for (int i = 0; i < chunks.size(); i++) {
				chunks.get(i).render(g);
			}
		}
		entities.render(g);
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
			return Tile.toTile(1);
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
		if (id == -1)
			return 0;
		return id;
	}

	/**
	 * returns the tile id at the given position even if the chunk is unloaded
	 * 
	 * @param x - the x coordinate of the tile (tile position)
	 * @param y - the y coordinate of the tile (tile position)
	 */
	public int getUnloadedTileID(int x, int y) {
		int id = -1;
		int chunkx = x / Chunk.chunkDim;
		int chunky = y / Chunk.chunkDim;
		String line = null;
		BufferedReader read = Loader.loadTextFromFile(Driver.saveDir + "saves/world/world.dat");
		int find = chunky * World.maxChunks + chunkx;

		try {
			for (int i = 0; i < find; i++) {
				read.readLine();
			}
			line = read.readLine();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringTokenizer data = new StringTokenizer(line);
		for (int i = 0; i < (y % Chunk.chunkDim) * Chunk.chunkDim + x % Chunk.chunkDim; i++) {
			data.nextToken();
		}
		id = Integer.parseInt(data.nextToken());
		return id;
	}

	public void setTile(int x, int y, int id) {
		for (int i = 0; i < chunks.size(); i++)
			chunks.get(i).setTile(x / Tile.tileSize, y / Tile.tileSize, id);
	}

	/**
	 * returns the tile at the given position
	 * 
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
	 */
	public Tile getOverlay(int x, int y) {
		int id = -1;
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i) != null) {
				int pos = chunks.get(i).overlayAt(x / Tile.tileSize, y / Tile.tileSize);
				if (pos != -1)
					id = pos;
			}
		}
		if (id == -1)
			return null;
		return Tile.toTile(id);
	}

	/**
	 * returns the tile id at the given position
	 * 
	 * @param x - the x coordinate of the tile (pixel position)
	 * @param y - the y coordinate of the tile (pixel position)
	 */
	public int getOverlayID(int x, int y) {
		int id = -1;
		for (int i = 0; i < chunks.size(); i++) {
			if (chunks.get(i) != null) {
				int pos = chunks.get(i).overlayAt(x / Tile.tileSize, y / Tile.tileSize);
				if (pos != -1)
					id = pos;
			}
		}
		return id;
	}

	public void setOverlay(int x, int y, int id) {
		for (int i = 0; i < chunks.size(); i++)
			chunks.get(i).setOverlay(x / Tile.tileSize, y / Tile.tileSize, id);
	}

	public EntityManager getEntities() {
		return entities;
	}

	public void unloadWorld() {
		for (int i = 0; i < chunks.size(); i++) {
			chunks.get(i).unload();
		}
	}

}
