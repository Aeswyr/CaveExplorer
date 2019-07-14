package tiles;

import java.util.ArrayList;

import core.Assets;
import item.Item;
import items.Gem;
import items.Ore;
import items.TileBlock;
import runtime.Handler;
import world.Tile;

public class Tile_IronVein extends Tile{

	public Tile_IronVein() {
		super(7);
		this.tileSet = Assets.ironOreTile;
		this.wall = true;
		this.breakable = true;
		this.solid = true;
	}
	
	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		if (Math.random() < 0.20) drop.add(new Ore(x, y, handler, 0));
		if (Math.random() < 0.30) drop.add(new TileBlock(x, y, handler, 6));
		if (Math.random() < 0.3) drop.add(new Gem(x, y, handler, 0));
		return drop;
	}

}
