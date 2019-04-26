package gfx;

import java.awt.image.BufferedImage;

public class Sprite {

	BufferedImage raw;
	
	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		raw = sheet.cut(x, y, size);
	}
	
}
