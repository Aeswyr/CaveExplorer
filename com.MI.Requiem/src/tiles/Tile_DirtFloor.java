package tiles;

import core.Assets;
import world.Tile;

public class Tile_DirtFloor extends Tile{

	public Tile_DirtFloor() {
		super(0);
		this.tileSet = Assets.test;
		this.breakable = false;
	}
	
}
