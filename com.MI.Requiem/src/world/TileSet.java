package world;

import java.awt.Graphics;

import gfx.SpriteSet;
import gfx.SpriteSheet;

public class TileSet extends SpriteSet {

	private int[] u = { 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1 };

	public TileSet(int x, int y, SpriteSheet sheet) {
		super(x, y, 8, 8, sheet);
	}

	public void render(int x, int y, int[] edges, boolean wall, Graphics g) {
		int p1 = u[((x + 1) * (y + x + 1)) % u.length];
		int p2 = u[(x + (y + x) ^ 2) % u.length];

		if (wall) {
			sprites[(p1 * 16 + 6)].render(x * Tile.tileSize, y * Tile.tileSize - Tile.tileSize, g);
			sprites[(p1 * 16 + 7)].render(x * Tile.tileSize + Tile.tileSize / 2, y * Tile.tileSize - Tile.tileSize, g);
			sprites[(p1 * 16 + 14)].render(x * Tile.tileSize, y * Tile.tileSize - Tile.tileSize / 2, g);
			sprites[(p1 * 16 + 15)].render(x * Tile.tileSize + Tile.tileSize / 2, y * Tile.tileSize - Tile.tileSize / 2,
					g);

			sprites[3 * 8 + 2 * (p2 + 2)].render(x * Tile.tileSize, y * Tile.tileSize, g);
			sprites[4 * 8 + 2 * (p2 + 3)].render(x * Tile.tileSize + Tile.tileSize / 2, y * Tile.tileSize, g);
			sprites[3 * 8 + 2 * (p2 + 2)].render(x * Tile.tileSize, y * Tile.tileSize + Tile.tileSize / 2, g);
			sprites[4 * 8 + 2 * (p2 + 3)].render(x * Tile.tileSize + Tile.tileSize / 2,
					y * Tile.tileSize + Tile.tileSize / 2, g);

		} else {
			sprites[((p1 + 2) * 16 + 6)].render(x * Tile.tileSize, y * Tile.tileSize - Tile.tileSize, g);
			sprites[((p1 + 2) * 16 + 7)].render(x * Tile.tileSize + Tile.tileSize / 2,
					y * Tile.tileSize - Tile.tileSize, g);
			sprites[((p1 + 2) * 16 + 14)].render(x * Tile.tileSize, y * Tile.tileSize - Tile.tileSize / 2, g);
			sprites[((p1 + 2) * 16 + 15)].render(x * Tile.tileSize + Tile.tileSize / 2,
					y * Tile.tileSize - Tile.tileSize / 2, g);
		}
	}

}
