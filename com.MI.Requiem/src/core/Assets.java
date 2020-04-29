package core;

import gfx.Font;
import gfx.Sprite;
import gfx.SpriteData;
import gfx.SpriteSheet;
import gui.NineSlice;
import sfx.Sound;
import world.TileBlock;
import world.TileMap;
import world.TileSet;
import world.TileSingle;

/**
 * stores and initializes all game assets
 * 
 * @author Pascal
 *
 */
public class Assets {

	private static final String WORLD = "/world/";
	private static final String PLAYER = "/player/";
	private static final String ENTITY = "/entity/";
	private static final String UI = "/gui/";
	private static final String ITEM = "/item/";
	private static final String SOUND = "/sound/";

	public static Font font;

	private static SpriteSheet tile;
	public static TileMap test;
	public static TileMap dirtTile;
	public static TileMap anvil;
	public static TileMap forgeLeft;
	public static TileMap forgeRight;
	public static TileMap limestoneTile;
	public static TileMap clayTile;
	public static TileMap waterTile;
	public static TileMap ironOreTile;
	public static TileMap copperOreTile;
	public static TileMap tinOreTile;
	public static TileMap aluminumOreTile;
	public static TileMap zincOreTile;
	public static TileMap galenaOreTile;
	public static TileMap goldOreTile;
	public static TileMap antimonyOreTile;
	public static TileMap garnetOreTile;
	public static TileMap mercuryOreTile;
	public static TileMap chromeOreTile;
	public static TileMap titaniumOreTile;
	public static TileMap quartzOreTile;
	public static TileMap worktableTile;
	public static TileMap crateTile;
	public static TileMap rockTile;
	public static TileMap sandTile;

	private static SpriteSheet ui;
	public static Sprite uiTop;
	public static Sprite uiMid16;
	public static Sprite uiMid20;
	public static Sprite uiBottom;
	public static Sprite life;
	public static Sprite spirit;
	public static Sprite resContainer;
	public static Sprite heart;
	public static Sprite heartDead;
	public static Sprite inventory_Empty;
	public static Sprite inventory_Trinket;
	public static Sprite inventory_Head;
	public static Sprite inventory_Body;
	public static Sprite inventory_Offhand;
	public static Sprite inventory_Mainhand;
	public static Sprite hTick;
	public static Sprite sTick;
	public static Sprite pTick;
	public static Sprite bTick;
	public static Sprite spellPointer;

	private static SpriteSheet player;
	public static Sprite player_idle;
	public static Sprite player_run;
	public static Sprite player_dive;

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
	public static Sprite mold;
	public static Sprite mold_inv;
	public static Sprite bone;
	public static Sprite bone_inv;
	public static Sprite worktable;
	public static Sprite worktable_inv;
	public static Sprite crystalRod;
	public static Sprite crystalRod_inv;
	public static Sprite crate;
	public static Sprite crate_inv;
	public static Sprite spellBook;
	public static Sprite spellBook_inv;
	public static Sprite spellScroll;
	public static Sprite spellScroll_inv;

	public static SpriteSheet corpse_sheet;
	public static Sprite corpse;

	public static Sprite GUI_MainSplash;
	
	private static SpriteSheet nineslices;
	public static NineSlice ns_grey;
	public static NineSlice ns_grey_dep;

	// Sound

	public static Sound pickaxe1;
	
	public static Sound MUSIC_IntoDarkness;

	public static void init() {
		initSheet();
		initSprite();
		initSound();
	}

	private static void initSheet() {
		tile = new SpriteSheet(WORLD + "tile.png");
		player = new SpriteSheet(PLAYER + "char.png");
		ui = new SpriteSheet(UI + "gui.png");
		item = new SpriteSheet(ITEM + "items.png");

		ooze = new SpriteSheet(ENTITY + "ooze.png");
		willowWisp = new SpriteSheet(ENTITY + "willowWisp.png");

		font = new Font(UI + "font.png", 0);

		corpse_sheet = new SpriteSheet(WORLD + "corpse.png");
		
		nineslices = new SpriteSheet(UI + "slice.png");
	}

