package entity;

import effects.Effect;
import gfx.DrawGraphics;
import gfx.Sprite;
import item.Item;
import runtime.Handler;

public abstract class Mob extends Entity {

	public Mob(Handler handler) {
		super(handler);
	}

	protected Sprite activeSprite;
	protected double speed;
	protected int health, healthMax, spirit, spiritMax;
	protected int con, wil, str, agi, kno, arm, luk;
	protected boolean starving;

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

	/**
	 * 
	 * @param amount - the
	 * @return
	 */
	public int harm(int amount, int type) {
		int val;
		switch (type) {
		case Effect.DAMAGE_TYPE_ENERGY:
			val = amount;
			if (health - amount <= 0)
				val = health;
			health -= amount;
			if (health <= 0)
				this.die();
			return amount;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit - amount <= 0)
				val = spirit;
			spirit -= amount;
			return amount;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (health - val <= 0)
				val = health;
			health -= amount - arm;
			if (health <= 0)
				this.die();
			return amount - arm;
		default:
			return 0;
		}
	}

	public int heal(int amount, int type) {
		int val;
		switch (type) {
		case Effect.DAMAGE_TYPE_ENERGY:
			val = amount;
			if (health + amount > healthMax)
				val = healthMax - health;
			health += val;
			return val;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit + amount > spiritMax)
				val = spiritMax - spirit;
			spirit += val;
			return val;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (health + amount - arm > healthMax)
				val = healthMax - health;
			health += val;
			return val;
		default:
			return 0;
		}

	}

	public abstract void equip(Item i);

	public abstract boolean pickup(Item i);

	public void adjSpeed(double adj) {
		speed += adj;
	}
}
