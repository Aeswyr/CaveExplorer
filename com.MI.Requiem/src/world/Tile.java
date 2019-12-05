package world;

import java.util.ArrayList;

import java.util.Random;
import core.Assets;
import gfx.DrawGraphics;
import item.Item;
import items.Anvil;
import items.Forge;
import items.Gem;
import items.Ore;
import items.TileBlock;
import items.Worktable;
import runtime.Handler;

/**
 * represents a single 16x16 section of the world
 * 
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

	private static Tile[] tiles;

	public static void initTile() {
		tiles = new Tile[TILE_MAX];
		for (int i = 0; i < tiles.length; i++)
			tiles[i] = new Tile(i);
	}

	protected final int id;
	public static final int TILE_MAX = 23;

	/**
	 * initializes a tile with a specific id
	 * 
	 * @param id - the id of the new tile
	 */
	public Tile(int id) {
		this.id = id;
		switch (id) {
		case 0: // Dirt floor
			this.tileSet = Assets.dirtTile;
			this.breakable = false;
			break;
		case 1: // Dirt wall
			this.tileSet = Assets.dirtTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 2: // anvil
			this.tileSet = Assets.anvil;
			this.breakable = true;
			this.solid = true;
			break;
		case 3: // forge left
			this.tileSet = Assets.forgeLeft;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 4: // forge right
			this.tileSet = Assets.forgeRight;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 5: // limestone floor
			this.tileSet = Assets.limestoneTile;
			this.breakable = false;
			break;
		case 6: // limestone wall
			this.tileSet = Assets.limestoneTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 7: // iron vein
			this.tileSet = Assets.ironOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 8: // clay floor
			this.tileSet = Assets.clayTile;
			this.breakable = false;
			break;
		case 9: // clay wall
			this.tileSet = Assets.clayTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 10: // worktable
			this.tileSet = Assets.worktableTile;
			this.breakable = true;
			this.solid = true;
			break;
		case 11: // aluminum vein
			this.tileSet = Assets.aluminumOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 12: // antimony vein
			this.tileSet = Assets.antimonyOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 13: // copper vein
			this.tileSet = Assets.copperOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 14: // galena vein
			this.tileSet = Assets.galenaOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 15: // gold vein
			this.tileSet = Assets.goldOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 16: // tin vein
			this.tileSet = Assets.tinOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 17: // zinc vein
			this.tileSet = Assets.zincOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 18: // chrome vein
			this.tileSet = Assets.chromeOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 19: // titanium vein
			this.tileSet = Assets.titaniumOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 20: // mercury vein
			this.tileSet = Assets.mercuryOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 21: // garnet vein
			this.tileSet = Assets.garnetOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		case 22: // quartz vein
			this.tileSet = Assets.quartzOreTile;
			this.wall = true;
			this.breakable = true;
			this.solid = true;
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param x       - x position to drop the item
	 * @param y       - y position to drop the item
	 * @param handler - the game handler
	 * @returns a list of items dropped by this tile, tied to this tiles location
	 */
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		switch (id) {
		case 0: // dirt floor
			break;
		case 1: // dirt wall
			drop.add(new TileBlock(x, y, handler, id));
			if (rng.nextDouble() < 0.1)
				drop.add(new TileBlock(x, y, handler, 9));
			break;
		case 2: // anvil
			drop.add(new Anvil(x, y, handler));
			break;
		case 3: // forge left
			drop.add(new Forge(x, y, handler));
			break;
		case 4: // forge right
			drop.add(new Forge(x, y, handler));
			break;
		case 5: // limestone floor
			break;
		case 6: // limestone wall
			drop.add(new TileBlock(x, y, handler, id));
			break;
		case 7: // iron vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 0));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 8: // clay floor
			break;
		case 9: // clay wall
			drop.add(new TileBlock(x, y, handler, id));
			break;
		case 10: // worktable
			drop.add(new Worktable(x, y, handler));
			break;
		case 11: // aluminum vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 1));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 12: // antimony vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 2));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 13: // copper vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 3));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 14: // galena vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 4));
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 5));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 15: // gold vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 6));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 16: // tin vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 7));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 17: // zinc vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 8));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 18: // chome vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 9));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 19: // titanium vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 10));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 20: // mercury vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Ore(x, y, handler, 11));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.03)
				drop.add(new Gem(x, y, handler, 0));
			break;
		case 21: // garnet vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Gem(x, y, handler, 3));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.30)
				drop.add(new Gem(x, y, handler, 3));
			break;
		case 22: // quartz vein
			if (rng.nextDouble() < 0.30)
				drop.add(new Gem(x, y, handler, 4));
			if (rng.nextDouble() < 0.30)
				drop.add(new TileBlock(x, y, handler, 6));
			if (rng.nextDouble() < 0.30)
				drop.add(new Gem(x, y, handler, 4));
			break;
		default:
			break;
		}
		return drop;
	}

	/**
	 * Draws a tile at the specified position
	 * 
	 * @param x - x draw position in pixels
	 * @param y - y draw position in pixels
	 * @param g - DrawGraphics component to draw with
	 */
	public void render(int x, int y, DrawGraphics g) {
		tileSet.render(x, y, wall, g);
	}

	/**
	 * returns the static tile associated with a specific id
	 * 
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

}
