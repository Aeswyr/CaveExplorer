package entity;

import effects.Effect;
import gfx.DrawGraphics;
import gfx.Sprite;
import item.Inventory;
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
	protected Inventory inventory;
	protected double move = speed;

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
			if (val < 0)
				val = 0;
			if (health - val <= 0)
				val = health;
			health -= val;
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
			if (val < 0)
				val = 0;
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
	
	public Inventory getInventory() {
		return inventory;
	}

	public void adjSpeed(double adj) {
		speed += adj;
		if (speed < 1) move = 1;
		else move = speed;
	}
	
	public void adjInv(int adj) {
		inventory.resize(inventory.getSize() + adj);
	}
	
	public void adjArmor(int adj) {
		this.arm += adj;
	}
	
	public void adjMaxhp(int adj) {
		this.healthMax += adj;
		if (health > healthMax) health = healthMax;
	}
	
	public void adjMaxsp(int adj) {
		this.spiritMax += adj;
		if (spirit > spiritMax) spirit = spiritMax;
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
	
	

}
