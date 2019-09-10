package gui;

import entity.Hitbox;
import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

public class Button extends UIObject {

	private ClickListener action;
	protected boolean hovered = false;
	private boolean visible = false;
	private Hitbox hitbox;
	private Handler handler;
	private Square base, hover;
	String text = null;
	
	/**
	 * create an invisible button
	 * @param action
	 * @param x
	 * @param y
	 * @param width
	 * @param height
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
	 * @param action
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color1
	 * @param color2
	 * @param handler
	 */
	public Button(ClickListener action, int x, int y, int width, int height, int color1, int color2, Handler handler) {
		visible = true;
		this.action = action;
		hitbox = new Hitbox(x, y, width, height, handler);
		this.handler = handler;
		this.x = x;
		this.y = y;
		
		base = new Square(width, height, color1, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
		hover = new Square(width, height, color2, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
	}
	
	/**
	 * create a button with text
	 * @param text
	 * @param action
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color1
	 * @param color2
	 * @param handler
	 */
	public Button(String text, ClickListener action, int x, int y, int width, int height, int color1, int color2, Handler handler) {
		visible = true;
		this.action = action;
		hitbox = new Hitbox(x, y, width, height, handler);
		this.handler = handler;
		this.x = x;
		this.y = y;
		
		this.text = text;
		
		base = new Square(width, height, color1, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
		hover = new Square(width, height, color2, 0xffffffff, Sprite.TYPE_GUI_COMPONENT);
	}

	@Override
	public void render(DrawGraphics g) {
		if (visible) {
			if  (hovered) hover.render(x, y, g);
			else base.render(x, y, g);
			
			if (text != null) g.write(text, x, y, 0xff000000);
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

	public boolean getHovered() {
		return hovered;
	}

	public void doClick() {
		action.onClick(this);
	}

}
