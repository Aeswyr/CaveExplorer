package world;

import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteData;
import gfx.SpriteSheet;
import runtime.Handler;

public class TileSingle extends TileMap {
	Sprite[] tile;

	public TileSingle(int x, int y, SpriteSheet sheet) {
		tile = new Sprite[2];
		tile[1] = new Sprite(x, y, 16, sheet, SpriteData.TYPE_CEILING);
		tile[0] = new Sprite(x, y + 16, 16, sheet, SpriteData.TYPE_WALL);
	}
	
	public TileSingle(int x, int y, SpriteSheet sheet, int single) {
		tile = new Sprite[1];
		tile[0] = new Sprite(x, y, 16, sheet, SpriteData.TYPE_FLOOR);
	}
	
	public TileSingle(int x, int y, SpriteSheet sheet, boolean overlay) {
		tile = new Sprite[2];
		tile[1] = new Sprite(x, y, 16, sheet, SpriteData.TYPE_FLOOR);
		tile[0] = new Sprite(x, y + 16, 16, sheet, SpriteData.TYPE_FLOOR);
	}

	@Override
	public void render(int x, int y, boolean w, int mask, DrawGraphics g) {
		if (w) {
			tile[1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
					(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
			tile[0].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
					y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
		} else {
		tile[0].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
				y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
		}
	}

}
