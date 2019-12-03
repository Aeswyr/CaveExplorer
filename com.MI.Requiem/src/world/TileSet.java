package world;

import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteSheet;
import runtime.Handler;

/**
 * stores 6 sprites for rendering a tile's floor and wall states, as well as
 * variants for each
 * 
 * @author Pascal
 *
 */
public class TileSet {

	public static Handler handler;
	private int[] u = { 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1 };

	Sprite[] floor;
	Sprite[] wall;
	Sprite[] ceiling;

	/**
	 * initializes a tileset for a tile, setting up separate light collisions for
	 * floor, wall and ceiling tiles
	 * 
	 * @param x     - x position of the top left tile of the tileset on the
	 *              spritesheet
	 * @param y     - y position of the top left tile of the tileset on the
	 *              spritesheet
	 * @param sheet
	 */
	public TileSet(int x, int y, SpriteSheet sheet) {
		floor = new Sprite[2];
		floor[0] = new Sprite(x, y, 16, sheet, Sprite.TYPE_FLOOR);
		floor[1] = new Sprite(x, y + 16, 16, sheet, Sprite.TYPE_FLOOR);
		wall = new Sprite[2];
		wall[0] = new Sprite(x + 16, y, 16, sheet, Sprite.TYPE_WALL);
		wall[1] = new Sprite(x + 16, y + 16, 16, sheet, Sprite.TYPE_WALL);
		ceiling = new Sprite[2];
		ceiling[0] = new Sprite(x + 32, y, 16, sheet, Sprite.TYPE_CEILING);
		ceiling[1] = new Sprite(x + 32, y + 16, 16, sheet, Sprite.TYPE_CEILING);

	}

	/**
	 * initializes a tileset for an overlay tile, marking all parts of the tile as
	 * floor type for light collisions
	 * 
	 * @param x       - x position of the top left tile of the tileset on the
	 *                spritesheet
	 * @param y       - y position of the top left tile of the tileset on the
	 *                spritesheet
	 * @param sheet   - spritesheet to draw tileset from
	 * @param overlay - having a boolean (true or false) marks this as an overlay
	 *                tile
	 */
	public TileSet(int x, int y, SpriteSheet sheet, boolean overlay) {
		floor = new Sprite[2];
		floor[0] = new Sprite(x, y, 16, sheet, Sprite.TYPE_FLOOR);
		floor[1] = new Sprite(x, y + 16, 16, sheet, Sprite.TYPE_FLOOR);
		wall = new Sprite[2];
		wall[0] = new Sprite(x + 16, y, 16, sheet, Sprite.TYPE_FLOOR);
		wall[1] = new Sprite(x + 16, y + 16, 16, sheet, Sprite.TYPE_FLOOR);
		ceiling = new Sprite[2];
		ceiling[0] = new Sprite(x + 32, y, 16, sheet, Sprite.TYPE_FLOOR);
		ceiling[1] = new Sprite(x + 32, y + 16, 16, sheet, Sprite.TYPE_FLOOR);
	}

	/**
	 * draws one tile from this tileset to the map at the specified coordinate
	 * 
	 * @param x - x position (in tiles) to draw at
	 * @param y - y position (in tiles) to draw at
	 * @param w - denotes if this tile is a floor or wall variant
	 * @param g - the DrawGraphics component with which to draw
	 */
	public void render(int x, int y, boolean w, DrawGraphics g) {
		int p1 = u[((x + 1) * (y + x + 1)) % u.length];

		if (w) {
			ceiling[p1].render(x * Tile.tileSize - handler.getCamera().xOffset(),
					(y - 1) * Tile.tileSize - handler.getCamera().yOffset(), g);
			wall[p1].render(x * Tile.tileSize - handler.getCamera().xOffset(),
					y * Tile.tileSize - handler.getCamera().yOffset(), g);
		} else {
			floor[p1].render(x * Tile.tileSize - handler.getCamera().xOffset(),
					y * Tile.tileSize - handler.getCamera().yOffset(), g);
		}
	}

}
