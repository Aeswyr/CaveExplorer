package gui;

import entity.Hitbox;
import geometry.Rect;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

/**
 * A clickable UI component which perfomes a specified action on click
 * 
 * @author Pascal
 *
 */
public class Button extends UIObject {

	private ClickListener action;
	protected boolean hovered = false;
	private boolean visible = false;
	private Hitbox hitbox;
	private Handler handler;
	private Rect base, hover;
	String text = null;

	/**
	 * create an invisible button
	 * 
	 * @param action  - action to perform on click
	 * @param x       - x position of this button
	 * @param y       - y position of this button
	 * @param width   - width of the button hitbox
	 * @param height  - height of the button hitbox
	 * @param handler
	 */
	public Button(ClickListener action, int x, int y, int width, int height, Handler handler) {
		this.action = action;
		hitbox = new Hitbox(x, y, width, height, handler);
		this.handler = handler;
		this.x = x;
		this.y = y;
	}

	/**
	 * creates a visible, blank button
	 * 
	 * @param action  - action to perform on click
	 * @param x       - x position of this button
	 * @param y       - y position of this button
	 * @param width   - width of the button hitbox
	 * @param height  - height of the button hitbox
	 * @param color1  - color of the button
	 * @param color2  - color of the button when the mouse hovers over it
	 * @param handler
	 */
	public Button(ClickListener action, int x, int y, int width, int height, int color1, int color2, Handler handler) {
		visible = true;
		this.action = action;
		hitbox = new Hitbox(x, y, width, height, handler);
		this.handler = handler;
		this.x = x;
		this.y = y;

		base = new Rect(width, height, color1, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
		hover = new Rect(width, height, color2, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
	}

	/**
	 * create a button with text
	 * 
	 * @param text    - text to display on the button
	 * @param action  - action to perform on click
	 * @param x       - x position of this button
	 * @param y       - y position of this button
	 * @param width   - width of the button hitbox
	 * @param height  - height of the button hitbox
	 * @param color1  - color of the button
	 * @param color2  - color of the button when the mouse hovers over it
	 * @param handler
	 */
	public Button(String text, ClickListener action, int x, int y, int width, int height, int color1, int color2,
			Handler handler) {
		visible = true;
		this.action = action;
		hitbox = new Hitbox(x, y, width, height, handler);
		this.handler = handler;
		this.x = x;
		this.y = y;

		this.text = text;

		base = new Rect(width, height, color1, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
		hover = new Rect(width, height, color2, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
	}

	@Override
	public void render(DrawGraphics g) {
		if (visible) {
			if (hovered)
				hover.render(x, y, g);
			else
				base.render(x, y, g);

			if (text != null)
				g.write(text, x, y, 0xff000000);
		}
	}

	boolean lastMouseFrame = false;

	@Override
	public void update() {
		if (hitbox.containsMouse()) {
			this.hovered = true;
			if (lastMouseFrame == true && !handler.getMouse().getLeft())
				doClick();
			lastMouseFrame = handler.getMouse().getLeft();

		} else {
			this.hovered = false;
			lastMouseFrame = false;
		}
	}

	/**
	 * @returns true if the mouse is within this button's hitbox, false otherwise
	 */
	public boolean getHovered() {
		return hovered;
	}

	/**
	 * Executes the button's onClick action
	 */
	public void doClick() {
		action.onClick(this);
	}

}
