package world;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
import core.Driver;
import core.Engine;
import gfx.DrawGraphics;
import runtime.Handler;
import utility.Loader;
import utility.Utils;
import world.Tile;
import world.World;

/**
 * Represents a 16 * 16 array of tiles and handles all actions on those tiles
 * 
 * @author Pascal
 *
 */
public class Chunk {
	protected int x, y;
	boolean loaded = false;
	int[][] chunk;
	int[][] map;
	int[][] mask;
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

		int offx = Handler.getCamera().xOffset() / Tile.TILE_SIZE;
		int offy = Handler.getCamera().yOffset() / Tile.TILE_SIZE;

		int startX = offx + (int) (12 / Driver.xScale);
		int startY = offy;
		int endX = offx + Handler.getWidth() / Tile.TILE_SIZE - (int) (18 / Driver.xScale);
		int endY = offy + Handler.getHeight() / Tile.TILE_SIZE + (int) (9 / Driver.yScale);

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
					Tile.toTile(chunk[i - x * chunkDim][j - y * chunkDim]).render(i, j,
							mask[i - x * chunkDim][j - y * chunkDim], g);
					if (map[i - x * chunkDim][j - y * chunkDim] != -1)
						Tile.toTile(map[i - x * chunkDim][j - y * chunkDim]).render(i, j,
								mask[i - x * chunkDim][j - y * chunkDim], g);
				}
			}
		}

	}

	/**
	 * updates all tiles in the chunk
	 */
	public void update(int[] biomeData) {
		int offx = Handler.getCamera().xOffset() / Tile.TILE_SIZE;
		int offy = Handler.getCamera().yOffset() / Tile.TILE_SIZE;

		int startX = offx + (int) (12 / Driver.xScale);
		int startY = offy;
		int endX = offx + Handler.getWidth() / Tile.TILE_SIZE - (int) (18 / Driver.xScale);
		int endY = offy + Handler.getHeight() / Tile.TILE_SIZE + (int) (9 / Driver.yScale);

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
			BufferedReader read = Loader.loadTextFromFile(
					Engine.ROOT_DIRECTORY + "saves/" + ((World)Handler.getLoadedWorld()).loadedWorld + "/world.dat", StandardCharsets.UTF_8);
			BufferedReader readMap = Loader.loadTextFromFile(
					Engine.ROOT_DIRECTORY + "saves/" + ((World)Handler.getLoadedWorld()).loadedWorld + "/map.dat", StandardCharsets.UTF_8);
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
			e.printStackTrace();
		}

		chunk = new int[chunkDim][chunkDim];
		map = new int[chunkDim][chunkDim];
		mask = new int[chunkDim][chunkDim];

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

		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				if (y != 0 && x != 0 && y != chunkDim - 1 && x != chunkDim - 1) {
					if (Tile.toTile(chunk[x - 1][y]).wall)
						mask[x][y] += 1;
					if (Tile.toTile(chunk[x][y + 1]).wall)
						mask[x][y] += 2;
					if (Tile.toTile(chunk[x + 1][y]).wall)
						mask[x][y] += 4;
					if (Tile.toTile(chunk[x][y - 1]).wall)
						mask[x][y] += 8;
				} else {
					if (this.x != 0 && this.y != 0 && this.x != World.maxChunks - 1 && this.y != World.maxChunks - 1) {
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x - 1, this.y * chunkDim + y, World.MAP_BASE).wall)
							mask[x][y] += 1;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x, this.y * chunkDim + y + 1, World.MAP_BASE).wall)
							mask[x][y] += 2;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x + 1, this.y * chunkDim + y, World.MAP_BASE).wall)
							mask[x][y] += 4;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x, this.y * chunkDim + y - 1, World.MAP_BASE).wall)
							mask[x][y] += 8;
					} else {
						mask[x][y] = 15;
					}
				}

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
		Utils.editText(c.toString(), find, Engine.ROOT_DIRECTORY + "saves/" + ((World)Handler.getLoadedWorld()).loadedWorld + "/world.dat");
		Utils.editText(m.toString(), find, Engine.ROOT_DIRECTORY + "saves/" + ((World)Handler.getLoadedWorld()).loadedWorld + "/map.dat");

		chunk = null;
		map = null;
		mask = null;
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
		
		for (int j = -1; j < 2; j++) {
			for (int i = -1; i < 2; i++) {
				int x0 = x + i;
				int y0 = y + j;
				
				if (y0 > 0 && x0 > 0 && y0 < chunkDim - 1 && x0 < chunkDim - 1) {
					mask[x0][y0] = 0;
					if (Tile.toTile(chunk[x0 - 1][y0]).wall)
						mask[x0][y0] += 1;
					if (Tile.toTile(chunk[x0][y0 + 1]).wall)
						mask[x0][y0] += 2;
					if (Tile.toTile(chunk[x0 + 1][y0]).wall)
						mask[x0][y0] += 4;
					if (Tile.toTile(chunk[x0][y0 - 1]).wall)
						mask[x0][y0] += 8;
				} else if (y0 == 0 && x0 == 0 && y0 == chunkDim - 1 && x0 == chunkDim - 1){
					if (this.x > 0 && this.y > 0 && this.x < World.maxChunks - 1 && this.y < World.maxChunks - 1) {
						mask[x0][y0] = 0;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x0 - 1, this.y * chunkDim + y0, World.MAP_BASE).wall)
							mask[x0][y0] += 1;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x0, this.y * chunkDim + y0 + 1, World.MAP_BASE).wall)
							mask[x0][y0] += 2;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x0 + 1, this.y * chunkDim + y0, World.MAP_BASE).wall)
							mask[x0][y0] += 4;
						if (((World)Handler.getLoadedWorld()).getTile(this.x * chunkDim + x0, this.y * chunkDim + y0 - 1, World.MAP_BASE).wall)
							mask[x0][y0] += 8;
					} else {
						mask[x0][y0] = 15;
					}
				}
			}
		}
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
