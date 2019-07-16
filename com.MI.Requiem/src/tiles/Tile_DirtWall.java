package tiles;

import java.util.ArrayList;
import core.Assets;
import item.Item;
import items.TileBlock;
import runtime.Handler;
import world.Tile;

public class Tile_DirtWall extends Tile {

	public Tile_DirtWall() {
		super(1);
		this.tileSet = Assets.dirtTile;
		this.wall = true;
		this.breakable = true;
		this.solid = true;
	}

	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new TileBlock(x, y, handler, id));
		if (rng.nextDouble() < 0.1)drop.add(new TileBlock(x, y, handler, 9));
		return drop;
	}

}
