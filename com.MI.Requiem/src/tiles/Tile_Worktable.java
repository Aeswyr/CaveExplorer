package tiles;

import java.util.ArrayList;

import core.Assets;
import item.Item;
import items.Worktable;
import runtime.Handler;
import world.Tile;

public class Tile_Worktable extends Tile{

	public Tile_Worktable() {
		super(10);
		this.tileSet = Assets.worktableTile;
		this.breakable = true;
		this.solid = true;
	}

	
	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new Worktable(x, y, handler));
		return drop;
	}
	
}
