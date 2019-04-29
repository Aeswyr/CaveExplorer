package world;

import java.awt.Graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import core.Driver;
import runtime.Handler;
import utility.Loader;
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
	public void render(Graphics g) {
		
		int offx = handler.getCamera().xOffset() / Tile.tileSize;
		int offy = handler.getCamera().yOffset() / Tile.tileSize;
		
		int startX = offx - handler.getWidth() / 2;
		int startY = offy - handler.getHeight() / 2;
		int endX = offx + handler.getWidth() / 2;
		int endY = offy + handler.getHeight() / 2;
		
		if (startX < x * chunkDim) startX = x * chunkDim;
		if (startY < y * chunkDim) startY = y * chunkDim;
		if (endX > x * chunkDim + chunkDim) endX = x * chunkDim + chunkDim;
		if (endY > y * chunkDim + chunkDim) endY = y * chunkDim + chunkDim;
		
		for (int i = startX; i < endX; i++) {
			for (int j = startY; j < endY; j++) {
				Tile.toTile(chunk[i - x * chunkDim][j - y * chunkDim]).render(i, j, new int[0], g);
			}
		}
	}

	/**
	 * updates all tiles in the chunk
	 */
	public void update() {
		// TODO update
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
		try {
			BufferedReader read = Loader.loadTextFromFile(Driver.saveDir + "saves/world/world.dat");
			int find = y * World.maxChunks + x;
			for (int i = 0; i < find; i++) {
				read.readLine();
			}
			line = read.readLine();

			read.close();
		} catch (IOException e) {

		}

		chunk = new int[chunkDim][chunkDim];
		StringTokenizer data = new StringTokenizer(line);
		for (int y = 0; y < chunkDim; y++) {
			for (int x = 0; x < chunkDim; x++) {
				chunk[x][y] = Integer.parseInt(data.nextToken());
			}
		}
		loaded = true;
	}

	/**
	 * unloads a chunk's data from memory
	 */
	public void unload() {
		chunk = null;
		loaded = false;
	}

	/**
	 * fetches the node at a given x and y
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
