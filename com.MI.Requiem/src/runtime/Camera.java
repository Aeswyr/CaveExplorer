package runtime;

import entity.Entity;

public class Camera {
	Handler handler;
	Entity target;
	private int xOffset;
	private int yOffset;

	public Camera(Handler handler) {
		this.handler = handler;
	}

	public void update() {
		if (target != null) {
			xOffset += (target.getX() - xOffset) / 4;
			yOffset += (target.getY() - yOffset) / 4;
			if (xOffset < handler.getWidth() / 2 - handler.getWidth() / 6)
				xOffset = handler.getWidth() / 2 - handler.getWidth() / 6;
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
