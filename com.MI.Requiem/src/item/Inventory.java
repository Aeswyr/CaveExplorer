package item;

import java.io.Serializable;
import java.util.ArrayList;
import core.Assets;
import crafting.Tag;
import entity.Entity;
import entity.Mob;
import gfx.DrawGraphics;
import runtime.Handler;
import utility.Utility;

/**
 * Object for storing large amounts of items and drawing them to the screen
 * 
 * @author Pascal
 *
 */
public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 491127628628667496L;
	private ArrayList<ItemContainer<Item>> storage;
	private ArrayList<ItemContainer<Item>> extra;
	transient private Handler handler;
	transient private Entity owner;
	private int x, y, size;

	/**
	 * initializes an inventory with specified size and position
	 * 
	 * @param x       - x position for the top leftmost box of the inventory to
	 *                render
	 * @param y       - y position for the top leftmost box of the inventory to
	 *                render
	 * @param size    - number of base storage spots in the inventory
	 * @param handler
	 * @param e       - the entity who this inventory belongs to
	 */
	public Inventory(int x, int y, int size, Handler handler, Entity e) {
		storage = new ArrayList<ItemContainer<Item>>();
		extra = new ArrayList<ItemContainer<Item>>();
		this.owner = e;
		this.size = size;
		this.handler = handler;
		this.x = x;
		this.y = y;
		for (int i = 0; i < size; i++) {
			storage.add(new ItemContainer<Item>((i % 3) * 40 + x, (i / 3) * 40 + y, Assets.inventory_Empty,
					Assets.inventory_Empty, handler));
		}
	}

	/**
	 * initializes an inventory with specified size, width, and position
	 * 
	 * @param x       - x position for the top leftmost box of the inventory to
	 *                render
	 * @param y       - y position for the top leftmost box of the inventory to
	 *                render
	 * @param size    - number of base storage spots in the inventory
	 * @param width   - the number of slots per row of the inventory
	 * @param handler
	 * @param e       - the entity who this inventory belongs to
	 */
	public Inventory(int x, int y, int size, int width, Handler handler, Entity e) {
		storage = new ArrayList<ItemContainer<Item>>();
		extra = new ArrayList<ItemContainer<Item>>();
		this.owner = e;
		this.size = size;
		this.handler = handler;
		this.x = x;
		this.y = y;
		for (int i = 0; i < size; i++) {
			storage.add(new ItemContainer<Item>((i % width) * 40 + x, (i / width) * 40 + y, Assets.inventory_Empty,
					Assets.inventory_Empty, handler));
		}
	}

	/**
	 * initializes a basic inventory with a size of 12
	 * 
	 * @param handler
	 * @param e       - the entity who this inventory belongs to
	 */
	public Inventory(Handler handler, Entity e) {
		this(0, 0, 12, handler, e);
	}

	/**
	 * Draws all item containers associated with this inventory
	 * 
	 * @param g
	 */
	public void render(DrawGraphics g) {
		for (int i = 0; i < size; i++) {
			storage.get(i).render(g);
		}
		for (int i = 0; i < extra.size(); i++) {
			extra.get(i).render(g);
		}
	}

	/**
	 * updates all stored items, as well as removes items that have been consumed
	 */
	public void update() {
		Item item = null;
		for (int i = 0; i < size; i++) {
			storage.get(i).update();
			item = storage.get(i).getContained();
			if (item != null) {
				if (item.holder != this.owner)
					item.setHolder(owner);
				if (item.equipped())
					item.onDequip();
				item.update();
				if (item.consumed) {
					item.setConsumed(false);
					storage.get(i).remove();
				}
			}
		}
		for (int i = 0; i < extra.size(); i++) {
			extra.get(i).update();
			item = extra.get(i).getContained();
			if (item != null) {
				if (item.holder != this.owner)
					item.setHolder(owner);
				if (!item.equipped())
					item.onEquip();
				item.update();
				if (item.consumed) {
					extra.get(i).remove();
					if (extra.get(i).contained == null)
						item.onDequip();
					else
						item.setConsumed(false);
				}
			}
		}
	}

	/**
	 * resizes the inventory, drops excess items if the inventory will not be big
	 * enough to accomodate
	 * 
	 * @param size - the new size of the inventory
	 */
	public void resize(int size) {
		if (size > this.size) {

			for (int i = this.size; i < size; i++) {
				storage.add(new ItemContainer<Item>((i % 3) * 40 + x, (i / 3) * 40 + y, Assets.inventory_Empty, null,
						handler));
			}

		} else if (size < this.size) {
			ArrayList<ItemContainer<Item>> hold = storage;
			storage = new ArrayList<ItemContainer<Item>>();
			for (int i = 0; i < hold.size(); i++) {
				if (i < size)
					storage.add(hold.get(i));
				else if (!hold.get(i).isEmpty())
					hold.get(i).getContained().drop();
			}
		}
		this.size = size;
	}

	/**
	 * @returns the size of the inventory
	 */
	public int getSize() {
		return size;
	}

	/**
	 * attempts to add an item to the inventory
	 * 
	 * @param item - the item to add to the inventory
	 * @returns true if the item was successfully added, false otherwise
	 */
	public boolean add(Item item) {
		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).getContained() != null && storage.get(i).getContained().canStack(item)) {
				storage.get(i).store(item);
				return true;
			}
		}
		for (int i = 0; i < storage.size(); i++) {
			if (storage.get(i).getContained() == null) {
				storage.get(i).store(item);
				return true;
			}
		}
		return false;
	}

	/**
	 * adds an extra item container to this inventory which is separate of the main
	 * inventory's functions
	 * 
	 * @param c - the new itemcontainer
	 */
	public void appendContainer(ItemContainer<Item> c) {
		extra.add(c);
	}

	/**
	 * equips an item
	 * 
	 * @param item - the item to equip
	 * @returns true if the item is successfully eqipt, false otherwise
	 */
	public boolean equip(Item item) {
		for (int i = 0; i < extra.size(); i++) {
			if (extra.get(i).store(item))
				return true;
		}
		return false;
	}

	/**
	 * @returns a list of the id and count of each item in the main inventory
	 */
	public ArrayList<IdCountPair> getRawHeld() {
		ArrayList<IdCountPair> pairs = new ArrayList<IdCountPair>();
		for (int i = 0; i < storage.size(); i++) {
			ItemContainer<Item> c = storage.get(i);
			if (!c.isEmpty()) {
				IdCountPair pair = new IdCountPair(c.getContained().ID, c.amount);
				if (pairs.contains(pair)) {
					for (int j = 0; j < pairs.size(); j++) {
						if (pairs.get(j).equals(pair)) {
							pairs.get(j).count += pair.count;
							break;
						}
					}
				} else
					pairs.add(pair);
			}
		}
		return pairs;
	}

	/**
	 * @returns an array containing the count of each general resource in the main
	 *          inventory
	 */
	public int[] getResourceHeld() {
		int[] res = new int[Tag.RESOURCE_MAX_ARRAY];
		for (int i = 0; i < storage.size(); i++) {
			ItemContainer<Item> c = storage.get(i);
			if (!c.isEmpty()) {
				Item t = c.getContained();
				if (Utility.tagOverlaps(t.getTags(), "carvable"))
					res[Tag.RESOURCE_CARVABLE] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "cloth"))
					res[Tag.RESOURCE_CLOTH] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "cord"))
					res[Tag.RESOURCE_CORD] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "gem"))
					res[Tag.RESOURCE_GEM] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "metal"))
					res[Tag.RESOURCE_METAL] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "mineral"))
					res[Tag.RESOURCE_MINERAL] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "phantasm"))
					res[Tag.RESOURCE_PHANTASM] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "soil"))
					res[Tag.RESOURCE_SOIL] += c.amount;
				if (Utility.tagOverlaps(t.getTags(), "tissue"))
					res[Tag.RESOURCE_TISSUE] += c.amount;
			}
		}
		return res;
	}

	/**
	 * removes an item from the inventory based on an inputed item id
	 * 
	 * @param id - id of the item to remove
	 * @returns the item which was removed
	 */
	public Item removeItemID(String id) {
		Item it = null;
		for (int i = 0; i < storage.size(); i++) {
			if (!storage.get(i).isEmpty() && storage.get(i).getContained().ID.equals(id)) {
				it = storage.get(i).getContained();
				storage.get(i).destroy();
				break;
			}
		}
		return it;
	}

	/**
	 * removes an item from the inventory based on an inputed item tag
	 * 
	 * @param tag - tag of the item to remove
	 * @returns the item which was removed
	 */
	public Item removeItemTag(String tag) {
		Item it = null;
		for (int i = 0; i < storage.size(); i++) {
			if (!storage.get(i).isEmpty() && Utility.tagOverlaps(storage.get(i).getContained().getTags(), tag)) {
				it = storage.get(i).getContained();
				storage.get(i).destroy();
				break;
			}
		}
		return it;
	}

	/**
	 * empties the whole inventory and drops each item
	 */
	public void dropAll() {
		for (int i = 0; i < storage.size(); i++) {
			while (storage.get(i).getContained() != null)
				storage.get(i).drop();
		}

		for (int i = 0; i < extra.size(); i++) {
			while (extra.get(i).getContained() != null)
				extra.get(i).drop();
		}
	}

	/**
	 * initializes the handler through every part of the inventory
	 * 
	 * @param h - the new handler
	 */
	public void load(Handler h, Entity e) {
		this.owner = e;
		for (ItemContainer<Item> i : storage) {
			i.load(h);
			if (!i.isEmpty())
				i.getContained().load(h, e);
		}

		for (ItemContainer<Item> i : extra) {
			i.load(h);
			if (!i.isEmpty())
				i.getContained().load(h, e);
		}
	}

	/**
	 * initializes the handler through every part of the inventory
	 * 
	 * @param h - the new handler
	 */
	public void load(Handler h) {
		for (ItemContainer<Item> i : storage) {
			i.load(h);
			if (!i.isEmpty())
				i.getContained().load(h);
		}

		for (ItemContainer<Item> i : extra) {
			i.load(h);
			if (!i.isEmpty())
				i.getContained().load(h);
		}
	}

}
