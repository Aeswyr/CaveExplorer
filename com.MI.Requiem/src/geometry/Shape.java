package geometry;

import gfx.DrawGraphics;
import gfx.Sprite;
import gfx.SpriteRequest;

public abstract class Shape {

	int[] raster;
	int width, height;
	int xpos, ypos;
	int type;
	int color;
	Sprite sprite;
	
	public abstract boolean contains(Shape s);
	
	public void render(int x, int y, DrawGraphics g) {
		g.submitRequest(new SpriteRequest(this, x, y));
	}
	
	public int[] getRaster() {
		return raster;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Sprite toSprite() {
		return sprite;
	}
 	
}
