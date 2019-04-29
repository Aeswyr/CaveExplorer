package core;

import gfx.Sprite;
import gfx.SpriteSheet;
import world.TileSet;

public class Assets {

	private static final String WORLD = "/world/";
	private  static final String PLAYER = "/player/";
	private static final String ENTITY = "/entity/";
	
	private static SpriteSheet tile;
	public static TileSet test;
	
	private static SpriteSheet player;
	public static Sprite player_still;
	public static Sprite player_run;
	
	
	public static void init() {
		initSheet();
		initSprite();
	}
	
	private static void initSheet() {
		tile = new SpriteSheet(WORLD + "tile.png");
		player = new SpriteSheet(PLAYER + "testChar.png");
	}
	
	private static void initSprite() {
		test = new TileSet(0, 0, tile);
		
		player_still= new Sprite(0, 0, 32, player);
		player_run = new Sprite(32, 0, 32, 32, 8, 10, player);
	}
}
