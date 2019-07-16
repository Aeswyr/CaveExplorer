package tiles;

import core.Assets;
import world.Tile;

public class Tile_ClayFloor extends Tile{

	public Tile_ClayFloor() {
		super(8);
		this.tileSet = Assets.clayTile;
		this.breakable = false;
	}
	
}
