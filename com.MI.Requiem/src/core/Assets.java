package core;

import gfx.SpriteSheet;
import world.TileSet;

public class Assets {

	private static final String WORLD = "/world/";
	
	
	private static SpriteSheet tile;
	public static TileSet test;
	
	public static void init() {
		initSheet();
		initSprite();
	}
	
	private static void initSheet() {
		tile = new SpriteSheet(WORLD + "tile.png");
	}
	
	private static void initSprite() {
		test = new TileSet(0, 0, tile);
	}
}
