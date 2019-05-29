package core;

import gfx.Sprite;
import gfx.SpriteSheet;
import world.TileSet;

public class Assets {

	private static final String WORLD = "/world/";
	private static final String PLAYER = "/player/";
	private static final String ENTITY = "/entity/";
	private static final String UI = "/gui/";
	private static final String ITEM = "/item/";

	private static SpriteSheet tile;
	public static TileSet test;

	private static SpriteSheet ui;
	public static Sprite heart;
	public static Sprite heartDead;
	public static Sprite healthBar;
	public static Sprite heartContainer_Left;
	public static Sprite heartContainer_Mid;
	public static Sprite heartContainer_Right;
	public static Sprite inventory_Empty;
	public static Sprite inventory_Trinket;
	public static Sprite inventory_Head;
	public static Sprite inventory_Body;
	public static Sprite inventory_Offhand;
	public static Sprite inventory_Mainhand;

	private static SpriteSheet player;
	public static Sprite player_still;
	public static Sprite player_run;
	
	private static SpriteSheet item;
	public static Sprite litTorch_inv;
	public static Sprite burntTorch_inv;

	public static void init() {
		initSheet();
		initSprite();
	}

	private static void initSheet() {
		tile = new SpriteSheet(WORLD + "tile.png");
		player = new SpriteSheet(PLAYER + "testChar.png");
		ui = new SpriteSheet(UI + "gui.png");
		item = new SpriteSheet(ITEM + "items.png");
	}

	private static void initSprite() {
		test = new TileSet(0, 0, tile);

		player_still = new Sprite(0, 0, 32, player);
		player_run = new Sprite(32, 0, 32, 32, 8, 10, player);

		healthBar = new Sprite(0, 0, 128, 36, ui);
		heart = new Sprite(27, 36, 20, ui);
		heartDead = new Sprite(47, 36, 20, ui);
		heartContainer_Left = new Sprite(0, 36, 9, 24, ui);
		heartContainer_Mid = new Sprite(9, 36, 9, 24, ui);
		heartContainer_Right = new Sprite(18, 36, 9, 24, ui);
		inventory_Empty = new Sprite(0, 64, 32, ui);
		inventory_Trinket = new Sprite(64, 96, 32, ui);
		inventory_Head = new Sprite(32, 64, 32, ui);
		inventory_Body = new Sprite(64, 64, 32, ui);
		inventory_Offhand = new Sprite(32, 96, 32, ui);
		inventory_Mainhand = new Sprite(0, 96, 32, ui);
		
		litTorch_inv = new Sprite(0, 0, 32, item);
		burntTorch_inv = new Sprite(32, 0, 32, item);		
	}
}
