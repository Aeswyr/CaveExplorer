package tiles;

import java.util.ArrayList;

import core.Assets;
import item.Item;
import items.TileBlock;
import runtime.Handler;
import world.Tile;

public class Tile_Anvil extends Tile {

	public Tile_Anvil() {
		super(2);
		this.tileSet = Assets.anvil;
		this.breakable = true;
	}

	
	@Override
	public ArrayList<Item> tileDrop(int x, int y, Handler handler) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new TileBlock(x, y, handler, id));
		return drop;
	}
	
}
