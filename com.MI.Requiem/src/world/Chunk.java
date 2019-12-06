package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import core.Driver;
import gfx.DrawGraphics;
import runtime.Handler;
import utility.Loader;
import utility.Utility;
import world.Tile;
import world.World;

/**
 * Represents a 16 * 16 array of tiles and handles all actions on those tiles
 * 
 * @author Pascal
 *
 */
public class Chunk {

	public static Handler handler;
	protected int x, y;
	boolean loaded = false;
	int[][] chunk;
	int[][] map;
	public static final int chunkDim = 16;

	/**
	 * initializes a chunk with the associated x and y coordinates
	 * 
	 * @param x
	 * @param y
	 */
	public Chunk(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * draws all tiles that are both in the chunk and on the screen
	 * 
	 * @param g - the graphics component to draw with
	 */
	public void render(DrawGraphics g) {

		int offx = handler.getCamera().xOffset() / Tile.tileSize;
		int offy = handler.getCamera().yOffset() / Tile.tileSize;

		int startX = offx + (int) (12 / Driver.xScale);
		int startY = offy;
		int endX = offx + handler.getWidth() / Tile.tileSize - (int) (18 / Driver.xScale);
		int endY = offy + handler.getHeight() / Tile.tileSize + (int) (9 / Driver.yScale);

		if (startX < x * chunkDim)
			startX = x * chunkDim;
		if (startY < y * chunkDim)
			startY = y * chunkDim;
		if (endX > x * chunkDim + chunkDim)
			endX = x * chunkDim + chunkDim;
		if (endY > y * chunkDim + chunkDim)
			endY = y * chunkDim + chunkDim;

		if (chunk != null) {
			for (int i = startX; i < endX; i++) {
				for (int j = startY; j < endY; j++) {
					Tile.toTile(chunk[i - x * chunkDim][j - y * chunkDim]).render(i, j, g);
					if (map[i - x * chunkDim][j - y * chunkDim] != -1)
						Tile.toTile(map[i - x * chunkDim][j - y * chunkDim]).render(i, j, g);
				}
			}
		}

	}

	/**
	 * updates all tiles in the chunk
	 */
	public void update(int[] biomeData) {
		int offx = handler.getCamera().xOffset() / Tile.tileSize;
		int offy = handler.getCamera().yOffset() / Tile.tileSize;

		int startX = offx + (int) (12 / Driver.xScale);
		int startY = offy;
		int endX = offx + handler.getWidth() / Tile.tileSize - (int) (18 / Driver.xScale);
		int endY = offy + handler.getHeight() / Tile.tileSize + (int) (9 / Driver.yScale);

		if (startX < x * chunkDim)
			startX = x * chunkDim;
		if (startY < y * chunkDim)
			startY = y * chunkDim;
		if (endX > x * chunkDim + chunkDim)
			endX = x * chunkDim + chunkDim;
		if (endY > y * chunkDim + chunkDim)
			endY = y * chunkDim + chunkDim;

		if (chunk != null) {
			for (int i = startX; i < endX; i++) {
				for (int j = startY; j < endY; j++) {
					biomeData[chunk[i - x * chunkDim][j - y * chunkDim]]++;
				}
			}
		}
	}

	/**
	 * updates only critical values in the chunk
	 */
	public void updateSoft() {
		// TODO less intensive update, critical functions only
	}

	/**
	 * loads a chunk from provided data and sets up borders
	 */
	public void load() {
		String line = null;
		String mapLine = null;

		try {
			BufferedReader read = Loader
					.loadTextFromFile(Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/world.dat", StandardCharsets.UTF_8);
			BufferedReader readMap = Loader
					.loadTextFromFile(Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/map.dat", StandardCharsets.UTF_8);
			int find = y * World.maxChunks + x;
			for (int i = 0; i < find; i++) {
				read.readLine();
				readMap.readLine();
			}
			line = read.readLine();
			mapLine = readMap.readLine();
			read.close();
			readMap.close();
		} catch (IOException e) {

		}

		chunk = new int[chunkDim][chunkDim];
		map = new int[chunkDim][chunkDim];

		StringTokenizer data = new StringTokenizer(line);
		StringTokenizer mapData = new StringTokenizer(mapLine);

		int id0 = 0;
		int c0 = 0;
		int id1 = 0;
		int c1 = 0;
		
		String a0;
		String a1;
		
		
		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				if (c0 == 0) {
					a0 = data.nextToken();
					id0 = a0.charAt(0) - MapGenerator.OFFSET;
					c0 = a0.charAt(1) - MapGenerator.OFFSET;
				}
				if (c1 == 0) {
					a1 = mapData.nextToken();
					id1 = a1.charAt(0) - MapGenerator.OFFSET;
					c1 = a1.charAt(1) - MapGenerator.OFFSET;
				}
				chunk[x][y] = id0;
				c0--;
				map[x][y] = id1;
				c1--;
			}
		}
		loaded = true;
	}

	/**
	 * unloads a chunk's data from memory and saves all changes to the world file
	 */
	public void unload() {
		StringBuilder c = new StringBuilder();
		StringBuilder m = new StringBuilder();

		int id0 = chunk[0][0];
		int c0 = 0;
		int id1 = map[0][0];
		int c1 = 0;

		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				if (chunk[x][y] == id0)
					c0++;
				else {
					c.append((char) (id0 + MapGenerator.OFFSET));
					c.append((char) (c0 + MapGenerator.OFFSET));
					c.append(' ');
					c0 = 1;
					id0 = chunk[x][y];
				}
				if (map[x][y] == id1)
					c1++;
				else {
					m.append((char) (id1 + MapGenerator.OFFSET));
					m.append((char) (c1 + MapGenerator.OFFSET));
					m.append(' ');
					c1 = 1;
					id1 = map[x][y];
				}
			}
		}

