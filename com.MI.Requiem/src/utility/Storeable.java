package utility;

import gfx.DrawGraphics;
import gfx.Sprite;

/**
 * interface for objects which can be held in an inventory screen (items/abilities/enemies/etc)
 * @author Pascal
 *
 */
public interface Storeable {

	/**
	 * Draws the storeable object within an inventory
	 * @param x - x position to render at
	 * @param y - y position to render at
	 * @param g - DrawGraphics component associated with the renderer
	 */
	public void renderInventory(int x, int y, DrawGraphics g);
	
	/**
	 * Draws a text box containing information about the object
	 * @param x - x position to render at
	 * @param y - y position to render at
	 * @param g - DrawGraphics component associated with the renderer
	 */
	public void renderTextBox(int x, int y, DrawGraphics g);
	
	/**
	 * gets the string of tags within the storeable object
	 * @returns tags associated with this storeable object
	 */
	public String getTags();
	
	/**
	 * gets the display sprite of the storeable object
	 * @returns the sprite associated with this storeable object
	 */
	public Sprite getAsset();
	
	/**
	 * returns whether or not the storeable s can stack with this storeable
	 * @param s - storeable object to test
	 * @returns true if they can stack, false otherwise
	 */
	public boolean canStack(Storeable s);
}
