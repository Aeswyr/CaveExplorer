package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import entity.Mob;

/**
 * hitbox class for entity collision
 * 
 * @author Pascal
 *
 */
public class Hitbox extends HitSquare_KS implements Serializable {

	public Hitbox(int xOffset, int yOffset, int width, int height, Entity_KS e) {
		super(xOffset, yOffset, width, height, e);
		// TODO Auto-generated constructor stub
	}

	public Hitbox(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}

	public Hitbox(int x, int y, int width, int height) {
		super(width, height);
		this.x = x;
		this.y = y;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8268649890127915848L;

	/**
	 * checks if this hitbox will collide with a tile if it were to move in the y
	 * direction
	 * 
	 * @param yMove - the amount to move in the y direction
	 * @returns true if there would be a collision, false otherwise
	 */
	public Entity collidingWith() {
		List<Entity_KS> entities = super.localCollisions();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != getEntity())
				return (Entity) entities.get(i);
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
		List<Entity_KS> entities = super.localCollisions();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) != getEntity() && entities.get(i).isMob())
				return (Mob) entities.get(i);
		}
		return null;
	}

	/**
	 * gets all entities which are colliding with this hitbox
	 * 
	 * @returns a list of all entities colliding with this hitbox
	 */
	public ArrayList<Entity> collidingAll() {
		ArrayList<Entity> found = new ArrayList<Entity>();

		for (Entity_KS e : localCollisions())
			found.add((Entity) e);
		return found;
	}

	/**
	 * gets all mobs which are colliding with this hitbox
	 * 
	 * @returns a list of all mobs colliding with this hitbox
	 */
	public ArrayList<Mob> collidingAllMob() {
		ArrayList<Mob> found = new ArrayList<Mob>();

		for (Entity_KS e : localCollisions())
			if (e.isMob())
				found.add((Mob) e);
		return found;
	}

}