	private static void initSprite() {
		
		test = new TileSet(192, 0, tile);
		
		dirtTile = new TileSet(192, 64, tile);
		anvil = new TileSingle(48, 0, tile, 0);
		forgeLeft = new TileSingle(64, 0, tile, true);
		forgeRight = new TileSingle(80, 0, tile, true);
		limestoneTile = new TileSet(192, 128, tile);
		waterTile = new TileBlock(0, 128, tile);
		ironOreTile = new TileBlock(64, 32, tile);
		copperOreTile = new TileBlock(32, 32, tile);
		tinOreTile = new TileBlock(96, 32, tile);
		aluminumOreTile = new TileBlock(64, 64, tile);
		zincOreTile = new TileBlock(96, 64, tile);
		galenaOreTile = new TileBlock(64, 96, tile);
		goldOreTile = new TileBlock(96, 96, tile);
		antimonyOreTile = new TileBlock(64, 128, tile);
		garnetOreTile = new TileBlock(96, 128, tile);
		mercuryOreTile = new TileBlock(128, 32, tile);
		chromeOreTile = new TileBlock(128, 96, tile);
		titaniumOreTile = new TileBlock(128, 64, tile);
		quartzOreTile = new TileBlock(128, 128, tile);
		clayTile = new TileSet(192, 192, tile);
		worktableTile = new TileSingle(48, 64, tile, 0);
		crateTile = new TileSingle(64, 64, tile, 0);
		rockTile = new TileSet(64, 192, tile);
		sandTile = new TileSet(128, 192, tile);

		player_idle = new Sprite(0, 0, 32, player, SpriteData.TYPE_ENTITY);
		player_run = new Sprite(32, 0, 32, 32, 8, 16, player, SpriteData.TYPE_ENTITY);
		player_dive = new Sprite(64, 0, 32, 32, 7, 12, player, SpriteData.TYPE_ENTITY);

		ooze_idle = new Sprite(0, 0, 32, ooze, SpriteData.TYPE_ENTITY);
		willowWisp_idle = new Sprite(0, 0, 32, willowWisp, SpriteData.TYPE_ENTITY);

		uiTop = new Sprite(0, 0, 128, 6, ui, SpriteData.TYPE_GUI_COMPONENT);
		uiMid16 = new Sprite(0, 6, 128, 16, ui, SpriteData.TYPE_GUI_COMPONENT);
		uiMid20 = new Sprite(0, 10, 128, 20, ui, SpriteData.TYPE_GUI_COMPONENT);
		uiBottom = new Sprite(0, 30, 128, 6, ui, SpriteData.TYPE_GUI_COMPONENT);
		life = new Sprite(112, 36, 16, ui, SpriteData.TYPE_GUI_COMPONENT);
		spirit = new Sprite(112, 52, 16, ui, SpriteData.TYPE_GUI_COMPONENT);
		resContainer = new Sprite(96, 36, 16, ui, SpriteData.TYPE_GUI_COMPONENT);
		heart = new Sprite(27, 36, 20, ui, SpriteData.TYPE_GUI_COMPONENT);
		heartDead = new Sprite(47, 36, 20, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Empty = new Sprite(0, 64, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Trinket = new Sprite(64, 96, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Head = new Sprite(32, 64, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Body = new Sprite(64, 64, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Offhand = new Sprite(32, 96, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		inventory_Mainhand = new Sprite(0, 96, 32, ui, SpriteData.TYPE_GUI_COMPONENT);
		hTick = new Sprite(0, 36, 1, 3, ui, SpriteData.TYPE_ENTITY);
		sTick = new Sprite(1, 36, 1, 3, ui, SpriteData.TYPE_ENTITY);
		pTick = new Sprite(2, 36, 1, 3, ui, SpriteData.TYPE_ENTITY);
		bTick = new Sprite(3, 36, 1, 3, ui, SpriteData.TYPE_ENTITY);
		spellPointer = new Sprite(128, 0, 35, 35, ui, SpriteData.TYPE_ENTITY);

		GUI_MainSplash = new Sprite(0, 0, 960, 540, new SpriteSheet(UI + "splash.png"),
				SpriteData.TYPE_GUI_BACKGROUND_SHAPE);
		ns_grey = new NineSlice(new Sprite(0, 0, 12, 12, nineslices, SpriteData.TYPE_GUI_COMPONENT));
		ns_grey_dep = new NineSlice(new Sprite(12, 0, 12, 12, nineslices, SpriteData.TYPE_GUI_COMPONENT));

		litTorch_inv = new Sprite(0, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		litTorch = new Sprite(0, 32, 32, item, SpriteData.TYPE_ENTITY);
		burntTorch_inv = new Sprite(32, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		burntTorch = new Sprite(32, 32, 32, item, SpriteData.TYPE_ENTITY);
		cloak_inv = new Sprite(64, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		cloak = new Sprite(64, 32, 32, item, SpriteData.TYPE_ENTITY);
		spineberry_inv = new Sprite(96, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		spineberry = new Sprite(96, 32, 32, item, SpriteData.TYPE_ENTITY);
		pickaxe_inv = new Sprite(128, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		pickaxe = new Sprite(128, 32, 32, item, SpriteData.TYPE_ENTITY);
		theOrb_inv = new Sprite(160, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		theOrb = new Sprite(160, 32, 32, item, SpriteData.TYPE_ENTITY);
		dirt_inv = new Sprite(192, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		dirt = new Sprite(192, 32, 32, item, SpriteData.TYPE_ENTITY);
		anvil_inv = new Sprite(224, 0, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		forge_inv = new Sprite(224, 32, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		limestone_inv = new Sprite(32, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		limestone = new Sprite(32, 96, 32, item, SpriteData.TYPE_ENTITY);
		corundum = new Sprite(0, 96, 32, item, SpriteData.TYPE_ENTITY);
		corundum_inv = new Sprite(0, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		ironOre = new Sprite(64, 96, 32, item, SpriteData.TYPE_ENTITY);
		ironOre_inv = new Sprite(64, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		ironIngot = new Sprite(96, 96, 32, item, SpriteData.TYPE_ENTITY);
		ironIngot_inv = new Sprite(96, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		clay = new Sprite(128, 96, 32, item, SpriteData.TYPE_ENTITY);
		clay_inv = new Sprite(128, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		mold = new Sprite(160, 96, 32, item, SpriteData.TYPE_ENTITY);
		mold_inv = new Sprite(160, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		bone = new Sprite(192, 96, 32, item, SpriteData.TYPE_ENTITY);
		bone_inv = new Sprite(192, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		worktable = new Sprite(224, 64, 32, item, SpriteData.TYPE_ENTITY);
		worktable_inv = new Sprite(224, 64, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		crystalRod = new Sprite(0, 160, 32, item, SpriteData.TYPE_ENTITY);
		crystalRod_inv = new Sprite(0, 128, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		crate = new Sprite(224, 96, 32, item, SpriteData.TYPE_ENTITY);
		crate_inv = new Sprite(224, 96, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		spellScroll = new Sprite(128, 160, 32, item, SpriteData.TYPE_ENTITY);
		spellScroll_inv = new Sprite(128, 128, 32, item, SpriteData.TYPE_INVENTORY_ITEM);
		spellBook = new Sprite(96, 160, 32, item, SpriteData.TYPE_ENTITY);
		spellBook_inv = new Sprite(96, 128, 32, item, SpriteData.TYPE_INVENTORY_ITEM);

		corpse = new Sprite(0, 0, 32, corpse_sheet, SpriteData.TYPE_ENTITY);

	}

	private static void initSound() {
		pickaxe1 = new Sound(SOUND + "pickaxe1.wav");
		MUSIC_IntoDarkness = new Sound(SOUND + "IntoDarknessOST - Track 01 (Into Darkness).wav");
	}
}
