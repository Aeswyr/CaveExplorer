package item;

import java.util.ArrayList;

import core.Assets;
import crafting.Tag;
import gfx.DrawGraphics;
import runtime.Handler;
import utility.Utility;

public class Inventory {

	private ArrayList<ItemContainer<Item>> storage;
	private ArrayList<ItemContainer<Item>> extra;
	private Handler handler;
	private int x, y, size;

	public Inventory(int x, int y, int size, Handler handler) {
		storage = new ArrayList<ItemContainer<Item>>();
		extra = new ArrayList<ItemContainer<Item>>();
		this.size = size;
		this.handler = handler;
		this.x = x;
		this.y = y;
		for (int i = 0; i < size; i++) {
			storage.add(
					new ItemContainer<Item>((i % 3) * 40 + x, (i / 3) * 40 + y, Assets.inventory_Empty, null, handler));
		}
	}

	public Inventory(Handler handler) {
		this(0, 0, 12, handler);
	}

	public void render(DrawGraphics g) {
		for (int i = 0; i < size; i++) {
			storage.get(i).render(g);
		}
		for (int i = 0; i < extra.size(); i++) {
			extra.get(i).render(g);
		}
	}

	public void update() {
		Item item = null;
		for (int i = 0; i < size; i++) {
			storage.get(i).update();
			item = storage.get(i).getContained();
			if (item != null) {
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

	public int getSize() {
		return size;
	}

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

	public void appendContainer(ItemContainer<Item> c) {
		extra.add(c);
	}

	public boolean equip(Item item) {
		for (int i = 0; i < extra.size(); i++) {
			if (extra.get(i).store(item))
				return true;
		}
		return false;
	}

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

}
