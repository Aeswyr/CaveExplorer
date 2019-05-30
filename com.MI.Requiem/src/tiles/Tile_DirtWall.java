package tiles;

import java.util.ArrayList;

import core.Assets;
import entity.Mob;
import item.Item;
import items.Torch;
import runtime.Handler;
import world.Tile;

public class Tile_DirtWall extends Tile {

	public Tile_DirtWall() {
		this.tileSet = Assets.test;
		this.wall = true;
		this.breakable = true;
	}

	@Override
	public ArrayList<Item> tileDrop(Handler handler, Mob holder) {
		ArrayList<Item> drop = new ArrayList<Item>();
		drop.add(new Torch(handler, holder));
		return drop;
	}
	
}
