package gui;

import gfx.DrawGraphics;
import runtime.Handler;
import utility.Storeable;

public class ContainerButton extends Button{

	Storeable stored;
	
	public ContainerButton(ClickListener action, int x, int y, int width, int height, Storeable s, Handler handler) {
		super(action, x, y, width, height, handler);
		stored = s;
	}
	
	
	public void render(DrawGraphics g) {
		stored.renderInventory(x, y, g);
		if (hovered) stored.renderTextBox(x, y, g);
	}

}
