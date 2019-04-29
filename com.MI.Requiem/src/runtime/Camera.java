package runtime;

import entity.Entity;

public class Camera {
	Handler handler;
	Entity target;
	int xOffset;
	int yOffset;

	public Camera(Handler handler) {
		this.handler = handler;
	}

	public void update() {
		if (target != null) {
			xOffset += (target.getX() - xOffset) / 4;
			yOffset += (target.getY() - yOffset) / 4;
			if (xOffset < handler.getWidth() / 2)
				xOffset = handler.getWidth() / 2;
			if (yOffset < handler.getHeight() / 2)
				yOffset = handler.getHeight() / 2;
		}
	}

	public void centerOnEntity(Entity e) {
		target = e;
	}

	public int xOffset() {
		return xOffset - handler.getWidth() / 2;
	}

	public int yOffset() {
		return yOffset - handler.getHeight() / 2;
	}

}
