package world;

import java.awt.Graphics;

import core.Driver;
import tiles.Tile_DirtFloor;
import tiles.Tile_DirtWall;

public class Tile {

	protected boolean wall;
	protected TileSet tileSet;
	public static int tileSize = (int)(16 * Driver.scale);
	
	private static Tile[] tiles = {
		new Tile_DirtFloor(),
		new Tile_DirtWall()
	};
	
	
	
	//An edge starts with 0 in the top left and goes around clockwise up to 7
	public void render(int x, int y, int[] edges, Graphics g) {
		tileSet.render(x, y, edges, wall, g);
	}
	
	public static Tile toTile(int id) {
		return tiles[id];
	}
}
