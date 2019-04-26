package world;

import java.awt.Graphics;

import gfx.SpriteSet;
import gfx.SpriteSheet;

public class TileSet extends SpriteSet {

	public TileSet(int x, int y, SpriteSheet sheet) {
		super(x, y, 8, 64, sheet);
	}

	public void render(int x, int y, int[] edges, boolean wall, Graphics g) {
		if (wall) {
			sprites[(((x * y) % 2) * 16 + 6)].render(x, y - Tile.tileSize, g);
			sprites[(((x * y) % 2) * 16 + 7)].render(x + Tile.tileSize / 2, y - Tile.tileSize, g);
			sprites[(((x * y) % 2) * 16 + 14)].render(x, y - Tile.tileSize / 2, g);
			sprites[(((x * y) % 2) * 16 + 15)].render(x + Tile.tileSize / 2, y - Tile.tileSize / 2, g);

			sprites[3 * 8 + 2 * ((x + y) % 2 + 2)].render(x, y, g);
			sprites[4 * 8 + 2 * ((x + y) % 2 + 3)].render(x + Tile.tileSize / 2, y, g);
			sprites[3 * 8 + 2 * ((x + y) % 2 + 2)].render(x, y + Tile.tileSize / 2, g);
			sprites[4 * 8 + 2 * ((x + y) % 2) + 3].render(x + Tile.tileSize / 2, y + Tile.tileSize / 2, g);
			
		} else {
			sprites[(((x * y) % 2 + 4) * 16 + 6)].render(x, y - Tile.tileSize, g);
			sprites[(((x * y) % 2 + 4) * 16 + 7)].render(x + Tile.tileSize / 2, y - Tile.tileSize, g);
			sprites[(((x * y) % 2 + 4) * 16 + 14)].render(x, y - Tile.tileSize / 2, g);
			sprites[(((x * y) % 2 + 4) * 16 + 15)].render(x + Tile.tileSize / 2, y - Tile.tileSize / 2, g);
		}
	}

}
