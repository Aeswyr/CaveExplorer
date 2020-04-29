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
public abstract class Entity extends Entity_KS implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5462530084929665736L;

	protected int xOff, yOff, w, h;

	/**
	 * removes this entity from the active entities
	 */
	public void die() {
		Handler.getEntityManager().removeEntity(this);
	}

	// Getters and setters;

	/**
	 * @returns this entity's x position (in tiles)
	 */
	public int getTileX() {
		return x / Tile.TILE_SIZE;
	}

	/**
	 * @returns this entity's y position (in tiles)
	 */
	public int getTileY() {
		return y / Tile.TILE_SIZE;
	}

	/**
	 * @returns this entity's x position (in chunks)
	 */
	public int getChunkX() {
		return x / Tile.TILE_SIZE / Chunk.chunkDim;
	}

	/**
	 * @returns this entity's y position (in chunks)
	 */
	public int getChunkY() {
		return y / Tile.TILE_SIZE / Chunk.chunkDim;
	}

	/**
	 * @returns this entity's x position (adjusted for the top of the entity)
	 */
	public int getAdjX() {
		return x + w / 2 / Tile.TILE_SIZE;
	}

	/**
	 * @returns this entity's y position (adjusted for the top of the entity)
	 */
	public int getAdjY() {
		return y / Tile.TILE_SIZE;
	}

	/**
	 * @returns this entity's x position (adjusted for the center of the entity)
	 */
	public int getCenteredX() {
		return x + w / 2;
	}

	/**
	 * @returns this entity's y position (adjusted for the center of the entity)
	 */
	public int getCenteredY() {
		return y + h / 2;
	}

	/**
	 * initializes the handler thru all parts of the entity
	 *
	 */
	public void load() {
		if (hitbox != null) {
			this.hitbox.setEntity(this);
		}
		if (vector != null)
			this.vector.linked = this;
	}

	/**
	 * gets the coordinate to render lights from on this entity
	 * 
	 * @returns the x position for the center of lights emitting from this entity
	 */
	public int getLightX() {
		return x + w / 2;
	}

	/**
	 * gets the coordinate to render lights from on this entity
	 * 
	 * @returns the y position for the center of lights emitting from this entity
	 */
	public int getLightY() {
		return y + h / 3;
	}

}
