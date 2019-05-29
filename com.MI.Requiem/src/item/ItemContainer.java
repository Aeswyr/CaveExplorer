package item;

import entity.Hitbox;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;
import utility.Storeable;
import utility.Utility;

public class ItemContainer<T extends Storeable> {

	String acceptedTags;
	T contained;
	Sprite container;
	Sprite empty;
	int x, y, amount;
	Handler handler;
	Hitbox h;

	public ItemContainer(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		h = new Hitbox(x, y, 32, 32, handler);
	}

	public ItemContainer(int x, int y, Sprite s, Sprite e, Handler handler) {
		this(x, y, handler);
		container = s;
		empty = e;
	}

	public ItemContainer(int x, int y, Sprite s, Sprite e, String acceptedTags, Handler handler) {
		this(x, y, s, e, handler);
		this.acceptedTags = acceptedTags;
	}

	public void render(DrawGraphics g) {
		if (container != null)
			container.render(x, y, g);
		if (contained != null) {
			if (this.equals(handler.getMouse().getStartHovered()))
				contained.renderInventory(handler.getMouse().getAdjX(), handler.getMouse().getAdjY(), g);
			else
				contained.renderInventory(x, y, g);
		} else {
			if (empty != null)
				empty.render(x, y, g);
		}
	}

	@SuppressWarnings("unchecked")
	public void update() {
		if (this instanceof ItemContainer<?>) {
			if (handler.getMouse().getLeft() && h.containsMouse()) {
				if (handler.getMouse().getStartHovered() == null)
					handler.getMouse().setStartHovered((ItemContainer<Item>) this);
				else {
					handler.getMouse().setEndHovered((ItemContainer<Item>) this);
				}
			}
		}
	}

	public T getContained() {
		return contained;
	}

	public void setDefaultBackground(Sprite s) {
		empty = s;
	}

	public boolean store(T item) {
		if ((contained != null && !contained.equals(item)) || (acceptedTags != null && !Utility.tagOverlaps(acceptedTags, item.getTags())))
			return false;
		if (contained == null)
		contained = item;
		amount++;
		return true;
	}
	
	public void remove() {
		amount--;
		if (amount <= 0) contained = null;
	}

	public boolean containsMouse() {
		return h.containsMouse();
	}
}
