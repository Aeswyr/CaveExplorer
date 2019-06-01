package world;

import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteSheet;
import runtime.Handler;
import runtime.Light;

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

	public TileSet(int x, int y, SpriteSheet sheet) {
		floor = new Sprite[2];
		floor[0] = new Sprite(x + 48, y + 32, 16, sheet, Sprite.TYPE_FLOOR);
		floor[1] = new Sprite(x + 48, y + 48, 16, sheet, Sprite.TYPE_FLOOR);
		wall = new Sprite[2];
		wall[0] = new Sprite(x + 16, y + 24, 16, sheet, Sprite.TYPE_WALL);
		wall[1] = new Sprite(x + 32, y + 24, 16, sheet, Sprite.TYPE_WALL);
		ceiling = new Sprite[2];
		ceiling[0] = new Sprite(x + 48, y + 0, 16, sheet, Sprite.TYPE_CEILING);
		ceiling[1] = new Sprite(x + 48, y + 16, 16, sheet, Sprite.TYPE_CEILING);

	}

	public void render(int x, int y, int[] edges, boolean w, DrawGraphics g) {
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
