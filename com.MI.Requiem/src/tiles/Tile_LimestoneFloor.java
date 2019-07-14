package tiles;

import core.Assets;
import world.Tile;

public class Tile_LimestoneFloor extends Tile{

	public Tile_LimestoneFloor() {
		super(5);
		this.tileSet = Assets.limestoneTile;
		this.breakable = false;
	}

}
