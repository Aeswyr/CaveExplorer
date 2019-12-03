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
	public Frame(int x, int y, int width, int height, int color, boolean border) {
		this.x = x;
		this.y = y;

		if (border)
			frame = new Rect(width, height, color, 0xff000000, Sprite.TYPE_GUI_COMPONENT);
		else
			frame = new Rect(width, height, color, Sprite.TYPE_GUI_COMPONENT);
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
