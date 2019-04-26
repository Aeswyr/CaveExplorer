package world;

import java.awt.Graphics;
import tiles.Tile_Test;

public class Tile {

	boolean wall;
	TileSet tileSet;
	public static int tileSize = 16;
	
	private static Tile[] tiles = {
		new Tile_Test()
	};
	
	
	
	//An edge starts with 0 in the top left and goes around clockwise up to 7
	public void render(int x, int y, int[] edges, Graphics g) {
		tileSet.render(x, y, edges, wall, g);
	}
	
	public static Tile toTile(int id) {
		return tiles[id];
	}
}
