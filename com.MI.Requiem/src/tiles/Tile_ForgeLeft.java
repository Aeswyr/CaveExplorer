package tiles;

import java.util.ArrayList;

import core.Assets;
import item.Item;
import items.Forge;
import runtime.Handler;
import world.Tile;

public class Tile_ForgeLeft extends Tile{

	protected Tile_ForgeLeft() {
		super(3);
		
		this.tileSet = Assets.forgeLeft;
		
		this.wall = true;
		this.breakable = true;
		this.solid = true;
	}
	
	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new Forge(x, y, handler));
		return drop;
	}

}
