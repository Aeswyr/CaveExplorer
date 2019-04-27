package entity;

import world.Chunk;
import world.Tile;

public class Entity {
 protected int x, y;
 
 
 
 
 
 
 public int getX() {
	 return x;
 }
 
 public int getY() {
	 return y;
 }
 
 public int getTileX() {
	 return x / Tile.tileSize;
 }
 
 public int getTileY() {
	 return y / Tile.tileSize;
 }
 
 public int getChunkX() {
	 return x / Tile.tileSize / Chunk.chunkDim;
 }
 
 public int getChunkY() {
	 return y / Tile.tileSize / Chunk.chunkDim;
 }
 
}
