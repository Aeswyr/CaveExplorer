package entity;

import effects.Effect;
import gfx.DrawGraphics;
import gfx.Sprite;
import item.Inventory;
import item.Item;
import particle.Particle;
import runtime.Handler;

public abstract class Mob extends Entity {

	public Mob(Handler handler) {
		super(handler);
	}

	protected Sprite activeSprite;
	protected double speed;
	protected int health, healthMax, spirit, spiritMax;
	protected int con, wil, str, agi, kno, arm, luk;
	protected Inventory inventory;
	protected double move = speed;
	protected boolean locked = false;

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
			health -= val;
			if (health <= 0)
				this.die();
			new Particle("" + val, 0xffAA0000, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit - amount <= 0)
				val = spirit;
			spirit -= val;
			if (spirit <= 0)
				this.die();
			new Particle("" + val, 0xff9999FF, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (val < 0)
				val = 0;
			if (health - val <= 0)
				val = health;
			health -= val;
			if (health <= 0)
				this.die();
			new Particle("" + val, 0xffAA0000, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
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
			new Particle("" + val, 0xffFFFF00, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
		case Effect.DAMAGE_TYPE_MENTAL:
			val = amount;
			if (spirit + amount > spiritMax)
				val = spiritMax - spirit;
			spirit += val;
			new Particle("" + val, 0xff0000FF, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
		case Effect.DAMAGE_TYPE_PHYSICAL:
			val = amount - arm;
			if (val < 0)
				val = 0;
			if (health + amount - arm > healthMax)
				val = healthMax - health;
			health += val;
			new Particle("" + val, 0xffFFFF00, 1, 30, getCenteredX(), getCenteredY() - 16, getCenteredX(),
					getCenteredY() + 32, Particle.SHAPE_CIRCLE, Particle.DISPERSE_BURST, Particle.DIRECTION_FLOAT,
					Particle.LIFETIME_SET, handler).start();
			return val;
		default:
			return 0;
		}

	}

	public abstract void equip(Item i);

	public abstract boolean pickup(Item i);

	public Inventory getInventory() {
		return inventory;
	}

	public void adjSpeed(double adj) {
		speed += adj;
		if (speed < 1)
			move = 1;
		else
			move = speed;
	}

	public void adjInv(int adj) {
		inventory.resize(inventory.getSize() + adj);
	}

	public void adjArmor(int adj) {
		this.arm += adj;
	}

	public void adjMaxhp(int adj) {
		this.healthMax += adj;
		if (health > healthMax)
			health = healthMax;
	}

	public void adjMaxsp(int adj) {
		this.spiritMax += adj;
		if (spirit > spiritMax)
			spirit = spiritMax;
	}

	public void adjLuck(int adj) {
		this.luk += adj;
	}

	public int getHealth() {
		return this.health;
	}

	public int getSpirit() {
		return this.spirit;
	}

	public void lock() {
		this.locked = true;
	}

	public void unlock() {
		this.locked = false;
	}

}
