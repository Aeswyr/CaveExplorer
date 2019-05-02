package runtime;

import java.util.ArrayList;

import gfx.DrawGraphics;

public class LightManager {

	
	ArrayList<Light> lights;
	ArrayList<Light> remove;
	Handler handler;
	
	public LightManager(Handler handler) {
		this.handler = handler;
		lights = new ArrayList<Light>();
		remove = new ArrayList<Light>();
	}
	
	public void update() {
		if (!remove.isEmpty()) {
			lights.removeAll(remove);
			remove.clear();
		}
	}
	
	public void render(DrawGraphics g) {
		for (int i = 0; i < lights.size(); i++) {
			lights.get(i).render(g);
		}
	}
	
	
	
	public void add(Light l) {
		lights.add(l);
	}
	
	public void remove(Light l) {
		remove.add(l);
	}
	
}
