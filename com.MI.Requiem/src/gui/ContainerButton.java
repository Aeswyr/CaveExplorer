package gui;

import gfx.DrawGraphics;
import utility.Storeable;

/**
 * A button object which can display a storeable object within it
 * 
 * @author Pascal
 *
 */
public class ContainerButton extends Button {

	Storeable stored;

	/**
	 * Initializes a container button
	 * 
	 * @param action  - action to perform on click
	 * @param x       - x position of this button
	 * @param y       - y position of this button
	 * @param width   - width of the button hitbox
	 * @param height  - height of the button hitbox
	 * @param s       - storeable to display in the button
	 * @param handler
	 */
	public ContainerButton(ClickListener action, int x, int y, int width, int height, Storeable s) {
		super(action, x, y, width, height);
		stored = s;
	}

	public void render(DrawGraphics g) {
		stored.getAsset().render(x, y, g);
		if (hovered)
			stored.renderTextBox(x, y, g);
	}

}
