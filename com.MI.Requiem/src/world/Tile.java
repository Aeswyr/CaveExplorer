package world;

import java.util.ArrayList;
import java.util.Random;

import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import tiles.Tile_Anvil;
import tiles.Tile_ClayFloor;
import tiles.Tile_ClayWall;
import tiles.Tile_DirtFloor;
import tiles.Tile_DirtWall;
import tiles.Tile_ForgeLeft;
import tiles.Tile_ForgeRight;
import tiles.Tile_IronVein;
import tiles.Tile_LimestoneFloor;
import tiles.Tile_LimestoneWall;
import tiles.Tile_Worktable;

/**
 * represents a single 16x16 section of the world
 * @author Pascal
 *
 */
public class Tile {

	protected boolean wall;
	protected boolean breakable;
	protected boolean solid;
	protected TileSet tileSet;
	static final protected Random rng = new Random();
	public static int tileSize = 16;

	private static Tile[] tiles = { new Tile_DirtFloor(), new Tile_DirtWall(), new Tile_Anvil(), new Tile_ForgeLeft(),
			new Tile_ForgeRight(), new Tile_LimestoneFloor(), new Tile_LimestoneWall(), new Tile_IronVein(),
			new Tile_ClayFloor(), new Tile_ClayWall(), new Tile_Worktable() };
	protected final int id;
	public static final int TILE_MAX = tiles.length;
	
	/**
	 * initializes a tile with a specific id
	 * @param id - the id of the new tile
	 */
	public Tile(int id) {
		this.id = id;
	}

	/**
	 * Draws a tile at the specified position
	 * @param x - x draw position in pixels
	 * @param y - y draw position in pixels
	 * @param g - DrawGraphics component to draw with
	 */
	public void render(int x, int y, DrawGraphics g) {
		tileSet.render(x, y, wall, g);
	}

	/**
	 * returns the static tile associated with a specific id
	 * @param id - the id of the desired tile
	 * @return
	 */
	public static Tile toTile(int id) {
		return tiles[id];
	}

	/**
	 * returns the floor tile associated with the tile id
	 * 
	 * @param id of the wall tile.
	 * @returns the id of the floor tile, dirt floor if wall id is not valid
	 */
	public static int tileToFloor(int id) {
		switch (id) {
		case 1:
			return 0;
		case 6:
			return 5;
		case 9:
			return 8;
		default:
			return 0;
		}
	}

	/**
	 * @returns if entities can collide with this tile
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @returns if this tile can be broken with tools
	 */
	public boolean isBreakable() {
		return breakable;
	}

	/**
	 * 
	 * @param x - x position to drop the item
	 * @param y - y position to drop the item
	 * @param handler - the game handler
	 * @returns a list of items dropped by this tile, tied to this tiles location
	 */
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		return null;
	}
}
