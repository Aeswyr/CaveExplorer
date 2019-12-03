package item;

import entity.Hitbox;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;
import utility.Storeable;
import utility.Utility;

/**
 * container which can store and display storeables
 * 
 * @author Pascal
 *
 * @param <T>
 */
public class ItemContainer<T extends Storeable> {

	String acceptedTags;
	T contained;
	Sprite container;
	Sprite empty;
	int x, y, amount;
	Handler handler;
	Hitbox h;

	/**
	 * Initializes an invisible item box
	 * 
	 * @param x       - x pos of the item box
	 * @param y       - y pos of the item box
	 * @param handler
	 */
	public ItemContainer(int x, int y, Handler handler) {
		this.x = x;
		this.y = y;
		this.handler = handler;
		h = new Hitbox(x, y, 32, 32, handler);
	}

	/**
	 * Initializes an item box with separate sprites for when it contains a
	 * storeable and when it is empty
	 * 
	 * @param x       - x pos of the item box
	 * @param y       - y pos of the item box
	 * @param s       - sprite to display when the box is full
	 * @param e       - sprite to display when the box is empty
	 * @param handler
	 */
	public ItemContainer(int x, int y, Sprite s, Sprite e, Handler handler) {
		this(x, y, handler);
		container = s;
		empty = e;
	}

	/**
	 * initializes an item box which can only hold storeables with the specified
	 * tags
	 * 
	 * @param x            - x pos of the item box
	 * @param y            - y pos of the item box
	 * @param s            - sprite to display when the box is full
	 * @param e            - sprite to display when the box is empty
	 * @param acceptedTags - string of accepted tags, separated by spaces
	 * @param handler
	 */
	public ItemContainer(int x, int y, Sprite s, Sprite e, String acceptedTags, Handler handler) {
		this(x, y, s, e, handler);
		this.acceptedTags = acceptedTags;
	}

	/**
	 * renders the item container and whatever is contained within
	 * 
	 * @param g - the DrawGraphics object associated with the Renderer
	 */
	public void render(DrawGraphics g) {
		if (contained != null) {
			if (container != null)
				container.render(x, y, g);
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

	/**
	 * updates and manages dragging items (specifically items, no other storeables)
	 * between containers
	 */
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

	/**
	 * @returns the storeable contained in this item box
	 */
	public T getContained() {
		return contained;
	}

	/**
	 * sets the sprite to display when the box is empty
	 * 
	 * @param s - new sprite to display
	 */
	public void setDefaultBackground(Sprite s) {
		empty = s;
	}

	/**
	 * stores a storeable in the item box
	 * 
	 * @param item - the storeable to store
	 * @returns true if the storeable is accepted, false otherwise
	 */
	public boolean store(T item) {
		if ((contained != null && !contained.canStack(item))
				|| (acceptedTags != null && !Utility.tagOverlaps(acceptedTags, item.getTags())))
			return false;
		if (contained == null)
			contained = item;
		amount++;
		return true;
	}

	/**
	 * stores an amount of storeables in the item box
	 * 
	 * @param item   - the storeable to store
	 * @param amount - the number of that storeable to store
	 * @returns true if the storeable is accepted, false otherwise
	 */
	public boolean store(T item, int amount) {
		if ((contained != null && !contained.canStack(item))
				|| (acceptedTags != null && !Utility.tagOverlaps(acceptedTags, item.getTags())))
			return false;
		if (contained == null)
			contained = item;
		this.amount += amount;
		return true;
	}

	/**
	 * removes a single unit of the currently stored storeable
	 */
	public void remove() {
		amount--;
		if (amount <= 0) {
			contained = null;
		}
	}

	/**
	 * removes a specified number of storeables from the box
	 * 
	 * @param num - the number of storeables to remove
	 * @returns the number of items which were actually removed
	 */
	public int remove(int num) {
		if (amount - num <= 0) {
			int s = amount;
			amount = 0;
			contained = null;
			return s;
		} else {
			amount -= num;
			return num;
		}
	}

	/**
	 * removes a single instance of an storeable from the item box. if this was the
	 * last instance, and the object is an item, strips all the active functions
	 * from that item before deleting it
	 */
	public void destroy() {
		amount--;
		if (amount <= 0) {
			if (contained instanceof Item)
				((Item) contained).strip();
			contained = null;
		}
	}

	/**
	 * @returns the number of storeables in the box
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * drops a single unit from the box into the world
	 */
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

	/**
	 * @returns true if the mouse is within this obejct's hitbox, false otherwise
	 */
	public boolean containsMouse() {
		return h.containsMouse();
	}

	/**
	 * @returns true if this box is empty, false otherwise
	 */
	public boolean isEmpty() {
		return (contained == null);
	}
}
