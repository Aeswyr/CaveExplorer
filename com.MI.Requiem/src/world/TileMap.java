package world;

import gfx.DrawGraphics;

public abstract class TileMap {
	
	public abstract void render(int x, int y, boolean w, int mask, DrawGraphics g);
	
}
