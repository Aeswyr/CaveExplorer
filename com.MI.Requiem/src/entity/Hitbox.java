package entity;

import java.awt.Rectangle;
import java.util.ArrayList;
import entity.Entity;
import entity.EntityManager;
import entity.Mob;
import geometry.Shape;
import geometry.Square;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

public class Hitbox {

	private Entity e;
	private int xoff, yoff;
	private int x, y, width, height;
	Rectangle r;
	private Handler handler;

	public Hitbox(int xOffset, int yOffset, int width, int height, Entity e, Handler handler) {
		this.e = e;
		this.width = width;
		this.height = height;
		xoff = xOffset;
		yoff = yOffset;
		this.handler = handler;
	}

	public Hitbox(int xOffset, int yOffset, int size, Entity e, Handler handler) {
		this.e = e;
		this.width = size;
		this.height = size;
		xoff = xOffset;
		yoff = yOffset;
		this.handler = handler;

	}

	public Hitbox(int x, int y, int width, int height, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
	}

	public void update() {
		x = e.getX();
		y = e.getY();
	}

	public boolean contains(Hitbox h) {
		int[] thisC = this.getCenter();
		int[] newC = h.getCenter();

		if (Math.abs(thisC[0] - newC[0]) < (this.width + h.width) / 2
				&& Math.abs(thisC[1] - newC[1]) < (this.height + h.height) / 2)
			return true;

		return false;
	}

	public boolean tileXCollide(double xMove) {
		int[] bound = this.getBounds();
		if ((handler.getWorld().getTile((int) (bound[0] + xMove), bound[2]).isSolid())
				|| (handler.getWorld().getTile((int) (bound[0] + xMove), bound[3] - 2).isSolid())
				|| (handler.getWorld().getTile((int) (bound[1] + xMove), bound[2]).isSolid())
				|| (handler.getWorld().getTile((int) (bound[1] + xMove), bound[3] - 2).isSolid())) {
			return true;
		}
		try {
			if ((handler.getWorld().getOverlay((int) (bound[0] + xMove), bound[2]).isSolid())
					|| (handler.getWorld().getOverlay((int) (bound[0] + xMove), bound[3] - 2).isSolid())
					|| (handler.getWorld().getOverlay((int) (bound[1] + xMove), bound[2]).isSolid())
					|| (handler.getWorld().getOverlay((int) (bound[1] + xMove), bound[3] - 2).isSolid())) {
				return true;
			}
		} catch (NullPointerException e) {

		}

		return false;
	}

	public boolean tileYCollide(double yMove) {
		int[] bound = this.getBounds();
		if (handler.getWorld().getTile(bound[0], (int) (bound[2] + yMove)).isSolid()
				|| handler.getWorld().getTile(bound[1] - 2, (int) (bound[2] + yMove)).isSolid()
				|| handler.getWorld().getTile(bound[0], (int) (bound[3] + yMove)).isSolid()
				|| handler.getWorld().getTile(bound[1] - 2, (int) (bound[3] + yMove)).isSolid()) {
			return true;
		}

		try {
			if (handler.getWorld().getOverlay(bound[0], (int) (bound[2] + yMove)).isSolid()
					|| handler.getWorld().getOverlay(bound[1] - 2, (int) (bound[2] + yMove)).isSolid()
					|| handler.getWorld().getOverlay(bound[0], (int) (bound[3] + yMove)).isSolid()
					|| handler.getWorld().getOverlay(bound[1] - 2, (int) (bound[3] + yMove)).isSolid()) {
				return true;
			}
		} catch (NullPointerException e) {

		}
		return false;
	}

	public Entity collidingWith() {
		EntityManager em = handler.getWorld().getEntities();
		for (int i = 0; i < em.totalEntities(); i++) {
			if (em.getEntity(i).getHitbox() != null && (this.contains(em.getEntity(i).getHitbox()))
					|| em.getEntity(i).getHitbox().contains(this))
				return em.getEntity(i);
		}
		return null;
	}

	public Entity collidingWithMob() {
		EntityManager em = handler.getWorld().getEntities();
		for (int i = 0; i < em.totalEntities(); i++) {
			if (em.getEntity(i).getHitbox() != null && this.contains(em.getEntity(i).getHitbox())
					&& em.getEntity(i) instanceof Mob)
				return em.getEntity(i);
		}
		return null;
	}

	public ArrayList<Entity> collidingAll() {
		EntityManager em = handler.getWorld().getEntities();
		ArrayList<Entity> found = new ArrayList<Entity>();
		for (int i = 0; i < em.totalEntities(); i++) {
			Entity e = em.getEntity(i);
			if (e.getHitbox() != null && this != e.getHitbox() && this.contains(e.getHitbox()))
				found.add(e);
		}
		return found;
	}

	public boolean containsMouse() {
		int mouseX = handler.getMouse().getAdjX();
		int mouseY = handler.getMouse().getAdjY();
		int[] bound = this.getBounds();

		if (mouseX > bound[0] && mouseX < bound[1] && mouseY > bound[2] && mouseY < bound[3])
			return true;

		return false;
	}

	// Getters and Setters

	private int[] getBounds() {
		int[] bounds = { x + xoff, x + xoff + width, y + yoff, y + yoff + height };
		return bounds;
	}

	private int[] getCenter() {
		int[] bounds = { x + xoff + width / 2, y + yoff + height / 2 };
		return bounds;
	}

	public void updatePos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void render(DrawGraphics g) {
		Shape s = new Square(width, height, 0xff0000ff, Sprite.TYPE_GUI_BACKGROUND_SHAPE, true);
		s.render(x - handler.getCamera().xOffset() + xoff, y - handler.getCamera().yOffset() + yoff, g);
	}

	public void renderStill(DrawGraphics g) {
		if (handler.devMode)
			g.fillRect(x, y, width, height, 0xff0000ff);
	}
}
