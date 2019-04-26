package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite {

	BufferedImage raw;
	
	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		raw = sheet.cut(x, y, size);
	}
	
	public void render(int x, int y, Graphics g) {
		g.drawImage(raw, x, y, null);
	}
	
}
