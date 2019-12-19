package gui;

import gfx.DrawGraphics;
import runtime.Handler;

/**
 * base class for all GUI componenets
 * 
 * @author Pascal
 *
 */
public abstract class UIObject {

	public static Handler handler;
	
	protected int x, y;

	/**
	 * Draws this UI component to the screen
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public abstract void render(DrawGraphics g);

	/**
	 * Updates this UI component
	 */
	public abstract void update();
	
	public void display() {
		handler.getUI().addObject(this);
	}
	
	public void close() {
		handler.getUI().removeObject(this);
	}

}
