package runtime;

import gfx.DrawGraphics;
import gfx.LightRequest;

public class Light {

	public static final int NONE = 0;
	public static final int FULL = 1;
	public static final int IGNORE = 2;

	Handler handler;

	int x, y;

	int radius, diameter, color;
	int[] lightMap;

	public Light(int radius, int color, Handler handler) {
		this.handler = handler;
		this.radius = radius;
		this.diameter = radius * 2;
		this.color = color;
		lightMap = new int[diameter * diameter];

		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				double dist = Math.sqrt((x - radius) * (x - radius) + (y - radius) * (y - radius));
				float persistence = (float) (1 - (dist / radius));
				if (dist < radius)
					lightMap[y * diameter + x] = (int) (((color >> 16) & 0xff) * persistence) << 16
							| (int) (((color >> 8) & 0xff) * persistence) << 8 | (int) ((color & 0xff) * persistence);
				else
					lightMap[y * diameter + x] = 0;
			}
		}

	}

	public int[] getLightMap() {
		return lightMap;
	}

	public int getDiameter() {
		return diameter;
	}

	public int getRadius() {
		return radius;
	}

	public int getLuminosity(int x, int y) {
		if (x >= diameter || x < 0 || y >= diameter || y < 0)
			return 0;

		return lightMap[y * diameter + x];

	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void light() {
		handler.getLights().add(this);
	}

	public void snuff() {
		handler.getLights().remove(this);
	}

	public void render(DrawGraphics g) {
		g.submitRequest(new LightRequest(this, x - handler.getCamera().xOffset(), y - handler.getCamera().yOffset()));

	}

}
