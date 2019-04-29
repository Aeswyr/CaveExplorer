package entity;

import java.awt.Graphics;

import gfx.Sprite;

public abstract class Mob extends Entity{

	protected Sprite activeSprite;
	
	public abstract void renderUI(Graphics g);
	
	public abstract void move();
}
