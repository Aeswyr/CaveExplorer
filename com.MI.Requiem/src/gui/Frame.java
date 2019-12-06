package gui;

import geometry.Rect;
import gfx.DrawGraphics;
import gfx.Sprite;

/**
 * blank frame UI object
 * 
 * @author Pascal
 *
 */
public class Frame extends UIObject {
	private Rect frame;

	/**
	 * Initializes a frame
	 * 
	 * @param x      - x position of the frame
	 * @param y      - y position of the frame
	 * @param width  - width of the frame
	 * @param height - height of the frame
	 * @param color  - primary color of the frame
	 * @param border - border color of the frame
	 */
	public Frame(int x, int y, int width, int height, int color, int border) {
		this.x = x;
		this.y = y;

		if (color != border)
			frame = new Rect(width, height, color, border, Sprite.TYPE_GUI_BACKGROUND_SHAPE);
		else
			frame = new Rect(width, height, color, Sprite.TYPE_GUI_BACKGROUND_SHAPE);
	}

	/**
	 * Initializes a frame
	 * 
	 * @param x      - x position of the frame
	 * @param y      - y position of the frame
	 * @param width  - width of the frame
	 * @param height - height of the frame
	 * @param color  - primary color of the frame
	 * @param border - border color of the frame
	 * @param type   - render type for gui sprite
	 */
	public Frame(int x, int y, int width, int height, int color, int border, int type) {
		this.x = x;
		this.y = y;

		if (color != border)
			frame = new Rect(width, height, color, border, type);
		else
			frame = new Rect(width, height, color, type);
	}

	@Override
	public void render(DrawGraphics g) {
		frame.render(x, y, g);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
