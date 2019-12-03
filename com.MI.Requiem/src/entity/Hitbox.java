package entity;

import java.util.ArrayList;
import entity.Entity;
import entity.EntityManager;
import entity.Mob;
import geometry.Shape;
import geometry.Rect;
import gfx.DrawGraphics;
import gfx.Sprite;
import runtime.Handler;

/**
 * hitbox class for entity collision
 * 
 * @author Pascal
 *
 */
public class Hitbox {

	private Entity e;
	private int xoff, yoff;
	private int x, y, width, height;
	private Handler handler;

	/**
	 * initializes a hitbox for an entity
	 * 
	 * @param xOffset - offset from the entity's x to place the hitbox
	 * @param yOffset - offset from the entity's y to place the hitbox
	 * @param width   - width of the hitbox
	 * @param height  - height of the hitbox
	 * @param e       - entity which this hitbox is bound to
	 * @param handler
	 */
	public Hitbox(int xOffset, int yOffset, int width, int height, Entity e, Handler handler) {
		this.e = e;
		this.width = width;
		this.height = height;
		xoff = xOffset;
		yoff = yOffset;
		this.handler = handler;
	}

	/**
	 * initializes a square hitbox for an entity
	 * 
	 * @param xOffset - offset from the entity's x to place the hitbox
	 * @param yOffset - offset from the entity's y to place the hitbox
	 * @param size    - width and height of this hitbox
	 * @param e       - entity which this hitbox is bound to
	 * @param handler
	 */
	public Hitbox(int xOffset, int yOffset, int size, Entity e, Handler handler) {
		this.e = e;
		this.width = size;
		this.height = size;
		xoff = xOffset;
		yoff = yOffset;
		this.handler = handler;

	}

	/**
	 * initializes a hitbox which isnt bound to any entity
	 * 
	 * @param x       - x pos of this hitbox
	 * @param y       - y pos of this hitbox
	 * @param width   - width of the hitbox
	 * @param height  - height of the hitbox
	 * @param handler
	 */
	public Hitbox(int x, int y, int width, int height, Handler handler) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.handler = handler;
	}

	/**
	 * sets this hitbox position to the entity's position
	 */
	public void update() {
		x = e.getX();
		y = e.getY();
	}

	/**
	 * checks if this hitbox contains the specified hitbox
	 * 
	 * @param h - the hitbox to test
	 * @returns true if the hitboxes collide, false otherwise
	 */
	public boolean contains(Hitbox h) {
		int[] thisC = this.getCenter();
		int[] newC = h.getCenter();

		if (Math.abs(thisC[0] - newC[0]) < (this.width + h.width) / 2
				&& Math.abs(thisC[1] - newC[1]) < (this.height + h.height) / 2)
			return true;

		return false;
	}

	/**
	 * checks if this hitbox will collide with a tile if it were to move in the x
	 * direction
	 * 
	 * @param xMove - the amount to move in the x direction
	 * @returns true if there would be a collision, false otherwise
	 */
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

	/**
	 * checks if this hitbox will collide with a tile if it were to move in the y
	 * direction
	 * 
	 * @param yMove - the amount to move in the y direction
	 * @returns true if there would be a collision, false otherwise
	 */
	public Entity collidingWith() {
		EntityManager em = handler.getWorld().getEntities();
		for (int i = 0; i < em.totalEntities(); i++) {
			if (em.getEntity(i).getHitbox() != null && (this.contains(em.getEntity(i).getHitbox()))
					|| em.getEntity(i).getHitbox().contains(this))
				return em.getEntity(i);
		}
		return null;
	}

	/**
	 * gets the first mob that collides with this hitbox other than the entity who
	 * has this hitbox
	 * 
	 * @returns the first mob which collides with this hitbox
	 */
	public Mob collidingWithMob() {
		EntityManager em = handler.getWorld().getEntities();
		for (int i = 0; i < em.totalEntities(); i++) {
			if (em.getEntity(i).getHitbox() != null && this.contains(em.getEntity(i).getHitbox())
					&& em.getEntity(i) instanceof Mob && em.getEntity(i) != this.e)
				return ((Mob) em.getEntity(i));
		}
		return null;
	}

	/**
	 * gets all entities which are colliding with this hitbox
	 * 
	 * @returns a list of all entities colliding with this hitbox
	 */
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

	/**
	 * checks if this hitbox contains the mouse cursor
	 * 
	 * @returns true if the cursor is within the hitbox, false otherwise
	 */
	public boolean containsMouse() {
		int mouseX = handler.getMouse().getAdjX();
		int mouseY = handler.getMouse().getAdjY();
		int[] bound = this.getBounds();

		if (mouseX > bound[0] && mouseX < bound[1] && mouseY > bound[2] && mouseY < bound[3])
			return true;

		return false;
	}

	// Getters and Setters

	/**
	 * gets the four corner points of this hitbox
	 * 
	 * @returns an array with the four corner points of the hitbox in the format x0,
	 *          x1, y0, y1
	 */
	private int[] getBounds() {
		int[] bounds = { x + xoff, x + xoff + width, y + yoff, y + yoff + height };
		return bounds;
	}

	/**
	 * gets the center point of this hitbox
	 * 
	 * @returns an array containing the x, y coordinate for the center of this
	 *          hitbox
	 */
	private int[] getCenter() {
		int[] bounds = { x + xoff + width / 2, y + yoff + height / 2 };
		return bounds;
	}

	/**
	 * updates this hitbox's position to a new one
	 * 
	 * @param x - the new x
	 * @param y - the new y
	 */
	public void updatePos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * draws this hitbox to the screen adjusted for the camera
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public void render(DrawGraphics g) {
		Shape s = new Rect(width, height, 0xff0000ff, Sprite.TYPE_GUI_BACKGROUND_SHAPE, true);
		s.render(x - handler.getCamera().xOffset() + xoff, y - handler.getCamera().yOffset() + yoff, g);
	}

	/**
	 * draws this hitbox to the screen based on raw screen coordinate
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public void renderStill(DrawGraphics g) {
		if (handler.devMode)
			g.drawRect(x + xoff, y + yoff, width, height, 0xff0000ff);
	}
}
