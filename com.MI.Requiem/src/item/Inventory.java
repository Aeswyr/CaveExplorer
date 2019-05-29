package item;

import java.util.ArrayList;

import core.Assets;
import gfx.DrawGraphics;
import runtime.Handler;

public class Inventory {

	private ArrayList<ItemContainer<Item>> storage;
	private ArrayList<ItemContainer<Item>> extra;
	private Handler handler;
	private int x, y, size;

	public Inventory(int x, int y, int size, Handler handler) {
		storage = new ArrayList<ItemContainer<Item>>();
		extra = new ArrayList<ItemContainer<Item>>();
		this.x = x;
		this.y = y;
		this.size = size;
		this.handler = handler;
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
			}
		}
		for (int i = 0; i < extra.size(); i++) {
			extra.get(i).update();
			item = extra.get(i).getContained();
			if (item != null) {
				if (!item.equipped())
					item.onEquip();
				item.update();
			}
		}
	}

	public void resize(int size) {

	}

	public boolean add(Item item) {
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

}
