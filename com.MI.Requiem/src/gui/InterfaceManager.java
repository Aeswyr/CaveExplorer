package gui;

import java.util.ArrayList;

import gfx.DrawGraphics;

public class InterfaceManager {

	private ArrayList<UIObject> list;
	private ArrayList<UIObject> remove;

	public InterfaceManager() {
		list = new ArrayList<UIObject>();
		remove = new ArrayList<UIObject>();
	}

	public void render(DrawGraphics g) {
		for (UIObject o : list) {
			o.render(g);
		}
	}

	public void update() {
		for (UIObject o : list)
			o.update();
		for (UIObject o : remove)
			list.remove(o);
		remove.clear();
	}

	// Array interaction
	public void addObject(UIObject o) {
		list.add(o);
	}

	public UIObject getObject(int index) {
		return list.get(index);
	}

	public void removeObject(int index) {
		remove.add(list.get(index));
	}

	public void removeObject(UIObject e) {
		remove.add(e);
	}

	public void flushObjects() {
		list.clear();
	}

	public int totalObjects() {
		return list.size();
	}

}
