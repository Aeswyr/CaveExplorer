package entity;

import gfx.DrawGraphics;
import gfx.Sprite;
import item.Item;

public abstract class Mob extends Entity {

	protected Sprite activeSprite;
	protected double health, healthMax;

	@Override
	public void render(DrawGraphics g) {
		activeSprite.render((int) x - handler.getCamera().xOffset() - xOff,
				(int) y - handler.getCamera().yOffset() - yOff, g);
	}

	@Override
	public void update() {
		hitbox.update();
		move();
	}

	public abstract void renderUI(DrawGraphics g);

	public abstract void move();

	public void harm(double amount) {
		health -= amount;
		if (health <= 0) this.die();
	}

	public void heal(double amount) {
		health += amount;
		if (health > healthMax)
			health = healthMax;
	}
	
	
	public abstract void equip(Item i);
	
	public abstract void pickup(Item i);
}
