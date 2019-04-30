package gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import core.Driver;

public class Sprite {

	BufferedImage[] raw;
	int width, height;

	int xOff, yOff;

	int frames;
	long delta, lastTime;

	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		frames = 1;
		raw = new BufferedImage[frames];
		raw[0] = sheet.cut(x, y, size);

		width = size;
		height = size;

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int width, int height, SpriteSheet sheet) {
		frames = 1;
		raw = new BufferedImage[frames];
		raw[0] = sheet.cut(x, y, width, height);

		this.width = width;
		this.height = height;

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int width, int height, int frames, int fps, SpriteSheet sheet) {
		this.frames = frames;
		this.delta = 1000000000 / fps;

		this.width = width;
		this.height = height;

		raw = new BufferedImage[frames];
		for (int i = 0; i < frames; i++) {
			raw[i] = sheet.cut(x, y + height * i, width, height);
		}

		lastTime = System.nanoTime();

	}

	byte frame;

	public void render(int x, int y, Graphics g) {

		if (frames > 1) {
			if (System.nanoTime() - lastTime > delta) {
				lastTime = System.nanoTime();
				frame++;
				if (frame >= frames)
					frame = 0;
			}
		}
		g.drawImage(raw[frame], x - xOff, y - yOff, (int) (width * Driver.scale), (int) (height * Driver.scale), null);
	}

	public BufferedImage[] getRaw() {
		return raw;
	}

}
