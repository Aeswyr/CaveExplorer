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
			else {
				contained.renderInventory(x, y, g);
				if (amount > 1)
					g.write("" + amount, x + 20, y + 18);
				if (this.h.containsMouse())
					contained.renderTextBox(x, y, g);
			}
		} else {
			if (empty != null)
				empty.render(x, y, g);
		}
	}

	@SuppressWarnings("unchecked")
	public void update() {
		if (this instanceof ItemContainer<?>) {
			if ((handler.getMouse().getLeft() || handler.getMouse().getRight() || handler.getMouse().getMiddle())
					&& h.containsMouse()) {
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
		if ((contained != null && !contained.canStack(item))
				|| (acceptedTags != null && !Utility.tagOverlaps(acceptedTags, item.getTags())))
			return false;
		if (contained == null)
			contained = item;
		amount++;
		return true;
	}

	public boolean store(T item, int amount) {
		if ((contained != null && !contained.canStack(item))
				|| (acceptedTags != null && !Utility.tagOverlaps(acceptedTags, item.getTags())))
			return false;
		if (contained == null)
			contained = item;
		this.amount += amount;
		return true;
	}

	public void remove() {
		amount--;
		if (amount <= 0) {
			contained = null;
		}
	}

	public int remove(int num) {
		if (amount - num <= 0) {
			amount = 0;
			contained = null;
			return amount;
		} else {
			amount -= num;
			return num;
		}
	}
	
	public void destroy() {
		amount--;
		if (amount <= 0) {
			if (contained instanceof Item) ((Item) contained).strip();
			contained = null;
		}
	}

	public int getAmount() {
		return amount;
	}

	public void drop() {
		if (contained instanceof Item) {
			try {
				Item i = (Item) ((Item) contained).clone();
				i.drop();
				remove();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public boolean containsMouse() {
		return h.containsMouse();
	}

	public boolean isEmpty() {
		return (contained == null);
	}
}
