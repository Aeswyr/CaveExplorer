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

public class Tile {

	protected boolean wall;
	protected boolean breakable;
	protected boolean solid;
	protected TileSet tileSet;
	static final protected Random rng = new Random();
	public static int tileSize = 16;

	private static Tile[] tiles = { new Tile_DirtFloor(), new Tile_DirtWall(), new Tile_Anvil(), new Tile_ForgeLeft(),
			new Tile_ForgeRight(), new Tile_LimestoneFloor(), new Tile_LimestoneWall(), new Tile_IronVein(),
			new Tile_ClayFloor(), new Tile_ClayWall() };
	protected final int id;

	public Tile(int id) {
		this.id = id;
	}

	// An edge starts with 0 in the top left and goes around clockwise up to 7
	public void render(int x, int y, DrawGraphics g) {
		tileSet.render(x, y, wall, g);
	}

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

	public boolean isSolid() {
		return solid;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		return null;
	}
}
