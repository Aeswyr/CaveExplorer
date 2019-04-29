package entity;

import java.awt.Graphics;

import world.Chunk;
import world.Tile;

public abstract class Entity {
	protected double x, y;

	public abstract void update();

	public abstract void render(Graphics g);

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public int getTileX() {
		return (int)(x / Tile.tileSize);
	}

	public int getTileY() {
		return (int)(y / Tile.tileSize);
	}

	public int getChunkX() {
		return (int)(x / Tile.tileSize / Chunk.chunkDim);
	}

	public int getChunkY() {
		return (int)(y / Tile.tileSize / Chunk.chunkDim);
	}

}
