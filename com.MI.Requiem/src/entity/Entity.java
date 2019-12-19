package entity;

import java.io.Serializable;
import gfx.DrawGraphics;
import runtime.Handler;
import world.Chunk;
import world.Tile;

/**
 * represents any object in the game which has a position and can be interacted
 * with in some way
 * 
 * @author Pascal
 *
 */
public abstract class Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5462530084929665736L;
	public int id;
	protected double x, y;
	protected int xOff, yOff, w , h;
	protected Hitbox hitbox;
	transient protected Handler handler;

	public Entity(Handler handler) {
		this.handler = handler;
	}

	/**
	 * updates all essential functions of this entity
	 */
	public abstract void update();

	/**
	 * draws this entity and associated ui to the screen
	 * 
	 * @param g - the DrawGraphics component associated with the renderer
	 */
	public abstract void render(DrawGraphics g);

	/**
	 * removes this entity from the active entities
	 */
	public void die() {
		handler.getWorld().getEntities().removeEntity(this);
	}

	// Getters and setters;

	/**
	 * gets this entity's hitbox
	 * 
	 * @returns this entity's main hitbox
	 */
	public Hitbox getHitbox() {
		return hitbox;
	}

	/**
	 * @returns this entity's x position
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * @returns this entity's y position
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * sets this entity's x to a new position
	 * 
	 * @param x - the new x value
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * sets this entity's y to a new position
	 * 
	 * @param x - the new y value
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @returns this entity's x position (in tiles)
	 */
	public int getTileX() {
		return (int) (x / Tile.tileSize);
	}

	/**
	 * @returns this entity's y position (in tiles)
	 */
	public int getTileY() {
		return (int) (y / Tile.tileSize);
	}

	/**
	 * @returns this entity's x position (in chunks)
	 */
	public int getChunkX() {
		return (int) (x / Tile.tileSize / Chunk.chunkDim);
	}

	/**
	 * @returns this entity's y position (in chunks)
	 */
	public int getChunkY() {
		return (int) (y / Tile.tileSize / Chunk.chunkDim);
	}

	/**
	 * @returns this entity's x position (adjusted for the top of the entity)
	 */
	public int getAdjX() {
		return (int) (x + w / 2) / Tile.tileSize;
	}

	/**
	 * @returns this entity's y position (adjusted for the top of the entity)
	 */
	public int getAdjY() {
		return (int) (y) / Tile.tileSize;
	}

	/**
	 * @returns this entity's x position (adjusted for the center of the entity)
	 */
	public int getCenteredX() {
		return (int) (x + w / 2);
	}

	/**
	 * @returns this entity's y position (adjusted for the center of the entity)
	 */
	public int getCenteredY() {
		return (int) (y + h / 2);
	}

	/**
	 * initializes the handler thru all parts of the entity
	 * 
	 * @param h
	 */
	public void load(Handler h) {
		this.handler = h;
		if (hitbox != null) {
			this.hitbox.setHandler(h);
			this.hitbox.setEntity(this);
		}
	}

	/**
	 * gets the coordinate to render lights from on this entity
	 * 
	 * @returns the x position for the center of lights emitting from this entity
	 */
	public int getLightX() {
		return (int) (x + w / 2);
	}

	/**
	 * gets the coordinate to render lights from on this entity
	 * 
	 * @returns the y position for the center of lights emitting from this entity
	 */
	public int getLightY() {
		return (int) (y + h / 3);
	}

}
