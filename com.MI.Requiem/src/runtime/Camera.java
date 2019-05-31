package runtime;

import entity.Entity;

public class Camera {
	Handler handler;
	Entity target;
	private int xOffset;
	private int yOffset;

	int w, h;

	public Camera(Handler handler) {
		this.handler = handler;
		w = handler.getWidth();
		h = handler.getHeight();
	}

	public void update() {
		if (target != null) {

			xOffset += (target.getCenteredX() - xOffset) / 4;
			yOffset += (target.getCenteredY() - yOffset) / 4;
			if (xOffset < w / 2 - w / 6)
				xOffset = w / 2 - w / 6;
			if (yOffset < h / 2)
				yOffset = h / 2;
		}
	}

	public void centerOnEntity(Entity e) {
		target = e;
	}

	public int xOffset() {
		return xOffset - w / 2;
	}

	public int yOffset() {
		return yOffset - h / 2;
	}
	
	public int xOffsetAdj() {
		return xOffset;
	}
	
	public int yOffsetAdj() {
		return yOffset;
	}
}
