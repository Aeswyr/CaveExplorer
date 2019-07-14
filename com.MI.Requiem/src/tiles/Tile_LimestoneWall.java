package tiles;

import java.util.ArrayList;

import core.Assets;
import item.Item;
import items.TileBlock;
import runtime.Handler;
import world.Tile;

public class Tile_LimestoneWall extends Tile{

	public Tile_LimestoneWall() {
		super(6);
		this.tileSet = Assets.limestoneTile;
		this.wall = true;
		this.breakable = true;
		this.solid = true;
	}
	
	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new TileBlock(x, y, handler, id));
		return drop;
	}

}
