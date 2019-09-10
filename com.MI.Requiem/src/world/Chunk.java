package world;

import java.io.BufferedReader;
import java.io.IOException;
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

		int startX = offx + (int) (12 / Driver.scale);
		int startY = offy;
		int endX = offx + handler.getWidth() / Tile.tileSize - (int) (18 / Driver.scale);
		int endY = offy + handler.getHeight() / Tile.tileSize + (int) (9 / Driver.scale);

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

		int startX = offx + (int) (12 / Driver.scale);
		int startY = offy;
		int endX = offx + handler.getWidth() / Tile.tileSize - (int) (18 / Driver.scale);
		int endY = offy + handler.getHeight() / Tile.tileSize + (int) (9 / Driver.scale);

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
			BufferedReader read = Loader.loadTextFromFile(Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/world.dat");
			BufferedReader readMap = Loader.loadTextFromFile(Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/map.dat");
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
		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				chunk[x][y] = Integer.parseInt(data.nextToken());
				map[x][y] = Integer.parseInt(mapData.nextToken());
			}
		}
		loaded = true;
	}

	/**
	 * unloads a chunk's data from memory
	 */
	public void unload() {
		String c = "";
		String m = "";
		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				c += chunk[x][y] + " ";
				m += map[x][y] + " ";
			}
		}
		int find = y * World.maxChunks + x;
		Utility.editText(c, find, Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/world.dat");
		Utility.editText(m, find, Driver.saveDir + "saves/" + handler.getWorld().loadedWorld + "/map.dat");

		chunk = null;
		map = null;
		loaded = false;
	}

	/**
	 * fetches the tile at a given x and y
	 * 
	 * @param x - the x coordinate of the node relative to the whole map
	 * @param y - the y coordinate of the node relative to the whole map
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

	public void setTile(int x, int y, int id) {
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return;
		chunk[x][y] = id;
	}

	public int overlayAt(int x, int y) {
		if (!loaded)
			return -1;
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return -1;
		return map[x][y];
	}

	public void setOverlay(int x, int y, int id) {
		x -= this.x * chunkDim;
		y -= this.y * chunkDim;
		if (x >= chunkDim || x < 0 || y >= chunkDim || y < 0)
			return;
		map[x][y] = id;
	}

	/**
	 * @returns the x value of this chunk
	 */
	public int getX() {
		return x;
	}

	/**
	 * @returns the y value of this chunk
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
