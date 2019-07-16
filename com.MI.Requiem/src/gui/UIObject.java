package gui;

import gfx.DrawGraphics;

public abstract class UIObject {

	protected int x, y;
	
	public abstract void render(DrawGraphics g);
	
	public abstract void update();
	
}
