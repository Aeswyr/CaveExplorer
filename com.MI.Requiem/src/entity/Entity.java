package entity;

import gfx.DrawGraphics;
import runtime.Handler;
import world.Chunk;
import world.Tile;

public abstract class Entity {
	protected double x, y;
	protected int xOff, yOff;
	protected Hitbox hitbox;
	protected Handler handler;

	public Entity(Handler handler) {
		this.handler = handler;
	}
	
	public abstract void update();

	public abstract void render(DrawGraphics g);

	public void die() {
		handler.getWorld().getEntities().removeEntity(this);
	}

	// Getters and setters;

	public Hitbox getHitbox() {
		return hitbox;
	}

	public int getX() {
		return (int) x;
	}

	public int getY() {
		return (int) y;
	}

	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getTileX() {
		return (int) (x / Tile.tileSize);
	}

	public int getTileY() {
		return (int) (y / Tile.tileSize);
	}

	public int getChunkX() {
		return (int) (x / Tile.tileSize / Chunk.chunkDim);
	}

	public int getChunkY() {
		return (int) (y / Tile.tileSize / Chunk.chunkDim);
	}

	public int getAdjX() {
		return (int) (x) / Tile.tileSize;
	}

	public int getAdjY() {
		return (int) (y - yOff / 2) / Tile.tileSize;
	}
	
	public int getCenteredX() {
		return (int) (x - xOff / 2);
	}
	
	public int getCenteredY() {
		return (int) (y - yOff / 2);
	}

}