		c.append((char) (id0 + MapGenerator.OFFSET));
		c.append((char) (c0 + MapGenerator.OFFSET));
		m.append((char) (id1 + MapGenerator.OFFSET));
		m.append((char) (c1 + MapGenerator.OFFSET));

		int find = y * World.maxChunks + x;
		Utility.editText(c.toString(), find, Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/world.dat");
		Utility.editText(m.toString(), find, Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/map.dat");

		chunk = null;
		map = null;
		loaded = false;
	}

	/**
	 * fetches the tile at a given x and y
	 * 
	 * @param x - the x coordinate (in tiles) relative to the whole map
	 * @param y - the y coordinate (in tiles) relative to the whole map
	 * @returns the node at that x, y. If that x, y lies outside the chunk, returns
	 *          null
	 */
	public int tileAt(int x, int y) {
		if (!loaded)
			return -1;
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return -1;
		return chunk[x][y];
	}

	/**
	 * Changes the tile at the specified coordinate to a new tile with the specified
	 * id
	 * 
	 * @param x  - target x (in tiles) relative to the whole map
	 * @param y  - target y (in tiles) relative to the whole map
	 * @param id - desired tile id
	 */
	public void setTile(int x, int y, int id) {
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return;
		chunk[x][y] = id;
	}

	/**
	 * fetches the overlay tile at a given x and y
	 * 
	 * @param x - the x coordinate (in tiles) relative to the whole map
	 * @param y - the y coordinate (in tiles) relative to the whole map
	 * @returns the node at that x, y. If that x, y lies outside the chunk, returns
	 *          null
	 */
	public int overlayAt(int x, int y) {
		if (!loaded)
			return -2;
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return -2;
		return map[x][y];
	}

	/**
	 * Changes the overlay tile at the specified coordinate to a new overlay tile
	 * with the specified id
	 * 
	 * @param x  - target x (in tiles) relative to the whole map
	 * @param y  - target y (in tiles) relative to the whole map
	 * @param id - desired overlay tile id
	 */
	public void setOverlay(int x, int y, int id) {
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return;
		map[x][y] = id;
	}

	/**
	 * @returns the x position of this chunk
	 */
	public int getX() {
		return x;
	}

	/**
	 * @returns the y position of this chunk
	 */
	public int getY() {
		return y;
	}

	// helper methods

	/**
	 * Tests if this object is equal to another. If the seond object is also a
	 * chunk, uses their x, y positions to test equality instead of object data
	 * 
	 * @returns true if equal, false otherwise
	 * @Override
	 */
	public boolean equals(Object o) {
		if (o instanceof Chunk) {
			return (((Chunk) o).x == this.x && ((Chunk) o).y == this.y);
		} else
			return super.equals(o);
	}
}
