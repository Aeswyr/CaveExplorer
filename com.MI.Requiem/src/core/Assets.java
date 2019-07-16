package core;

import gfx.Font;
import gfx.Sprite;
import gfx.SpriteSheet;
import sfx.Sound;
import world.TileSet;

public class Assets {

	private static final String WORLD = "/world/";
	private static final String PLAYER = "/player/";
	private static final String ENTITY = "/entity/";
	private static final String UI = "/gui/";
	private static final String ITEM = "/item/";
	private static final String SOUND = "/sound/";

	public static Font font;

	private static SpriteSheet tile;
	public static TileSet dirtTile;
	public static TileSet anvil;
	public static TileSet forgeLeft;
	public static TileSet forgeRight;
	public static TileSet limestoneTile;
	public static TileSet clayTile;
	public static TileSet ironOreTile;
	public static TileSet azuriteOreTile; // copper ore
	public static TileSet casseriteTile; // tin ore

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
	public static Sprite player_idle;
	public static Sprite player_run;

	private static SpriteSheet ooze;
	public static Sprite ooze_idle;

	private static SpriteSheet willowWisp;
	public static Sprite willowWisp_idle;

	private static SpriteSheet item;
	public static Sprite litTorch_inv;
	public static Sprite burntTorch_inv;
	public static Sprite cloak_inv;
	public static Sprite spineberry_inv;
	public static Sprite pickaxe_inv;
	public static Sprite theOrb_inv;
	public static Sprite dirt_inv;
	public static Sprite litTorch;
	public static Sprite burntTorch;
	public static Sprite cloak;
	public static Sprite spineberry;
	public static Sprite pickaxe;
	public static Sprite theOrb;
	public static Sprite dirt;
	public static Sprite anvil_inv;
	public static Sprite forge_inv;
	public static Sprite limestone_inv;
	public static Sprite limestone;
	public static Sprite corundum;
	public static Sprite corundum_inv;
	public static Sprite ironOre;
	public static Sprite ironOre_inv;
	public static Sprite ironIngot;
	public static Sprite ironIngot_inv;
	public static Sprite clay;
	public static Sprite clay_inv;

	// Sound

	public static Sound pickaxe1;

	public static void init() {
		initSheet();
		initSprite();
		initSound();
	}

	private static void initSheet() {
		tile = new SpriteSheet(WORLD + "tile.png");
		player = new SpriteSheet(PLAYER + "testChar.png");
		ui = new SpriteSheet(UI + "gui.png");
		item = new SpriteSheet(ITEM + "items.png");

		ooze = new SpriteSheet(ENTITY + "ooze.png");
		willowWisp = new SpriteSheet(ENTITY + "willowWisp.png");

		font = new Font(UI + "font.png");
	}

	private static void initSprite() {
		dirtTile = new TileSet(0, 0, tile);
		anvil = new TileSet(48, 0, tile, true);
		forgeLeft = new TileSet(48, 0, tile, true);
		forgeRight = new TileSet(80, 0, tile, true);
		limestoneTile = new TileSet(0, 32, tile);
		ironOreTile = new TileSet(64, 32, tile);
		azuriteOreTile = new TileSet(32, 32, tile); // copper ore
		casseriteTile = new TileSet(96, 32, tile); // tin ore
		clayTile = new TileSet(0, 64, tile);

		player_idle = new Sprite(0, 0, 32, player, Sprite.TYPE_ENTITY);
		player_run = new Sprite(32, 0, 32, 32, 8, 10, player, Sprite.TYPE_ENTITY);

		ooze_idle = new Sprite(0, 0, 32, ooze, Sprite.TYPE_ENTITY);
		willowWisp_idle = new Sprite(0, 0, 32, willowWisp, Sprite.TYPE_ENTITY);

		healthBar = new Sprite(0, 0, 128, 36, ui, Sprite.TYPE_GUI_COMPONENT);
		heart = new Sprite(27, 36, 20, ui, Sprite.TYPE_GUI_COMPONENT);
		heartDead = new Sprite(47, 36, 20, ui, Sprite.TYPE_GUI_COMPONENT);
		heartContainer_Left = new Sprite(0, 36, 9, 24, ui, Sprite.TYPE_GUI_COMPONENT);
		heartContainer_Mid = new Sprite(9, 36, 9, 24, ui, Sprite.TYPE_GUI_COMPONENT);
		heartContainer_Right = new Sprite(18, 36, 9, 24, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Empty = new Sprite(0, 64, 32, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Trinket = new Sprite(64, 96, 32, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Head = new Sprite(32, 64, 32, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Body = new Sprite(64, 64, 32, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Offhand = new Sprite(32, 96, 32, ui, Sprite.TYPE_GUI_COMPONENT);
		inventory_Mainhand = new Sprite(0, 96, 32, ui, Sprite.TYPE_GUI_COMPONENT);

		litTorch_inv = new Sprite(0, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		litTorch = new Sprite(0, 32, 32, item, Sprite.TYPE_ENTITY);
		burntTorch_inv = new Sprite(32, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		burntTorch = new Sprite(32, 32, 32, item, Sprite.TYPE_ENTITY);
		cloak_inv = new Sprite(64, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		cloak = new Sprite(64, 32, 32, item, Sprite.TYPE_ENTITY);
		spineberry_inv = new Sprite(96, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		spineberry = new Sprite(96, 32, 32, item, Sprite.TYPE_ENTITY);
		pickaxe_inv = new Sprite(128, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		pickaxe = new Sprite(128, 32, 32, item, Sprite.TYPE_ENTITY);
		theOrb_inv = new Sprite(160, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		theOrb = new Sprite(160, 32, 32, item, Sprite.TYPE_ENTITY);
		dirt_inv = new Sprite(192, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		dirt = new Sprite(192, 32, 32, item, Sprite.TYPE_ENTITY);
		anvil_inv = new Sprite(224, 0, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		forge_inv = new Sprite(224, 32, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		limestone_inv = new Sprite(32, 64, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		limestone = new Sprite(32, 96, 32, item, Sprite.TYPE_ENTITY);
		corundum = new Sprite(0, 96, 32, item, Sprite.TYPE_ENTITY);
		corundum_inv = new Sprite(0, 64, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		ironOre = new Sprite(64, 96, 32, item, Sprite.TYPE_ENTITY);
		ironOre_inv = new Sprite(64, 64, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		ironIngot = new Sprite(96, 96, 32, item, Sprite.TYPE_ENTITY);
		ironIngot_inv = new Sprite(96, 64, 32, item, Sprite.TYPE_INVENTORY_ITEM);
		clay = new Sprite(128, 96, 32, item, Sprite.TYPE_ENTITY);
		clay_inv = new Sprite(128, 64, 32, item, Sprite.TYPE_INVENTORY_ITEM);
	}

	private static void initSound() {
		pickaxe1 = new Sound(SOUND + "pickaxe1.wav");
	}
}
