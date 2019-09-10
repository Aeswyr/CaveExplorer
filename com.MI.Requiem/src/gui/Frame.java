package gui;

import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;

public class Frame extends UIObject {
	private Square frame;

	public Frame(int x, int y, int width, int height, int color, boolean border) {
		this.x = x;
		this.y = y;

		if (border)
			frame = new Square(width, height, color, 0xff000000, Sprite.TYPE_GUI_COMPONENT);
		else
			frame = new Square(width, height, color, Sprite.TYPE_GUI_COMPONENT);
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
