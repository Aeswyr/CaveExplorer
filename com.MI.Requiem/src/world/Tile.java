package world;

import java.util.ArrayList;
import gfx.DrawGraphics;
import item.Item;
import runtime.Handler;
import tiles.Tile_Anvil;
import tiles.Tile_DirtFloor;
import tiles.Tile_DirtWall;
import tiles.Tile_ForgeLeft;
import tiles.Tile_ForgeRight;

public class Tile {

	protected boolean wall;
	protected boolean breakable;
	protected boolean solid;
	protected TileSet tileSet;
	public static int tileSize = 16;

	private static Tile[] tiles = { new Tile_DirtFloor(), new Tile_DirtWall(), new Tile_Anvil(), new Tile_ForgeLeft(), new Tile_ForgeRight() };
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
