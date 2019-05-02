package gfx;

import java.awt.image.BufferedImage;
import runtime.Light;

public class Sprite {

	int lightInteraction = Light.NONE;

	int[][] raw;
	int width, height;

	int xOff, yOff;

	int frames;
	long delta, lastTime;

	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		frames = 1;

		width = size;
		height = size;
		BufferedImage b = sheet.cut(x, y, size);

		raw = new int[frames][];
		raw[0] = b.getRGB(0, 0, width, height, null, 0, width);

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int width, int height, SpriteSheet sheet) {
		frames = 1;
		BufferedImage b = sheet.cut(x, y, width, height);

		raw = new int[frames][];
		raw[0] = b.getRGB(0, 0, width, height, null, 0, width);

		this.width = width;
		this.height = height;

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int width, int height, int frames, int fps, SpriteSheet sheet) {
		this.frames = frames;
		this.delta = 1000000000 / fps;

		this.width = width;
		this.height = height;

		raw = new int[frames][];
		for (int i = 0; i < frames; i++) {
			BufferedImage b = sheet.cut(x, y + height * i, width, height);
			raw[i] = b.getRGB(0, 0, width, height, null, 0, width);
		}

		lastTime = System.nanoTime();

	}

	byte frame;

	public void render(int x, int y, DrawGraphics g) {

		if (frames > 1) {
			if (System.nanoTime() - lastTime > delta) {
				lastTime = System.nanoTime();
				frame++;
				if (frame >= frames)
					frame = 0;
			}
		}
		g.draw(this, x - xOff, y - yOff);
	}

	protected int[] getRawFrame() {
		return raw[frame];
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setLightInteraction(int value) {
		this.lightInteraction = value;
	}

	public int getLightInteraction() {
		return lightInteraction;
	}
}
