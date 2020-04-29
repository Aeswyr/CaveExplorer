package world;

import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteData;
import gfx.SpriteSheet;
import runtime.Handler;

public class TileSet extends TileMap {

	private int[] u = { 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0,
			1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1 };

	Sprite[] edge;
	Sprite[] floor;
	Sprite[] ceiling;
	Sprite[] wall;

	public TileSet(int x, int y, SpriteSheet sheet) {
		edge = new Sprite[10];
		floor = new Sprite[2];
		ceiling = new Sprite[2];
		wall = new Sprite[2];
		
		int n = 0;
		for (int i = 0; i < 16; i++) {
			if (i / 4 != 3 && i != 6 && i != 10) {
				int type = SpriteData.TYPE_CEILING;
				if (i/4 == 2) type = SpriteData.TYPE_WALL;
				edge[n] = new Sprite(x + 16 * (i % 4), y + 16 * (i/4), 16, sheet, type);
						n++;
			}
		}
		
		floor[0] =new Sprite(x + 32, y + 48, 16, sheet, SpriteData.TYPE_FLOOR);
		floor[1] =new Sprite(x + 48, y + 48, 16, sheet, SpriteData.TYPE_FLOOR);
		wall[0] =new Sprite(x + 32, y + 32, 16, sheet, SpriteData.TYPE_WALL);
		wall[1] =new Sprite(x + 16, y + 48, 16, sheet, SpriteData.TYPE_WALL);
		ceiling[0] =new Sprite(x + 32, y + 16, 16, sheet, SpriteData.TYPE_CEILING);
		ceiling[1] =new Sprite(x, y + 48, 16, sheet, SpriteData.TYPE_CEILING);
		
	}

	@Override
	public void render(int x, int y, boolean w, int mask, DrawGraphics g) {
		int p1 = u[((x + 1) * (y + x + 1)) % u.length];
		if (w) {

			switch (mask) {
			case 0:
				edge[0].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[7].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 1:
				edge[3].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[9].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 2:
				edge[0].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[7].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 3:
				edge[3].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 4:
				edge[1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[8].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 5:
				edge[2].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 6:
				edge[1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 7:
				edge[2].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 8:
				edge[4].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[7].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 9:
				edge[6].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[9].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 10:
				edge[4].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 11:
				edge[6].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 12:
				edge[5].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				edge[8].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 13:
				ceiling[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 14:
				edge[5].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			case 15:
				ceiling[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						(y - 1) * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				wall[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
						y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
				break;
			}
		} else {
			floor[p1].render(x * Tile.TILE_SIZE - Handler.getCamera().xOffset(),
					y * Tile.TILE_SIZE - Handler.getCamera().yOffset(), g);
		}

	}

}
