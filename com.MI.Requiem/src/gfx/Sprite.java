package gfx;

import java.awt.image.BufferedImage;
import runtime.Light;

public class Sprite {

	public static final int TYPE_ENTITY = 0;
	public static final int TYPE_GUI_COMPONENT = 1;
	public static final int TYPE_INVENTORY_ITEM = 2;
	public static final int TYPE_GUI_FOREGROUND_SHAPE = 3;
	public static final int TYPE_GUI_BACKGROUND_SHAPE = 4;
	public static final int TYPE_GUI_ITEM_SHAPE = 5;
	public static final int TYPE_ITEM_DROP = 6;
	public static final int TYPE_FLOOR = 7;
	public static final int TYPE_WALL = 8;
	public static final int TYPE_CEILING = 9;

	int lightInteraction = Light.NONE;

	int[][] raw;
	int width, height;

	int xOff, yOff;
	int priority = 0;
	/*
	 * PRIORITY NUMBERS 11 - shapes which are used to help display inventory items,
	 * such as use bars 10 - any inventory item display 9 - shapes which render over
	 * ui components 8 - ui components 7 - shapes which render below ui components
	 */

	int frames;
	long delta, lastTime;

	public Sprite(int width, int height, int[] raw, int type) {

		settings(type);

		frames = 1;

		this.raw = new int[frames][];
		this.raw[0] = raw;

		this.width = width;
		this.height = height;
	}

	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		frames = 1;

		width = size;
		height = size;
		BufferedImage b = sheet.cut(x, y, size);

		raw = new int[frames][];
		raw[0] = b.getRGB(0, 0, width, height, null, 0, width);

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int size, SpriteSheet sheet, int type) {
		settings(type);

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

	public Sprite(int x, int y, int width, int height, SpriteSheet sheet, int type) {

		settings(type);

		frames = 1;
		BufferedImage b = sheet.cut(x, y, width, height);

		raw = new int[frames][];
		raw[0] = b.getRGB(0, 0, width, height, null, 0, width);

		this.width = width;
		this.height = height;

		lastTime = System.nanoTime();
	}

	public Sprite(int x, int y, int width, int height, int frames, int fps, SpriteSheet sheet, int type) {

		settings(type);

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
		g.submitRequest(new SpriteRequest(this, priority, x - xOff, y - yOff));
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

	public void setPriority(int value) {
		this.priority = value;
	}

	public void settings(int type) {
		switch (type) {
		case TYPE_GUI_COMPONENT:
			lightInteraction = Light.IGNORE;
			priority = 108;
			break;
		case TYPE_INVENTORY_ITEM:
			lightInteraction = Light.IGNORE;
			priority = 1010;
			break;
		case TYPE_GUI_BACKGROUND_SHAPE:
			lightInteraction = Light.IGNORE;
			priority = 107;
			break;
		case TYPE_GUI_FOREGROUND_SHAPE:
			lightInteraction = Light.IGNORE;
			priority = 109;
			break;
		case TYPE_GUI_ITEM_SHAPE:
			lightInteraction = Light.IGNORE;
			priority = 1011;
			break;
		case TYPE_ITEM_DROP:
			lightInteraction = Light.NONE;
			priority = 1;
			break;
		case TYPE_ENTITY:
			lightInteraction = Light.NONE;
			priority = 1;
			break;
		case TYPE_FLOOR:
			lightInteraction = Light.NONE;
			priority = 0;
			break;
		case TYPE_WALL:
			lightInteraction = Light.NONE;
			priority = 0;
			break;
		case TYPE_CEILING:
			lightInteraction = Light.FULL;
			priority = 1;
			break;
		default:
			break;
		}
	}
}
