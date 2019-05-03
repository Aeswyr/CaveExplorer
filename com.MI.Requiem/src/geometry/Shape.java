package geometry;

import gfx.DrawGraphics;

public abstract class Shape {

	int[] raster;
	int width, height;
	int xpos, ypos;
	
	public abstract void contains(Shape s);
	
	public void render(DrawGraphics g) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				g.draw(this, x + xpos, y + ypos);
			}
		}
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
 	
}
